package com.musicplayer.app.commands.track_commands;

import com.musicplayer.app.models.CommandParams.PlayTrackCmdParam;
import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.models.Track.Track;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PlayPlaylistTracksCommand extends DelegateCommand {

    private static List<Track> playlistTracks;

    private static Property<Number> selectedAudioIndexProperty;

    private static Property<Media> mediaProperty;
    private static Property<MediaPlayer> mediaPlayerProperty;

    private static ChangeListener<Duration> durationChangeListener;
    private static MapChangeListener<String, Object> metaDataChangeListener;
    private static Runnable onReadyMediaListener;
    private static Runnable onStoppedMediaListener;
    private static Runnable onEndMediaListener;

    private static void disposeOldMedia() {

        Media oldMedia;
        MediaPlayer oldMediaPlayer;

        if((oldMedia = mediaProperty.getValue()) != null && (oldMediaPlayer = mediaPlayerProperty.getValue()) != null)
        {

            if(oldMediaPlayer.getStatus() == Status.STOPPED) {
                return;
            }

            onStoppedMediaListener.run();
            oldMedia.getMetadata().removeListener(metaDataChangeListener);
            oldMediaPlayer.currentTimeProperty().removeListener(durationChangeListener);
            oldMediaPlayer.setOnReady(null);
            oldMediaPlayer.setOnEndOfMedia(null);
            oldMediaPlayer.stop();
            oldMediaPlayer.dispose();
        }

    }

    private static void setNewMedia() {

        if(playlistTracks.isEmpty()) {
            System.out.println("Playlist is empty.");
            return;
        }

        if ( !Files.exists(Paths.get(playlistTracks.getFirst().getFileName())) ) {
            System.out.println("Track not found");
            disposeOldMedia();
            return;
        }

        Track firstTrack = playlistTracks.getFirst();
        String fileNameURI = new File( firstTrack.getFileName() ).toURI().toString();

        Media newMedia = new Media(fileNameURI);
        newMedia.getMetadata().addListener(metaDataChangeListener);
        MediaPlayer newMediaPlayer = new MediaPlayer(newMedia);
        newMediaPlayer.currentTimeProperty().addListener(durationChangeListener);
        newMediaPlayer.setOnReady(onReadyMediaListener);
        newMediaPlayer.setOnEndOfMedia(onEndMediaListener);

        selectedAudioIndexProperty.setValue(0);

        mediaProperty.setValue(newMedia);
        mediaPlayerProperty.setValue(newMediaPlayer);

        mediaPlayerProperty.getValue().play();
    }

    private static void playPlaylistTracks(PlayTrackCmdParam playTrackCmdParam) {

        Property<Playlist> selectedPlaylistProperty = playTrackCmdParam.getSelectedPlaylistProperty();

        playlistTracks = selectedPlaylistProperty.getValue().getTracks();
        selectedAudioIndexProperty = playTrackCmdParam.getSelectedAudioIndexProperty();
        mediaProperty = playTrackCmdParam.getMediaProperty();
        mediaPlayerProperty = playTrackCmdParam.getMediaPlayerProperty();

        // Listeners
        metaDataChangeListener = playTrackCmdParam.getMetaDataChangeListener().getValue();
        durationChangeListener = playTrackCmdParam.getDurationChangeListener().getValue();
        onReadyMediaListener = playTrackCmdParam.getOnReadyMediaListener().getValue();
        onStoppedMediaListener = playTrackCmdParam.getOnStoppedMediaListener().getValue();
        onEndMediaListener = playTrackCmdParam.getOnEndMediaListener().getValue();

        // TrackList Queue
        List<Track> trackListQueue = playTrackCmdParam.getTrackListQueue();

        trackListQueue.clear();
        trackListQueue.addAll(playlistTracks);

        disposeOldMedia();
        setNewMedia();
    }

    public PlayPlaylistTracksCommand(PlayTrackCmdParam playTrackCmdParam) {
        super(() -> new Action() {
            @Override
            protected void action() {
                playPlaylistTracks(playTrackCmdParam);
            }
        });
    }

}
