package com.musicplayer.app.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Getter
public class PlaylistJsonDeserializer {


    private final String fileName;

    public PlaylistJsonDeserializer(String fileName) {
        this.fileName = fileName;
    }

    public List<Playlist> Deserialize() throws IOException {

        File config = new File(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(config, new TypeReference<>(){});
    }

}
