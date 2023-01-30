package sk.kapitan.NBAstats.data.models


import com.google.gson.annotations.SerializedName

data class Games(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("meta")
    val meta: Meta
)