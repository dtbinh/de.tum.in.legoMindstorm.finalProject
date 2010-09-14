
public class LightSettings {
	private int groundValue;
	private int tolerance;
	
	public LightSettings(int groundValue,int tolerance){
		this.groundValue = groundValue;
		this.tolerance = tolerance;
	}
	
	public int getGroundValue(){
		return groundValue;
	}
	
	public int getTolerance(){
		return tolerance;
	}
	
	public void setTolerance(int tolerance){
		this.tolerance = tolerance;
	}
	
	public void setGroundValue(int groundValue){
		this.groundValue = groundValue;
	}
	
	public boolean groundChange(int lightValue){
		return Math.abs(lightValue - groundValue) > tolerance;
	}

}
