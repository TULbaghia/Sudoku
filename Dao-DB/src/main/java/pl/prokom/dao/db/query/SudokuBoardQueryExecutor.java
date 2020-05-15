package pl.prokom.dao.db.query;

import pl.prokom.dao.db.exception.JdbcDaoQueryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SudokuBoardQueryExecutor {

    /**
     * PreparedStatement instance with each database query/ies.
     */
    private static PreparedStatement dbStatement;

    /**
     * Database connection class.
     * Configuration method in JdbcSudokuBoardDao.
     */
    private static Connection dbConnection;

    public static void createNewTable() throws JdbcDaoQueryException {
        try {
            dbStatement = dbConnection
                    .prepareStatement("CREATE TABLE IF NOT EXISTS SavedSudokuBoards(name varchar(25), fields varchar(81), serializedObject bytea)");
            executeAndCloseStatement();
        } catch (SQLException e) {
            throw new JdbcDaoQueryException("Database query error while: Creating new table.", e);
        }
    }

    public static void insertSudokuBoard(String tableName, String fields, byte[] sudokuBoard) throws JdbcDaoQueryException {
        try {
            dbStatement = dbConnection
                    .prepareStatement("INSERT INTO SavedSudokuBoards VALUES (?, ?, ?);");
            dbStatement.setString(1, tableName);
            dbStatement.setString(2, fields);
            dbStatement.setBytes(3, sudokuBoard);
            executeAndCloseStatement();
        } catch (SQLException e) {
            throw new JdbcDaoQueryException("Database query error while: Inserting data.", e);
        }

    }

    public static byte[] selectSudokuBoard(String sudokuBoard) throws JdbcDaoQueryException {
        try {
            dbStatement = dbConnection
                    .prepareStatement("SELECT * FROM SavedSudokuBoards WHERE name = ?;");
            dbStatement.setString(1, sudokuBoard);
            byte[] obtainedBoard = getSerializedObject();
            dbStatement.close();
            return obtainedBoard;
        } catch (SQLException e) {
            throw new JdbcDaoQueryException("Database query error while: Selecting chosen SudokuBoard blob state from database.", e);
        }
    }

    private static byte[] getSerializedObject() throws SQLException {
        byte[] obtainedBoard = null;
        java.sql.ResultSet obtainedRecord = dbStatement.executeQuery();
        while(obtainedRecord.next()){
            obtainedBoard = obtainedRecord.getBytes("serializedObject");
        }
        return obtainedBoard;
    }

    private static void executeAndCloseStatement() throws SQLException {
        dbStatement.execute();
        dbStatement.close();
    }

    public static void setDbConnection(Connection dbConnection) {
        SudokuBoardQueryExecutor.dbConnection = dbConnection;
    }

    public static Connection getDbConnection() {
        return dbConnection;
    }
}
