package com.musicplayer.app.commands.media_commands;

import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.media.MediaPlayer;

public class RepeatAudioCommand extends DelegateCommand {

    private static void repeat(MediaPlayer mediaPlayer, Property<Boolean> isRepeat, StringProperty repeatButtonText) {

        isRepeat.setValue(!isRepeat.getValue());

        repeatButtonText.setValue(isRepeat.getValue() ? "Не повторять" : "Повторять");
        mediaPlayer.setCycleCount(isRepeat.getValue() ? MediaPlayer.INDEFINITE : 0);
    }

    public RepeatAudioCommand(Property<MediaPlayer> mediaPlayer, Property<Boolean> isRepeat, StringProperty repeatButtonText) {
        super(() -> new Action() {
            @Override
            protected void action() throws Exception {
                repeat(mediaPlayer.getValue(), isRepeat, repeatButtonText);
            }
        });
    }

}
