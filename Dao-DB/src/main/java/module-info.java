module pl.prokom.dao.db {
    requires pl.prokom.dao.api;
    requires pl.prokom.model;
    requires java.sql;

    exports pl.prokom.dao.db.model;
    exports pl.prokom.dao.db.exception;
}