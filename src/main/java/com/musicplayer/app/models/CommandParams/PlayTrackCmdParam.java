package com.musicplayer.app.models.CommandParams;

import com.musicplayer.app.models.Playlist.Playlist;
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

@Getter
@AllArgsConstructor
@SuppressWarnings("all")
public class PlayTrackCmdParam {
    private final List<Track> trackListQueue;
    private final Property<Track> selectedTrackProperty;
    private final Property<Number> selectedAudioIndexProperty;
    private final Property<Playlist> selectedPlaylistProperty;
    private final Property<Media> mediaProperty;
    private final Property<MediaPlayer> mediaPlayerProperty;
    private final Property<MapChangeListener<String, Object>> metaDataChangeListener;
    private final Property<ChangeListener<Duration>> durationChangeListener;
    private final Property<Runnable> onReadyMediaListener;
    private final Property<Runnable> onStoppedMediaListener;
    private final Property<Runnable> onEndMediaListener;
}
