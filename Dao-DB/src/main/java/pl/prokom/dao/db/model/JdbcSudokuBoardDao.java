package pl.prokom.dao.db.model;


import pl.prokom.dao.api.exception.DaoException;
import pl.prokom.dao.api.model.Dao;
import pl.prokom.dao.db.exception.JdbcDaoConnectionException;
import pl.prokom.model.board.SudokuBoard;
import java.sql.*;

/**
 * Class created to save instances of SudokuBoard class to database.
 */
public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    // TODO: now it's only a prototype of jdbc connection manager;
    private static final String URL = "jdbc:postgresql://localhost/SudokuBoard";
    private static final String USER = "postgres";
    private static final String PASSWORD = "12345";
    private Connection dbConnection;
    private Statement statement;
    private String sudokuBoardName;

    public JdbcSudokuBoardDao(String sudokuBoardName) throws JdbcDaoConnectionException {
        this.sudokuBoardName = sudokuBoardName;
        try {
            dbConnection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new JdbcDaoConnectionException("Cannot establish a connection with database.", e);
        }
    }

    public SudokuBoard read() throws DaoException {
        return null;
    }

    public void write(SudokuBoard sudokuBoard) throws DaoException {

    }

    @Override
    public void close() throws Exception {

    }

}
