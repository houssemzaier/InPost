package pl.inpost.recruitmenttask.utils

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Qualifier

class CoroutineDispatcherProvider @Inject constructor(
    @MainDispatcher val main: CoroutineDispatcher,
    @IoDispatcher val io: CoroutineDispatcher,
    @ComputationDispatcher val computation: CoroutineDispatcher
)


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ComputationDispatcher
