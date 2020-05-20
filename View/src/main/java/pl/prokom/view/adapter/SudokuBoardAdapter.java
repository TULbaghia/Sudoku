package pl.prokom.view.adapter;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.model.exception.IllegalFieldValueException;
import pl.prokom.model.partial.group.SudokuGroup;
import pl.prokom.model.solver.BacktrackingSudokuSolver;
import pl.prokom.view.adapter.correctness.CorrectnessMode;
import pl.prokom.view.adapter.level.SudokuBoardLevel;
import pl.prokom.view.bundles.BundleHelper;
import pl.prokom.view.exception.SudokuBoardDuplicateValuesException;

/**
 * Adapter of SudokuBoard to match view restrictions.
 */
public class SudokuBoardAdapter extends SudokuBoard {

    /**
     * Logger instance. Logging events of SudokuBoardController class.
     */
    private static final transient Logger logger =
            LoggerFactory.getLogger(SudokuBoardAdapter.class);

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

    /**
     * Generates protected and editable field lists.
     *
     * @param sudokuBoardLevel difficulty level of sudoku
     */
    public void setSudokuBoardLevel(SudokuBoardLevel sudokuBoardLevel) {
        this.sudokuBoardLevel = sudokuBoardLevel;

        List<Integer> randomValues = IntStream
                .range(0, getBoardSize() * getBoardSize())
                .boxed()
                .collect(Collectors.toList());

        Collections.shuffle(randomValues);

        protectedIntegerFields = new ArrayList<>(
                randomValues.subList(0, sudokuBoardLevel.getFilledCells()));

        logger.debug(
                BundleHelper.getApplication("boardAdapterProtectedFields"), protectedIntegerFields);

        editableIntegerFields = new ArrayList<>(randomValues
                .subList(sudokuBoardLevel.getFilledCells(), getBoardSize() * getBoardSize()));

        logger.debug(
                BundleHelper.getApplication("boardAdapterEditableFields"), editableIntegerFields);
    }

    public boolean basicGroupCorrectnessCheck() {
        return getGroups().stream().allMatch(SudokuGroup::containsUniqueValues);
    }

    public CorrectnessMode getSudokuCorrectnessMode() {
        return sudokuCorrectnessMode;
    }

    /**
     * Enables or disables sudokuField's listeneres.
     *
     * @param status validation status used during gameplay
     */
    public void setSudokuFieldListenerEnabled(CorrectnessMode status) {
        if (!basicGroupCorrectnessCheck()) {
            throw new SudokuBoardDuplicateValuesException("sudokuBoardDuplicateValuesException");
        } else if (status != sudokuCorrectnessMode) {
            if (status == CorrectnessMode.SUPERVISOR) {
                getGroups().forEach(SudokuGroup::initializeListeners);
                logger.debug(BundleHelper.getApplication("boardAdapterListenerSupervisor"));
            } else {
                IntStream.range(0, getBoardSize() * getBoardSize()).forEach(x -> {
                    PropertyChangeSupport pcs = getSudokuField(x / 9, x % 9).getPcs();
                    Arrays.stream(pcs.getPropertyChangeListeners())
                            .forEach(pcs::removePropertyChangeListener);
                });
                logger.debug(BundleHelper.getApplication("boardAdapterListenerFreeStyle"));
            }
            sudokuCorrectnessMode = status;
        }
    }

    /**
     * Replaces parameters of class with given one.
     *
     * @param sudokuBoardDao class from DAO
     */
    public void replaceParametersWith(SudokuBoardAdapter sudokuBoardDao) {
        this.protectedIntegerFields = sudokuBoardDao.protectedIntegerFields;
        this.editableIntegerFields = sudokuBoardDao.editableIntegerFields;
        this.sudokuBoardLevel = sudokuBoardDao.sudokuBoardLevel;
    }
}
