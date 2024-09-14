package com.musicplayer.app.models;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.util.Duration;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;

@Getter
public class SwitchAudioCmdParam {

    private final List<String> fileNamesList;
    private final Property<Media> mediaProperty;
    private final Property<MediaPlayer> mediaPlayerProperty;
    private final HashMap<String, Object> metaDataHash;
    private final StringProperty playButtonTextProperty;
    private final Property<Number> selectedVolumeProperty;
    private final Property<Number> selectedAudioIndex;
    private final ChangeListener<Duration> durationChangeListener;
    private final MapChangeListener<String, Object> metaDataChangeListenger;
    private final Runnable onEndMediaListener;

    public SwitchAudioCmdParam(List<String> fileNamesList,
                               Property<Media> mediaProperty,
                               Property<MediaPlayer> mediaPlayerProperty,
                               HashMap<String, Object> metaDataHash,
                               StringProperty playButtonTextProperty,
                               Property<Number> selectedVolume,
                               Property<Number> selectedAudioIndex,
                               ChangeListener<Duration> durationChangeListener,
                               MapChangeListener<String, Object> metaDataListenger,
                               Runnable onEndMediaListener) {

        this.fileNamesList = fileNamesList;
        this.mediaProperty = mediaProperty;
        this.mediaPlayerProperty = mediaPlayerProperty;
        this.metaDataHash = metaDataHash;
        this.playButtonTextProperty = playButtonTextProperty;
        this.selectedVolumeProperty = selectedVolume;
        this.selectedAudioIndex = selectedAudioIndex;
        this.durationChangeListener = durationChangeListener;
        this.metaDataChangeListenger = metaDataListenger;
        this.onEndMediaListener = onEndMediaListener;
    }

}
