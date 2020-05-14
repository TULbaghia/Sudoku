package pl.prokom.dao.db.model;


import pl.prokom.dao.api.exception.DaoException;
import pl.prokom.dao.api.model.Dao;
import pl.prokom.dao.db.exception.JdbcDaoConnectionException;
import pl.prokom.dao.db.exception.JdbcDaoQueryException;
import pl.prokom.dao.db.query.SudokuBoardQueryExecutor;
import pl.prokom.model.board.SudokuBoard;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.sql.*;

/**
 * Class created to save instances of SudokuBoard class to database.
 */
public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    /**
     * Data required to establish connection with database.
     */
    private static final String URL = "jdbc:postgresql://localhost/SudokuBoard1";
    private static final String USER = "postgres";
    private static final String PASSWORD = "12345";

    /**
     * Each saved instance of SudokuBoard class name.
     */
    private String sudokuBoardName;

    /**
     * Database connection class.
     * Method used while connection configuration: {@link #establishConnection()}.
     */

    /**
     * JdbcSudokuBoardDao constructor.
     * Initial connection with database is being established with each use of this constructor.
     */
    public JdbcSudokuBoardDao(String sudokuBoardName) {
        this.sudokuBoardName = sudokuBoardName;
    }
    /**
     * DriverManager connection creating.
     * @throws JdbcDaoConnectionException
     */
    private void establishConnection() throws JdbcDaoConnectionException {

        try {
            SudokuBoardQueryExecutor.setDbConnection(DriverManager.getConnection(URL, USER, PASSWORD));
        } catch (SQLException e) {
            throw new JdbcDaoConnectionException("Cannot establish a connection with database.", e);
        }
    }
    /**
     * Closing currently using connection.
     * @throws JdbcDaoConnectionException
     */
    private void closeConnection() throws JdbcDaoConnectionException {
        try {
            SudokuBoardQueryExecutor.getDbConnection().close();
        } catch (SQLException e) {
            throw new JdbcDaoConnectionException("Error while closing connection.", e);
        }
    }

    public SudokuBoard read() throws DaoException {
        return null;
    }

    /**
     * Writing SudokuBoard instance state to database.
     * @param sudokuBoard - currently processed SudokuBoard instance.
     * @throws JdbcDaoQueryException
     * @throws JdbcDaoConnectionException
     */
    public void write(SudokuBoard sudokuBoard) throws JdbcDaoQueryException, JdbcDaoConnectionException {
        StringBuilder concatFields = new StringBuilder();
        for (int x = 0; x < sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize(); ++x){
            concatFields.append(sudokuBoard.get(x / 9, x % 9));
        }

        ByteArrayOutputStream sudokuBoardBlob;
        try (ByteArrayOutputStream byteOut =  new ByteArrayOutputStream();
             ObjectOutputStream objOut = new ObjectOutputStream(byteOut)) {
            objOut.writeObject(sudokuBoard);
            sudokuBoardBlob = byteOut;
        } catch (IOException e) {
            throw new IllegalCallerException("Illegal file access.", e);
        }

        establishConnection();
        SudokuBoardQueryExecutor.createNewTable();
        SudokuBoardQueryExecutor.insertSudokuBoard(sudokuBoardName, concatFields.toString(), sudokuBoardBlob.toByteArray());
        closeConnection();
    }

    @Override
    public void close() throws JdbcDaoConnectionException {
        closeConnection();
    }

}
