package com.musicplayer.app.commands.media_commands;

import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.StringProperty;
import javafx.scene.media.MediaPlayer;

public class MuteAudioCommand extends DelegateCommand {

    private static void mute(MediaPlayer mediaPlayer, StringProperty muteButtonText) {

        boolean isMuted = mediaPlayer.isMute();

        muteButtonText.setValue(!isMuted ? "UnMute" : "Mute");
        mediaPlayer.setMute(!isMuted);
    }

    public MuteAudioCommand(MediaPlayer mediaPlayer, StringProperty muteButtonText) {
        super(() -> new Action() {
            @Override
            protected void action() {
                mute(mediaPlayer, muteButtonText);
            }
        });
    }

}
