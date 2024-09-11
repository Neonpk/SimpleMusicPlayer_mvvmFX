package com.musicplayer.app.commands.media_commands;

import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.media.MediaPlayer;

public class PlayPauseCommand extends DelegateCommand {

    private static void playPause(MediaPlayer mediaPlayer, StringProperty playButtonText) {

        switch(mediaPlayer.getStatus()) {
            case MediaPlayer.Status.PAUSED, MediaPlayer.Status.READY, MediaPlayer.Status.STOPPED -> {
                playButtonText.setValue("||");
                mediaPlayer.play();
            }
            case MediaPlayer.Status.PLAYING -> {
                playButtonText.setValue(">");
                mediaPlayer.pause();
            }
            default -> {
                playButtonText.setValue(">");
                mediaPlayer.stop();
            }
        }
    }

    public PlayPauseCommand(Property<MediaPlayer> mediaPlayer, StringProperty playButtonText) {
        super(() -> new Action() {
            @Override
            protected void action() {
                playPause(mediaPlayer.getValue(), playButtonText);
            }
        });
    }
}
