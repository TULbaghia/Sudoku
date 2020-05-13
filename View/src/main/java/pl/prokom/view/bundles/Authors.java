package pl.prokom.view.bundles;

import java.util.ListResourceBundle;

public class Authors extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"label", "Authors"},
                {"authorNumber", "2"},
                {"author_1", "Guzek Paweł"},
                {"author_2", "Maksajda Michał"},
        };
    }
}
