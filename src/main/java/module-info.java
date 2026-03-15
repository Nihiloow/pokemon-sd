module com.example.pslikemyversion {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.pslikemyversion to javafx.fxml;
    opens com.example.pslikemyversion.ui to javafx.fxml;
    opens com.example.pslikemyversion.core to javafx.fxml;

    exports com.example.pslikemyversion;
}