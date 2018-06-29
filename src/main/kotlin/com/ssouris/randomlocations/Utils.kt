package com.ssouris.randomlocations

import java.util.Properties
import java.util.concurrent.ThreadLocalRandom

fun ThreadLocalRandom.nextCoordinate() = Coordinates(this.nextDouble(-90.0, 90.0), this.nextDouble(-90.0, 90.0))

fun Properties.loadFrom(properties: Map<String, Any>) = properties.forEach { this[it.key] = it.value }
