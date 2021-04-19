package com.borja.currency.data.di

import android.content.Context
import androidx.room.Room
import com.borja.currency.data.room.AppDataBase
import com.borja.currency.data.room.CurrencyRoomDataSource
import com.borja.currency.data.room.RatesDao
import com.borja.currency.data.room.RoomDataSource
import com.borja.currency.data.server.ApiResponseCallAdapterFactory
import com.borja.currency.data.server.CurrencyService
import com.borja.currency.data.server.source.CurrencyServerDataSource
import com.borja.currency.data.server.source.RemoteDataSource
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation().create()

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        HttpLoggingInterceptor().run {
            level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        }


    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson) =
        Retrofit.Builder().baseUrl("https://api.exchangerate.host/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .build()

    @Provides
    fun provideCurrencyService(retrofit: Retrofit): CurrencyService =
        retrofit.run {
            create(CurrencyService::class.java)
        }

    @Provides
    fun provideRatesDao(appDatabase: AppDataBase): RatesDao {
        return appDatabase.ratesDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "db-currency"
        ).fallbackToDestructiveMigration().build()

    }
}

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindRemoteDataSource(currencyServerDataSource: CurrencyServerDataSource) : RemoteDataSource

    @Binds
    abstract fun bindRoomDataSource(currencyRoomDataSource: CurrencyRoomDataSource) : RoomDataSource

}