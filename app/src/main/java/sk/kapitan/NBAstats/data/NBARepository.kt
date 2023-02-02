package sk.kapitan.NBAstats.data

import sk.kapitan.NBAstats.data.models.GameData
import sk.kapitan.NBAstats.data.models.TeamData
import sk.kapitan.NBAstats.dateToString
import java.util.Date

class NBARepository(private val nbaService: NBAService) {
    private val teams = mutableListOf<TeamData>()
    suspend fun getTeams(): List<TeamData> {
        return nbaService.getTeams().body()?.data?.also {
            teams.clear()
            teams.addAll(it)
        } ?: emptyList()
    }

    suspend fun getGames(teamId: Int, startDate : Date, endDate : Date): List<GameData>{

        val startString = dateToString(startDate) ?: return emptyList()
        val endString = dateToString(endDate) ?: return emptyList()

        return nbaService.getGames(teamId,startString, endString).body()?.data ?: emptyList()
    }

    fun getTeamById(teamId: Int): TeamData {
        return teams.find { it.id == teamId } !!
    }
}