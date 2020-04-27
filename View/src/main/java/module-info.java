module pl.prokom.view {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires pl.prokom.model;

    // fixed IllegalAccessErrors from SudokuBoardWindow.start().
    exports pl.prokom.view.controllers;
    exports pl.prokom.view.menu;
    opens pl.prokom.view.controllers;
}