import java.net.URL;
import java.util.Scanner;

import com.project.controller.*;
import com.project.network.Node;
import com.project.utils.Message;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	static Scanner scanner;
	
	public static void main (String[] args) {
		
		launch(args);
		scanner = new Scanner(System.in);
		
		Node node = new Node();
		node.initialise();	
		node.queueToSend(new Message("TO ALL!"));
		
		while(true) {
			
			String text = scanner.nextLine();
			node.queueToSend(new Message(text));
			
		}
	}

	public void start(Stage primaryStage) throws Exception {
		
		URL url = getClass().getResource("com.project.view/Login.fxml");
        if (url == null) {
            System.out.println("Can't load FXML file");
            System.exit(0);
        }
		
		FXMLLoader loader = FXMLLoader.load(getClass().getResource("/3rd Year Project/src/com.project.view/Login.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("/com.project.view/application.css").toExternalForm());
		
		primaryStage.setTitle("Login");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		primaryStage.setOnCloseRequest( event -> {
			System.exit(0);
		});
	}
}

/*
 * 
 * Maybe consider programming a TCP backup
 * 
 * For each new broadcasted peerdata thing received, attempt to connect using TCP and sending peerdata in case
 * they didn't receive the data 
 * 
 * 
 * 
 * 
 * 
 */
