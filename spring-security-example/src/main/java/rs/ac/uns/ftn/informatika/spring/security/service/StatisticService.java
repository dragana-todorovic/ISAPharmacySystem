package rs.ac.uns.ftn.informatika.spring.security.service;

import java.time.LocalDate;
import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.view.StatisticDTO;

public interface StatisticService {
	
	List<StatisticDTO> getDermatologistAppoitmentByYear(String email);

	List<StatisticDTO> getDermatologistAppoitmentByMounth(String email);

	List<StatisticDTO> getDermatologistAppoitmentByQuarter(String email);

	List<StatisticDTO> getPharmacistConselingByYear(String email);

	List<StatisticDTO> getPharmacistConselingByMounth(String email);

	List<StatisticDTO> getPharmacistConselingByQuarter(String email);

	List<StatisticDTO> getMedicineConsumptionByYear(String email);

	List<StatisticDTO> getMedicineConsumptionByMonth(String email);

	List<StatisticDTO> getMedicineConsumptionQuarter(String email);

	List<StatisticDTO> getPharmacyIncome(String email, LocalDate from, LocalDate to);

	List<StatisticDTO> getPharmacyIncomeFromPharmacistCouseling(String email, LocalDate from, LocalDate to);

	List<StatisticDTO> getPharmacyIncomeFromMedicineConsumption(String email, LocalDate from, LocalDate to);

}
