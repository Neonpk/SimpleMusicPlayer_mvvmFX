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
import javafx.scene.image.ImageView;

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
    private Button muteButton;

    @FXML
    private Button repeatButton;

    @FXML
    private Slider sliderVolume;

    @FXML
    private Slider sliderProgress;

    @FXML
    private Label labelTimePosition;

    @FXML
    private Label labelDuration;

    @FXML
    Label labelArtist;

    @FXML
    Label labelTitle;

    @FXML
    Label labelFileName;

    @FXML
    ImageView imageViewCover;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Bindings

        listViewPlaylist.setItems( viewModel.getPlaylistProperty() );

        sliderVolume.valueProperty().bindBidirectional( viewModel.getSelectedVolumeProperty() );
        sliderProgress.valueProperty().bindBidirectional( viewModel.getSelectedProgressProperty() );

        labelTimePosition.textProperty().bindBidirectional( viewModel.getTimePositionProperty() );
        labelDuration.textProperty().bindBidirectional( viewModel.getTimeDurationProperty() );

        labelArtist.textProperty().bindBidirectional( viewModel.getArtistTextProperty() );
        labelTitle.textProperty().bindBidirectional( viewModel.getTitleTextProperty() );
        labelFileName.textProperty().bindBidirectional( viewModel.getFileNameTextProperty() );

        muteButton.textProperty().bindBidirectional( viewModel.getMuteButtonTextProperty() );
        repeatButton.textProperty().bindBidirectional( viewModel.getRepeatButtonTextProperty() );

        playButton.textProperty().bindBidirectional( viewModel.getPlayButtonTextProperty() );
        playButton.disableProperty().bind( viewModel.getPlayPauseCommand().executableProperty().not() );

        imageViewCover.imageProperty().bindBidirectional( viewModel.getImageCoverProperty() );

        // Events

        sliderProgress.valueProperty().addListener( (_) -> {
            boolean isUpdating = viewModel.getSliderProgressUpdateProperty().getValue();
            if (!isUpdating) viewModel.getSeekAudioCommand().execute();
        } );

        sliderVolume.valueProperty().addListener( (_) -> viewModel.getVolumeControlCommand().execute() );

        playButton.setOnAction( (_) -> viewModel.getPlayPauseCommand().execute() );
        muteButton.setOnAction( (_) -> viewModel.getMuteAudioCommand().execute() );
        repeatButton.setOnAction( (_) -> viewModel.getRepeatAudioCommand().execute() );
    }
}