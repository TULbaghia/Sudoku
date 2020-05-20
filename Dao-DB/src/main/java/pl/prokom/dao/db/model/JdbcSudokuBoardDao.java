package pl.prokom.dao.db.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import pl.prokom.dao.api.exception.DaoException;
import pl.prokom.dao.api.model.Dao;
import pl.prokom.dao.db.exception.JdbcCallerException;
import pl.prokom.dao.db.exception.JdbcDaoConnectionException;
import pl.prokom.dao.db.query.SudokuBoardQueryExecutor;
import pl.prokom.dao.file.exception.DaoClassException;
import pl.prokom.model.board.SudokuBoard;

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
     * JdbcSudokuBoardDao constructor.
     * Initial connection with database is being established with each use of this constructor.
     */
    public JdbcSudokuBoardDao(String sudokuBoardName) {
        this.sudokuBoardName = sudokuBoardName;
    }

    /**
     * DriverManager connection creating.
     *
     * @throws JdbcDaoConnectionException - when cannot establish connection with db
     */
    private void establishConnection() throws JdbcDaoConnectionException {
        try {
            SudokuBoardQueryExecutor.setDbConnection(
                    DriverManager.getConnection(URL, USER, PASSWORD));
        } catch (SQLException e) {
            throw new JdbcDaoConnectionException("JdbcDaoConnectionException.connectionError", e);
        }
    }

    /**
     * Closing currently using connection.
     *
     * @throws JdbcDaoConnectionException - when cannot close connection
     */
    private void closeConnection() throws JdbcDaoConnectionException {
        try {
            SudokuBoardQueryExecutor.getDbConnection().close();
        } catch (SQLException e) {
            throw new JdbcDaoConnectionException("JdbcDaoConnectionException.closingError", e);
        }
    }

    /**
     * Reading SudokuBoard instance state from database.
     *
     * @throws DaoException - while obtained class would not be an expected class type.
     */
    public SudokuBoard read() throws DaoException {
        SudokuBoard deserialized = null;
        establishConnection();
        deserialized = deserializeSudokuBoard();
        closeConnection();
        return deserialized;
    }

    /**
     * Writing SudokuBoard instance state to database.
     *
     * @param sudokuBoard - currently processed SudokuBoard instance.
     * @throws DaoException - when cannot handle write operation
     */
    public void write(SudokuBoard sudokuBoard) throws DaoException {
        StringBuilder concatFields = new StringBuilder();
        for (int x = 0; x < sudokuBoard.getBoardSize() * sudokuBoard.getBoardSize(); ++x) {
            concatFields.append(sudokuBoard.get(x / 9, x % 9));
        }
        establishConnection();
        SudokuBoardQueryExecutor.createNewTable();
        SudokuBoardQueryExecutor.insertSudokuBoard(sudokuBoardName, concatFields.toString(),
                serializeSudokuBoard(sudokuBoard));
        closeConnection();
    }

    /**
     * Storing SudokuBoard instance to form accessible in database structure.
     * (here, its transformed into byte array)
     *
     * @param sudokuBoard - currently processed SudokuBoard instance.
     */
    private byte[] serializeSudokuBoard(SudokuBoard sudokuBoard) throws JdbcCallerException {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutputStream objOut = new ObjectOutputStream(byteOut)) {
            objOut.writeObject(sudokuBoard);
            return byteOut.toByteArray();
        } catch (IOException e) {
            throw new JdbcCallerException("JdbcCallerException.illegalFile", e);
        }
    }

    /**
     * Transformation of SudokuBoard saved as byte array into SudokuBoard class.
     * throws JdbcDaoQueryException      - while there was a try of executing illegal query.
     * throws JdbcDaoConnectionException - while connection was not established.
     *
     * @throws DaoException - when can't deserialize data
     */
    private SudokuBoard deserializeSudokuBoard() throws DaoException {
        byte[] sudokuBoardBlob = SudokuBoardQueryExecutor.selectSudokuBoard(sudokuBoardName);
        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(sudokuBoardBlob);
             ObjectInputStream objIn = new ObjectInputStream(byteIn);) {
            return (SudokuBoard) objIn.readObject();
        } catch (IOException e) {
            throw new JdbcCallerException("JdbcCallerException.illegalFile", e);
        } catch (ClassNotFoundException e) {
            throw new DaoClassException("DaoClassException.illegalClassObtained", e);
        } catch (NullPointerException e) {
            throw new DaoClassException("DaoClassException.nullCast", e);
        }
    }

    @Override
    public void close() throws JdbcDaoConnectionException {
        closeConnection();
    }

}
