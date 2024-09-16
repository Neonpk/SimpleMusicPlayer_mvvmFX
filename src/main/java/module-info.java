module com.musicplayer.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires javafx.media;
    requires com.fasterxml.jackson.databind;
    requires static lombok;
    requires java.desktop;
    requires easy.di;
    requires de.saxsys.mvvmfx.easydi;
    requires de.saxsys.mvvmfx;

    opens com.musicplayer.app to de.saxsys.mvvmfx, de.saxsys.mvvmfx.easydi, easy.di, javafx.fxml;
    opens com.musicplayer.app.views to de.saxsys.mvvmfx, de.saxsys.mvvmfx.easydi, easy.di, javafx.fxml;
    opens com.musicplayer.app.viewmodels to de.saxsys.mvvmfx, de.saxsys.mvvmfx.easydi, easy.di, javafx.fxml;
    opens com.musicplayer.app.models to de.saxsys.mvvmfx, de.saxsys.mvvmfx.easydi, easy.di, javafx.fxml, com.fasterxml.jackson.databind;

    exports com.musicplayer.app to javafx.fxml, javafx.graphics;
}