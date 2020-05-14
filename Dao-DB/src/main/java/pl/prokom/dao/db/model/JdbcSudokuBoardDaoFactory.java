package pl.prokom.dao.db.model;

import pl.prokom.dao.api.model.Dao;
import pl.prokom.dao.db.exception.JdbcDaoConnectionException;
import pl.prokom.model.board.SudokuBoard;

public class JdbcSudokuBoardDaoFactory {

    /**
     * Creates an instance of JdbcSudokuBoardDao class.
     * @param fileName value that has to be assigned to JdbcSudokuBoardDao filePath member.
     * @return new instance of JdbcSudokuBoardDao class, connected with file {@code fileName}
     */
    public Dao<SudokuBoard> getDBDao(final String fileName) throws JdbcDaoConnectionException {
        return new JdbcSudokuBoardDao(fileName);
    }
}
