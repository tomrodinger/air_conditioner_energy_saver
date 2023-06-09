package com.app.imotion.di

import com.app.imotion.repo.device.DevicesRepo
import com.app.imotion.repo.device.DevicesRepoImpl
import com.app.imotion.repo.ircode.IrCodesRepo
import com.app.imotion.repo.ircode.IrCodesRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by hani.fakhouri on 2023-06-08.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class ReposModule {

    @Binds
    @Singleton
    abstract fun bindDevicesRepo(impl: DevicesRepoImpl) : DevicesRepo

    @Binds
    @Singleton
    abstract fun bindIrCodesRepo(impl: IrCodesRepoImpl) : IrCodesRepo

}