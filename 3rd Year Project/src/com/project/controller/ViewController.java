package com.project.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import com.project.network.Node;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ViewController {
		
	private Node node;
	
	@FXML
	private ListView<String> nodesInChat;
	
	@FXML
	private TextArea console;

	private PrintStream ps;
	
	public ViewController() {
		nodesInChat = new ListView<String>();
	}
	
	public void setNode(Node node) {
		this.node = node;
	}
	
	public void addNode(String nodeIp) {
		nodesInChat.getItems().add(nodeIp);
	}
	
	public void removeNode(String nodeIp) {
		
		if(nodesInChat.getItems().contains(nodeIp)) {
			nodesInChat.getItems().remove(nodeIp);
		}
	}
	
	public void initialize() {
		ps = new PrintStream(new Console(console));
		System.setOut(ps);
		System.setErr(ps);
		System.out.print("Hello..\n");
	}
	
	public class Console extends OutputStream {
		
		private TextArea console;
		
		public Console(TextArea console) {
			this.console = console;
		}
		
		public void appendText(String valueOf) {
			Platform.runLater(() -> console.appendText(valueOf));
		}
		
		public void write(int b) throws IOException {
			appendText(String.valueOf((char) b));
		}
	}
}
