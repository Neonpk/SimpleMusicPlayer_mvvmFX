package com.musicplayer.app.services;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

@AllArgsConstructor
@SuppressWarnings("all")
public class VmProvider {
    private final PlaylistJsonProvider playlistJsonProvider;
    private final PlaylistsProvider playlistsProvider;
    private final NavigationService navigationService;
}
