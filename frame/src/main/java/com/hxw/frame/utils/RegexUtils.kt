package com.hxw.frame.utils

import com.hxw.frame.utils.constant.RegexConstants
import java.util.*
import java.util.regex.Pattern

/**
 * 正则相关工具类
 * @author hxw
 * @date 2017/11/6
 */
object RegexUtils {
}

/**
 * 验证手机号（简单）
 *
 * @param input 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun CharSequence.isMobileSimple(): Boolean =
        isMatch(RegexConstants.REGEX_MOBILE_SIMPLE)

/**
 * 验证手机号（精确）
 *
 * @param input 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun CharSequence.isMobileExact(): Boolean =
        isMatch(RegexConstants.REGEX_MOBILE_EXACT)

/**
 * 验证电话号码
 *
 * @param input 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun CharSequence.isTel(): Boolean = isMatch(RegexConstants.REGEX_TEL)

/**
 * 验证身份证号码15位
 *
 * @param input 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun CharSequence.isIDCard15(): Boolean = isMatch(RegexConstants.REGEX_ID_CARD15)

/**
 * 验证身份证号码18位
 *
 * @param input 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun CharSequence.isIDCard18(): Boolean = isMatch(RegexConstants.REGEX_ID_CARD18)

/**
 * 验证邮箱
 *
 * @param input 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun CharSequence.isEmail(): Boolean = isMatch(RegexConstants.REGEX_EMAIL)

/**
 * 验证URL
 *
 * @param input 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun CharSequence.isURL(): Boolean = isMatch(RegexConstants.REGEX_URL)

/**
 * 验证汉字
 *
 * @param input 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun CharSequence.isZh(): Boolean = isMatch(RegexConstants.REGEX_ZH)

/**
 * 验证用户名
 *
 * 取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位
 *
 * @param input 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun CharSequence.isUsername(): Boolean = isMatch(RegexConstants.REGEX_USERNAME)

/**
 * 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
 *
 * @param input 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun CharSequence.isDate(): Boolean = isMatch(RegexConstants.REGEX_DATE)

/**
 * 验证IP地址
 *
 * @param input 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun CharSequence.isIP(): Boolean = isMatch(RegexConstants.REGEX_IP)

/**
 * 验证车牌
 *
 * @param input 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun CharSequence.isLicense(): Boolean = isMatch(RegexConstants.REGEX_LICENSE)

/**
 * 判断是否匹配正则
 *
 * @param regex 正则表达式
 * @param input 要匹配的字符串
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun CharSequence.isMatch(regex: String): Boolean =
        this.isNotEmpty() && Pattern.matches(regex, this)

/**
 * 获取正则匹配的部分
 *
 * @param regex 正则表达式
 * @param input 要匹配的字符串
 * @return 正则匹配的部分
 */
fun CharSequence.getMatches(regex: String): List<String> {
    val matches = ArrayList<String>()
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    while (matcher.find()) {
        matches.add(matcher.group())
    }
    return matches
}

/**
 * 获取正则匹配分组
 *
 * @param input 要分组的字符串
 * @param regex 正则表达式
 * @return 正则匹配分组
 */
fun String.getSplits(regex: String): Array<String>? =
        this.split(regex.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

/**
 * 替换正则匹配的第一部分
 *
 * @param input       要替换的字符串
 * @param regex       正则表达式
 * @param replacement 代替者
 * @return 替换正则匹配的第一部分
 */
fun String.getReplaceFirst(regex: String, replacement: String): String =
        Pattern.compile(regex).matcher(this).replaceFirst(replacement)

/**
 * 替换所有正则匹配的部分
 *
 * @param input       要替换的字符串
 * @param regex       正则表达式
 * @param replacement 代替者
 * @return 替换所有正则匹配的部分
 */
fun String.getReplaceAll(regex: String, replacement: String): String =
        Pattern.compile(regex).matcher(this).replaceAll(replacement)

