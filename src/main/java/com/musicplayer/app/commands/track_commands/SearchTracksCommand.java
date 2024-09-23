package com.musicplayer.app.commands.track_commands;

import com.musicplayer.app.models.Track.Track;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.collections.transformation.FilteredList;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchTracksCommand extends DelegateCommand {

    private static void searchTracks(Property<FilteredList<Track>> filteredTrackListProperty, StringProperty searchTextProperty) {

        FilteredList<Track> filteredTrackList = filteredTrackListProperty.getValue();
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

    public SearchTracksCommand(Property<FilteredList<Track>> filteredTrackListProperty, StringProperty searchTextProperty) {
        super(() -> new Action() {
            @Override
            protected void action() {
                searchTracks(filteredTrackListProperty, searchTextProperty);
            }
        });
    }

}
