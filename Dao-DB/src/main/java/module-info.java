module pl.prokom.dao.db {
    requires pl.prokom.dao.api;
    requires pl.prokom.model;
    requires java.sql;
    requires pl.prokom.dao.file;

    exports pl.prokom.dao.db.model;
    exports pl.prokom.dao.db.exception;
}