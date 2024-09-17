package com.musicplayer.app.models;

import com.musicplayer.app.AppStarter;
import com.musicplayer.app.commands.media_commands.SwitchNextAudioCommand;
import com.musicplayer.app.commands.media_commands.SwitchPrevAudioCommand;
import com.musicplayer.app.models.CommandParams.SwitchAudioCmdParam;
import com.musicplayer.app.viewmodels.MainViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
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

    @Getter
    private final Command switchNextAudioCommand;
    @Getter
    private final Command switchPrevAudioCommand;

    private final Image defaultCover = new Image(Objects.requireNonNull(AppStarter.class.getResource("images/nocover.jpg")).toString());
    private final HashMap<String, Object> metaDataHash = new HashMap<>();

    @Getter
    private final MapChangeListener<String, Object> metaDataChangeListenger = ch -> {
        if (ch.wasAdded()) {
            metaDataHash.put(String.valueOf(ch.getKey()), ch.getValueAdded());

            artistText.setValue( metaDataHash.getOrDefault("artist", "Неизвестный артист").toString() );
            titleText.setValue( metaDataHash.getOrDefault("title", "Неизвестный заголовок").toString() );
            imageCover.setValue( (Image) metaDataHash.getOrDefault("image", defaultCover ) );

            if(ch.getKey().equals("raw metadata"))
                fileNameText.setValue( media.getValue().getSource().replaceFirst(".*/(.*\\.(?:mp3|mp4))","$1") );
        }
    };

    @Getter
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

    @Getter
    private final Runnable onEndMediaListener = new Runnable() {
        @Override
        public void run() {
            boolean isRepeat = repeatStatus.getValue();
            if(!isRepeat) switchNextAudioCommand.execute();
        }
    };

    public MediaListeners(MainViewModel mainViewModel) {

        this.artistText = mainViewModel.getArtistTextProperty();
        this.titleText = mainViewModel.getTitleTextProperty();
        this.imageCover = mainViewModel.getImageCoverProperty();
        this.fileNameText = mainViewModel.getFileNameTextProperty();
        this.media = mainViewModel.getMediaProperty();
        this.mediaPlayer = mainViewModel.getMediaPlayerProperty();
        this.sliderProgressUpdate = mainViewModel.getSliderProgressUpdateProperty();
        this.timePositionText = mainViewModel.getTimePositionTextProperty();
        this.timeDurationText = mainViewModel.getTimeDurationTextProperty();
        this.selectedProgress = mainViewModel.getSelectedProgressProperty();
        this.repeatStatus = mainViewModel.getRepeatStatusProperty();

        List<String> fileNamesList = mainViewModel.getFileNamesList();
        StringProperty playButtonTextProperty = mainViewModel.getPlayButtonTextProperty();
        Property<Number> selectedVolumeProperty = mainViewModel.getSelectedVolumeProperty();
        Property<Number> selectedAudioIndexProperty = mainViewModel.getSelectedAudioIndexProperty();

        SwitchAudioCmdParam switchAudioCmdParam = new SwitchAudioCmdParam(
                fileNamesList, media, mediaPlayer, metaDataHash,
                playButtonTextProperty, selectedVolumeProperty, selectedAudioIndexProperty,
                durationChangeListener, metaDataChangeListenger, onEndMediaListener
        );

        this.switchNextAudioCommand = new SwitchNextAudioCommand(switchAudioCmdParam);
        this.switchPrevAudioCommand = new SwitchPrevAudioCommand(switchAudioCmdParam);

    }

}
