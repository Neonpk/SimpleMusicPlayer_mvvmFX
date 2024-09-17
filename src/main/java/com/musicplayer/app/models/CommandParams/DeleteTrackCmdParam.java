package com.musicplayer.app.models.CommandParams;

import com.musicplayer.app.models.Track.Track;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteTrackCmdParam {
    private int playlistId;
    private ObservableList<Track> tracks;
    private Property<Track> selectedTrackProperty;
}
