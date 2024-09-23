package com.musicplayer.app.commands.track_commands;

import com.musicplayer.app.models.Track.Track;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;

public class DeleteMissingTracksCommand extends DelegateCommand {

    private static void deleteMissingTracks(Property<ObservableList<Track>> tracksProperty) {

        ObservableList<Track> tracks = tracksProperty.getValue();

        tracks.removeIf(track -> {
            if(track.isMissing()) {
                System.out.printf("The missing track (%s) was removed from the cache \n", track.getFileName());
            }
            return track.isMissing();
        });
    }

    public DeleteMissingTracksCommand(Property<ObservableList<Track>> tracksProperty) {
        super(() -> new Action() {
            @Override
            protected void action() {
                deleteMissingTracks(tracksProperty);
            }
        });
    }

}
