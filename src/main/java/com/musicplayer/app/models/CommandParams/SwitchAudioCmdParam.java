package com.musicplayer.app.models.CommandParams;

import com.musicplayer.app.models.Track.Track;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.util.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@SuppressWarnings("all")
public class SwitchAudioCmdParam {
    private final List<Track> trackList;
    private final Property<Media> mediaProperty;
    private final Property<MediaPlayer> mediaPlayerProperty;
    private final Property<Number> selectedAudioIndex;
    private final Runnable onReadyMediaListener;
    private final ChangeListener<Duration> durationChangeListener;
    private final MapChangeListener<String, Object> metaDataChangeListenger;
    private final Runnable onEndMediaListener;
}
