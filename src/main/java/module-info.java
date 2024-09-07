module com.musicplayer.app {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires de.saxsys.mvvmfx;

    opens com.musicplayer.app to de.saxsys.mvvmfx, javafx.fxml;
    opens com.musicplayer.app.views to de.saxsys.mvvmfx, javafx.fxml;
    opens com.musicplayer.app.viewmodels to de.saxsys.mvvmfx, javafx.fxml;

    exports com.musicplayer.app to javafx.graphics;
}