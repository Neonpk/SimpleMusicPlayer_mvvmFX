package com.musicplayer.app.views;
import com.musicplayer.app.models.Playlist.PlaylistContextMenu;
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
    private ListView<Track> listViewTracks;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button addTracksButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listViewTracks.setItems(viewModel.getTracks());
        listViewTracks.contextMenuProperty().bindBidirectional(viewModel.getContextMenuProperty() );
        searchTextField.textProperty().bindBidirectional( viewModel.getSearchTextProperty() );

        // Events

        addTracksButton.setOnAction((_) -> viewModel.getAddTracksCommand().execute());
        searchTextField.textProperty().addListener((_) -> {  });
        listViewTracks.getSelectionModel().selectedItemProperty().addListener((_, _, newVal) -> viewModel.getSelectedTrackProperty().setValue(newVal));

        PlaylistContextMenu playlistContextMenu = new PlaylistContextMenu();
        viewModel.getContextMenuProperty().setValue( playlistContextMenu.getContextMenu() );
        playlistContextMenu.getDeleteTrack().setOnAction( (_) -> viewModel.getDeleteTrackCommand().execute() );
    }

}
