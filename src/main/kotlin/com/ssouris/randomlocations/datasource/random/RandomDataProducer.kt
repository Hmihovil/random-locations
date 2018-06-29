package com.ssouris.randomlocations.datasource.random

import java.time.Duration

interface RandomDataProducer : Runnable {

    fun getInterval(): Duration

}
