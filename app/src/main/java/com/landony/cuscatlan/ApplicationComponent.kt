package com.landony.cuscatlan

import com.landony.data.di.NetworkModule
import com.landony.data.di.RepositoryModule
import com.landony.data.di.UseCaseModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, UseCaseModule::class, RepositoryModule::class])
interface ApplicationComponent {
}