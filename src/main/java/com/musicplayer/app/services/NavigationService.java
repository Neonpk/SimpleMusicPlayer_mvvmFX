package com.musicplayer.app.services;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import java.util.function.Function;

public class NavigationService {

    private final Property<Node> selectedView = new SimpleObjectProperty<>();

    // View function factory
    private final Function<
            Class<? extends ViewModel>,
            ViewTuple<? extends FxmlView<? extends ViewModel>, ? extends ViewModel>
            > viewFactory;

    public NavigationService(Function<
            Class<? extends ViewModel>,
            ViewTuple<? extends FxmlView<? extends ViewModel>, ? extends ViewModel>
            > viewFactory) {
        this.viewFactory = viewFactory;
    }

    public void bindBidirectional( Property<Node> view ) {
        selectedView.bindBidirectional( view );
    }

    public void navigate(Class<? extends ViewModel> viewModelType) {
        selectedView.setValue( viewFactory.apply(viewModelType).getView() );
        System.gc();
    }

}
