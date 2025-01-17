package com.musicplayer.app.models.Track;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Track {

    private int id;
    private String fileName;
    private long added;

    @JsonIgnore
    private final HashMap<String, Object> metaData = new HashMap<>();

    @JsonIgnore
    public boolean isMissing() {
        return !Files.exists(Paths.get(fileName));
    }
}
