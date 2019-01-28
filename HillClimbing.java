

import java.io.*; 
import java.lang.*;
import java.util.*;

class HillClimbing{
	public static int cityNum;
	public static ArrayList<city> cities = new ArrayList<city>();
	public static Path path;
	public static Path finalpath;
	public static final int Iteration = 100;
	public static final int Random_times = 10;
	public static final double BIGNUM = 10000000;
	public static final double CoolingRate = 0.005;
	public static final double InitTemperature = 999;
	public static final double MIN_TEMP = 0.99;
	public static int c = 0;
	
	// Basic Hill climbing
	public static Path shortestPath(Path curPath) {
		
		double curcost = BIGNUM;
		Path nextPath  = null;
		int len = curPath.getPath().size();
		int count = 0;
		while(true) {
			count++;
			//find best neighbor
			
			for(int i =0; i < len-1; ++i) {
				for(int j = i+1; j < len; ++j) {
					Path temp = new Path(curPath);
					temp = SwapCity(temp, i, j);
					
					
					if(temp.getCost() < curcost) {
						curcost = temp.getCost();
						nextPath = new Path(temp);
					}
					
				}
			}
			if(nextPath.getCost() < curPath.getCost()) {
				curPath = new Path(nextPath);
				
			}else {
				break;
			}
			//testcode
			/*int len = nextPath.getPath().size();
			for(int i = 0; i < len; ++i) {
				 System.out.println(nextPath.getPath().get(i).getName() + " ");
			 }
			 System.out.println(nextPath.getCost());*/
		}
		
		c = count;
		return curPath;
		
	}
	
	
	
	//Hillclimbing with Random Restart
	public static Path RandomRestart(ArrayList<city> cities) {
		
		ArrayList<Path> opt_paths = new ArrayList<Path>();
		for(int i = 0; i < Random_times; ++i) {
			Path path = new Path(cities);
			opt_paths.add(shortestPath(path));
		}
		int len = opt_paths.size();
		double opt_cost = BIGNUM;
		Path opt_path = null;
		//find the optimal path
		for(int i = 0; i < len; ++i) {
			if(opt_paths.get(i).getCost() < opt_cost) {
				opt_cost = opt_paths.get(i).getCost();
				opt_path = new Path(opt_paths.get(i));
			}
		}
		/*for(int i =0; i < len; ++i) {
			System.out.println(opt_paths.get(i).getCost());
		}*/
		return opt_path;
		
	}
	
	
	
	// Hill Climbing with sideway moves
	public static Path SidewayMove(Path curPath) {
		double curcost = BIGNUM;
		Path nextPath  = null;
		int len = curPath.getPath().size();
		int count = 0;
		
		while(true) {
			//find best neighbor
			//System.out.println(count);
			for(int i =0; i < len-1; ++i) {
				for(int j = i+1; j < len; ++j) {
					Path temp = new Path(curPath);
					temp = SwapCity(temp, i, j);
					//System.out.println(temp.getCost());
					if(temp.getCost() < curcost) {
						curcost = temp.getCost();
						nextPath = new Path(temp);
					}
					
				}
			}
			if(nextPath.getCost() > curPath.getCost()) {
				//System.out.println("IAMHERE");
				break;
				
			}else if(nextPath.getCost() == curPath.getCost()) {
				
				if(count > 100) {
					break;
				}else {
					curPath = new Path(nextPath);
					count++;
				}
			}else {
				
				curPath = new Path(nextPath);
				
				count = 0;
			}
			//testcode
			/*int len = nextPath.getPath().size();
			for(int i = 0; i < len; ++i) {
				 System.out.println(nextPath.getPath().get(i).getName() + " ");
			 }*/
			 //System.out.println(nextPath.getCost());
		}
		return curPath;
		
		
	}
	
	public static Path SwapCity(Path path, int x, int y) {
		city c1 = path.getPath().get(x);
		city c2 = path.getPath().get(y);
		path.getPath().set(y, c1);
		path.getPath().set(x, c2);
		
		path.update();
		
		return path;
	}
	
