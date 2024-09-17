package com.musicplayer.app.commands.track_commands;

import com.musicplayer.app.models.Track.Track;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import java.util.Date;

public class AddNewTracksCommand extends DelegateCommand {

    private static void addNewTracks(ObservableList<Track> tracks) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите mp3 файлы");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Music files (*.mp3)", "*.mp3"));

        long timestamp = new Date().getTime() / 1000;

        fileChooser.showOpenMultipleDialog(null).
                forEach(obj -> tracks.add(
                        new Track(
                            !tracks.isEmpty() ? tracks.getLast().getId() + 1 : 1,
                            obj.getPath(),
                            timestamp
                        )
                ));
    }

    public AddNewTracksCommand(ObservableList<Track> tracks) {
        super(() -> new Action() {
            @Override
            protected void action() {
                addNewTracks(tracks);
            }
        });
    }

}
