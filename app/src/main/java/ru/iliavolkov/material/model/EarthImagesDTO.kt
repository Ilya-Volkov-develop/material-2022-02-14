package ru.iliavolkov.material.model

import com.google.gson.annotations.SerializedName


data class Earth (
    val identifier: String,
    val caption: String,
    val image: String,
    val version: String,

    @SerializedName("centroid_coordinates")
    val centroidCoordinates: CentroidCoordinates,

    @SerializedName("dscovr_j2000_position")
    val dscovrJ2000Position: J2000Position,

    @SerializedName("lunar_j2000_position")
    val lunarJ2000Position: J2000Position,

    @SerializedName("sun_j2000_position")
    val sunJ2000Position: J2000Position,

    @SerializedName("attitude_quaternions")
    val attitudeQuaternions: AttitudeQuaternions,

    val date: String,
    val coords: Coords
)

data class AttitudeQuaternions (
    val q0: Double,
    val q1: Double,
    val q2: Double,
    val q3: Double
)

data class CentroidCoordinates (
    val lat: Double,
    val lon: Double
)

data class Coords (
    @SerializedName("centroid_coordinates")
    val centroidCoordinates: CentroidCoordinates,

    @SerializedName("dscovr_j2000_position")
    val dscovrJ2000Position: J2000Position,

    @SerializedName("lunar_j2000_position")
    val lunarJ2000Position: J2000Position,

    @SerializedName("sun_j2000_position")
    val sunJ2000Position: J2000Position,

    @SerializedName("attitude_quaternions")
    val attitudeQuaternions: AttitudeQuaternions
)

data class J2000Position (
    val x: Double,
    val y: Double,
    val z: Double
)
