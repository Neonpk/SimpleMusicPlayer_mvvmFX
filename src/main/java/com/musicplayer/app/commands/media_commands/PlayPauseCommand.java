package com.musicplayer.app.commands.media_commands;

import com.musicplayer.app.models.MainContainerVmProperties;
import com.musicplayer.app.viewmodels.MainContainerViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.collections.MapChangeListener;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.scene.image.Image;

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

    public PlayPauseCommand(MediaPlayer mediaPlayer, StringProperty playButtonText) {
        super(() -> new Action() {
            @Override
            protected void action() {
                playPause(mediaPlayer, playButtonText);
            }
        });
    }
}
