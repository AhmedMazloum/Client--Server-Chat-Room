package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class ServerMain extends Application {

    private VBox root;

    private TextArea Messages = new TextArea();

    private TextField inputMessage;

    private Button sendBtn;
    private boolean isServer = true;
    private NetworkConnection connection = createServer();

    private Parent createContent()  {


        Messages.setPrefHeight(636);

        inputMessage = new TextField();
        sendBtn = new Button("Send");

        sendBtn.setOnAction(event -> {
            String message = "YOU: ";
            message+=inputMessage.getText();
            String sendMessage = inputMessage.getText();
            inputMessage.clear();
            Messages.appendText(message + "\n");
            try {
                connection.send(sendMessage);
            } catch (Exception e) {
                Messages.appendText("");
            }
        });


        root = new VBox(20,Messages,inputMessage,sendBtn);
        root.setPrefSize(426,650);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        return root;
    }



    @Override
    public void init() throws Exception{
        connection.startConnection();
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("ChatRoom");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }


    private Server createServer(){
        return new Server(12345, data ->{
            Platform.runLater(() -> {
                Messages.appendText(data.toString() + "\n");
            });
        });
    }

    public static void main(String[] args) {
        launch(args);
    }






}
