package pl.prokom.dao.db.query;

import pl.prokom.dao.db.exception.JdbcDaoQueryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryExecutor {

    private static PreparedStatement dbStatement;

    public static void createNewTable(Connection dbConnection) throws JdbcDaoQueryException {
        try {
            dbStatement = dbConnection
                    .prepareStatement("DROP TABLE IF EXISTS SavedSudokuBoards CASCADE;" +
                            "CREATE TABLE SavedSudokuBoards(name varchar(25), fields varchar(81))");
            dbStatement.execute();
            dbStatement.close();
        } catch (SQLException e) {
            throw new JdbcDaoQueryException("Error while creating new table.", e);
        }
    }

    public static void insertSudokuBoard(Connection dbConnection, String tableName, String fields) throws JdbcDaoQueryException {
        try {
            dbStatement = dbConnection
                    .prepareStatement("INSERT INTO SavedSudokuBoards VALUES (?, ?)");
            dbStatement.setString(1, tableName);
            dbStatement.setString(2, fields);
            dbStatement.execute();
            dbStatement.close();
        } catch (SQLException e) {
            throw new JdbcDaoQueryException("Error while adding values as new record.", e);
        }
    }
}
