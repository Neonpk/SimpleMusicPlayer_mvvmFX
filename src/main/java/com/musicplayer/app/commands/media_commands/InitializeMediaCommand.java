package com.musicplayer.app.commands.media_commands;

import com.musicplayer.app.models.MediaListeners;
import com.musicplayer.app.models.Track.Track;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.List;

public class InitializeMediaCommand extends DelegateCommand {

    private static void initializeMedia(List<Track> trackList,
                                        Property<Media> mediaProperty,
                                        Property<MediaPlayer> mediaPlayerProperty,
                                        MediaListeners mediaListeners)
    {

        Track track = trackList.getFirst();
        String fileName = new File(track.getFileName()).toURI().toString();

        mediaProperty.setValue(new Media(fileName));
        mediaPlayerProperty.setValue(new MediaPlayer(mediaProperty.getValue()));

        mediaProperty.getValue().getMetadata().addListener(mediaListeners.getMetaDataChangeListener());
        mediaPlayerProperty.getValue().currentTimeProperty().addListener(mediaListeners.getDurationChangeListener());
        mediaPlayerProperty.getValue().setOnEndOfMedia(mediaListeners.getOnEndMediaListener());
    }

    public InitializeMediaCommand(List<Track> trackList,
                                  Property<Media> mediaProperty,
                                  Property<MediaPlayer> mediaPlayerProperty,
                                  MediaListeners mediaListeners)
    {
        super(() -> new Action() {
            @Override
            protected void action()  {
                initializeMedia(trackList, mediaProperty, mediaPlayerProperty, mediaListeners);
            }
        });
    }

}
