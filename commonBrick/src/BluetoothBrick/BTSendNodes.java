package BluetoothBrick;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import lejos.nxt.LCD;
import lejos.nxt.Sound;
import miscBrick.Helper;
import Graph.Graph;
import Graph.GraphTools;
import Graph.Node;
import Graph.Pair;
import Graph.Type;

public class BTSendNodes {

	private static DataOutputStream dos;
	private static DataInputStream dis;
	private static int arrayLength = 6;
	private static Graph graph;
	private static boolean toBrick;

	public static int getArrayLength() {
		return arrayLength;
	}
	
	
	
	public static void sendNodes(Graph graph1, DataOutputStream dataOut,
			DataInputStream dataIn, boolean toBrick1) throws IOException,
			InterruptedException {
		dos = dataOut;
		dis = dataIn;
		graph = graph1;
		toBrick = toBrick1;
		sendSize();
		Enumeration enumeration = graph.getHashtable().keys();
		byte[] send = new byte[arrayLength];
		int control;
		while (enumeration.hasMoreElements()) {
			Pair pair = (Pair) enumeration.nextElement();
			Node node = graph.getNode(pair);
			int type = typeToInt(node);
			control = (int) (Math.random() * 255);
			send[4] = (byte) type;
			if (pair.getX() < 0) {
				send[1] = (byte) 1;
				send[0] = (byte) -pair.getX();
			} else {
				send[1] = (byte) 0;
				send[0] = (byte) pair.getX();
			}
			if (pair.getY() < 0) {
				send[3] = (byte) 1;
				send[2] = (byte) -pair.getY();
			} else {
				send[3] = (byte) 0;
				send[2] = (byte) pair.getY();

			}
			send[5] = (byte) control;
			dos.write(send);
			dos.flush();
			Helper.drawString("Data send", 0, 1);
			Helper.drawString("Waiting for Data", 0, 2);
			Helper.drawString("Data received", 0, 3);
			if (toBrick) {
				dis.read();
				dis.read();
			}
			if (dis.read() == control) {
				Sound.beep();
			} else {
				Sound.buzz();
			}
			// Thread.sleep(200);
			LCD.clearDisplay();
		}
		if (!toBrick) {
			BTBrick.closeConnectionAfterOK();
		} 
	}

	public static void sendSize() throws IOException {
		byte[] numberOfNodes = new byte[1];
		numberOfNodes[0] = (byte) GraphTools.getSize(graph);
		dos.write(numberOfNodes);
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