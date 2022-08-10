package com.codecool.dungeoncrawl.logic;

public enum CellType {
    NUMBER0("numberZero"),
    NUMBER1("numberOne"),
    NUMBER2("numberTwo"),
    NUMBER3("numberThree"),
    NUMBER4("numberFour"),
    NUMBER5("numberFive"),
    NUMBER6("numberSix"),
    NUMBER7("numberSeven"),
    NUMBER8("numberEight"),
    NUMBER9("numberNine"),
    EMPTY("empty"),
    FLOOR("floor"),
    FLOOR1("floor1"),
    FLOOR2("floor2"),
    CORPSE("corpse"),
    WALL("wall"),
    ORANGEWALL("orangeWall"),
    ORANGEWALL2("orangeWall2"),
    ORANGEWALLBROKEN("orangeWallBroken"),
    KEY("key"),
    STAIRUP("stairUp"),
    STAIRDOWN("stairDown"),
    CLOSEDBLUEDOOR("closedBlueDoor"),
    OPENBLUEDOOR("openedBlueDoor"),
    INFOBARMIDDLE("infoBarMiddle"),
    HEART("heart"),
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
