package rs.ac.uns.ftn.informatika.spring.security.service;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservation;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MedicineReservationDTO;

public interface MedicineReservationService {
	MedicineReservation saveReservation(MedicineReservationDTO medicineReservationDto);
	void editReservation(MedicineReservation medicineReservation);
	MedicineReservation findById(Long id);

}
