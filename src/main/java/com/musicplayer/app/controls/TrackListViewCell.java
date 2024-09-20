package com.musicplayer.app.controls;

import com.musicplayer.app.models.Track.Track;
import com.musicplayer.app.models.Track.TrackMetadataListener;
import com.musicplayer.app.models.Track.TrackMetadataListenerParam;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.media.Media;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@SuppressWarnings("all")
public class TrackListViewCell extends ListCell<Track> {

    @FXML
    private Label labelFileName;

    @FXML
    private Label labelArtist;

    @FXML
    private Label labelTitle;

    @FXML
    private Label labelAddedTime;

    @FXML
    private ImageView imageViewCover;

    // Custom Fields

    private Media media;

    public TrackListViewCell() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(TrackListViewCell.class.getResource("TrackListViewCell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Track item, boolean empty) {

        super.updateItem(item, empty);

        if ( !empty && !Objects.equals(item, null) )
        {

            TrackMetadataListener trackMetadataListener = new TrackMetadataListener(
                    new TrackMetadataListenerParam(
                            labelTitle.textProperty(),
                            labelArtist.textProperty(),
                            imageViewCover.imageProperty(),
                            item.getMetaData()
                    )
            );

            if(media != null) {
                media.getMetadata().removeListener(trackMetadataListener.getMetaDataChangeListenger());
                System.gc();
            }

            media = new Media(new File(item.getFileName()).toURI().toString());
            media.getMetadata().addListener(trackMetadataListener.getMetaDataChangeListenger());

            String addedDateText = new SimpleDateFormat("dd.MM.yyyy\nHH:mm:ss").
                    format(new Date(item.getAdded()));

            labelAddedTime.setText(addedDateText);
            labelFileName.setText(item.getFileName().replaceFirst(".*/(.*\\.(?:mp3|mp4))","$1"));

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        } else {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    }
}