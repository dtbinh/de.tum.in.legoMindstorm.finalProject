package Bluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import Graph.Graph;
import Memory.Action;
import Memory.Map;
import Memory.Move;


public class BTSendMessage {
	public static int sendType(MessageType type){
		switch(type){
		case MOVE:
			return 1;
		case ACTION:
			return 2;
		case MAP:
			return 3;
		}
		return 0;
	}
	
	public static void sendMessage(MessageType type, DataOutputStream dataOut,
			DataInputStream dataIn) throws IOException, InterruptedException{
			dataOut.writeInt(BTSendMessage.sendType(type));
			dataOut.flush();
			switch(type){
			case MOVE:
				BTSendAnything.sendMove(Move.getMove(), dataOut, dataIn);
			case ACTION:
				BTSendAnything.sendAction(Action.getAction(), dataOut, dataIn);
			case MAP:
				BTSendAnything.sendNodes(Map.getMap(),dataOut,dataIn);
			}
		}
	}

