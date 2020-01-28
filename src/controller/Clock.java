package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class Clock extends Pane {
    Label timeLabel=new Label();
    int x,y,time;
    public Timeline animation;



    public Clock(int time, int x, int y) {
        this.time = time;
        this.x = x;
        this.y = y;
        timeLabel.setTranslateX(x);
        timeLabel.setTranslateY(y);
        timeLabel.setFont(Font.font(15));

        getChildren().addAll(timeLabel);
        animation=new Timeline(new KeyFrame(Duration.seconds(1),e->CountDown()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }
    public void CountDown(){
        if(time>0){
            time--;
            timeLabel.setText("The time is "+String.valueOf(time));
        }else {
            timeLabel.setText("Time is over");
        }
    }
}
