package com.musicplayer.app.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PlaylistJsonSerializer {

    private final String fileName;

    public PlaylistJsonSerializer(String fileName) {
        this.fileName = fileName;
    }

    public String Serialize(List<Playlist> playlists) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(playlists);
    }

    public List<Playlist> Deserialize() throws IOException {

        File config = new File(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(config, new TypeReference<>(){});
    }

}
