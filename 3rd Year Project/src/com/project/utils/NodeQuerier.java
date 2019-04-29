package com.project.utils;

import com.project.network.Node;

public class NodeQuerier {
	
	private static Node node;
	
	public NodeQuerier(Node node) {
		this.node = node;
	}
	
	public static int currentPeerNumber() {
		return node.getNumberOfPeers();
	}
}
