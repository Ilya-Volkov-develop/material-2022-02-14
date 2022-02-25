package ru.iliavolkov.material.model

import com.google.gson.annotations.SerializedName

data class AsteroidDTO(
        @SerializedName( "element_count")
        val elementCount: Long,

        @SerializedName("near_earth_objects")
        val nearEarthObjects: Map<String, List<NearEarthObject>>
)

data class NearEarthObject (
        val id: String,

        @SerializedName("neo_reference_id")
        val neoReferenceID: String,

        val name: String,

        @SerializedName("nasa_jpl_url")
        val nasaJplURL: String,

        @SerializedName("absolute_magnitude_h")
        val absoluteMagnitudeH: Double,

        @SerializedName("estimated_diameter")
        val estimatedDiameter: EstimatedDiameter,

        @SerializedName("is_potentially_hazardous_asteroid")
        val isPotentiallyHazardousAsteroid: Boolean,

        @SerializedName("close_approach_data")
        val closeApproachData: List<CloseApproachDatum>,

        @SerializedName("is_sentry_object")
        val isSentryObject: Boolean
)

data class CloseApproachDatum (
        @SerializedName("close_approach_date")
        val closeApproachDate: String,

        @SerializedName("close_approach_date_full")
        val closeApproachDateFull: String,

        @SerializedName("epoch_date_close_approach")
        val epochDateCloseApproach: Long,

        @SerializedName("relative_velocity")
        val relativeVelocity: RelativeVelocity,

        @SerializedName("miss_distance")
        val missDistance: MissDistance,

        @SerializedName("orbiting_body")
        val orbitingBody: OrbitingBody
)

data class MissDistance (
        val astronomical: String,
        val lunar: String,
        val kilometers: String,
        val miles: String
)

enum class OrbitingBody(val value: String) {
    Earth("Earth");

    companion object {
        fun fromValue(value: String): OrbitingBody = when (value) {
            "Earth" -> Earth
            else    -> throw IllegalArgumentException()
        }
    }
}

data class RelativeVelocity (
        @SerializedName("kilometers_per_second")
        val kilometersPerSecond: String,

        @SerializedName("kilometers_per_hour")
        val kilometersPerHour: String,

        @SerializedName("miles_per_hour")
        val milesPerHour: String
)

data class EstimatedDiameter (
        val kilometers: Feet,
        val meters: Feet,
        val miles: Feet,
        val feet: Feet
)

data class Feet (
        @SerializedName("estimated_diameter_min")
        val estimatedDiameterMin: Double,

        @SerializedName("estimated_diameter_max")
        val estimatedDiameterMax: Double
)