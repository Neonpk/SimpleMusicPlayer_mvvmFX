package com.musicplayer.app.services;

import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.models.Track.Track;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@SuppressWarnings("all")
public class MediaProvider {
    private final ObservableList<Playlist> playlists;
    private final ArrayList<Track> trackList;
    private final Property<Number> selectedAudioIndexProperty;
    private final Property<Media> mediaProperty;
    private final Property<MediaPlayer> mediaPlayerProperty;
    private final Property<MapChangeListener<String, Object>> metaDataChangeListener;
    private final Property<ChangeListener<Duration>> durationChangeListener;
    private final Property<Runnable> onReadyMediaListener;
    private final Property<Runnable> onEndMediaListener;
}