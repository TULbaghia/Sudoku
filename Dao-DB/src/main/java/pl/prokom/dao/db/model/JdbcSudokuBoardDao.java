package pl.prokom.dao.db.model;


import pl.prokom.dao.api.exception.DaoException;
import pl.prokom.dao.api.model.Dao;
import pl.prokom.dao.db.exception.JdbcDaoConnectionException;
import pl.prokom.dao.db.exception.JdbcDaoQueryException;
import pl.prokom.dao.db.query.QueryExecutor;
import pl.prokom.model.board.SudokuBoard;
import java.sql.*;

/**
 * Class created to save instances of SudokuBoard class to database.
 */
public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    /**
     * Data required to establish connection with database.
     */
    private static final String URL = "jdbc:postgresql://localhost/SudokuBoard";
    private static final String USER = "postgres";
    private static final String PASSWORD = "12345";

    /**
     * Instance of QueryExecutor class.
     * Allows to execute different kind of queries.
     */
    QueryExecutor queryExecutor = new QueryExecutor();

    /**
     * Database connection class.
     * Method used while connection configuration: {@link #establishConnection()}.
     */
    private Connection dbConnection;

    /**
     * Each saved instance of SudokuBoard class name.
     */
    private String sudokuBoardName;

    /**
     * JdbcSudokuBoardDao constructor.
     * Initial connection with database is being established with each use of this constructor.
     */
    public JdbcSudokuBoardDao(String sudokuBoardName) throws JdbcDaoConnectionException {
        this.sudokuBoardName = sudokuBoardName;
    }
    /**
     * DriverManager connection creating.
     * @throws JdbcDaoConnectionException
     */
    private void establishConnection() throws JdbcDaoConnectionException {
        try {
            dbConnection = DriverManager.getConnection(URL, USER, PASSWORD);
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
            dbConnection.close();
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
        establishConnection();
        queryExecutor.createNewTable(dbConnection);
        queryExecutor.insertSudokuBoard(dbConnection, sudokuBoardName, concatFields.toString());
        closeConnection();
    }

    @Override
    public void close() throws JdbcDaoConnectionException {
        closeConnection();
    }

}
