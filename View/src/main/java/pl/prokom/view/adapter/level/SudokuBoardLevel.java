package pl.prokom.view.adapter.level;


import java.io.Serializable;

/**
 * Class holds enums for all difficulty levels of SudokuBoard.
 * Used while filling entire sudokuBoard in SudokuBoardController (pl.prokom.view.controllers).
 */
public enum SudokuBoardLevel implements Cloneable, Serializable {

    EASY(60), MEDIUM(50), HARD(40);

    private Integer filledCells;

    SudokuBoardLevel(Integer filledCells) {
        this.filledCells = filledCells;
    }

    public Integer getFilledCells() {
        return filledCells;
    }
}
