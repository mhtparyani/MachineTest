package com.turtlemint.mymachinetest.di

import android.app.Application
import androidx.room.Room
import com.turtlemint.mymachinetest.controller.service.IssuesService
import com.turtlemint.mymachinetest.data.IssuesListDatabase
import com.turtlemint.mymachinetest.util.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(AppConstants.SERVER_VAL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideCarListAPI(retrofit: Retrofit): IssuesService =
        retrofit.create(IssuesService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): IssuesListDatabase =
        Room.databaseBuilder(app, IssuesListDatabase::class.java, "issue_database")
            .build()
}