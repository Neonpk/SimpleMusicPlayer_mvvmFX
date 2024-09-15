package com.musicplayer.app.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {

    private int id;
    private String name;
    private List<Track> tracks;

    @Override
    public String toString() {
        return name;
    }
}
