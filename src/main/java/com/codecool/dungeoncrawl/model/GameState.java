package com.codecool.dungeoncrawl.model;

import java.util.ArrayList;
import java.util.List;

public class GameState extends BaseModel {
    private byte[] currentMap;
    private List<String> discoveredMaps = new ArrayList<>();
    private PlayerModel player;

    public GameState(byte[] currentMap, PlayerModel player) {
        this.currentMap = currentMap;
        this.player = player;
    }
    public GameState(byte[] currentMap) {
        this.currentMap = currentMap;
    }
    public byte[] getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(byte[] currentMap) {
        this.currentMap = currentMap;
    }

    public List<String> getDiscoveredMaps() {
        return discoveredMaps;
    }

    public void addDiscoveredMap(String map) {
        this.discoveredMaps.add(map);
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }
}
