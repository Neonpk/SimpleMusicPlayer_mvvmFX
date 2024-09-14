package com.musicplayer.app.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {

    private int id;

    @ToString.Exclude
    private String name;

    private List<Track> tracks;
}
