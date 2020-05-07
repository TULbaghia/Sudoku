package pl.prokom.dao.file.model;

import pl.prokom.dao.api.model.Dao;
import pl.prokom.model.board.SudokuBoard;

public class SudokuBoardDaoFactory {
    /**
     * Creates an instance of FileSudokuBoardDao class.
     * @param fileName value that has to be assigned to FileSudokuBoardDao filePath member.
     * @return new instance of FileSudokuBoardDao class, connected with file {@code fileName}
     */
    public Dao<SudokuBoard> getFileDao(final String fileName) {
        return new FileSudokuBoardDao(fileName);
    }
}
