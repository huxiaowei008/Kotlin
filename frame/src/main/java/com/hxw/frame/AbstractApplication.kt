package com.qingmei2.kodeinsample.test

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @author hxw on 2018/7/13.
 *
 */
abstract class AbstractApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidModule(this@AbstractApplication))

        bind<Config>() with singleton {
            getConfig()
        }

        bind<Gson>() with singleton {
            val builder = GsonBuilder()
                    .serializeNulls()
            instance<Config>().configGson(instance<Application>(), builder)

            builder.create()
        }

        bind<OkHttpClient>() with singleton {
            val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { })

            logging.level = HttpLoggingInterceptor.Level.BODY
            val builder = OkHttpClient.Builder()
                    .addInterceptor(logging)
            instance<Config>().configOkHttp(instance<Application>(), builder)
            builder.build()
        }
        bind<Retrofit>() with singleton {
            val builder = Retrofit.Builder()
                    .client(instance())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(instance()))
            instance<Config>().configRetrofit(instance<Application>(), builder)
            builder.build()
        }

    }

    abstract fun getConfig(): Config
}