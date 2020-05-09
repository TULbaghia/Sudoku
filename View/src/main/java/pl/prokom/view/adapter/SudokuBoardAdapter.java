package pl.prokom.view.adapter;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.model.exception.IllegalFieldValueException;
import pl.prokom.model.partial.group.SudokuGroup;
import pl.prokom.model.solver.BacktrackingSudokuSolver;
import pl.prokom.view.adapter.correctness.CorrectnessMode;
import pl.prokom.view.adapter.level.SudokuBoardLevel;
import pl.prokom.view.exception.SudokuBoardDuplicateValuesException;

/**
 * Adapter of SudokuBoard to match view restrictions.
 */
public class SudokuBoardAdapter extends SudokuBoard {
    private List<Integer> protectedIntegerFields;

    private List<Integer> editableIntegerFields;

    private SudokuBoardLevel sudokuBoardLevel;

    CorrectnessMode sudokuCorrectnessMode = CorrectnessMode.SUPERVISOR;

    public SudokuBoardAdapter() {
        super(new BacktrackingSudokuSolver());
        setSudokuBoardLevel(SudokuBoardLevel.EASY);
    }

    @Override
    public void set(int row, int column, int value) throws IllegalFieldValueException {
        if (value == 0) {
            super.reset(row, column);
        } else {
            super.set(row, column, value);
        }
    }

    public List<Integer> getProtectedIntegerFields() {
        return protectedIntegerFields;
    }

    public List<Integer> getEditableIntegerFields() {
        return editableIntegerFields;
    }

    public SudokuBoardLevel getSudokuBoardLevel() {
        return sudokuBoardLevel;
    }

    public void setSudokuBoardLevel(SudokuBoardLevel sudokuBoardLevel) {
        this.sudokuBoardLevel = sudokuBoardLevel;

        List<Integer> randomValues = IntStream
                .range(0, getBoardSize() * getBoardSize())
                .boxed()
                .collect(Collectors.toList());

        Collections.shuffle(randomValues);

        protectedIntegerFields = new ArrayList<>(
                randomValues.subList(0, sudokuBoardLevel.getFilledCells()));

        editableIntegerFields = new ArrayList<>(randomValues
                .subList(sudokuBoardLevel.getFilledCells(), getBoardSize() * getBoardSize()));
    }

    public boolean basicGroupCorrectnessCheck() {
        return sudokuGroups.stream().allMatch(SudokuGroup::containsUniqueValues);
    }

    public CorrectnessMode getSudokuCorrectnessMode() {
        return sudokuCorrectnessMode;
    }

    public void setSudokuFieldListenerEnabled(CorrectnessMode status) {
        if (!basicGroupCorrectnessCheck()) {
            throw new SudokuBoardDuplicateValuesException("Incorrect group validation");
        } else if (status != sudokuCorrectnessMode) {
            if (status == CorrectnessMode.SUPERVISOR) {
                sudokuGroups.forEach(SudokuGroup::initializeListeners);
            } else {
                IntStream.range(0, getBoardSize() * getBoardSize()).forEach(x -> {
                    PropertyChangeSupport pcs = getSudokuField(x / 9, x % 9).getPcs();
                    Arrays.stream(pcs.getPropertyChangeListeners())
                            .forEach(pcs::removePropertyChangeListener);
                });
            }
            sudokuCorrectnessMode = status;
        }
    }

    public void replaceParametersWith(SudokuBoardAdapter sudokuBoardDAO) {
        this.protectedIntegerFields = sudokuBoardDAO.protectedIntegerFields;
        this.editableIntegerFields = sudokuBoardDAO.editableIntegerFields;
        this.sudokuBoardLevel = sudokuBoardDAO.sudokuBoardLevel;
    }
}
