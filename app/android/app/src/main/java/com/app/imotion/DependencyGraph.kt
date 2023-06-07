package com.app.imotion

import com.app.imotion.repo.device.DevicesDataSourceImpl
import com.app.imotion.repo.device.DevicesRepo
import com.app.imotion.repo.device.DevicesRepoImpl
import com.app.imotion.repo.ircode.IrCodesDataSourceImpl
import com.app.imotion.repo.ircode.IrCodesRepo
import com.app.imotion.repo.ircode.IrCodesRepoImpl

/**
 * Created by hani.fakhouri on 2023-06-08.
 */
object DependencyGraph {

    private val dataCoroutineScope = DataCoroutineScope()

    val devicesRepo: DevicesRepo = DevicesRepoImpl(DevicesDataSourceImpl())
    val irCodesRepo: IrCodesRepo = IrCodesRepoImpl(IrCodesDataSourceImpl(dataCoroutineScope))
}