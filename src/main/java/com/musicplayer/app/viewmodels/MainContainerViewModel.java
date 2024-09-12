package com.musicplayer.app.viewmodels;

import com.musicplayer.app.AppStarter;
import com.musicplayer.app.commands.media_commands.*;
import com.musicplayer.app.models.MediaListeners;
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

    private final MediaListeners mediaListeners = new MediaListeners(this);

    // Commands

    private final Command playPauseCommand = new PlayPauseCommand(mediaPlayer, playButtonText);
    private final Command volumeControlCommand = new VolumeControlCommand(mediaPlayer, selectedVolume);
    private final Command muteAudioCommand = new MuteAudioCommand(mediaPlayer, muteButtonText);
    private final Command seekAudioCommand = new SeekAudioCommand(mediaPlayer, selectedProgress);
    private final Command repeatAudioCommand = new RepeatAudioCommand(mediaPlayer, repeatStatus, repeatButtonText);

    private final Command switchNextAudioCommand = mediaListeners.getSwitchNextAudioCommand();
    private final Command switchPrevAudioCommand = mediaListeners.getSwitchPrevAudioCommand();

    // Constructor

    public MainContainerViewModel() {

        fileNamesList.add(new File("/home/chichard/Музыка/timberlake.mp3").toURI().toString());
        fileNamesList.add(new File("/home/chichard/Музыка/lalala.mp3").toURI().toString());

        media.setValue(new Media(fileNamesList.getFirst()));
        mediaPlayer.setValue(new MediaPlayer(media.getValue()));

        media.getValue().getMetadata().addListener(mediaListeners.getMetaDataListenger());
        mediaPlayer.getValue().currentTimeProperty().addListener(mediaListeners.getDurationChangeListener());
        mediaPlayer.getValue().setOnEndOfMedia(mediaListeners.getOnEndMediaListener());
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

    // -> Fields //

    public List<String> getFileNamesList() {
        return fileNamesList;
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

    public Property<Media> getMediaProperty() {
        return media;
    }

    public Property<MediaPlayer> getMediaPlayerProperty() {
        return mediaPlayer;
    }

    public Property<Boolean> getRepeatStatusProperty() {
        return repeatStatus;
    }

    public Property<Number> getSelectedAudioIndex() {
        return selectedAudioIndex;
    }
}
