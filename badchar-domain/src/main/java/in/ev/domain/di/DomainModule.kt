package `in`.ev.domain.di

import `in`.ev.domain.usecase.GetCharacterUseCaseImpl
import `in`.ev.domain.usecase.GetCharacterUsecase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    abstract fun bindGetCharUseCase(useCaseImpl: GetCharacterUseCaseImpl):
            GetCharacterUsecase

}