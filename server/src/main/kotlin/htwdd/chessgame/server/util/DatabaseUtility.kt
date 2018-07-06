package htwdd.chessgame.server.util

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.table.TableUtils
import htwdd.chessgame.server.model.Draw
import htwdd.chessgame.server.model.Field
import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.Player
import htwdd.chessgame.server.util.DatabaseUtility.Companion.connection
import htwdd.chessgame.server.util.DatabaseUtility.Companion.drawDao
import htwdd.chessgame.server.util.DatabaseUtility.Companion.fieldDao
import htwdd.chessgame.server.util.DatabaseUtility.Companion.matchDao
import htwdd.chessgame.server.util.DatabaseUtility.Companion.playerDao

/**
 * Utility class to handle database interactions
 *
 * @author Felix Dimmel
 *
 * @property connection Holds the connection to the database
 * @property playerDao Database access object for players
 * @property matchDao Database access object for matches
 * @property drawDao Database access object for draws
 * @property fieldDao Database access object for fields
 *
 * @since 1.0.0
 */
class DatabaseUtility {
    /**
     * Static DatabaseUtility object
     */
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

        /**
         * Initialize the database connection
         *
         * @author Felix Dimmel
         *
         * @since 1.0.0
         */
        private fun connect() {
            if (connection != null) return
            connection = JdbcConnectionSource("jdbc:sqlite:chessgame.db")
            createTables()
            createDaos()
        }

        /**
         * Creates all necessary table if these are not exist
         *
         * @author Felix Dimmel
         *
         * @since 1.0.0
         */
        private fun createTables() {
            TableUtils.createTableIfNotExists(connection, Player::class.java)
            TableUtils.createTableIfNotExists(connection, Match::class.java)
            TableUtils.createTableIfNotExists(connection, Draw::class.java)
            TableUtils.createTableIfNotExists(connection, Field::class.java)
        }

        /**
         * Initialize all necessary database access objects
         *
         * @author Felix Dimmel
         *
         * @since 1.0.0
         */
        private fun createDaos() {
            playerDao = DaoManager.createDao<Dao<Player, Int>, Player>(connection, Player::class.java)
            matchDao = DaoManager.createDao<Dao<Match, Int>, Match>(connection, Match::class.java)
            drawDao = DaoManager.createDao<Dao<Draw, Int>, Draw>(connection, Draw::class.java)
            fieldDao = DaoManager.createDao<Dao<Field, Int>, Field>(connection, Field::class.java)
        }
    }
}