package `in`.ev.data.di.module

import `in`.ev.data.datasource.BadCharRemoteDataSource
import `in`.ev.data.model.ErrorEntity
import `in`.ev.data.network.api.BadCharacterApi
import `in`.ev.data.repository.BadcharRepositoryImpl
import `in`.ev.domain.repository.BadCharacterRepository
import `in`.ev.domain.usecase.GetCharacterUseCaseImpl
import `in`.ev.domain.usecase.GetCharacterUsecase
import android.app.Application
import com.squareup.moshi.JsonAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
abstract class RepoModule {
    @Binds
    @Singleton
    abstract fun bindBadCharReposiotry(repositoryImpl: BadcharRepositoryImpl):
            BadCharacterRepository
}

@Module
@InstallIn(ApplicationComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideDataSource(
        badCharacterApi: BadCharacterApi, retrofit: Retrofit, moshiAdapter:
        JsonAdapter<ErrorEntity>
    ):
            BadCharRemoteDataSource {
        return BadCharRemoteDataSource(badCharacterApi, retrofit, moshiAdapter)
    }

}