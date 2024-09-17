package com.musicplayer.app.services;

import javafx.beans.property.Property;
import javafx.scene.Node;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NavigationService {

    private final Property<Node> selectedView;

    public void bindBidirectional( Property<Node> view ) {
        selectedView.bindBidirectional( view );
    }

    public void navigate(Node view) {
        selectedView.setValue(view);
        System.gc();
    }
}
