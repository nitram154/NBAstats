package sk.kapitan.NBAstats.data

import sk.kapitan.NBAstats.data.models.GameData
import sk.kapitan.NBAstats.data.models.TeamData

class NBARepository(private val nbaService: NBAService) {
    private val teams = mutableListOf<TeamData>()
    suspend fun getTeams(): List<TeamData> {
        return nbaService.getTeams().body()?.data?.also {
            teams.clear()
            teams.addAll(it)
        } ?: emptyList()
    }

    suspend fun getGames(teamId: Int ): List<GameData>{
        return nbaService.getGames(teamId).body()?.data ?: emptyList()
    }

    fun getTeamById(teamId: Int): TeamData {
        return teams.find { it.id == teamId } !!
    }
}