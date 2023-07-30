package pl.inpost.recruitmenttask.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.domain.repositories.ShipmentRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideShipmentRepository(impl: ShipmentRepositoryImpl): ShipmentRepository
}
