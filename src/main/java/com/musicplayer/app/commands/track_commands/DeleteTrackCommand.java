package com.musicplayer.app.commands.track_commands;

import com.musicplayer.app.models.CommandParams.DeleteTrackCmdParam;
import com.musicplayer.app.models.Track.Track;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.collections.ObservableList;

public class DeleteTrackCommand extends DelegateCommand {

    private static void deleteTrack(DeleteTrackCmdParam deleteTrackCmdParam) {

        Track selectedTrack = deleteTrackCmdParam.getSelectedTrackProperty().getValue();
        ObservableList<Track> tracks = deleteTrackCmdParam.getTracks();
        tracks.remove(selectedTrack);
    }

    public DeleteTrackCommand(DeleteTrackCmdParam deleteTrackCmdParam) {
        super(() -> new Action() {
            @Override
            protected void action() {
                deleteTrack(deleteTrackCmdParam);
            }
        });
    }

}
