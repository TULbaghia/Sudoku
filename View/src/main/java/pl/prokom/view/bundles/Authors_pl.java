package pl.prokom.view.bundles;

import java.util.ListResourceBundle;

public class Authors_pl extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"label", "Autorzy"},
                {"authorNumber", "2"},
                {"author_1", "Maksajda Michał"},
                {"author_2", "Guzek Paweł"},
        };
    }
}
