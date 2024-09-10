package com.musicplayer.app.commands.media_commands;

import com.musicplayer.app.models.MetadataSnd;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class InitializeMetadataListenerCommand extends DelegateCommand {

    private static void initializeMetadataListener(Media media, MetadataSnd metadataSnd) {

        media.getMetadata().addListener((MapChangeListener<String, Object>) ch -> {
            if (ch.wasAdded()) {
                switch(String.valueOf(ch.getKey())) {
                    case "title" -> metadataSnd.getTitleText().setValue(ch.getValueAdded().toString());
                    case "artist" -> metadataSnd.getArtistText().setValue(ch.getValueAdded().toString());
                    case "image" -> metadataSnd.getImageCover().setValue((Image) ch.getValueAdded());
                }
                metadataSnd.getFileNameText().setValue(
                        media.getSource().replaceFirst(".*/(.*\\.(?:mp3|mp4))", "$1")
                );
            }
        });

    }

    public InitializeMetadataListenerCommand(Media media, MetadataSnd metadataSnd) {
        super(() -> new Action() {
            @Override
            protected void action() throws Exception {
                initializeMetadataListener(media, metadataSnd);
            }
        });
    }

}
