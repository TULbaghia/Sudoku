package pl.prokom.view.bundles;

import java.util.ListResourceBundle;

public class Authors_pl extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"AuthorNumbers", "2"},
                {"Author1", "Maksajda Michał"},
                {"Author2", "Guzek Paweł"},
        };
    }
}
