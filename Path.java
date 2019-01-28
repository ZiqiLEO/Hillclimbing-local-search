

import java.io.*; 
import java.lang.*;
import java.util.*;

class Path{
	private double cost;
	private ArrayList<city> path= new ArrayList<city>();
	
	public Path(ArrayList<city> cities) {
		int len = cities.size();
		for(int i = 0; i < len; ++i) {
			this.path.add(cities.get(i));
			
		}
		Collections.shuffle(this.path);
		double cost = 0;
		
		if(len > 1) {
			for(int i = 0; i < len-1; ++i) {
				cost = cost + city.getEuclid(this.path.get(i),this.path.get(i+1));
			}
			cost = cost + city.getEuclid(this.path.get(len-1), this.path.get(0));
		}
		this.cost = cost;
	}
	public Path(Path p) {
		int len = p.getPath().size();
		for(int i = 0; i < len; ++i) {
			this.path.add(p.getPath().get(i));
		}
		this.cost = p.getCost();
	}
	public double getCost() {
		return this.cost;
	}
	
	public ArrayList<city> getPath() {
		return this.path;
	}
	public void update() {
		double cost = 0;
		int len = this.path.size();
		if(len > 1) {
			for(int i = 0; i < len-1; ++i) {
				cost = cost + city.getEuclid(this.path.get(i),this.path.get(i+1));
			}
			cost = cost + city.getEuclid(this.path.get(len-1), this.path.get(0));
		}
		this.cost = cost;
		
		
	}
	
	
	
}