	public static Path RandomSwap(Path path) {
		int x = 0, y = 0;
		int len = path.getPath().size();
		while(x == y) {
			x = (int) (len * Math.random());
			y = (int) (len * Math.random());
		}
		Path p = new Path(path);
		p = SwapCity(p,x,y);
		return p;
	}
	
	
	// Simulated Annealing method1
	// T exponential decay
	public static Path SimulatedAnnealing1(Path curPath) {
		double T = InitTemperature;
		Path nextPath;
		Path optPath = new Path(curPath);
		
		while(T > MIN_TEMP) {
			nextPath = RandomSwap(curPath);
			double DeltE = curPath.getCost() - nextPath.getCost();
			if(DeltE > 0) {
				curPath = new Path(nextPath);
			}else {
				double AcceptProb = Math.exp(DeltE/T);
				if(AcceptProb >= Math.random()) {
					curPath = new Path(nextPath);
				}
			}
			T *= 1- CoolingRate;
		}
		
		return curPath;
	}
	// Simulated Annealing method2
	// T linear decay
		public static Path SimulatedAnnealing2(Path curPath) {
			double T = InitTemperature;
			Path nextPath;
			Path optPath = new Path(curPath);
			
			while(T > MIN_TEMP) {
				nextPath = RandomSwap(curPath);
				double DeltE = curPath.getCost() - nextPath.getCost();
				if(DeltE > 0) {
					curPath = new Path(nextPath);
				}else {
					double AcceptProb = Math.exp(DeltE/T);
					if(AcceptProb >= Math.random()) {
						curPath = new Path(nextPath);
					}
				}
				T -= 1;
			}
			
			return curPath;
		}
		
		// Simulated Annealing method3
		// T logarithm decay
			public static Path SimulatedAnnealing3(Path curPath) {
				double T = InitTemperature;
				double x = 1.1;
				Path nextPath;
				Path optPath = new Path(curPath);
				
				while(T > MIN_TEMP) {
					nextPath = RandomSwap(curPath);
					double DeltE = curPath.getCost() - nextPath.getCost();
					if(DeltE > 0) {
						curPath = new Path(nextPath);
					}else {
						double AcceptProb = Math.exp(DeltE/T);
						if(AcceptProb >= Math.random()) {
							curPath = new Path(nextPath);
						}
					}
					T -= Math.log(x);
					x += 0.1;
				}
				
				return curPath;
			}
	
	
	public static void main(String[] args) throws Exception{
		 if(args.length != 1){
		      System.out.println("Invalid input numbers");
		      System.exit(1);
		 }
		 String filename = args[0];
		 try {
			 File file = new File(filename);
			 Scanner sc = new Scanner(file);
			 cityNum = sc.nextInt();
			 
			 
			 for(int i = 0; i < cityNum; ++i) {
				 char name = sc.next().charAt(0);
				 int x = sc.nextInt();
				 int y = sc.nextInt();
				 city c = new city(name,x,y);
				 cities.add(c);
			 }
			
			 double sum = 0;
			 double ratio = 0;
			 double cost = 0;
			 double best =    354.55691319058866;
			 int steps = 0;
			 c = 0;
			 int bestcount = 0;
			 long time = 0;
			 finalpath = new Path(cities);
			 int len = finalpath.getPath().size();
			 /*for(int i = 0; i < 100; ++i) {
				 
				 long start = System.nanoTime();
				 //finalpath = SimulatedAnnealing1(finalpath);
				 //finalpath = SimulatedAnnealing2(finalpath); // linear decay
				 //finalpath = SimulatedAnnealing3(finalpath);
				 long end = System.nanoTime();
				 time += (end-start);
			
				 steps += c;
				 c = 0;
				 cost = finalpath.getCost();
				 sum += cost;
				 ratio += (cost / best);
				 if(cost <= best){
				 	bestcount++;
				 }
			 }
			 System.out.println(time/1000000);
			 System.out.println(ratio);*/
			 //System.out.println(bestcount);
			 
			 
			 // Solution with hill climbing
			 //finalpath = shortestPath(finalpath);
			 
			 // Solution with Random Restart
			 finalpath = RandomRestart(cities);
			 
			  // Solution with sideway moves
			 //finalpath = SidewayMove(finalpath);
			 
			 // Solution with Simulated Annealing
			 //finalpath = SimulatedAnnealing1(finalpath); // exponential decay
			 //finalpath = SimulatedAnnealing2(finalpath); // linear decay
			 //finalpath = SimulatedAnnealing3(finalpath); // logarithm decay
			 
			 
			 
			 /*for(int i = 0; i < finalpath.getPath().size(); ++i) {
				 System.out.println(finalpath.getPath().get(i).getName() + " ");
			 }*/
			 System.out.println(finalpath.getCost());
			 
		}catch(Exception e) {
			 System.err.println("ERROR");
		 }
	 }
	

	
	
}
	
