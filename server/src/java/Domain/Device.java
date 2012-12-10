package Domain;

public class Device {
	
	private int id;
	private String name;
	private String photo_url;
	private String logo_url;
	private double watt_total;
	private int divide_by;
	private String sensor;
	
	public Device(){
	}

	public Device(int id) {
		this.id = id;
	}

	public Device(String name, String photo_url, double watt_total, int divide_by, String sensor) {
		this.name = name;
		this.photo_url = photo_url;
		this.watt_total = watt_total;
		this.divide_by = divide_by;
		this.sensor = sensor;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto_url() {
		return photo_url;
	}

	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}
	
	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	public double getWatt_total() {
		return watt_total;
	}

	public void setWatt_total(double watt_total) {
		this.watt_total = watt_total;
	}

	public int getDivide_by() {
		return divide_by;
	}

	public void setDivide_by(int divide_by) {
		this.divide_by = divide_by;
	}

	public String getSensor() {
		return sensor;
	}

	public void setSensor(String sensor) {
		this.sensor = sensor;
	}
	
	@Override
	public String toString(){
		return "id = "+ id + "; Name = " + name + "; Watt total = " + watt_total + "; devide_by = " + divide_by + "; Sensor" + sensor;
	}
}