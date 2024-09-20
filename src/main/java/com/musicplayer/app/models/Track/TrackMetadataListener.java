package com.musicplayer.app.models.Track;

import com.musicplayer.app.AppStarter;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import lombok.Getter;

import java.util.HashMap;
import java.util.Objects;

public class TrackMetadataListener {

    private final Image defaultCover = new Image(Objects.requireNonNull(AppStarter.class.getResource("images/nocover.jpg")).toString());

    @Getter
    private final MapChangeListener<String, Object> metaDataChangeListenger;

    public TrackMetadataListener(TrackMetadataListenerParam trackMetadataListenerParam) {

        HashMap<String, Object> metaDataHash = trackMetadataListenerParam.getMetaDataHash();
        StringProperty artistProperty = trackMetadataListenerParam.getArtistProperty();
        StringProperty titleProperty = trackMetadataListenerParam.getTitleProperty();
        Property<Image> imageCoverProperty = trackMetadataListenerParam.getImageCoverProperty();

        metaDataHash.clear();

        metaDataChangeListenger = ch -> {
            if (ch.wasAdded()) {
                metaDataHash.put(String.valueOf(ch.getKey()), ch.getValueAdded());
                artistProperty.setValue(metaDataHash.getOrDefault("artist", "Неизвестный артист").toString());
                titleProperty.setValue(metaDataHash.getOrDefault("title", "Неизвестный заголовок").toString());
                imageCoverProperty.setValue((Image) metaDataHash.getOrDefault("image", defaultCover));
            }
        };

    }

}
