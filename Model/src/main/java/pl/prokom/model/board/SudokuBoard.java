package pl.prokom.model.board;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.prokom.model.exception.IllegalFieldValueException;
import pl.prokom.model.partial.field.SudokuField;
import pl.prokom.model.partial.group.SudokuBox;
import pl.prokom.model.partial.group.SudokuColumn;
import pl.prokom.model.partial.group.SudokuGroup;
import pl.prokom.model.partial.group.SudokuRow;
import pl.prokom.model.solver.BacktrackingSudokuSolver;
import pl.prokom.model.solver.SudokuSolver;

//TODO: handle {Array,*}IndexOutOfBoundException

/**
 * Class holds SudokuBoard object and allows to interact with it.
 */
public class SudokuBoard implements Cloneable, Serializable {
    private final int miniBoxDim;
    private final int boardSize;

    private transient SudokuSolver<SudokuBoard> sudokuSolver;
    private List<List<SudokuField>> sudokuFields;
    private transient List<SudokuGroup> sudokuGroups;

    public SudokuBoard(final SudokuSolver<SudokuBoard> sudokuSolver) {
        this(sudokuSolver, null);
    }

    public SudokuBoard(final SudokuSolver<SudokuBoard> sudokuSolver,
                       final List<List<SudokuField>> sudokuFields) {
        this(sudokuSolver, sudokuFields, 3);
    }

    /**
     * Constructor that handles object creation.
     * @param sudokuSolver type of SudokuSolver that you want to use
     * @param sudokuFields list of fields, which values are copied to SudokuBoard (default null)
     * @param miniBoxDim number of boxes and box sizes in sudokuBoard (default 3)
     */
    public SudokuBoard(final SudokuSolver<SudokuBoard> sudokuSolver,
                       final List<List<SudokuField>> sudokuFields, final int miniBoxDim) {
        this.sudokuSolver = sudokuSolver;
        this.miniBoxDim = miniBoxDim;
        this.boardSize = miniBoxDim * miniBoxDim;
        initializeFields(sudokuFields);
        initializeGroups();
    }

    private void initializeFields(List<List<SudokuField>> fields) {
        sudokuFields = Stream.generate(() -> new SudokuField[boardSize])
                .limit(boardSize)
                .map(Arrays::asList)
                .collect(Collectors.toList());

        if (fields != null) {
            for (int i = 0; i < fields.size(); i++) {
                for (int j = 0; j < fields.size(); j++) {
                    if (fields.get(i).get(j) != null) {
                        sudokuFields.get(i).set(j, fields.get(i).get(j).clone());
                    }
                }
            }
        }

        sudokuFields.forEach(list -> list.replaceAll(x -> x == null ? new SudokuField() : x));
    }

    private void initializeGroups() {
        sudokuGroups = new ArrayList<>(3 * boardSize);
        initializeColumnGroup();
        initializeRowGroup();
        initializeBoxGroup();
    }

    private void initializeColumnGroup() {
        for (int i = 0; i < boardSize; i++) {
            int finalI = i;
            sudokuGroups.add(new SudokuColumn(
                    sudokuFields.stream()
                            .map(list -> list.get(finalI))
                            .collect(Collectors.toList())
            ));
        }
    }

    private void initializeRowGroup() {
        sudokuFields.forEach(x -> sudokuGroups.add(new SudokuRow(x)));
    }

    private void initializeBoxGroup() {
        List<List<SudokuField>> boxGroups = Stream
                .generate(() -> new SudokuField[boardSize])
                .limit(boardSize)
                .map(Arrays::asList)
                .collect(Collectors.toList());

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                boxGroups.get((row / miniBoxDim) * miniBoxDim + col / miniBoxDim)
                        .set((row % miniBoxDim) * miniBoxDim + col % miniBoxDim,
                                getSudokuField(row, col));
            }
        }

        boxGroups.forEach(list -> sudokuGroups.add(new SudokuBox(list)));
    }

    /**
     * Returns reference to queried column.
     * @param column number of column from 0..8
     * @return reference to queried SudokuColumn
     */
    public SudokuColumn getColumn(final int column) {
        return sudokuGroups.stream()
                .filter(x -> x instanceof SudokuColumn).skip(column).findFirst()
                .map(x -> (SudokuColumn) x)
                .get();
    }

    /**
     * Returns reference to queried row.
     * @param row number of row from 0..8
     * @return reference to queried SudokuRow
     */
    public SudokuRow getRow(final int row) {
        return sudokuGroups.stream()
                .filter(x -> x instanceof SudokuRow).skip(row).findFirst()
                .map(x -> (SudokuRow) x)
                .get();
    }

    /**
     * Returns reference to queried 3x3 box.
     * @param row number of row from 0..2
     * @param column number of column from 0..2
     * @return reference to queried SudokuBoard
     */
    public SudokuBox getBox(final int row, final int column) {
        return sudokuGroups.stream()
                .filter(x -> x instanceof SudokuBox).skip(row * miniBoxDim + column).findFirst()
                .map(x -> (SudokuBox) x)
                .get();
    }

    private SudokuField getSudokuField(int row, int column) {
        return sudokuFields.get(row).get(column);
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

    public final int getMiniBoxDim() {
        return miniBoxDim;
    }

    public final int getBoardSize() {
        return boardSize;
    }

    private List<List<SudokuField>> getCopyOfBoard() {
        List<List<SudokuField>> sudokuFields = Arrays.stream(new SudokuField[boardSize][boardSize])
                .map(Arrays::asList)
                .collect(Collectors.toList());

        for (int i = 0; i < boardSize; i++) {
            sudokuFields.set(i, Arrays.asList(this.sudokuFields.get(i).stream()
                    .map(SudokuField::clone)
                    .toArray(SudokuField[]::new)));
        }
        return sudokuFields;
    }

    /**
     * Customizing deserialization process of SudokuBoard class instance.
     *
     * @param in deserialization stream under customization.
     */
    private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        initializeGroups();
        SudokuSolver<SudokuBoard> deserializedSolver = new BacktrackingSudokuSolver();
        sudokuSolver = deserializedSolver;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("miniBoxDim", miniBoxDim)
                .append("boardSize", boardSize)
                .append("sudokuSolver", sudokuSolver)
                .append("sudokuFields", sudokuFields)
                .append("sudokuGroups", sudokuGroups)
                .toString();
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

        return new EqualsBuilder()
                .append(this.miniBoxDim, that.miniBoxDim)
                .append(this.boardSize, that.boardSize)
                .append(this.sudokuSolver, that.sudokuSolver)
                .append(this.sudokuFields, that.sudokuFields)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.getClass().getName())
                .append(this.miniBoxDim)
                .append(this.boardSize)
                .append(this.sudokuSolver)
                .append(this.sudokuFields)
                .toHashCode();
    }

    @Override
    public SudokuBoard clone() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (SudokuBoard) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
