package sk.kapitan.NBAstats.data

import sk.kapitan.NBAstats.data.models.GameData
import sk.kapitan.NBAstats.data.models.TeamData

class NBARepository(private val nbaService: NBAService) {
    suspend fun getTeams(): List<TeamData> {
        return nbaService.getTeams().body()?.data ?: emptyList()
    }

    suspend fun getGames(teamId: Int ): List<GameData>{
        return nbaService.getGames(teamId).body()?.data ?: emptyList()
    }
}