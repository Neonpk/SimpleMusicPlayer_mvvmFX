package com.musicplayer.app.commands.media_commands;

import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.media.MediaPlayer;

public class MuteAudioCommand extends DelegateCommand {

    private static void mute(MediaPlayer mediaPlayer, Property<Boolean> muteStatusProperty, StringProperty muteButtonText) {

        muteStatusProperty.setValue( !muteStatusProperty.getValue() );

        muteButtonText.setValue(muteStatusProperty.getValue() ? "Вкл звук" : "Выкл звук");
        mediaPlayer.setMute(muteStatusProperty.getValue());
    }

    public MuteAudioCommand(Property<MediaPlayer> mediaPlayer, Property<Boolean> muteStatusProperty, StringProperty muteButtonText) {
        super(() -> new Action() {
            @Override
            protected void action() {
                mute(mediaPlayer.getValue(), muteStatusProperty, muteButtonText);
            }
        });
    }

}
