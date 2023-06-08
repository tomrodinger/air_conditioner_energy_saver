package com.app.imotion.di

import com.app.imotion.repo.device.DevicesRepo
import com.app.imotion.repo.device.DevicesRepoImpl
import com.app.imotion.repo.ircode.IrCodesRepo
import com.app.imotion.repo.ircode.IrCodesRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by hani.fakhouri on 2023-06-08.
 */

@Module
@InstallIn(ViewModelComponent::class)
abstract class ReposModule {

    @Binds
    abstract fun bindDevicesRepo(impl: DevicesRepoImpl) : DevicesRepo

    @Binds
    abstract fun bindIrCodesRepo(impl: IrCodesRepoImpl) : IrCodesRepo

}