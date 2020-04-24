module pl.prokom.view {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires pl.prokom.model;

    exports pl.prokom.view.menu;
    // fixed IllegalAccessErrors from SudokuBoardWindow.start().
    exports pl.prokom.view.controllers;
    opens pl.prokom.view.controllers;
}