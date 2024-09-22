package com.musicplayer.app.commands.track_commands;

import com.musicplayer.app.models.Track.Track;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;

import java.util.List;

public class DeleteMissingTracksCommand extends DelegateCommand {

    private static void deleteMissingTracks(List<Track> playlistTracks) {
        playlistTracks.removeIf(track -> {
            if(track.isMissing()) {
                System.out.printf("The missing track (%s) was removed from the cache \n", track.getFileName());
            }
            return track.isMissing();
        });
    }

    public DeleteMissingTracksCommand(List<Track> playlistTracks) {
        super(() -> new Action() {
            @Override
            protected void action() {
                deleteMissingTracks(playlistTracks);
            }
        });
    }

}
