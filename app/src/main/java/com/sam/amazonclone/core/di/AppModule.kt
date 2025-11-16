package com.sam.amazonclone.core.di
import com.sam.amazonclone.core.data.CartRepository
import com.sam.amazonclone.core.data.FakeProductRepository
import com.sam.amazonclone.core.data.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Defines a Hilt Dependency Injection (DI) module that tells your app how to create and share important objects (like repositories)
// @Module: marks this object as a Hilt module — a place where you define how to provide dependencies.
// @InstallIn(SingletonComponent::class): means all dependencies defined here will live as long as the app (singleton scope).
// So anywhere you inject ProductRepository, Hilt will automatically give you this FakeProductRepository object.
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // @Provides: Defines a function that returns an instance Hilt can use
    // @Singleton: ensures only one shared instance exists across the whole app
    @Provides @Singleton
    fun provideProductRepository(): ProductRepository = FakeProductRepository()

    @Provides @Singleton
    fun provideCartRepository(): CartRepository = CartRepository()
}
