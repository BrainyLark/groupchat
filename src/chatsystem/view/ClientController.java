/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author archer
 */
public class ClientController implements Initializable {

    @FXML
    private TextArea textField;
    @FXML
    private TextArea textArea;
    @FXML
    private Button sendBtn;
    private Socket socket;
    private DataOutputStream outstream;
    private DataInputStream instream;
    @FXML
    private Label nameLabel;
    private String username;

    public void setNameLabel(String name) {
        nameLabel.setText("Тавтай морил, " + name);
        username = name;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            socket = new Socket("localhost", 8000);
            outstream = new DataOutputStream(socket.getOutputStream());
            instream = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {

        }
        inputHandler();
        sendBtn.setOnAction((e) -> {
            String sending = textField.getText();
            try {
                outstream.writeUTF("<" + username + ">: " + sending);
                textField.setText("");
            } catch (IOException ex) {

            }
        });
    }

    public void inputHandler() {
        Thread input = new Thread(() -> {
            try {
                while (true) {
                    String rcvMsg = instream.readUTF();
                    textArea.appendText(rcvMsg);
                }
            } catch (IOException ex) {

            }
        });
        input.start();
    }

}
