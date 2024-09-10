package com.musicplayer.app.models;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class MetadataSnd {

    private final StringProperty titleText;
    private final StringProperty artistText;
    private final StringProperty fileNameText;
    private final Property<Image> imageCover;

    public MetadataSnd(StringProperty titleText, StringProperty artistText, StringProperty fileNameText, Property<Image> imageCover) {
        this.titleText = titleText;
        this.artistText = artistText;
        this.fileNameText = fileNameText;
        this.imageCover = imageCover;
    }

    public Property<Image> getImageCover() {
        return imageCover;
    }

    public StringProperty getTitleText() {
        return titleText;
    }

    public StringProperty getFileNameText() {
        return fileNameText;
    }

    public StringProperty getArtistText() {
        return artistText;
    }

}