module com.musicplayer.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires de.saxsys.mvvmfx;
    requires javafx.media;
    requires com.fasterxml.jackson.databind;
    requires static lombok;
    requires java.desktop;

    opens com.musicplayer.app to de.saxsys.mvvmfx, javafx.fxml;
    opens com.musicplayer.app.views to de.saxsys.mvvmfx, javafx.fxml;
    opens com.musicplayer.app.viewmodels to de.saxsys.mvvmfx, javafx.fxml;
    opens com.musicplayer.app.models to de.saxsys.mvvmfx, javafx.fxml, com.fasterxml.jackson.databind;

    exports com.musicplayer.app to javafx.fxml, javafx.graphics;
}