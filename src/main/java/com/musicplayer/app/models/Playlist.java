package com.musicplayer.app.models;
import lombok.*;

import java.util.List;

@Setter
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
