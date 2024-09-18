package com.musicplayer.app.models.Playlist;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Getter
@SuppressWarnings("all")
public class PlaylistsJsonDeserializer {

    private final String fileName;

    public List<Playlist> Deserialize() throws IOException {

        File config = new File(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(config, new TypeReference<>(){});
    }
}
