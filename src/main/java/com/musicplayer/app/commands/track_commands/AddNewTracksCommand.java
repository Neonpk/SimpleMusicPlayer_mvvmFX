package com.musicplayer.app.commands.track_commands;

import com.musicplayer.app.models.Track.Track;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Date;

public class AddNewTracksCommand extends DelegateCommand {

    private static void addNewTracks(Property<ObservableList<Track>> tracksProperty) {

        ObservableList<Track> tracks = tracksProperty.getValue();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите mp3 файлы");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Media files (*.mp3, *.mp4)", "*.mp3", "*.mp4"));

        long timestamp = new Date().getTime();
        Window primaryStage = Stage.getWindows().getFirst();

        fileChooser.showOpenMultipleDialog(primaryStage).
                forEach(obj -> tracks.add(
                        new Track(
                            !tracks.isEmpty() ? tracks.getLast().getId() + 1 : 1,
                            obj.getPath(),
                            timestamp
                        )
                ));
    }

    public AddNewTracksCommand(Property<ObservableList<Track>> tracksProperty) {
        super(() -> new Action() {
            @Override
            protected void action() {
                addNewTracks(tracksProperty);
            }
        });
    }

}
