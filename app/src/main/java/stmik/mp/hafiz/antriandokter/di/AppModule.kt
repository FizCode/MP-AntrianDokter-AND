package stmik.mp.hafiz.antriandokter.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import stmik.mp.hafiz.antriandokter.data.api.auth.AuthAPI
import stmik.mp.hafiz.antriandokter.data.api.queue.QueueAPI
import stmik.mp.hafiz.antriandokter.data.local.auth.UserDAO
import stmik.mp.hafiz.antriandokter.datastore.AuthDataStoreManager
import stmik.mp.hafiz.antriandokter.repository.AuthRepository
import stmik.mp.hafiz.antriandokter.repository.QueueRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideAuthRepository(
        authDataStoreManager: AuthDataStoreManager,
        api: AuthAPI,
        dao: UserDAO
    ): AuthRepository {
        return AuthRepository(
            authDataStore = authDataStoreManager,
            api = api,
            dao = dao
        )
    }

    @Singleton
    @Provides
    fun provideQueueRepository(
        api: QueueAPI
    ): QueueRepository {
        return QueueRepository(
            api = api
        )
    }

}