package rs.ac.uns.ftn.informatika.spring.security.view;

public class WorkingDayDTO {
	private String day;
	private String startTime;
	private String endTime;
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Override
	public String toString() {
		return "WorkingDayDTO [day=" + day + ", startTime=" + startTime + ", endTime=" + endTime + ", getDay()="
				+ getDay() + ", getStartTime()=" + getStartTime() + ", getEndTime()=" + getEndTime() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	

}
