module hu.csepel.gyakorlasdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens hu.csepel.gyakorlasdb to javafx.fxml;
    exports hu.csepel.gyakorlasdb;
}