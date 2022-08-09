package com.codecool.dungeoncrawl.logic;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;


public class MobTimer {

    private final Timeline timeline;
    private Runnable method;


    public MobTimer(Runnable method) {
        this.method = method;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(750), this::doStep));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void doStep(ActionEvent actionEvent) {
        method.run();
    }

    public void start() {
        this.timeline.play();
    }
}
