module com.musicplayer.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires de.saxsys.mvvmfx;
    requires java.desktop;
    requires javafx.media;

    opens com.musicplayer.app to de.saxsys.mvvmfx, javafx.fxml;
    opens com.musicplayer.app.views to de.saxsys.mvvmfx, javafx.fxml;
    opens com.musicplayer.app.viewmodels to de.saxsys.mvvmfx, javafx.fxml;
    opens com.musicplayer.app.models to de.saxsys.mvvmfx, javafx.fxml;

    exports com.musicplayer.app to javafx.fxml, javafx.graphics;
}