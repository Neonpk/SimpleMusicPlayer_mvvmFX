package com.musicplayer.app.commands.media_commands;

import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;

public class SwitchNextAudioCommand extends DelegateCommand {

    private static void disposeOldMedia(Property<Media> mediaProperty,
                                        Property<MediaPlayer> mediaPlayerProperty,
                                        ChangeListener<Duration> durationChangeListener,
                                        MapChangeListener<String, Object> metaDataListenger) {

        Media oldMedia = mediaProperty.getValue();
        MediaPlayer oldMediaPlayer = mediaPlayerProperty.getValue();
        oldMedia.getMetadata().removeListener(metaDataListenger);
        oldMediaPlayer.currentTimeProperty().removeListener(durationChangeListener);
        oldMediaPlayer.setOnEndOfMedia(null);
        oldMediaPlayer.stop();
        oldMediaPlayer.dispose();
    }

    private static void setNewMedia(String filePath,
                                    Property<Media> mediaProperty,
                                    Property<MediaPlayer> mediaPlayerProperty,
                                    HashMap<String, Object> metaDataHash,
                                    Property<Number> selectedVolume,
                                    ChangeListener<Duration> durationChangeListener,
                                    MapChangeListener<String, Object> metaDataListenger,
                                    Runnable onEndMediaListener) {

        metaDataHash.clear();
        Media newMedia = new Media(filePath);
        MediaPlayer newMediaPlayer = new MediaPlayer(newMedia);
        newMedia.getMetadata().addListener(metaDataListenger);
        newMediaPlayer.setVolume( selectedVolume.getValue().floatValue() / 100 );
        newMediaPlayer.currentTimeProperty().addListener(durationChangeListener);
        newMediaPlayer.setOnEndOfMedia(onEndMediaListener);
        mediaProperty.setValue(newMedia);
        mediaPlayerProperty.setValue(newMediaPlayer);
    }

    private static void next(List<String> fileNamesList,
                             Property<Media> mediaProperty,
                             Property<MediaPlayer> mediaPlayerProperty,
                             HashMap<String, Object> metaDataHash,
                             StringProperty playButtonTextProperty,
                             Property<Number> selectedVolume,
                             Property<Number> selectedAudioIndex,
                             ChangeListener<Duration> durationChangeListener,
                             MapChangeListener<String, Object> metaDataListenger,
                             Runnable onEndMediaListener
                             ) {

        int count = fileNamesList.size();
        int index = selectedAudioIndex.getValue().intValue();

        index = (index + 1) % count;
        String filePath = fileNamesList.get(index);

        disposeOldMedia(mediaProperty, mediaPlayerProperty, durationChangeListener, metaDataListenger);
        setNewMedia(
                filePath, mediaProperty, mediaPlayerProperty, metaDataHash,
                selectedVolume, durationChangeListener, metaDataListenger, onEndMediaListener
        );

        playButtonTextProperty.setValue("||");
        mediaPlayerProperty.getValue().play();

        selectedAudioIndex.setValue(index);
    }

    public SwitchNextAudioCommand(List<String> fileNamesList,
                                  Property<Media> mediaProperty,
                                  Property<MediaPlayer> mediaPlayerProperty,
                                  HashMap<String, Object> metaDataHash,
                                  StringProperty playButtonTextProperty,
                                  Property<Number> selectedVolume,
                                  Property<Number> selectedAudioIndex,
                                  ChangeListener<Duration> durationChangeListener,
                                  MapChangeListener<String, Object> metaDataListenger,
                                  Runnable onEndMediaListener
    ) {
        super(() -> new Action() {
            @Override
            protected void action() {
                next(
                        fileNamesList, mediaProperty, mediaPlayerProperty, metaDataHash, playButtonTextProperty,
                        selectedVolume, selectedAudioIndex, durationChangeListener, metaDataListenger, onEndMediaListener
                );
            }
        });
    }

}
