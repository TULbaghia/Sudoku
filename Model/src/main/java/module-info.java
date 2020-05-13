module pl.prokom.model {
    requires org.apache.commons.lang3;
    requires java.desktop;               //java.beans
    exports pl.prokom.model.board;
    exports pl.prokom.model.exception;
    exports pl.prokom.model.partial.field;
    exports pl.prokom.model.partial.group;
    exports pl.prokom.model.solver;

    opens pl.prokom.model.board;         // allows reflection's from other modules access this package
}