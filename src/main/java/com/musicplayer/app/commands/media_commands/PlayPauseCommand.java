package com.musicplayer.app.commands.media_commands;

import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;

public class PlayPauseCommand extends DelegateCommand {

    private static void PlayPause() {

        //Business logic
    }

    public PlayPauseCommand()  {
        super(() -> new Action() {
            @Override
            protected void action() {
                PlayPause();
            }
        });
    }
}
