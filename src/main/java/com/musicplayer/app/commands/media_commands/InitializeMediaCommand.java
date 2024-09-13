package com.musicplayer.app.commands.media_commands;

import com.musicplayer.app.models.MediaListeners;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.List;

public class InitializeMediaCommand extends DelegateCommand {

    private static void initializeMedia(List<String> fileNamesList,
                                        Property<Media> mediaProperty,
                                        Property<MediaPlayer> mediaPlayerProperty,
                                        MediaListeners mediaListeners)
    {
        fileNamesList.add(new File("/home/chichard/Музыка/timberlake.mp3").toURI().toString());
        fileNamesList.add(new File("/home/chichard/Музыка/lalala.mp3").toURI().toString());

        mediaProperty.setValue(new Media(fileNamesList.getFirst()));
        mediaPlayerProperty.setValue(new MediaPlayer(mediaProperty.getValue()));

        mediaProperty.getValue().getMetadata().addListener(mediaListeners.getMetaDataChangeListenger());
        mediaPlayerProperty.getValue().currentTimeProperty().addListener(mediaListeners.getDurationChangeListener());
        mediaPlayerProperty.getValue().setOnEndOfMedia(mediaListeners.getOnEndMediaListener());
    }

    public InitializeMediaCommand(List<String> fileNamesList,
                                  Property<Media> mediaProperty,
                                  Property<MediaPlayer> mediaPlayerProperty,
                                  MediaListeners mediaListeners)
    {
        super(() -> new Action() {
            @Override
            protected void action()  {
                initializeMedia(fileNamesList, mediaProperty, mediaPlayerProperty, mediaListeners);
            }
        });
    }

}