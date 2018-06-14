package com.ssouris.randomlocations.model

import com.fasterxml.jackson.databind.JsonNode

data class Coordinates(var longitude: Double, var latitude: Double)

data class Flight(val origin: Coordinates, val destination: Coordinates) {

    companion object {
        fun from(jsonNode: JsonNode) = Flight(
                Coordinates(jsonNode.get("fromLatitude").asDouble(), jsonNode.get("fromLongitude").asDouble()),
                Coordinates(jsonNode.get("toLatitude").asDouble(), jsonNode.get("toLongitude").asDouble()))
    }

}

data class MobileCoordinates(val latitude: Double, val longitude: Double, val radius: Int = 5) {

    companion object {
        fun from(coordinates: Coordinates) = MobileCoordinates(coordinates.latitude, coordinates.longitude)


        fun from(jsonCoordinates: JsonNode) =
                MobileCoordinates(jsonCoordinates.get("latitude").asDouble(), jsonCoordinates.get("longitude").asDouble())

    }

}
