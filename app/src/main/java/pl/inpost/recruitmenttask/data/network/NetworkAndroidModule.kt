package pl.inpost.recruitmenttask.data.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.network.api.MockShipmentApi
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi

@InstallIn(SingletonComponent::class)
@Module
interface NetworkAndroidModule {

    @Binds
    fun bindShipmentApi(impl: MockShipmentApi): ShipmentApi
}
