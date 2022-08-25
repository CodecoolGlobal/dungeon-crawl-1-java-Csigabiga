package com.codecool.dungeoncrawl.utils;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.google.gson.Gson;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.Base64;

public class SerializationDeserialization implements Serializable {

    public static byte[] serializeMap(GameMap gameMap) {
        return SerializationUtils.serialize(gameMap);
    }
    public static GameMap deSerializeMap(byte[] serializedMap){
        return SerializationUtils.deserialize(serializedMap);
    }
}
