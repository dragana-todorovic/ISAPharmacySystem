package rs.ac.uns.ftn.informatika.spring.security.view;

import java.util.List;

public class NewDermatologistDTO {
	
	String dermatologistId;
	List<WorkingDayDTO> workingTimes;
	public String getDermatologistId() {
		return dermatologistId;
	}
	public void setDermatologistId(String dermatologistId) {
		this.dermatologistId = dermatologistId;
	}
	public List<WorkingDayDTO> getWorkingTimes() {
		return workingTimes;
	}
	public void setWorkingTimes(List<WorkingDayDTO> workingTimes) {
		this.workingTimes = workingTimes;
	}
	@Override
	public String toString() {
		return "NewDermatologistDTO [dermatologistId=" + dermatologistId + ", workingTimes=" + workingTimes + "]";
	}
	
	
	

}
