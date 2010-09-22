package Bluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import lejos.nxt.LCD;
import lejos.nxt.Sound;

import Graph.Graph;
import Graph.GraphTools;
import Graph.Node;
import Graph.Pair;
import Graph.Type;

public class BTSendAnything {

	private static DataOutputStream dos;
	private static DataInputStream dis;
	private static Graph graph;
	
	public static void sendAction(Pair[] action, DataOutputStream dataOut,
			DataInputStream dataIn) throws IOException{
		int control = (int) (Math.random() * 255);
		for(int i=0;i<=1;i++){
			dos.writeInt(action[i].getX());	
			dos.flush();
			dos.writeInt(action[i].getY());
			dos.flush();
		}
		dos.writeInt(control);
		dos.flush();
		if (dis.read() == control) {
			System.out.println("Control OK");
		} else {
			System.out.println("Control FAIL");
		}
	}
	
	public static void sendMove(Pair move, DataOutputStream dataOut,
			DataInputStream dataIn) throws IOException{
		int control = (int) (Math.random() * 255);
		dos = dataOut;
		dis = dataIn;
		dos.writeInt(move.getX());
		dos.flush();
		dos.writeInt(move.getY());
		dos.flush();
		dos.writeInt(control);
		dos.flush();
		if (dis.read() == control) {
			System.out.println("Control OK");
		} else {
			System.out.println("Control FAIL");
		}
	}
	
	public static void sendNodes(Graph graph1, DataOutputStream dataOut,
			DataInputStream dataIn) throws IOException,
			InterruptedException {
		dos = dataOut;
		dis = dataIn;
		graph = graph1;
		dos.flush();
		sendSize();
		Enumeration enumeration = graph.getHashtable().keys();
		int control;
		while (enumeration.hasMoreElements()) {
			Pair pair = (Pair) enumeration.nextElement();
			Node node = graph.getNode(pair);
			int type = typeToInt(node);
			control = (int) (Math.random() * 255);
			dos.writeInt(pair.getX());
			dos.flush();
			dos.writeInt(pair.getY());
			dos.flush();
			dos.writeInt(type);
			dos.flush();
			dos.writeInt(control);
			dos.flush();
			if (dis.read() == control) {
				System.out.println("Control OK");
			} else {
				System.out.println("Control FAIL");
			}
		}
	}

	public static void sendSize() throws IOException {
		dos.writeInt(GraphTools.getSize(graph));
		dos.flush();
	}

	public static int typeToInt(Node node) {
		Type a = node.getType();
		switch (a) {
		case EMPTY:
			return 0;
		case UNKNOWN:
			return 1;
		case PUSHSTART:
			return 3;
		case PULLSTART:
			return 2;
		case BOX:
			return 4;
		case DEST:
			return 5;
		case UNDEFINED:
			return 6;
		case PULLER:
			return 7;
		case PUSHER:
			return 8;
		}
		return 6;
	}

}
