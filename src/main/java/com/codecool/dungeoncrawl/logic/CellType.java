package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    FLOOR1("floor1"),
    FLOOR2("floor2"),
    CORPSE("corpse"),
    WALL("wall"),
    KEY("key"),
    CLOSEDBLUEDOOR("closedBlueDoor"),
    OPENBLUEDOOR("openedBlueDoor"),
    TRIGGER("trigger"),
    BOX("box");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
