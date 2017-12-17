/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

import chatsystem.view.ClientController;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author archer
 */
public class MainApp extends Application {
    private ClientController controller;
            
    @Override
    public void start(Stage primaryStage) {
        try {
            Optional<String> username = getNameDialog();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Client.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            controller = loader.getController();
            username.ifPresent(name -> controller.setNameLabel(name));
            
            primaryStage.setTitle("MyChat!");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException ex) {

        }
    }
    
    public Optional<String> getNameDialog() {
        TextInputDialog dialog = new TextInputDialog("Ebi");
        dialog.setTitle("Чат Аппликейшн");
        dialog.setHeaderText("Чатанд ашиглах нэрээ сонгоорой!");
        dialog.setContentText("Хэрэглэгчийн нэр:");
        
        return dialog.showAndWait();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
