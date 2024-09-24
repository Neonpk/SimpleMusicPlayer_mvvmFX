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
    private static int count = 0;
    private static List<Track> trackListQueue;

    private static Property<Media> mediaProperty;
    private static Property<MediaPlayer> mediaPlayerProperty;
    private static ChangeListener<Duration> durationChangeListener;
    private static MapChangeListener<String, Object> metaDataListenger;
    private static Runnable onReadyMediaListener;
    private static Runnable onEndMediaListener;

    private static void disposeOldMedia() {

        Media oldMedia;
        MediaPlayer oldMediaPlayer;

        if((oldMedia = mediaProperty.getValue()) != null && (oldMediaPlayer = mediaPlayerProperty.getValue()) != null)
        {
            if(oldMediaPlayer.getStatus() == MediaPlayer.Status.STOPPED) {
                return;
            }

            oldMedia.getMetadata().removeListener(metaDataListenger);
            oldMediaPlayer.currentTimeProperty().removeListener(durationChangeListener);
            oldMediaPlayer.setOnReady(null);
            oldMediaPlayer.setOnEndOfMedia(null);
            oldMediaPlayer.stop();
            oldMediaPlayer.dispose();
        }
    }

    private static void setNewMedia() {

        // Check for missing files

        if(trackListQueue.stream().allMatch(Track::isMissing)) {
            System.out.println("Playback is not possible because all tracks are missing");
            return;
        }

        while(trackListQueue.get(index).isMissing()) {
            System.out.printf("Missing track (%s) was skipped \n", trackListQueue.get(index).getFileName());
            index = (index + 1) % count;
        }

        // Get Instance

        String fileUri = new File(trackListQueue.get( index ).getFileName()).toURI().toString();
        Media newMedia = new Media( fileUri );
        newMedia.getMetadata().addListener(metaDataListenger);
        MediaPlayer newMediaPlayer = new MediaPlayer(newMedia);
        newMediaPlayer.setOnReady(onReadyMediaListener);
        newMediaPlayer.currentTimeProperty().addListener(durationChangeListener);
        newMediaPlayer.setOnEndOfMedia(onEndMediaListener);
        mediaProperty.setValue(newMedia);
        mediaPlayerProperty.setValue(newMediaPlayer);

        mediaPlayerProperty.getValue().play();
    }

    private static void next(SwitchAudioCmdParam switchAudioCmdParam) {

        trackListQueue = switchAudioCmdParam.getTrackList();
        mediaProperty = switchAudioCmdParam.getMediaProperty();
        mediaPlayerProperty = switchAudioCmdParam.getMediaPlayerProperty();

        durationChangeListener = switchAudioCmdParam.getDurationChangeListener();
        metaDataListenger = switchAudioCmdParam.getMetaDataChangeListenger();
        onEndMediaListener = switchAudioCmdParam.getOnEndMediaListener();
        onReadyMediaListener = switchAudioCmdParam.getOnReadyMediaListener();

        Property<Number> selectedAudioIndex = switchAudioCmdParam.getSelectedAudioIndex();

        count = trackListQueue.size();
        index = selectedAudioIndex.getValue().intValue();

        index = (index + 1) % count;

        disposeOldMedia();
        setNewMedia();

        selectedAudioIndex.setValue(index);
    }

    public SwitchNextAudioCommand(SwitchAudioCmdParam switchAudioCmdParam) {
        super(() -> new Action() {
            @Override
            protected void action() {
                next(switchAudioCmdParam);
                System.gc();
            }
        });
    }

}
