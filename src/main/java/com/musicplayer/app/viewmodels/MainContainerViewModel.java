package com.musicplayer.app.viewmodels;

import com.musicplayer.app.AppStarter;
import com.musicplayer.app.commands.media_commands.*;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.io.File;
import java.util.*;

//import java.util.PriorityQueue;

public class MainContainerViewModel implements ViewModel {

    // Properties (fields)

    private final Property<Number> selectedAudioIndex = new SimpleObjectProperty<>(0);

    private final Property<Number> selectedVolume = new SimpleObjectProperty<>(50.0f);
    private final Property<Number> selectedProgress = new SimpleObjectProperty<>(0.0f);

    private final StringProperty timePositionText = new SimpleStringProperty("00:00");
    private final StringProperty timeDurationText = new SimpleStringProperty("00:00");

    private final StringProperty artistText = new SimpleStringProperty("Unknown artist");
    private final StringProperty titleText = new SimpleStringProperty("Unknown title");
    private final StringProperty fileNameText = new SimpleStringProperty("FileName not found.");

    private final StringProperty playButtonText = new SimpleStringProperty(">");
    private final StringProperty muteButtonText = new SimpleStringProperty("Mute");
    private final StringProperty repeatButtonText = new SimpleStringProperty("Repeat");

    private final Property<Boolean> sliderProgressUpdate = new SimpleObjectProperty<>(false);
    private final Property<Boolean> repeatStatus = new SimpleObjectProperty<>(false);

    private final Property<Image> imageCover = new SimpleObjectProperty<>(
            new Image( Objects.requireNonNull(AppStarter.class.getResource("images/nocover.jpg")).toString() )
    );

    // Fields
    private final ObservableList<String> playlist = FXCollections.observableArrayList();

    private final List<String> fileNamesList = new ArrayList<>();
    private final Property<Media> media = new SimpleObjectProperty<>();
    private final Property<MediaPlayer> mediaPlayer = new SimpleObjectProperty<>();

    // Fields => Listeners

    private final MapChangeListener<String, Object> metaDataListenger = (MapChangeListener<String, Object>) ch -> {

        if (ch.wasAdded()) {
            switch(String.valueOf(ch.getKey())) {
                case "artist" -> artistText.setValue(ch.getValueAdded().toString());
                case "title" -> titleText.setValue(ch.getValueAdded().toString());
                case "image" -> imageCover.setValue((Image) ch.getValueAdded());
                case "raw metadata" -> {
                    fileNameText.setValue(mediaPlayer.getValue().getMedia().getSource().replaceFirst(".*/(.*\\.(?:mp3|mp4))","$1"));
                }
            }
        }
    };

    private final ChangeListener<Duration> durationChangeListener = (observableValue, oldValue, newValue) -> {

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

    // Commands

    private final Command playPauseCommand = new PlayPauseCommand(mediaPlayer, playButtonText);
    private final Command volumeControlCommand = new VolumeControlCommand(mediaPlayer, selectedVolume);
    private final Command muteAudioCommand = new MuteAudioCommand(mediaPlayer, muteButtonText);
    private final Command seekAudioCommand = new SeekAudioCommand(mediaPlayer, selectedProgress);
    private final Command repeatAudioCommand = new RepeatAudioCommand(mediaPlayer, repeatStatus, repeatButtonText);

    private final Command switchNextAudioCommand = new SwitchNextAudioCommand(
            fileNamesList, media, mediaPlayer, selectedAudioIndex, durationChangeListener, metaDataListenger, onEndMediaListener
    );
    private final Command switchPrevAudioCommand = new SwitchPrevAudioCommand(
            fileNamesList, media, mediaPlayer, selectedAudioIndex, durationChangeListener, metaDataListenger, onEndMediaListener
    );

    // Constructor

    public MainContainerViewModel() {

        fileNamesList.add(new File("/home/chichard/Музыка/timberlake.mp3").toURI().toString());
        fileNamesList.add(new File("/home/chichard/Музыка/lalala.mp3").toURI().toString());

        media.setValue(new Media(fileNamesList.getFirst()));
        mediaPlayer.setValue(new MediaPlayer(media.getValue()));

        media.getValue().getMetadata().addListener(metaDataListenger);
        mediaPlayer.getValue().currentTimeProperty().addListener(durationChangeListener);
        mediaPlayer.getValue().setOnEndOfMedia(onEndMediaListener);
    }

    // Methods (getters and setters)

    // -> Commands //

    public Command getPlayPauseCommand() {
        return playPauseCommand;
    }

    public Command getVolumeControlCommand() {
        return volumeControlCommand;
    }

    public Command getMuteAudioCommand() {
        return muteAudioCommand;
    }

    public Command getSeekAudioCommand() {
        return seekAudioCommand;
    }

    public Command getRepeatAudioCommand() {
        return repeatAudioCommand;
    }

    public Command getSwitchNextAudioCommand() {
        return switchNextAudioCommand;
    }

    public Command getSwitchPrevAudioCommand() {
        return switchPrevAudioCommand;
    }

    // -> Properties //

    public ObservableList<String> getPlaylistProperty() {
        return playlist;
    }

    public Property<Number> getSelectedVolumeProperty() {
        return selectedVolume;
    }

    public Property<Number> getSelectedProgressProperty() {
        return selectedProgress;
    }

    public StringProperty getTimePositionProperty() {
        return timePositionText;
    }

    public StringProperty getTimeDurationProperty() {
        return timeDurationText;
    }

    public StringProperty getPlayButtonTextProperty() {
        return playButtonText;
    }

    public StringProperty getRepeatButtonTextProperty() {
        return repeatButtonText;
    }

    public StringProperty getMuteButtonTextProperty() {
        return muteButtonText;
    }

    public StringProperty getArtistTextProperty() {
        return artistText;
    }

    public StringProperty getTitleTextProperty() {
        return titleText;
    }

    public StringProperty getFileNameTextProperty() {
        return fileNameText;
    }

    public Property<Image> getImageCoverProperty() {
        return imageCover;
    }

    public Property<Boolean> getSliderProgressUpdateProperty() {
        return sliderProgressUpdate;
    }

}
