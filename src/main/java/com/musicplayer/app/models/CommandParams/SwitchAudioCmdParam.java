package com.musicplayer.app.models.CommandParams;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.util.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Getter
@SuppressWarnings("all")
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
}
