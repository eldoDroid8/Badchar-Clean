package `in`.ev.data.di.module
import `in`.ev.data.BuildConfig
import `in`.ev.data.model.ErrorEntity
import `in`.ev.data.network.api.BadCharacterApi
import `in`.ev.data.utils.NetworkConstants
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val httpClient = OkHttpClient().newBuilder()
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return httpClient.addInterceptor(interceptor).build()
        }
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient, converterFactory:
        MoshiConverterFactory,  url: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshiErrorAdapter(moshi: Moshi): JsonAdapter<ErrorEntity> {
        return moshi.adapter(ErrorEntity::class.java)
    }

    @Provides
    @Singleton
    fun provideBadCharacterApiService(retrofit: Retrofit): BadCharacterApi {
        return retrofit.create(BadCharacterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUrl(): String {
        //we can move the url to buildConfig
        return NetworkConstants.ENDPOINT
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory{
        return  MoshiConverterFactory.create(moshi)
    }

}