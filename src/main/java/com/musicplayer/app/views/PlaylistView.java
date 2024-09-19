package com.musicplayer.app.views;
import com.musicplayer.app.models.Track.TrackListViewCellFactory;
import com.musicplayer.app.models.Track.TrackContextMenu;
import com.musicplayer.app.models.Track.Track;
import com.musicplayer.app.viewmodels.PlaylistViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistView implements FxmlView<PlaylistViewModel>, Initializable {

    @InjectViewModel
    private PlaylistViewModel viewModel;

    @FXML
    private Label playlistNameLabel;

    @FXML
    private ListView<Track> listViewTracks;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button addTracksButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listViewTracks.setItems(viewModel.getFilteredTrackList());
        listViewTracks.contextMenuProperty().bindBidirectional(viewModel.getContextMenuProperty() );
        listViewTracks.setCellFactory( new TrackListViewCellFactory() );

        playlistNameLabel.textProperty().bindBidirectional( viewModel.getPlaylistNameProperty() );
        searchTextField.textProperty().bindBidirectional( viewModel.getSearchTextProperty() );

        // Events

        addTracksButton.setOnAction((_) -> viewModel.getAddTracksCommand().execute());
        searchTextField.textProperty().addListener((_) -> viewModel.getSearchTracksCommand().execute() );
        listViewTracks.getSelectionModel().selectedItemProperty().addListener((_, _, newVal) -> viewModel.getSelectedTrackProperty().setValue(newVal));

        TrackContextMenu trackContextMenu = new TrackContextMenu();
        viewModel.getContextMenuProperty().setValue( trackContextMenu.getContextMenu() );
        trackContextMenu.getDeleteTrack().setOnAction( (_) -> viewModel.getDeleteTrackCommand().execute() );
    }

}
