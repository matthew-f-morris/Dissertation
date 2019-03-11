package com.project.controller;

import com.project.network.Node;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ViewController {
		
	private Node node;
	
	@FXML
	private ListView<String> nodesInChat;
	
	public ViewController(Node node2) {
		
		this.node = node;
		nodesInChat = new ListView<String>();
	}
	
	public void addNode(String nodeIp) {
		nodesInChat.getItems().add(nodeIp);
	}
	
	public void removeNode(String nodeIp) {
		
		if(nodesInChat.getItems().contains(nodeIp)) {
			nodesInChat.getItems().remove(nodeIp);
		}
	}
}
