

class city{
	private char name;
	private int x;
	private int y;
	
	public city(char name,int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	public char getName() {
		return this.name;
	}
	public int getX(){
		return this.x; 
	}
	public int getY() {
		return this.y;
	}
	public static double getEuclid(city a, city b) {
		int temp = a.x - b.x;
		int sqrx = temp*temp;
		int temp1 = a.y- b.y;
		int sqry = temp1*temp1;
		temp = sqrx + sqry;
		double Euclid = Math.sqrt(temp);
		return Euclid;
	}
}
