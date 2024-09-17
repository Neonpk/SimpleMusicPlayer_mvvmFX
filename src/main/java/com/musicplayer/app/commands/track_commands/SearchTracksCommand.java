package com.musicplayer.app.commands.track_commands;

import com.musicplayer.app.models.Track.Track;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class SearchTracksCommand extends DelegateCommand {

    private static void searchTracks(ObservableList<Track> tracks, StringProperty searchText) {

    }

    public SearchTracksCommand(ObservableList<Track> tracks, StringProperty searchText) {
        super(() -> new Action() {
            @Override
            protected void action() {
                searchTracks(tracks, searchText);
            }
        });
    }

}
