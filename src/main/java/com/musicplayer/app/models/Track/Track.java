package com.musicplayer.app.models.Track;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Track {

    private int id;
    private String fileName;
    private long added;

    @Override
    public String toString() {
        return fileName;
    }
}
