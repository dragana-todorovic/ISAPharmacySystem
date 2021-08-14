package rs.ac.uns.ftn.informatika.spring.security.view;


public class WorkingTimeIntervalDTO {
	
	private String startTime;
	private String endTime;
	

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
		return "WorkingDayDTO [ startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
