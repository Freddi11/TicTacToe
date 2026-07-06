module com.example.projektgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.projektgui to javafx.fxml;
    exports com.example.projektgui;
}