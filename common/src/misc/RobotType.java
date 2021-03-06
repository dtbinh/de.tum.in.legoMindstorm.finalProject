package misc;

public enum RobotType {
MAPPER(0), PUSHER(1), PULLER(2),UNDEFINED(3);


	int id;
	
	RobotType(int id){
		this.id = id;
	}
	
	public int toInt(){
		return id;
	}
	
	public static RobotType int2RobotType(int i){
		switch(i){
		case 0:
			return MAPPER;
		case 1:
			return PUSHER;
		case 2:
			return PULLER;
		default:
			return UNDEFINED;
		}
	}
}

