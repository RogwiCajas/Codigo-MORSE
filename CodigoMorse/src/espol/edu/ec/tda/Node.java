/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espol.edu.ec.tda;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Christian Guerrero
 */
public class Node extends StackPane {
    private Node right;
    private Node left;
    private String data;
    private Text text;
    private Timeline timeline;
    private Circle content;
    
    
    public Node(String data){
        this.data=data;
        content = new Circle();
        timeline = new Timeline();
        right=left=null;
        circleNode();
    }
    private void circleNode(){
        text= new Text(data);
        text.setFont(Font.font(20));
        content.setRadius(25);
        super.getChildren().addAll(content,text);
        super.setAlignment(Pos.CENTER);
    }
    public void pass(Color color) {
        timeline.stop();
        timeline.getKeyFrames().clear();
        KeyValue kv = new KeyValue(content.fillProperty(), color);
        KeyFrame kf = new KeyFrame(Duration.millis(700), kv);
        timeline.setAutoReverse(true);
        timeline.setCycleCount(2);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
    
     public void play(Color color) {
        timeline.stop();
        timeline.getKeyFrames().clear();
        KeyValue kv = new KeyValue(content.fillProperty(), color);
        KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setAutoReverse(true);
        timeline.setCycleCount(2);
        timeline.play();
    }
     public void setFill(Color color){
         content.setFill(color);
     }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.text.setText(data);
        this.data = data;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public Circle getContent() {
        return content;
    }

    public void setContent(Circle content) {
        this.content = content;
    }
    
}
