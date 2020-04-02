package pl.prokom.sudoku.partial.group;

import java.util.SortedSet;
import java.util.TreeSet;
import pl.prokom.sudoku.partial.field.SudokuField;

public abstract class SudokuGroup {
    private SudokuField[] fields;

    SudokuGroup(SudokuField[] fields) {
        this.fields = fields.clone();
    }

    public void setFields(SudokuField[] fields) {
        this.fields = fields.clone();
    }

    public SudokuField[] getFields() {
        return fields;
    }

    public boolean verify() {
        SortedSet<Integer> uniqueValues = new TreeSet<>();
        for (SudokuField field : fields) {
            if (!uniqueValues.add(field.getFieldValue())) {
                return false;
            }
        }
        if (uniqueValues.first() != 1 || uniqueValues.last() != fields.length) {
            return false;
        }
        return true;
    }
}
