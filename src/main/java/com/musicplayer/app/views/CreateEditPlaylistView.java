package com.musicplayer.app.views;

import com.musicplayer.app.viewmodels.CreateEditPlaylistViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateEditPlaylistView implements FxmlView<CreateEditPlaylistViewModel>, Initializable {

    @InjectViewModel
    CreateEditPlaylistViewModel viewModel;

    @FXML
    private TextField playlistNameTextField;

    @FXML
    private Button savePlaylistButton;

    @FXML
    private Label labelStatusMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playlistNameTextField.textProperty().bindBidirectional( viewModel.getPlaylistNameProperty() );
        labelStatusMessage.textProperty().bindBidirectional( viewModel.getStatusTextProperty() );
        savePlaylistButton.setOnAction( (_) -> viewModel.getSavePlaylistCommand().execute()  );
    }

}
