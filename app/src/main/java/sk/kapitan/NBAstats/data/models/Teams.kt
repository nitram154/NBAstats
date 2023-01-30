package sk.kapitan.NBAstats.data.models


import com.google.gson.annotations.SerializedName

data class Teams(
    @SerializedName("data")
    val `data`: List<DataX>,
    @SerializedName("meta")
    val meta: MetaX
)