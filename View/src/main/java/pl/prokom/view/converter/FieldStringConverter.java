package pl.prokom.view.converter;

import javafx.util.StringConverter;

public class FieldStringConverter extends StringConverter<Integer> {
    public FieldStringConverter() {
    }

    @Override
    public String toString(Integer value) {
        return value == null || value == 0 ? "" : Integer.toString(value);
    }

    @Override
    public Integer fromString(String value) {
        if (value == null) {
            return null;
        } else {
            value = value.trim();
            return value.length() < 1 ? null : Integer.valueOf(value);
        }
    }
}
