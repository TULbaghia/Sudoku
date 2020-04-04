package pl.prokom.sudoku.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.prokom.sudoku.exception.IllegalFieldValueException;
import pl.prokom.sudoku.partial.field.SudokuField;
import pl.prokom.sudoku.partial.group.SudokuBox;
import pl.prokom.sudoku.partial.group.SudokuColumn;
import pl.prokom.sudoku.partial.group.SudokuGroup;
import pl.prokom.sudoku.partial.group.SudokuRow;
import pl.prokom.sudoku.solver.SudokuSolver;

//TODO: handle ArrayIndexOutOfBound exception
//TODO: change initialize field to generator stream with null predicate
//TODO: miniBoxCount should always be equal to miniBoxCount- only one variable should be used

/**
 * Class holds SudokuBoard object and allows to interact with it.
 */
public class SudokuBoard implements Cloneable {
    private final int miniBoxSize;
    private final int miniBoxCount;
    private final int boardSize;

    private SudokuSolver<SudokuBoard> sudokuSolver;
    private SudokuField[][] sudokuFields;
    private List<SudokuGroup> sudokuGroups;

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
        sudokuGroups = new ArrayList<>(3 * boardSize);
        initializeColumnGroup();
        initializeRowGroup();
        initializeBoxGroup();
    }

    private void initializeColumnGroup() {
        for (int i = 0; i < boardSize; i++) {
            SudokuField[] tmp = new SudokuField[boardSize];
            for (int j = 0; j < boardSize; j++) {
                tmp[j] = sudokuFields[j][i];
            }
            sudokuGroups.add(new SudokuColumn(tmp));
        }
    }

    private void initializeRowGroup() {
        Arrays.stream(sudokuFields).forEach(x -> sudokuGroups.add(new SudokuRow(x)));
    }

    private void initializeBoxGroup() {
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
                sudokuGroups.add(new SudokuBox(tmp));
            }
        }
    }

    public SudokuColumn getColumn(final int column) {
        return sudokuGroups.stream()
                .filter(x -> x instanceof SudokuColumn).skip(column).findFirst()
                .map(x -> (SudokuColumn) x)
                .get();
    }

    public SudokuRow getRow(final int row) {
        return sudokuGroups.stream()
                .filter(x -> x instanceof SudokuRow).skip(row).findFirst()
                .map(x -> (SudokuRow) x)
                .get();
    }

    public SudokuBox getBox(final int row, final int column) {
        return sudokuGroups.stream()
                .filter(x -> x instanceof SudokuBox).skip(row * miniBoxCount + column).findFirst()
                .map(x -> (SudokuBox) x)
                .get();
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
        return sudokuGroups.stream().allMatch(SudokuGroup::verify);
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

    private SudokuField[][] getCopyOfBoard() {
        SudokuField[][] sudokuFields = new SudokuField[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            sudokuFields[i] = Arrays.stream(this.sudokuFields[i])
                    .map(SudokuField::clone)
                    .toArray(SudokuField[]::new);
        }
        return sudokuFields;
    }

    @Override
    public String toString() {
        return "SudokuBoard{"
                + "miniBoxSize=" + miniBoxSize
                + ", miniBoxCount=" + miniBoxCount
                + ", boardSize=" + boardSize
                + ", sudokuSolver=" + sudokuSolver.toString()
                + ", sudokuFields=" + Arrays.deepToString(sudokuFields)
                + ", sudokuGroups=" + sudokuGroups.toString()
                + '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        SudokuBoard that = (SudokuBoard) object;

        if (this.boardSize != that.boardSize) {
            return false;
        }

        EqualsBuilder equalsBuilder = new EqualsBuilder()
                .append(this.miniBoxSize, that.miniBoxSize)
                .append(this.miniBoxCount, that.miniBoxCount)
                .append(this.boardSize, that.boardSize)
                .append(this.sudokuSolver, that.sudokuSolver);

        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                equalsBuilder.append(this.sudokuFields[i][j], that.sudokuFields[i][j]);
            }
        }

        return equalsBuilder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 37)
                .append(this.miniBoxSize)
                .append(this.miniBoxCount)
                .append(this.boardSize)
                .append(this.sudokuSolver);

        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                hashCodeBuilder.append(this.sudokuFields[i][j]);
            }
        }

        return hashCodeBuilder.append(this.getClass().getName()).toHashCode();
    }

    @Override
    public SudokuBoard clone() {
        try {
            SudokuBoard sudokuBoard = (SudokuBoard) super.clone();
            sudokuBoard.initializeFields(getCopyOfBoard());
            sudokuBoard.initializeGroups();
            return sudokuBoard;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
