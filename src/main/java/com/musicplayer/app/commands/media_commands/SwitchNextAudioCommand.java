package com.musicplayer.app.commands.media_commands;

import com.musicplayer.app.models.CommandParams.SwitchAudioCmdParam;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import com.musicplayer.app.models.Track.Track;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

public class SwitchNextAudioCommand extends DelegateCommand {

    private static int index = 0;
    private static List<Track> trackList;

    private static Property<Media> mediaProperty;
    private static Property<MediaPlayer> mediaPlayerProperty;
    private static ChangeListener<Duration> durationChangeListener;
    private static MapChangeListener<String, Object> metaDataListenger;
    private static Runnable onReadyMediaListener;
    private static Runnable onEndMediaListener;

    private static void disposeOldMedia() {

        Media oldMedia = mediaProperty.getValue();
        MediaPlayer oldMediaPlayer = mediaPlayerProperty.getValue();
        oldMedia.getMetadata().removeListener(metaDataListenger);
        oldMediaPlayer.currentTimeProperty().removeListener(durationChangeListener);
        oldMediaPlayer.setOnReady(null);
        oldMediaPlayer.setOnEndOfMedia(null);
        oldMediaPlayer.stop();
        oldMediaPlayer.dispose();
    }

    private static void setNewMedia() {

        String fileUri = new File(trackList.get( index ).getFileName()).toURI().toString();
        Media newMedia = new Media( fileUri );
        newMedia.getMetadata().addListener(metaDataListenger);
        MediaPlayer newMediaPlayer = new MediaPlayer(newMedia);
        newMediaPlayer.setOnReady(onReadyMediaListener);
        newMediaPlayer.currentTimeProperty().addListener(durationChangeListener);
        newMediaPlayer.setOnEndOfMedia(onEndMediaListener);
        mediaProperty.setValue(newMedia);
        mediaPlayerProperty.setValue(newMediaPlayer);
    }

    private static void next(SwitchAudioCmdParam switchAudioCmdParam) {

        trackList = switchAudioCmdParam.getTrackList();
        mediaProperty = switchAudioCmdParam.getMediaProperty();
        mediaPlayerProperty = switchAudioCmdParam.getMediaPlayerProperty();

        durationChangeListener = switchAudioCmdParam.getDurationChangeListener();
        metaDataListenger = switchAudioCmdParam.getMetaDataChangeListenger();
        onEndMediaListener = switchAudioCmdParam.getOnEndMediaListener();
        onReadyMediaListener = switchAudioCmdParam.getOnReadyMediaListener();

        Property<Number> selectedAudioIndex = switchAudioCmdParam.getSelectedAudioIndex();

        int count = trackList.size();
        index = selectedAudioIndex.getValue().intValue();

        index = (index + 1) % count;

        disposeOldMedia();
        setNewMedia();

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
