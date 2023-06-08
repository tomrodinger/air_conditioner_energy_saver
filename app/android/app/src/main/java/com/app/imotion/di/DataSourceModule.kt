package com.app.imotion.di

import com.app.imotion.repo.device.DevicesDataSource
import com.app.imotion.repo.device.DevicesDataSourceImpl
import com.app.imotion.repo.ircode.IrCodesDataSource
import com.app.imotion.repo.ircode.IrCodesDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by hani.fakhouri on 2023-06-08.
 */

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindDevicesDataSource(impl: DevicesDataSourceImpl): DevicesDataSource

    @Binds
    abstract fun bindDIrCodesDataSource(impl: IrCodesDataSourceImpl): IrCodesDataSource

}