package com.musicplayer.app.services;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

@AllArgsConstructor
@SuppressWarnings("all")
public class VmProvider {
    private final PlaylistSelectionProvider playlistSelectionProvider;
    private final PlaylistJsonProvider playlistJsonProvider;
    private final MediaProvider mediaProvider;
    private final NavigationService navigationService;
}
