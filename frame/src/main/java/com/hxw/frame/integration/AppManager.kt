package com.hxw.frame.integration

import android.app.Activity
import android.content.Intent
import com.hxw.frame.base.delegate.AppDelegate
import java.util.*

/**
 * APP的活动管理类
 * @author hxw
 * @date 2017/8/29
 */
object AppManager {
    //当前在前台的activity
    private var mCurrentActivity: Activity? = null
    //管理所有activity
    private val activityStack: Stack<Activity> = Stack()

    /**
     * 将在前台的activity保存
     *
     * @param currentActivity
     */
    fun setCurrentActivity(currentActivity: Activity?) {
        mCurrentActivity = currentActivity
    }


    /**
     * 获得当前在前台的activity
     */
    fun getCurrentActivity() = mCurrentActivity

    /**
     * 获取位于栈顶的 activity,此方法不保证获取到的 acticity 正处于可见状态,即使 App 进入后台也会返回当前栈顶的 activity
     * 因此基本不会出现 null 的情况,比较适合大部分的使用场景,如 startActivity,Glide 加载图片
     */
    fun getTopActivity() = if (!activityStack.empty()) {
        activityStack.lastElement()
    } else {
        null
    }

    /**
     * 添加Activity到集合
     */
    fun addActivity(activity: Activity?) {
        if (activity != null) {
            synchronized(AppManager::class) {
                activityStack.add(activity)
            }
        }
    }

    /**
     * 删除集合里的指定activity
     */
    fun removeActivity(activity: Activity?) {
        if (activity != null) {
            synchronized(AppManager::class) {
                activityStack.remove(activity)
            }
        }
    }

    /**
     * 关闭指定activity
     */
    fun killActivity(activityClass: Class<out Activity>) {
        activityStack.forEach {
            if (it.javaClass == activityClass) {
                it.finish()
            }
        }
    }

    /**
     * 让在前台的activity,打开下一个activity
     */
    fun startActivity(intent: Intent) {
        if (getTopActivity() == null) {
            //如果没有前台的activity就使用new_task模式启动activity
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            AppDelegate.instance.startActivity(intent)

        } else {
            getTopActivity()!!.startActivity(intent)
        }
    }

    /**
     * 关闭所有 activity
     */
    fun killAll() {
        activityStack.forEach {
            activityStack.remove(it)
            it.finish()
        }
    }

    /**
     * 关闭所有 activity,排除指定的 activity
     *
     * @param excludeActivityClasses activity class
     */
    fun killAll(vararg excludeActivityClasses: Class<*>) {
        val excludeList: List<Class<*>> = (excludeActivityClasses).asList()
        activityStack.forEach {
            if (!excludeList.contains(it.javaClass)) {
                activityStack.remove(it)
                it.finish()
            }
        }
    }

    /**
     * 退出应用程序
     */
    fun exitApp() {
        try {
            killAll()
            activityStack.clear()
            //退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
            System.exit(0)
            //从操作系统中结束掉当前程序的进程
            android.os.Process.killProcess(android.os.Process.myPid())
        } catch (e: Exception) {
            e.printStackTrace()
            System.exit(-1)
        }
    }
}