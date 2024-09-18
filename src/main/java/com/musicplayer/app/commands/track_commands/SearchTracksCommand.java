package com.musicplayer.app.commands.track_commands;

import com.musicplayer.app.models.Track.Track;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.StringProperty;
import javafx.collections.transformation.FilteredList;

import java.util.Date;

public class SearchTracksCommand extends DelegateCommand {

    private static void searchTracks(FilteredList<Track> filteredTrackList, StringProperty searchTextProperty) {

        String searchText = searchTextProperty.getValueSafe().toLowerCase();

        if(searchText.trim().isEmpty()) {
            filteredTrackList.setPredicate(_ -> true);
            return;
        }

        filteredTrackList.setPredicate(track ->
                searchText.startsWith(String.valueOf(track.getId()).toLowerCase())
                ||
                searchText.startsWith(track.getFileName().toLowerCase())
                ||
                searchText.startsWith(new Date(track.getAdded()).toString().toLowerCase())
        );

    }

    public SearchTracksCommand(FilteredList<Track> filteredTrackList, StringProperty searchTextProperty) {
        super(() -> new Action() {
            @Override
            protected void action() {
                searchTracks(filteredTrackList, searchTextProperty);
            }
        });
    }

}
