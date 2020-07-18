package vehicles;

public class Car {
	
	private String name;
	private String color;
	private String[] interior;
	private int doors;
	
	public Car(String name, String color, String[] interior, int doors) {
		super();
		this.name = name;
		this.color = color;
		this.interior = interior;
		this.doors = doors;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String[] getInterior() {
		return interior;
	}
	public void setInterior(String[] interior) {
		this.interior = interior;
	}
	public int getDoors() {
		return doors;
	}
	public void setDoors(int doors) {
		this.doors = doors;
	}
	
	

}
