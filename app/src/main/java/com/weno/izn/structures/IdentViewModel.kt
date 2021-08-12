package com.weno.izn.structures

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class IdentViewModel : ViewModel() {
    val counterNumLD = MutableLiveData<Int>()
 /*   fun setupEvent() {
        counterNumLD.value = 0
        Client.eventBus.observeEventOnUi<CounterAddEvent>().subscribe(Consumer {
            counterNumLD.value = counterNumLD.value?.plus(it.num)
        })
        Client.eventBus.observeEventOnUi<CounterResetEvent>().subscribe(Consumer {
            if (it.reset)
                counterNumLD.value = 0
        })

    }*/
}