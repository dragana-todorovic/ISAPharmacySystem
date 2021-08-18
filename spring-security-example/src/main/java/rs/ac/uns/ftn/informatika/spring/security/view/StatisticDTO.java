package rs.ac.uns.ftn.informatika.spring.security.view;

public class StatisticDTO {
	
	private String time;
	private int data;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "StatisticDTO [time=" + time + ", data=" + data + "]";
	}
	
	
	

}
