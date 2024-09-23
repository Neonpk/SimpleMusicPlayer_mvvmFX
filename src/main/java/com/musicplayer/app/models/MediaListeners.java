package com.musicplayer.app.models;

import com.musicplayer.app.AppStarter;
import com.musicplayer.app.commands.media_commands.SwitchNextAudioCommand;
import com.musicplayer.app.commands.media_commands.SwitchPrevAudioCommand;
import com.musicplayer.app.models.CommandParams.SwitchAudioCmdParam;
import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.models.Track.Track;
import com.musicplayer.app.models.Track.TrackCollectionListener;
import com.musicplayer.app.models.Track.TrackMetadataListener;
import com.musicplayer.app.models.Track.TrackMetadataListenerParam;
import com.musicplayer.app.services.PlaylistJsonProvider;
import com.musicplayer.app.viewmodels.MainViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.util.Duration;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MediaListeners {

    private Property<MediaPlayer> mediaPlayerProperty;
    private Property<Boolean> sliderProgressUpdateProperty;
    private StringProperty timePositionTextProperty;
    private StringProperty timeDurationTextProperty;
    private Property<Number> selectedProgressProperty;
    private final Property<Boolean> muteStatusProperty;
    private final Property<Boolean> repeatStatusProperty;
    private final Property<Number> selectedVolumeProperty;
    private final StringProperty playButtonTextProperty;

    private final StringProperty artistTextProperty;
    private final StringProperty titleTextProperty;
    private final Property<Image> imageCoverProperty;

    private final Image defaultCover = new Image( Objects.requireNonNull(AppStarter.class.getResource("images/nocover.jpg")).toString() );

    @Getter
    private final Command switchNextAudioCommand;
    @Getter
    private final Command switchPrevAudioCommand;

    @Getter
    private final MapChangeListener<String, Object> metaDataChangeListener;

    @Getter
    private final ListChangeListener<Track> trackCollectionListener;

    @Getter
    private final HashMap<String, Object> metaDataHash = new HashMap<>();

    @Getter
    private final ChangeListener<Duration> durationChangeListener = (_, _, newValue) -> {

        sliderProgressUpdateProperty.setValue(true);

        try {
            Duration duration = mediaPlayerProperty.getValue().getTotalDuration();
            double durationSeconds = duration.toSeconds();
            double durationMinutes = duration.toMinutes();

            double posSeconds = newValue.toSeconds();
            double posMinutes = newValue.toMinutes();

            timePositionTextProperty.setValue(String.format("%02d:%02d", (int) posMinutes % 60, (int) posSeconds % 60));
            timeDurationTextProperty.setValue(String.format("%02d:%02d", (int) durationMinutes % 60, (int) durationSeconds % 60));

            selectedProgressProperty.setValue(posSeconds / durationSeconds * 100);
        } finally {
            sliderProgressUpdateProperty.setValue(false);
        }

    };

    @Getter
    private final Runnable onReadyMediaListener = new Runnable() {
        @Override
        public void run() {
            metaDataHash.clear();
            playButtonTextProperty.setValue("||");
            mediaPlayerProperty.getValue().setMute( muteStatusProperty.getValue() );
            mediaPlayerProperty.getValue().setVolume( selectedVolumeProperty.getValue().floatValue() / 100 );
        }
    };

    @Getter
    private final Runnable onStoppedMediaListener = new Runnable() {
        @Override
        public void run() {
            metaDataHash.clear();

            artistTextProperty.setValue("Неизвестный артист");
            titleTextProperty.setValue("Неизвестный заголовок");
            imageCoverProperty.setValue( defaultCover );

            timePositionTextProperty.setValue("00:00");
            timeDurationTextProperty.setValue("00:00");
            selectedProgressProperty.setValue(0);

            playButtonTextProperty.setValue(">");
        }
    };

    @Getter
    private final Runnable onEndMediaListener = new Runnable() {
        @Override
        public void run() {

            boolean isRepeat = repeatStatusProperty.getValue();
            if(!isRepeat) switchNextAudioCommand.execute();
        }
    };

    public MediaListeners(MainViewModel mainViewModel, PlaylistJsonProvider playlistJsonProvider) {

        this.mediaPlayerProperty = mainViewModel.getMediaPlayerProperty();
        this.sliderProgressUpdateProperty = mainViewModel.getSliderProgressUpdateProperty();
        this.timePositionTextProperty = mainViewModel.getTimePositionTextProperty();
        this.timeDurationTextProperty = mainViewModel.getTimeDurationTextProperty();
        this.playButtonTextProperty = mainViewModel.getPlayButtonTextProperty();
        this.selectedProgressProperty = mainViewModel.getSelectedProgressProperty();
        this.selectedVolumeProperty = mainViewModel.getSelectedVolumeProperty();
        this.muteStatusProperty = mainViewModel.getMuteStatusProperty();
        this.repeatStatusProperty = mainViewModel.getRepeatStatusProperty();

        this.artistTextProperty = mainViewModel.getArtistTextProperty();
        this.titleTextProperty = mainViewModel.getTitleTextProperty();
        this.imageCoverProperty = mainViewModel.getImageCoverProperty();

        Property<Playlist> selectedPlaylistProperty = mainViewModel.getSelectedPlaylistProperty();
        Property<Number> selectedAudioIndexProperty = mainViewModel.getSelectedAudioIndexProperty();
        List<Track> trackListQueue = mainViewModel.getTrackListQueue();
        Property<Media> mediaProperty = mainViewModel.getMediaProperty();

        metaDataChangeListener = new TrackMetadataListener(
                new TrackMetadataListenerParam(titleTextProperty, artistTextProperty, imageCoverProperty, metaDataHash)
        ).getMetaDataChangeListenger();

        trackCollectionListener = new TrackCollectionListener(
                playlistJsonProvider, selectedPlaylistProperty, trackListQueue
        ).getTrackCollectionListener();

        mainViewModel.getTrackCollectionListener().setValue( trackCollectionListener );
        mainViewModel.getMetaDataChangeListener().setValue( metaDataChangeListener );
        mainViewModel.getDurationChangeListener().setValue( durationChangeListener );
        mainViewModel.getOnReadyMediaListener().setValue( onReadyMediaListener );
        mainViewModel.getOnStoppedMediaListener().setValue( onStoppedMediaListener );
        mainViewModel.getOnEndMediaListener().setValue( onEndMediaListener );

        SwitchAudioCmdParam switchAudioCmdParam = new SwitchAudioCmdParam(
                trackListQueue, mediaProperty, mediaPlayerProperty,
                selectedAudioIndexProperty, onReadyMediaListener,
                durationChangeListener, metaDataChangeListener, onEndMediaListener
        );

        this.switchNextAudioCommand = new SwitchNextAudioCommand(switchAudioCmdParam);
        this.switchPrevAudioCommand = new SwitchPrevAudioCommand(switchAudioCmdParam);

    }

}
