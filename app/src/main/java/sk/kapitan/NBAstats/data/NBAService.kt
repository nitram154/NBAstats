package sk.kapitan.NBAstats.data

import retrofit2.Response
import retrofit2.http.*
import sk.kapitan.NBAstats.data.models.Games
import sk.kapitan.NBAstats.data.models.Teams

interface NBAService {

    @Headers("Content-Type: application/json")
    @GET("api/v1/teams")
    suspend fun getTeams(): Response<Teams>

    @Headers("Content-Type: application/json")
    @GET("api/v1/games")
    suspend fun getGames( @Query("team_ids[]") teamID : Int, @Query("start_date") startDate : String, @Query("end_date") endDate : String): Response<Games>

}