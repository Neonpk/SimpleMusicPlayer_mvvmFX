package com.musicplayer.app.commands.track_commands;

import com.musicplayer.app.models.Track.Track;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;

public class DeleteTrackCommand extends DelegateCommand {

    private static void deleteTrack(Property<ObservableList<Track>> tracksProperty, Property<Track> selectedTrackProperty) {

        ObservableList<Track> tracks = tracksProperty.getValue();
        Track selectedTrack = selectedTrackProperty.getValue();

        tracks.remove(selectedTrack);
    }

    public DeleteTrackCommand(Property<ObservableList<Track>> tracksProperty, Property<Track> selectedTrackProperty) {
        super(() -> new Action() {
            @Override
            protected void action() {
                deleteTrack(tracksProperty, selectedTrackProperty);
            }
        });
    }

}
