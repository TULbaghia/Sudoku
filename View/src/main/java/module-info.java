module pl.prokom.view {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires pl.prokom.model;
    requires pl.prokom.dao.file;
    requires pl.prokom.dao.api;

    exports pl.prokom.view.controllers;
    exports pl.prokom.view.menu;

    opens pl.prokom.view.controllers;
    opens pl.prokom.view.menu;
}