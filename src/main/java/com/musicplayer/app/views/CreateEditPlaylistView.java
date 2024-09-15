package com.musicplayer.app.views;

import com.musicplayer.app.viewmodels.CreateEditPlaylistViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateEditPlaylistView implements FxmlView<CreateEditPlaylistViewModel>, Initializable {

    @InjectViewModel
    CreateEditPlaylistViewModel viewModel;

    @FXML
    private TextField textFieldPlaylistName;

    @FXML
    private Button savePlaylistButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFieldPlaylistName.textProperty().bindBidirectional( viewModel.getPlaylistNameProperty() );
        savePlaylistButton.setOnAction( (_) -> viewModel.getSavePlaylistCommand().execute()  );
    }

}
