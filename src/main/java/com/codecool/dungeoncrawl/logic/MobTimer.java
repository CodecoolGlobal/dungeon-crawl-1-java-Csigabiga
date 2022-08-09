package com.codecool.dungeoncrawl.logic;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;


public class MobTimer {

    private final Timeline timeline;
    private Runnable skeletonMethod;

    private Runnable staticMobMethod;


    public MobTimer(Runnable skeletonMethod, Runnable staticMobMethod) {
        this.skeletonMethod = skeletonMethod;
        this.staticMobMethod = staticMobMethod;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), this::doStep));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void doStep(ActionEvent actionEvent) {
        skeletonMethod.run();
        staticMobMethod.run();
    }

    public void start() {
        this.timeline.play();
    }
}
