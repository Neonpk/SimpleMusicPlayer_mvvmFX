package com.musicplayer.app.views;

import com.musicplayer.app.viewmodels.MainContainerViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class MainContainerView implements FxmlView<MainContainerViewModel>, Initializable {

    // Inject ViewModel

    @InjectViewModel
    private MainContainerViewModel viewModel;

    // Inject Controls

    @FXML
    private ListView<String> listViewPlaylist;

    @FXML
    private Button playButton;

    @FXML
    private Slider sliderVolume;

    @FXML
    private Slider sliderProgress;

    @FXML
    private Label labelTimePosition;

    @FXML
    private Label labelDuration;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listViewPlaylist.setItems( viewModel.getPlaylistProperty() );

        sliderVolume.valueProperty().bindBidirectional( viewModel.getSelectedVolumeProperty() );
        sliderProgress.valueProperty().bindBidirectional( viewModel.getSelectedProgressProperty() );

        labelTimePosition.textProperty().bindBidirectional( viewModel.getTimePositionProperty() );
        labelDuration.textProperty().bindBidirectional( viewModel.getTimeDurationProperty() );

        playButton.disableProperty().bind( viewModel.getListViewEditCommand().executableProperty().not() );
    }

    @FXML
    void buttonPlayPausePressed() {
        viewModel.getListViewEditCommand().execute();
    }
}