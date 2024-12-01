package com.example.project.ui.di

import com.example.project.ui.network.SpotifyApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object SpotifyAPIModule {

    @Provides
    @Singleton
    fun provideSpotifyRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer YOUR_ACCESS_TOKEN")
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.spotify.com/")
            .addConverterFactory(
                Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType())
            )
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideSpotifyAPI(retrofit: Retrofit): SpotifyApiService {
        return retrofit.create(SpotifyApiService::class.java)
    }
}