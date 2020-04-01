package pl.prokom.sudoku.partials;

import java.util.SortedSet;
import java.util.TreeSet;

public abstract class SudokuPartial {
    private SudokuField[] fields;

    SudokuPartial(SudokuField[] fields) {
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
