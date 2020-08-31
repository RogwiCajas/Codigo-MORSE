/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espol.edu.ec.main;

import espol.edu.ec.tda.Constantes;
import espol.edu.ec.tda.TreeMorse;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Isaac
 */
public class main extends Application {
   @Override
    public void start(Stage primaryStage) throws Exception {
        TreeMorse tm = new TreeMorse();
        Text text = new Text("Códigos Morse");
        text.setFont(Font.font(text.getFont().getFamily(), FontWeight.BOLD, 24));  
        text.setFill(Color.BLACK);
        TextField input = new TextField();
        Button btn = new Button("Iniciar");
        Button reiniciar = new Button("Limpiar");
        HBox box = new HBox(10, new Text("Texto"),input, btn, reiniciar);
        HBox box2 = new HBox(10,btn,reiniciar);
        box.setAlignment(Pos.TOP_LEFT); 
        VBox v = new VBox(5, text, box,box2,tm);
        v.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY))); 
        v.setAlignment(Pos.TOP_LEFT);         
        primaryStage.setScene(new Scene(v,Constantes.MAX_WIDTH,Constantes.MAX_HEIGHT));
        btn.setOnAction(e-> tm.descifrar(input.getText()));
        reiniciar.setOnAction(e ->{
            tm.reset();
            input.setText(""); 
        }); 
        primaryStage.show();
   
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
