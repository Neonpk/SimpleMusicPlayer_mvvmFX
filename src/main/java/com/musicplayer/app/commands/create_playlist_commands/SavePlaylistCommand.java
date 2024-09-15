package com.musicplayer.app.commands.create_playlist_commands;

import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;

public class SavePlaylistCommand extends DelegateCommand {

    private static void savePlaylist() {
        System.out.println(1);
    }

    public SavePlaylistCommand() {
        super(() -> new Action() {
            @Override
            protected void action() throws Exception {
                savePlaylist();
            }
        });
    }

}
