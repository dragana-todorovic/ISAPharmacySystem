package rs.ac.uns.ftn.informatika.spring.security.service;
import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservation;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MedicineReservationDTO;

public interface MedicineReservationService {
	MedicineReservation saveReservation(MedicineReservationDTO medicineReservationDto,Long v);
	void editReservation(MedicineReservation medicineReservation);
	MedicineReservation findById(Long id);
	List<MedicineReservation> getAll();
	void saveReservation(MedicineReservation medicineReservation);

}
