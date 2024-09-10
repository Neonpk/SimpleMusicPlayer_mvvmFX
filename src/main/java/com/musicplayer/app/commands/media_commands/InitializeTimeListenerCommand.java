package com.musicplayer.app.commands.media_commands;

import com.musicplayer.app.models.MainContainerVmProperties;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class InitializeTimeListenerCommand extends DelegateCommand {

    private static void initializeTimeListener(MainContainerVmProperties mainContainerVmProperties) {

        MediaPlayer mediaPlayer = mainContainerVmProperties.getMediaPlayer();

        Property<Number> selectedProgress = mainContainerVmProperties.getSelectedProgressProperty();

        StringProperty timePositionText = mainContainerVmProperties.getTimePositionProperty();
        StringProperty timeDurationText = mainContainerVmProperties.getTimeDurationProperty();

        mediaPlayer.currentTimeProperty().addListener(((observableValue, oldValue, newValue) -> {

            mainContainerVmProperties.getSliderProgressUpdateProperty().setValue(true);

            try
            {
                Duration duration = mediaPlayer.getTotalDuration();
                double durationSeconds = duration.toSeconds();
                double durationMinutes = duration.toMinutes();

                double posSeconds = newValue.toSeconds();
                double posMinutes = newValue.toMinutes();

                timePositionText.setValue(String.format("%02d:%02d", (int) posMinutes % 60, (int) posSeconds % 60));
                timeDurationText.setValue(String.format("%02d:%02d", (int) durationMinutes % 60, (int) durationSeconds % 60));

                selectedProgress.setValue(posSeconds / durationSeconds * 100);
            } finally {
                mainContainerVmProperties.getSliderProgressUpdateProperty().setValue(false);
            }

        }));

        mediaPlayer.setOnEndOfMedia(() -> {

            boolean isRepeat = mainContainerVmProperties.getRepeatStatusProperty().getValue();
            StringProperty playButtonText = mainContainerVmProperties.getPlayButtonTextProperty();

            if(!isRepeat) {
                mediaPlayer.stop();
                playButtonText.setValue(">");
            }

        });

    }

    public InitializeTimeListenerCommand(MainContainerVmProperties mainContainerVmProperties) {
        super(() -> new Action() {
            @Override
            protected void action() throws Exception {
                initializeTimeListener(mainContainerVmProperties);
            }
        });
    }

}
