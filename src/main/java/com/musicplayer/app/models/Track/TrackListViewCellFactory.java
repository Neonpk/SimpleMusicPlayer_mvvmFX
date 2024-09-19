package com.musicplayer.app.models.Track;

import com.musicplayer.app.controls.TrackListViewCell;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class TrackListViewCellFactory implements Callback<ListView<Track>, ListCell<Track>> {

    @Override
    public ListCell<Track> call(ListView<Track> param) {
        return new TrackListViewCell();
    }
}