package com.musicplayer.app.viewmodels;

import com.musicplayer.app.commands.media_commands.PlayPauseCommand;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainContainerViewModel implements ViewModel {

    // Properties (fields)

    private final ObservableList<String> playlist = FXCollections.observableArrayList();

    private final Property<Number> selectedVolume = new SimpleObjectProperty<>(0.0f);
    private final Property<Number> selectedProgress = new SimpleObjectProperty<>(0.0f);

    private final StringProperty timePositionText = new SimpleStringProperty("00:00");
    private final StringProperty timeDurationText = new SimpleStringProperty("00:00");

    // Commands

    private final Command listViewEditCommand = new PlayPauseCommand();

    // Constructor

    public MainContainerViewModel() {
    }

    // Methods (getters and setters)

    public Command getListViewEditCommand() {
        return listViewEditCommand;
    }

    public ObservableList<String> getPlaylistProperty() {
        return playlist;
    }

    public Property<Number> getSelectedVolumeProperty() {
        return selectedVolume;
    }

    public Property<Number> getSelectedProgressProperty() {
        return selectedProgress;
    }

    public StringProperty getTimePositionProperty() {
        return timePositionText;
    }

    public StringProperty getTimeDurationProperty() {
        return timeDurationText;
    }

}
