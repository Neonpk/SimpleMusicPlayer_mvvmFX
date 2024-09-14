package com.musicplayer.app.commands.media_commands;

import com.musicplayer.app.models.SwitchAudioCmdParam;
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

    private static int index = 0;

    private static List<String> fileNamesList;
    private static Property<Media> mediaProperty;
    private static Property<MediaPlayer> mediaPlayerProperty;
    private static HashMap<String, Object> metaDataHash;
    private static Property<Number> selectedVolume;
    private static ChangeListener<Duration> durationChangeListener;
    private static MapChangeListener<String, Object> metaDataListenger;
    private static Runnable onEndMediaListener;

    private static void disposeOldMedia() {

        Media oldMedia = mediaProperty.getValue();
        MediaPlayer oldMediaPlayer = mediaPlayerProperty.getValue();
        oldMedia.getMetadata().removeListener(metaDataListenger);
        oldMediaPlayer.currentTimeProperty().removeListener(durationChangeListener);
        oldMediaPlayer.setOnEndOfMedia(null);
        oldMediaPlayer.stop();
        oldMediaPlayer.dispose();
    }

    private static void setNewMedia() {

        metaDataHash.clear();
        Media newMedia = new Media( fileNamesList.get( index ) );
        MediaPlayer newMediaPlayer = new MediaPlayer(newMedia);
        newMedia.getMetadata().addListener(metaDataListenger);
        newMediaPlayer.setVolume( selectedVolume.getValue().floatValue() / 100 );
        newMediaPlayer.currentTimeProperty().addListener(durationChangeListener);
        newMediaPlayer.setOnEndOfMedia(onEndMediaListener);
        mediaProperty.setValue(newMedia);
        mediaPlayerProperty.setValue(newMediaPlayer);
    }

    private static void next(SwitchAudioCmdParam switchAudioCmdParam) {

        fileNamesList = switchAudioCmdParam.getFileNamesList();
        mediaProperty = switchAudioCmdParam.getMediaProperty();
        mediaPlayerProperty = switchAudioCmdParam.getMediaPlayerProperty();
        metaDataHash = switchAudioCmdParam.getMetaDataHash();
        selectedVolume = switchAudioCmdParam.getSelectedVolumeProperty();
        durationChangeListener = switchAudioCmdParam.getDurationChangeListener();
        metaDataListenger = switchAudioCmdParam.getMetaDataChangeListenger();
        onEndMediaListener = switchAudioCmdParam.getOnEndMediaListener();

        Property<Number> selectedAudioIndex = switchAudioCmdParam.getSelectedAudioIndex();
        StringProperty playButtonTextProperty = switchAudioCmdParam.getPlayButtonTextProperty();

        int count = fileNamesList.size();
        index = selectedAudioIndex.getValue().intValue();

        index = (index + 1) % count;

        disposeOldMedia();
        setNewMedia();

        playButtonTextProperty.setValue("||");
        mediaPlayerProperty.getValue().play();

        selectedAudioIndex.setValue(index);
    }

    public SwitchNextAudioCommand(SwitchAudioCmdParam switchAudioCmdParam) {
        super(() -> new Action() {
            @Override
            protected void action() {
                next(switchAudioCmdParam);
            }
        });
    }

}
