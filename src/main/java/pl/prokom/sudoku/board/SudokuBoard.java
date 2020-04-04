package pl.prokom.sudoku.board;

import java.util.Objects;
import java.util.stream.Stream;
import pl.prokom.sudoku.exception.IllegalFieldValueException;
import pl.prokom.sudoku.partial.field.SudokuField;
import pl.prokom.sudoku.partial.group.SudokuBox;
import pl.prokom.sudoku.partial.group.SudokuColumn;
import pl.prokom.sudoku.partial.group.SudokuGroup;
import pl.prokom.sudoku.partial.group.SudokuRow;
import pl.prokom.sudoku.solver.SudokuSolver;

//TODO: handle ArrayIndexOutOfBound exception
//TODO: change initialize field to generator stream with null predicate
//TODO: change sudokuGroups to 1D array with filter predicate to select correct instanceof obj
//TODO: miniBoxCount should always be equal to miniBoxCount- only one variable should be used

/**
 * Class holds SudokuBoard object and allows to interact with it.
 */
public class SudokuBoard {
    private final int miniBoxSize;
    private final int miniBoxCount;
    private final int boardSize;

    private SudokuSolver<SudokuBoard> sudokuSolver;
    private SudokuField[][] sudokuFields;
    /**
     * Stores groups of objects:.
     * - [0] = SudokuColumn
     * - [1] = SudokuRow
     * - [2] = SudokuBox
     */
    private SudokuGroup[][] sudokuGroups;

    public SudokuBoard(SudokuSolver<SudokuBoard> sudokuSolver) {
        this(sudokuSolver, null);
    }

    public SudokuBoard(SudokuSolver<SudokuBoard> sudokuSolver, SudokuField[][] sudokuFields) {
        this(sudokuSolver, sudokuFields, 3, 3);
    }

    public SudokuBoard(SudokuSolver<SudokuBoard> sudokuSolver, SudokuField[][] sudokuFields,
                       final int miniBoxCount, final int miniBoxSize) {
        this.sudokuSolver = sudokuSolver;
        this.miniBoxCount = miniBoxCount;
        this.miniBoxSize = miniBoxSize;
        this.boardSize = miniBoxSize * miniBoxCount;
        initializeFields(sudokuFields);
        initializeGroups();
    }

    private void initializeFields(SudokuField[][] fields) {
        sudokuFields = Objects.requireNonNullElseGet(fields,
                () -> new SudokuField[boardSize][]);

        for (int i = 0; i < boardSize; i++) {
            if (sudokuFields[i] == null) {
                sudokuFields[i] = Stream.generate(SudokuField::new)
                        .limit(boardSize)
                        .toArray(SudokuField[]::new);
            } else {
                for (int j = 0; j < sudokuFields[i].length; j++) {
                    if (this.sudokuFields[i][j] == null) {
                        this.sudokuFields[i][j] = new SudokuField();
                    }
                }
            }
        }
    }

    private void initializeGroups() {
        sudokuGroups = new SudokuGroup[3][];
        initializeColumnGroup();
        initializeRowGroup();
        initializeBoxGroup();
    }

    private void initializeColumnGroup() {
        sudokuGroups[0] = new SudokuColumn[boardSize];
        for (int i = 0; i < boardSize; i++) {
            SudokuField[] tmp = new SudokuField[boardSize];
            for (int j = 0; j < boardSize; j++) {
                tmp[j] = sudokuFields[j][i];
            }
            sudokuGroups[0][i] = new SudokuColumn(tmp);
        }
    }

    private void initializeRowGroup() {
        sudokuGroups[1] = new SudokuRow[boardSize];
        for (int i = 0; i < boardSize; i++) {
            sudokuGroups[1][i] = new SudokuRow(sudokuFields[i]);
        }
    }

    private void initializeBoxGroup() {
        sudokuGroups[2] = new SudokuBox[boardSize];

        for (int maxRow = 0; maxRow < miniBoxCount; maxRow++) {
            for (int maxCol = 0; maxCol < miniBoxCount; maxCol++) {
                SudokuField[] tmp = new SudokuField[boardSize];
                for (int minRow = 0; minRow < miniBoxSize; minRow++) {
                    System.arraycopy(sudokuFields[maxRow * miniBoxCount + minRow],
                            maxCol * miniBoxCount,
                            tmp,
                            minRow * miniBoxCount,
                            miniBoxSize);
                }
                sudokuGroups[2][maxRow * miniBoxCount + maxCol] = new SudokuBox(tmp);
            }
        }
    }

    public SudokuColumn getColumn(final int column) {
        return (SudokuColumn) sudokuGroups[0][column];
    }

    public SudokuRow getRow(final int row) {
        return (SudokuRow) sudokuGroups[1][row];
    }

    public SudokuBox getBox(final int row, final int column) {
        return (SudokuBox) sudokuGroups[2][row * miniBoxCount + column];
    }

    private SudokuField getSudokuField(int row, int column) {
        return sudokuFields[row][column];
    }

    public int get(final int row, final int column) {
        return getSudokuField(row, column).getFieldValue();
    }

    public void set(final int row, final int column, final int value)
            throws IllegalFieldValueException {
        getSudokuField(row, column).setFieldValue(value);
    }

    public void reset(final int row, final int column) {
        getSudokuField(row, column).resetValue();
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    private boolean checkBoard() {
        for (SudokuGroup[] sudokuGroup : sudokuGroups) {
            for (SudokuGroup groupItem : sudokuGroup) {
                if (!groupItem.verify()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSolved() {
        return checkBoard();
    }

    public final int getMiniBoxSize() {
        return miniBoxSize;
    }

    public final int getMiniBoxCount() {
        return miniBoxCount;
    }

    public final int getBoardSize() {
        return boardSize;
    }
}
