package com.musicplayer.app.viewmodels;

import com.musicplayer.app.AppStarter;
import com.musicplayer.app.commands.media_commands.*;
import com.musicplayer.app.models.MainContainerVmProperties;
import com.musicplayer.app.models.MetadataSnd;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.Image;

import java.util.Objects;

//import java.util.PriorityQueue;

public class MainContainerViewModel implements ViewModel {

    // Properties (fields)

    private final ObservableList<String> playlist = FXCollections.observableArrayList();

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

    private final Media media = new Media("file:///home/chichard/Музыка/timberlake.mp3");
    private final MediaPlayer mediaPlayer = new MediaPlayer(media);

    // Commands

    private final Command playPauseCommand = new PlayPauseCommand(mediaPlayer, playButtonText);
    private final Command volumeControlCommand = new VolumeControlCommand(mediaPlayer, selectedVolume);
    private final Command muteAudioCommand = new MuteAudioCommand(mediaPlayer, muteButtonText);
    private final Command seekAudioCommand = new SeekAudioCommand(mediaPlayer, selectedProgress);
    private final Command repeatAudioCommand = new RepeatAudioCommand(mediaPlayer, repeatStatus, repeatButtonText);

    private final Command initializeMetadataListenerCommand = new InitializeMetadataListenerCommand(
            mediaPlayer.getMedia(),
            new MetadataSnd(titleText, artistText, fileNameText, imageCover)
    );

    private final Command initializeTimeListenerCommand = new InitializeTimeListenerCommand(
            new MainContainerVmProperties(
                mediaPlayer, selectedProgress, selectedVolume, timePositionText,
                    timeDurationText, playButtonText, sliderProgressUpdate, repeatStatus
            )
    );

    // Constructor

    public MainContainerViewModel() {
        initializeMetadataListenerCommand.execute();
        initializeTimeListenerCommand.execute();
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
