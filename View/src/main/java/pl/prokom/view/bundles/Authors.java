package pl.prokom.view.bundles;

import java.util.ListResourceBundle;

public class Authors extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"AuthorNumbers", "2"},
                {"Author1", "Guzek Paweł"},
                {"Author2", "Maksajda Michał"},
        };
    }
}
