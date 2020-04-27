package pl.prokom.view;

import pl.prokom.view.menu.MainPaneWindow;

/**
 * Jar bundler needs Main class that does not extends/implements any other class.
 * This class is just a wrapper for jar-builder
 */
public class Main {
    /**
     * Default class of program that handles further execution.
     * @param args Parameters to customize settings
     */
    public static void main(String[] args) {
        MainPaneWindow.main(args);
    }
}
