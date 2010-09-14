import java.util.ArrayList;
import java.util.List;


public class ColorSettings {
	private class ColorSettingsEntry{
		String name;
		int[] rgb;
		
		ColorSettingsEntry(String name, int[] rgb){
			this.name = name;
			this.rgb =rgb;
		}
	}
	
	private List<ColorSettingsEntry> colors;
	
	public ColorSettings(){
		colors = new ArrayList<ColorSettingsEntry>();
	}
	
	public boolean addColor(String name, int[] rgb){
		return colors.add(new ColorSettingsEntry(name,rgb));
	}
	
	public int size(){
		return colors.size();
	}
	
	public boolean empty(){
		return size() == 0;
	}
	
	public String getColor(int[] rgb){
		
		if(empty()){
			Helper.error("ColorSettings.getColor: is empty");
		}
		
		String result = colors.get(0).name;
		int diff = getQuadraticDiff(rgb,colors.get(0).rgb);
		int cDiff;
		
		for(int i =1; i< colors.size();i++){
			cDiff = getQuadraticDiff(rgb,colors.get(i).rgb);
			if(cDiff < diff){
				diff = cDiff;
				result = colors.get(i).name;
			}
		}
		
		return result;
	}
	
	private int getQuadraticDiff(int[] rgb1, int [] rgb2){
		if(rgb1.length != rgb2.length){
			Helper.error("ColorSettings.getQuadraticDiff: arguments have different length");
		}
		
		int diff = 0;
		
		for(int i=0;i<rgb1.length;i++){
			diff += (rgb1[i]-rgb2[i])*(rgb1[i]-rgb2[i]);
		}
		
		return diff;
	}
}
