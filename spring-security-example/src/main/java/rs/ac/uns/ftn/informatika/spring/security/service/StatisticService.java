package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.view.StatisticDTO;

public interface StatisticService {
	
	List<StatisticDTO> getDermatologistAppoitmentByYear(String email);

	List<StatisticDTO> getDermatologistAppoitmentByMounth(String email);

	List<StatisticDTO> getDermatologistAppoitmentByQuarter(String email);

	List<StatisticDTO> getPharmacistConselingByYear(String email);

	List<StatisticDTO> getPharmacistConselingByMounth(String email);

	List<StatisticDTO> getPharmacistConselingByQuarter(String email);

}
