package com.codecool.dungeoncrawl.logic.gamecycle;

import com.codecool.dungeoncrawl.actors.Mob;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.util.ArrayList;


public class GameCycle {
    private final Timeline timeline;
    GameMap map;
    Runnable refresh;
    double hp = 0.0;


    public GameCycle(GameMap map, Runnable refresh) {
        this.map = map;
        this.refresh = refresh;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), this::doStep));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void doStep(ActionEvent actionEvent) {
        ArrayList<Item> inventory = map.getPlayer().getInventory();
        for (Item item:
             inventory) {
            if (item.getTileName().equals("shield")) {
                hp += item.getSpecialBonus();
                if (hp >= 1) {
                    map.getPlayer().heal((int) hp);
                    hp = 0.0;
                }
            }
        }
        map.getMobs().removeIf(mob -> !mob.isAlive());
        for (Mob mob: map.getMobs()) {
            mob.mobRound();
        }
        refresh.run();

    }

    public void start() {
        this.timeline.play();
    }

    public void stop() {this.timeline.stop();}
}
