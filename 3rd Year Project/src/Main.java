import java.net.URL;
import java.util.Scanner;

import com.project.controller.*;
import com.project.network.Node;
import com.project.utils.Message;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	
	static Scanner scanner;
	Node node;

	public static void main (String[] args) {		
		launch(args);		
	}

	public void start(Stage primaryStage) throws Exception {
		
		scanner = new Scanner(System.in);
		node = new Node();
		node.initialise();	
		node.queueToSend(new Message("TO ALL!"));
        ViewController viewControl = new ViewController(node);
        node.addViewController(viewControl);
		
        checkResource();
        
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("com/project/view/Login.fxml")); 
        loader.setController(viewControl);
                
        Pane root = loader.load();		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("com/project/view/application.css").toExternalForm());
		
		primaryStage.setTitle("Login");
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest( event -> {
			System.exit(0);
		});
		
		primaryStage.show();
//		while(true) {			
//			String text = scanner.nextLine();
//			node.queueToSend(new Message(text));			
//		}
	}
	
	private void checkResource() {
		
		URL url = getClass().getResource("com/project/view/Login.fxml");
        if (url == null) {
            System.out.println("Can't load FXML file");
            System.exit(0);
        }
	}
	
	private void initNode() {
			
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
