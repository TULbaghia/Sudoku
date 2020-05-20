module pl.prokom.view {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires pl.prokom.model;
    requires pl.prokom.dao.file;
    requires pl.prokom.dao.api;
    requires pl.prokom.dao.db;
    requires slf4j.api;
    requires java.desktop;

    exports pl.prokom.view.adapter;
    exports pl.prokom.view.adapter.correctness;
    exports pl.prokom.view.adapter.level;

    exports pl.prokom.view.bundles;

    exports pl.prokom.view.controllers;
    exports pl.prokom.view.controllers.sudokuboard;

    exports pl.prokom.view.exception;

    exports pl.prokom.view.menu;

    exports pl.prokom.view.stage;

    opens pl.prokom.view.controllers;
    opens pl.prokom.view.controllers.sudokuboard;
    opens pl.prokom.view.menu;
}