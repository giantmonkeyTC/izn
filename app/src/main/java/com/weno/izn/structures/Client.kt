package com.weno.izn.structures

import com.weno.izn.network.NetworkClient

object Client {
    //val eventBus: RxBus = RxBus()>
    val api : NetworkClient = NetworkClient()
}