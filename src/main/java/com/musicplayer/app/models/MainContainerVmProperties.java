package com.musicplayer.app.models;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.media.MediaPlayer;

public class MainContainerVmProperties {

    private final MediaPlayer mediaPlayer;
    private final Property<Number> selectedProgress;
    private final Property<Number> selectedVolume;
    private final StringProperty timePositionText;
    private final StringProperty timeDurationText;
    private final StringProperty playButtonText;
    private final Property<Boolean> sliderProgressUpdateProperty;

    public MainContainerVmProperties(
            MediaPlayer mediaPlayer,
            Property<Number> selectedProgress,
            Property<Number> selectedVolume,
            StringProperty timePositionText,
            StringProperty timeDurationText,
            StringProperty playButtonText,
            Property<Boolean> sliderProgressUpdateProperty
            ) {

        this.mediaPlayer = mediaPlayer;
        this.selectedProgress = selectedProgress;
        this.selectedVolume = selectedVolume;
        this.timeDurationText = timeDurationText;
        this.timePositionText = timePositionText;
        this.playButtonText = playButtonText;
        this.sliderProgressUpdateProperty = sliderProgressUpdateProperty;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public Property<Number> getSelectedProgressProperty() {
        return selectedProgress;
    }

    public Property<Number> getSelectedVolumeProperty() {
        return selectedVolume;
    }

    public StringProperty getTimePositionProperty() {
        return timePositionText;
    }

    public StringProperty getTimeDurationProperty() {
        return timeDurationText;
    }

    public StringProperty getPlayButtonTextProperty() {
        return playButtonText;
    }

    public Property<Boolean> getSliderProgressUpdateProperty() {
        return sliderProgressUpdateProperty;
    }

}
