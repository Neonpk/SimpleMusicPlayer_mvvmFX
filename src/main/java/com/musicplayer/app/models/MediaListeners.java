package com.musicplayer.app.models;

import com.musicplayer.app.AppStarter;
import com.musicplayer.app.commands.media_commands.SwitchNextAudioCommand;
import com.musicplayer.app.commands.media_commands.SwitchPrevAudioCommand;
import com.musicplayer.app.viewmodels.MainContainerViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MediaListeners {

    private StringProperty artistText;
    private StringProperty titleText;
    private Property<Image> imageCover;
    private StringProperty fileNameText;
    private Property<Media> media;
    private Property<MediaPlayer> mediaPlayer;
    private Property<Boolean> sliderProgressUpdate;
    private StringProperty timePositionText;
    private StringProperty timeDurationText;
    private Property<Number> selectedProgress;
    private final Property<Boolean> repeatStatus;

    private final Command switchNextAudioCommand;
    private final Command switchPrevAudioCommand;

    private final Image defaultCover = new Image(Objects.requireNonNull(AppStarter.class.getResource("images/nocover.jpg")).toString());
    private final HashMap<String, Object> metaDataHash = new HashMap<>();

    private final MapChangeListener<String, Object> metaDataListenger = ch -> {
        if (ch.wasAdded()) {
            metaDataHash.put(String.valueOf(ch.getKey()), ch.getValueAdded());

            artistText.setValue( metaDataHash.getOrDefault("artist", "Unknown artist").toString() );
            titleText.setValue( metaDataHash.getOrDefault("title", "Unknown title").toString() );
            imageCover.setValue( (Image) metaDataHash.getOrDefault("image", defaultCover ) );

            if(ch.getKey().equals("raw metadata"))
                fileNameText.setValue( media.getValue().getSource().replaceFirst(".*/(.*\\.(?:mp3|mp4))","$1") );
        }
    };

    private final ChangeListener<Duration> durationChangeListener = (_, _, newValue) -> {

        sliderProgressUpdate.setValue(true);

        try {
            Duration duration = mediaPlayer.getValue().getTotalDuration();
            double durationSeconds = duration.toSeconds();
            double durationMinutes = duration.toMinutes();

            double posSeconds = newValue.toSeconds();
            double posMinutes = newValue.toMinutes();

            timePositionText.setValue(String.format("%02d:%02d", (int) posMinutes % 60, (int) posSeconds % 60));
            timeDurationText.setValue(String.format("%02d:%02d", (int) durationMinutes % 60, (int) durationSeconds % 60));

            selectedProgress.setValue(posSeconds / durationSeconds * 100);
        } finally {
            sliderProgressUpdate.setValue(false);
        }

    };

    private final Runnable onEndMediaListener = new Runnable() {
        @Override
        public void run() {
            boolean isRepeat = repeatStatus.getValue();
            if(!isRepeat) switchNextAudioCommand.execute();
        }
    };

    public MediaListeners(MainContainerViewModel mainContainerViewModel) {

        this.artistText = mainContainerViewModel.getArtistTextProperty();
        this.titleText = mainContainerViewModel.getTitleTextProperty();
        this.imageCover = mainContainerViewModel.getImageCoverProperty();
        this.fileNameText = mainContainerViewModel.getFileNameTextProperty();
        this.media = mainContainerViewModel.getMediaProperty();
        this.mediaPlayer = mainContainerViewModel.getMediaPlayerProperty();
        this.sliderProgressUpdate = mainContainerViewModel.getSliderProgressUpdateProperty();
        this.timePositionText = mainContainerViewModel.getTimePositionProperty();
        this.timeDurationText = mainContainerViewModel.getTimeDurationProperty();
        this.selectedProgress = mainContainerViewModel.getSelectedProgressProperty();
        this.repeatStatus = mainContainerViewModel.getRepeatStatusProperty();

        List<String> fileNamesList = mainContainerViewModel.getFileNamesList();
        Property<Number> selectedVolume = mainContainerViewModel.getSelectedVolumeProperty();
        Property<Number> selectedAudioIndex = mainContainerViewModel.getSelectedAudioIndex();
        StringProperty playButtonText = mainContainerViewModel.getPlayButtonTextProperty();

        this.switchNextAudioCommand = new SwitchNextAudioCommand(
                fileNamesList, media, mediaPlayer, metaDataHash,
                playButtonText, selectedVolume, selectedAudioIndex,
                durationChangeListener, metaDataListenger, onEndMediaListener
        );

        this.switchPrevAudioCommand = new SwitchPrevAudioCommand(
                fileNamesList, media, mediaPlayer, metaDataHash,
                playButtonText, selectedVolume, selectedAudioIndex,
                durationChangeListener, metaDataListenger, onEndMediaListener
        );

    }

    public ChangeListener<Duration> getDurationChangeListener() {
        return durationChangeListener;
    }

    public MapChangeListener<String, Object> getMetaDataListenger() {
        return metaDataListenger;
    }

    public Runnable getOnEndMediaListener() {
        return onEndMediaListener;
    }

    public Command getSwitchPrevAudioCommand() {
        return switchPrevAudioCommand;
    }

    public Command getSwitchNextAudioCommand() {
        return switchNextAudioCommand;
    }
}
