package com.musicplayer.app.commands.media_commands;

import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.scene.media.MediaPlayer;

import javafx.util.Duration;

public class SeekAudioCommand extends DelegateCommand {

    private static void rewindAudio(MediaPlayer mediaPlayer, Property<Number> selectedProgress) {
        Duration selectedTime = new Duration(mediaPlayer.getTotalDuration().toMillis() * (selectedProgress.getValue().doubleValue() / 100) );
        mediaPlayer.seek( selectedTime );
    }

    public SeekAudioCommand(MediaPlayer mediaPlayer, Property<Number> selectedProgress) {
        super(() -> new Action() {
            @Override
            protected void action() {
                rewindAudio(mediaPlayer, selectedProgress);
            }
        });
    }
}
