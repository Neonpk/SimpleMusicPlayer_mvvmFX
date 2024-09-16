package com.musicplayer.app.views;

import com.musicplayer.app.models.Playlist;
import com.musicplayer.app.viewmodels.MainContainerViewModel;
import de.saxsys.mvvmfx.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainContainerView implements FxmlView<MainContainerViewModel>, Initializable {

    // Inject ViewModel

    @InjectViewModel
    private MainContainerViewModel viewModel;

    @FXML
    private BorderPane contentPresenter;

    // Inject Controls

    @FXML
    private ListView<Playlist> listViewPlaylist;

    @FXML
    private Button createPlaylistButton;

    @FXML
    private Button playButton;

    @FXML
    private Button muteButton;

    @FXML
    private Button repeatButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;

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

        listViewPlaylist.setItems( viewModel.getPlaylists() );
        listViewPlaylist.contextMenuProperty().bindBidirectional( viewModel.getContextMenuProperty() );

        sliderVolume.valueProperty().bindBidirectional( viewModel.getSelectedVolumeProperty() );
        sliderProgress.valueProperty().bindBidirectional( viewModel.getSelectedProgressProperty() );

        labelTimePosition.textProperty().bindBidirectional( viewModel.getTimePositionTextProperty() );
        labelDuration.textProperty().bindBidirectional( viewModel.getTimeDurationTextProperty() );

        labelArtist.textProperty().bindBidirectional( viewModel.getArtistTextProperty() );
        labelTitle.textProperty().bindBidirectional( viewModel.getTitleTextProperty() );
        labelFileName.textProperty().bindBidirectional( viewModel.getFileNameTextProperty() );

        muteButton.textProperty().bindBidirectional( viewModel.getMuteButtonTextProperty() );
        repeatButton.textProperty().bindBidirectional( viewModel.getRepeatButtonTextProperty() );

        playButton.textProperty().bindBidirectional( viewModel.getPlayButtonTextProperty() );
        playButton.disableProperty().bind( viewModel.getPlayPauseCommand().executableProperty().not() );

        imageViewCover.imageProperty().bindBidirectional( viewModel.getImageCoverProperty() );

        contentPresenter.centerProperty().bindBidirectional( viewModel.getSelectedView() );

        // Events

        sliderProgress.valueProperty().addListener( (_) -> {
            boolean isUpdating = viewModel.getSliderProgressUpdateProperty().getValue();
            if (!isUpdating) viewModel.getSeekAudioCommand().execute();
        } );

        sliderVolume.valueProperty().addListener( (_) -> viewModel.getVolumeControlCommand().execute() );

        listViewPlaylist.getSelectionModel().selectedItemProperty().addListener((_, _, newVal) -> {
            viewModel.getSelectedPlaylistProperty().setValue(newVal);
            viewModel.getSelectedPlaylistCommand().execute();
        });

        playButton.setOnAction( (_) -> viewModel.getPlayPauseCommand().execute() );
        muteButton.setOnAction( (_) -> viewModel.getMuteAudioCommand().execute() );
        repeatButton.setOnAction( (_) -> viewModel.getRepeatAudioCommand().execute() );
        nextButton.setOnAction((_) -> viewModel.getSwitchNextAudioCommand().execute() );
        prevButton.setOnAction((_) -> viewModel.getSwitchPrevAudioCommand().execute() );
        createPlaylistButton.setOnAction((_) -> viewModel.getPlaylistCreateCommand().execute() );
    }
}