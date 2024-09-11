package com.musicplayer.app.commands.media_commands;

import com.musicplayer.app.viewmodels.MainContainerViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.scene.media.MediaPlayer;

public class VolumeControlCommand extends DelegateCommand {

    private static void setVolume(MediaPlayer mediaPlayer, Property<Number> selectedVolume) {
        mediaPlayer.setVolume( selectedVolume.getValue().floatValue() / 100 );
    }

    public VolumeControlCommand(Property<MediaPlayer> mediaPlayer, Property<Number> selectedVolume) {
        super(() -> new Action() {
            @Override
            protected void action() {
                setVolume(mediaPlayer.getValue(), selectedVolume);
            }
        });
    }
}
