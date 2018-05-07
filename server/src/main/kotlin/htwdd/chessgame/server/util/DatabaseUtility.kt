package htwdd.chessgame.server.util

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.table.TableUtils
import htwdd.chessgame.server.model.Draw
import htwdd.chessgame.server.model.Field
import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.Player

class DatabaseUtility {
    companion object {
        private var connection: JdbcConnectionSource? = null
        var playerDao: Dao<Player, Int>? = null
            get() {
                if (field == null) connect()
                return field
            }
        var matchDao: Dao<Match, Int>? = null
            get() {
                if (field == null) connect()
                return field
            }
        var drawDao: Dao<Draw, Int>? = null
            get() {
                if (field == null) connect()
                return field
            }
        var fieldDao: Dao<Field, Int>? = null
            get() {
                if (field == null) connect()
                return field
            }

        private fun connect() {
            if (connection != null) return
            connection = JdbcConnectionSource("jdbc:sqlite:chessgame.db")
            createTables()
            createDaos()
        }

        private fun createTables() {
            TableUtils.createTableIfNotExists(connection, Player::class.java)
            TableUtils.createTableIfNotExists(connection, Match::class.java)
            TableUtils.createTableIfNotExists(connection, Draw::class.java)
            TableUtils.createTableIfNotExists(connection, Field::class.java)
        }

        private fun createDaos() {
            playerDao = DaoManager.createDao<Dao<Player, Int>, Player>(connection, Player::class.java)
            matchDao = DaoManager.createDao<Dao<Match, Int>, Match>(connection, Match::class.java)
            drawDao = DaoManager.createDao<Dao<Draw, Int>, Draw>(connection, Draw::class.java)
            fieldDao = DaoManager.createDao<Dao<Field, Int>, Field>(connection, Field::class.java)
        }
    }
}