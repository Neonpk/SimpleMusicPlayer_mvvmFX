package com.musicplayer.app.services;

import javafx.beans.property.Property;
import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;

public class NavigationService {

    private final Property<Node> selectedView;

    public NavigationService(Property<Node> selectedView) {
        this.selectedView = selectedView;
    }

    public void bindBidirectional( Property<Node> view ) {
        selectedView.bindBidirectional( view );
    }

    public void navigate(Node view) {
        selectedView.setValue(view);
    }
}
