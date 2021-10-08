package model;

public class UserPackage {

	private int id;
	private String name;
	private char type;
	private int limit;
	private String businessZone;
	private char visibility;
	
	public UserPackage() {}
	
	public UserPackage(int id,String name, char type, int limit, String businessZone, char visibility) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.limit = limit;
		this.businessZone = businessZone;
		this.visibility = visibility;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getBusinessZone() {
		return businessZone;
	}

	public void setBusinessZone(String businessZone) {
		this.businessZone = businessZone;
	}

	public char getVisibility() {
		return visibility;
	}

	public void setVisibility(char visibility) {
		this.visibility = visibility;
	}
	
}
