package sk.kapitan.NBAstats.data.models


import com.google.gson.annotations.SerializedName

data class Teams(
    @SerializedName("data")
    val `data`: List<TeamData>,
    @SerializedName("meta")
    val meta: MetaX
)