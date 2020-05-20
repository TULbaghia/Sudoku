package pl.prokom.dao.db.model;

import pl.prokom.dao.api.model.Dao;
import pl.prokom.model.board.SudokuBoard;

public class JdbcSudokuBoardDaoFactory {

    /**
     * Creates an instance of JdbcSudokuBoardDao class.
     * @param fileName value that has to be assigned to JdbcSudokuBoardDao filePath member.
     * @return new instance of JdbcSudokuBoardDao class, connected with file {@code fileName}
     */
    public Dao<SudokuBoard> getDbDao(final String fileName) {
        return new JdbcSudokuBoardDao(fileName);
    }
}
