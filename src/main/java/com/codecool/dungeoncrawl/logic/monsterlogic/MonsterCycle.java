package com.codecool.dungeoncrawl.logic.monsterlogic;

import com.codecool.dungeoncrawl.logic.GameMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.util.Random;


public class MonsterCycle {
    private final Timeline timeline;
    GameMap map;
    Runnable refresh;



    public MonsterCycle(GameMap map, Runnable refresh) {
        this.map = map;
        this.refresh = refresh;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), this::doStep));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void doStep(ActionEvent actionEvent) {
        map.isAlive();
        map.skeletonRound();
        map.bomberRound();
        map.threeMusketeerRound();
        refresh.run();

    }

    public void start() {
        this.timeline.play();
    }

    public void stop() {this.timeline.stop();}
}
