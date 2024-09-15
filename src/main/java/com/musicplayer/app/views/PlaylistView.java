package com.musicplayer.app.views;

import com.musicplayer.app.models.Playlist;
import com.musicplayer.app.models.Track;
import com.musicplayer.app.viewmodels.PlaylistViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import lombok.Getter;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistView implements FxmlView<PlaylistViewModel>, Initializable {

    @InjectViewModel
    private PlaylistViewModel viewModel;

    @FXML
    private ListView<Track> selectedPlaylist;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedPlaylist.setItems(FXCollections.observableArrayList());
    }

}
