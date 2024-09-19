package com.musicplayer.app.commands.track_commands;

import com.musicplayer.app.models.Track.Track;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.StringProperty;
import javafx.collections.transformation.FilteredList;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchTracksCommand extends DelegateCommand {

    private static void searchTracks(FilteredList<Track> filteredTrackList, StringProperty searchTextProperty) {

        String searchText = searchTextProperty.getValueSafe().toLowerCase();

        if(searchText.trim().isEmpty()) {
            filteredTrackList.setPredicate(_ -> true);
            return;
        }

        filteredTrackList.setPredicate(track ->
            track.getMetaData().getOrDefault("artist", "").toString().toLowerCase().startsWith(searchText)
            ||
            track.getMetaData().getOrDefault("title", "").toString().toLowerCase().startsWith(searchText)
            ||
            track.getFileName().replaceFirst(".*/(.*\\.(?:mp3|mp4))","$1").toLowerCase().startsWith(searchText)
            ||
            String.valueOf(track.getId()).startsWith(searchText)
            ||
            new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
                    .format(new Date(track.getAdded())).startsWith(searchText)
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
