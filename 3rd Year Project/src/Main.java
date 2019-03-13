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
		
		scanner = new Scanner(System.in);
		launch(args);		
	}

	public void start(Stage primaryStage) throws Exception {

		ViewController viewControl = new ViewController();
		
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
		
		node = new Node();
        node.addViewController(viewControl);
		node.initialise();	
		node.queueToSend(new Message("TO ALL!"));
		viewControl.setNode(node);
		
		primaryStage.show();

		InputScanner input = new InputScanner();
		Thread t = new Thread(input);
		t.start();
	}
	
	private void checkResource() {
		
		URL url = getClass().getResource("com/project/view/Login.fxml");
        if (url == null) {
            System.out.println("Can't load FXML file");
            System.exit(0);
        }
	}
	
	class InputScanner extends Thread {
		
		public void run() {
			
			System.out.println("Scanner started");
			
			while(true) {				
				String text = scanner.nextLine();
				node.queueToSend(new Message(text));
			}
		}
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
