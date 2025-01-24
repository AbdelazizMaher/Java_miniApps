package chatapplication;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class ClientChatApplication extends BorderPane {

    protected final TextArea textArea;
    protected final FlowPane flowPane;
    protected final TextField textField;
    protected final Button sendButton;

    private Socket serverSocket;
    private DataInputStream ear;
    private PrintStream mouth;

    private boolean isConnected = true;

    public ClientChatApplication() {

        textArea = new TextArea();
        flowPane = new FlowPane();
        textField = new TextField();
        sendButton = new Button();

        try {
            serverSocket = new Socket("127.0.0.1", 5005);
            ear = new DataInputStream(serverSocket.getInputStream());
            mouth = new PrintStream(serverSocket.getOutputStream());
            
            Thread th = new Thread(()-> {
                    while (isConnected) {
                        String replyMsg;
                        try {
                            replyMsg = ear.readLine();
                            System.out.println(replyMsg);
                             Platform.runLater(()-> {
                                 textArea.appendText(replyMsg + "\n");
                             });

                        } catch (Exception ex) {
                            //Logger.getLogger(ClientChatApplication.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println(ex.getLocalizedMessage());                          
                        }
                    }            
            });
            th.start();
        } catch (Exception ex) {
            //Logger.getLogger(ClientChatApplication.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getLocalizedMessage());
        }

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        BorderPane.setAlignment(textArea, javafx.geometry.Pos.CENTER);
        textArea.setPrefHeight(200.0);
        textArea.setPrefWidth(200.0);
        setCenter(textArea);
        textArea.setEditable(false);

        BorderPane.setAlignment(flowPane, javafx.geometry.Pos.CENTER_LEFT);
        flowPane.setHgap(20.0);
        flowPane.setPrefHeight(82.0);
        flowPane.setPrefWidth(590.0);
        flowPane.setVgap(15.0);

        textField.setPrefHeight(31.0);
        textField.setPrefWidth(454.0);
        textField.setPromptText("Enter terxt...");
        FlowPane.setMargin(textField, new Insets(20.0, 0.0, 0.0, 0.0));
        textField.setPadding(new Insets(5.0, 0.0, 0.0, 0.0));

        sendButton.setMnemonicParsing(false);
        sendButton.setPrefHeight(31.0);
        sendButton.setPrefWidth(111.0);
        sendButton.setText("Send");
        FlowPane.setMargin(sendButton, new Insets(20.0, 0.0, 0.0, 0.0));
        BorderPane.setMargin(flowPane, new Insets(5.0, 0.0, 0.0, 10.0));
        setBottom(flowPane);
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!textField.getText().isEmpty()) {
                    mouth.println(textField.getText());
                    textField.clear();
                }
            }
        });

        flowPane.getChildren().add(textField);
        flowPane.getChildren().add(sendButton);

    }

    public void closeConnection() {
        try {
            isConnected = false;
            mouth.println("Close my connection");
            ear.close();
            mouth.close();
            serverSocket.close();
        } catch (Exception ex) {
            Logger.getLogger(ClientChatApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
