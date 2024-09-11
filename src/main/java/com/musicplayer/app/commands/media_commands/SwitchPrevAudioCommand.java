package com.musicplayer.app.commands.media_commands;

import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.List;

public class SwitchPrevAudioCommand extends DelegateCommand {

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
                                    ChangeListener<Duration> durationChangeListener,
                                    MapChangeListener<String, Object> metaDataListenger,
                                    Runnable onEndMediaListener) {

        Media newMedia = new Media(filePath);
        MediaPlayer newMediaPlayer = new MediaPlayer(newMedia);
        newMedia.getMetadata().addListener(metaDataListenger);
        newMediaPlayer.currentTimeProperty().addListener(durationChangeListener);
        newMediaPlayer.setOnEndOfMedia(onEndMediaListener);

        mediaProperty.setValue(newMedia);
        mediaPlayerProperty.setValue(newMediaPlayer);
    }

    private static void prev(List<String> fileNamesList,
                             Property<Media> mediaProperty,
                             Property<MediaPlayer> mediaPlayerProperty,
                             Property<Number> selectedAudioIndex,
                             ChangeListener<Duration> durationChangeListener,
                             MapChangeListener<String, Object> metaDataListenger,
                             Runnable onEndMediaListener
                             ) {

        int count = fileNamesList.size();
        int index = selectedAudioIndex.getValue().intValue();

        index = Math.abs(index - 1) % count;

        String filePath = fileNamesList.get(index);

        disposeOldMedia(mediaProperty, mediaPlayerProperty, durationChangeListener, metaDataListenger);
        setNewMedia(filePath, mediaProperty, mediaPlayerProperty, durationChangeListener, metaDataListenger, onEndMediaListener);

        mediaPlayerProperty.getValue().play();

        selectedAudioIndex.setValue(index);

    }

    public SwitchPrevAudioCommand(List<String> fileNamesList,
                                  Property<Media> mediaProperty,
                                  Property<MediaPlayer> mediaPlayerProperty,
                                  Property<Number> selectedAudioIndex,
                                  ChangeListener<Duration> durationChangeListener,
                                  MapChangeListener<String, Object> metaDataListenger,
                                  Runnable onEndMediaListener
                                        ) {
        super(() -> new Action() {
            @Override
            protected void action() throws Exception {
                prev(
                        fileNamesList, mediaProperty, mediaPlayerProperty,
                        selectedAudioIndex, durationChangeListener, metaDataListenger, onEndMediaListener
                );
            }
        });
    }

}
