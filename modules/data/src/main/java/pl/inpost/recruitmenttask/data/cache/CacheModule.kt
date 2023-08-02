package pl.inpost.recruitmenttask.data.cache

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CacheModule {
    @Singleton
    @Provides
    fun provideLocalRoomDatabase(@ApplicationContext context: Context): LocalRoomDatabase =
        LocalRoomDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideShipmentDao(localRoomDatabase: LocalRoomDatabase) =
        localRoomDatabase.shipmentDao()

}
