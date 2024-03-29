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
    STAIRUP("stairUp"),
    STAIRDOWN("stairDown"),
    CLOSEDBLUEDOOR("closedBlueDoor"),
    OPENBLUEDOOR("openedBlueDoor"),
    INFOBARMIDDLE("infoBarMiddle"),
    INFOBARSHIELD("infoBarShield"),
    INFOBARSWORD("infoBarSword"),
    INFOBARCOINS("infoBarCoins"),
    INFOBARBAG("infoBarBag"),
    DOUBLEDOT("doubleDot"),
    HEART("heart"),
    TRIGGER("trigger"),
    TRAPROUTETILE("trapRouteTile"),
    BOX("box"),
    BLUESWITCHLEFT("blueSwitchLeft"),
    BLUESWITCHLOCK("blueSwitchLock"),
    BLUESWITCHRIGHT("blueSwitchRight"),
    FINISH("finish"),
    W("W"),
    E("E"),
    L("L"),
    D("D"),
    O("O"),
    N("N"),
    DIAMOND("diamond");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
