package application;

import java.util.HashMap;

public class GridPaneStore {
	
	public static HashMap<String, GridLocation> map = new HashMap<String, GridLocation>();

}

class GridLocation {
	
	int x;
	int y;
	
	GridLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	int getX() {
		return this.x;
	}
	
	int getY() {
		return this.y;
	}
}