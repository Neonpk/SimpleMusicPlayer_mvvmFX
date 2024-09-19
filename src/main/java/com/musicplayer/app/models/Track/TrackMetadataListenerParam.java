package com.musicplayer.app.models.Track;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@Getter
@AllArgsConstructor
@SuppressWarnings("all")
public class TrackMetadataListenerParam {
    private final StringProperty titleProperty;
    private final StringProperty artistProperty;
    private final Property<Image> imageCoverProperty;
    private final HashMap<String, Object> metaDataHash;
}
