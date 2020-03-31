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
        for (int i = 0; i < fields.length; i++) {
            if (!uniqueValues.add(fields[i].getFieldValue())) {
                return false;
            }
        }
        return true;
    }
}
