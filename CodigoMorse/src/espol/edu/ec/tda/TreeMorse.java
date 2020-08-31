/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espol.edu.ec.tda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 *
 * @author Christian Guerrero
 */
public class TreeMorse extends Pane {
    private Node root;
    HashMap<String,String[]> mapa;
    private final AudioClip line;
    private final AudioClip point;
    Timeline t1;
    public TreeMorse() {
        root = new Node("Inicio");
        root.setFill(Color.BLUE);
        root.setTranslateX(Constantes.X/2-20);
        root.setTranslateY(0);
        mapa =loadMap();
                super.getChildren().add(root);

        t1= new Timeline();
        line = new AudioClip(TreeMorse.class.getResource("/espol/edu/ec/Sound/linea.mp3").toExternalForm());
        point = new AudioClip(TreeMorse.class.getResource("/espol/edu/ec/Sound/punto.mp3").toExternalForm());

         for(Map.Entry<String, String[]> m: mapa.entrySet())
            disenioArbol(root, m.getValue(), m.getKey(), 0, m.getValue().length, Constantes.MAX_WIDTH);
        String[] s = {" "};
        mapa.put(" ", s);
        
        
    }
   
    private Node disenioArbol(Node node, String[] data, String key, int i, int j, double ancho) {
        if(i == j) {
            node.setData(key); 
            return node;
        }else if(data[i].equals("-")){
            if(node.getLeft() == null){
                Node n = new Node("");
                node.setLeft(n);
                n.setTranslateX(node.getTranslateX() - ancho/4);
                n.setTranslateY(node.getTranslateY() + 100);
                Line l = new Line(node.getTranslateX()+20, node.getTranslateY()+20, n.getTranslateX()+20, n.getTranslateY()+20);
                l.setStroke(Color.RED);
                super.getChildren().remove(node);
                super.getChildren().addAll(l, node, n);
                n.setFill(Color.WHITE); 
                return disenioArbol(n, data, key, i+1, j, ancho/2);
            }else{
                return disenioArbol(node.getLeft(), data, key, i+1, j, ancho/2);
            }
        }else if(data[i].equals(".")){
            if(node.getRight() == null){
                Node n = new Node("");
                node.setRight(n);
                n.setTranslateX(node.getTranslateX() + ancho/4);
                n.setTranslateY(node.getTranslateY() + 100);
                Line l = new Line(node.getTranslateX()+20, node.getTranslateY()+20, n.getTranslateX()+20, n.getTranslateY()+20);
                l.setStroke(Color.BLUE); 
                super.getChildren().remove(node);
                super.getChildren().addAll(l, node, n);
                n.setFill(Color.WHITE);
                return disenioArbol(n, data, key, i+1, j, ancho/2);
            }else{
                return disenioArbol(node.getRight(), data, key, i+1, j, ancho/2);
            }
        }  
        return node;
    }
    private HashMap<String,String[]> loadMap(){
        String file2= "traducciones.txt";
        HashMap<String, String[]> mapa = new HashMap<>();
        try(BufferedReader bf = new BufferedReader(new FileReader(file2))){
            String line;
            while((line=bf.readLine())!= null){
                String key = line.charAt(0)+"";
                line=line.substring(2,line.length());
                String[] data = line.split(" ");
                mapa.put(key, data);
                
            }
        }catch(IOException e){
            System.err.println(e);
        }
        return mapa;
    }
    
    public void descifrar(String frase){
        frase=frase.toUpperCase();
        descifrar(frase, 0, frase.length());

    }
    private void descifrar(String frase,int inicio, int fin){
        if(inicio==fin){
            return;
        }else{
             try{
                descifrar(root, mapa.get(frase.charAt(inicio)+""), 0, mapa.get(frase.charAt(inicio)+"").length, frase, inicio, fin);
            }catch(NullPointerException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("El caracter ingresado no es válido");
                alert.setTitle("Error");
                alert.setHeaderText("Error!");
                alert.show();
            }  
        }
        
    }
    private void descifrar(Node node, String[] array, int i, int j, String frase, int start, int end) {
        t1.stop();
        t1.getKeyFrames().clear();
        if(i==j){
            KeyValue kv = new KeyValue(node.getContent().fillProperty(), Color.ORANGE);
            KeyFrame kf = new KeyFrame(Duration.millis(300), e-> descifrar(frase, start+1, end), kv);
            t1.getKeyFrames().add(kf);
            t1.setAutoReverse(false);
            t1.setCycleCount(1);
            t1.play();
        }else if(array[i].equals(".")){
            KeyValue kv = new KeyValue(node.getContent().fillProperty(), Color.WHITE);
            KeyFrame kf = new KeyFrame(Duration.millis(700), e -> decolor(node.getRight(), array, i+1, j, frase, start, end, 200), kv);
            t1.getKeyFrames().add(kf);
            t1.setAutoReverse(false);
            t1.setCycleCount(1);
            t1.play();
        }else if(array[i].equals("-")){
            KeyValue kv = new KeyValue(node.getContent().fillProperty(), Color.WHITE);
            KeyFrame kf = new KeyFrame(Duration.millis(1000), e -> decolor(node.getLeft(), array, i+1, j, frase, start, end, 350), kv);
            t1.getKeyFrames().add(kf);
            t1.setAutoReverse(false);
            t1.setCycleCount(1);
            t1.play();
        }else if(array[i].equals(" ")){
            try{
               Thread.sleep(1111);
            } catch (InterruptedException ex) {
               Thread.currentThread().interrupt();
               Logger.getLogger(TreeMorse.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void decolor(Node node, String[] array, int i, int j, String word, int start, int end, double ms) {
        t1.stop();
        t1.getKeyFrames().clear();
        KeyValue kv = new KeyValue(node.getContent().fillProperty(), Color.BLUE);
        KeyFrame kf = new KeyFrame(Duration.millis(ms), e -> descifrar(node, array, i, j, word, start, end), kv);
        t1.getKeyFrames().add(kf);
        t1.setAutoReverse(true);
        t1.setCycleCount(1);
        t1.play();
        line.stop();
        point.stop();
        if(ms == 200)
            point.play();
        else
            line.play();
    }
    public void reset() {
        recorrer(root);
    }
   
    private void recorrer(Node node) {
        if(node == null){
            return ;
        }else {
            node.setFill(Color.WHITE); 
            recorrer(node.getLeft());
            recorrer(node.getRight());
        }
    }    

        
}
