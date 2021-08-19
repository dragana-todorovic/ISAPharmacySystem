package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservation;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservationStatus;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounseling;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.WorkingTime;
import rs.ac.uns.ftn.informatika.spring.security.repository.ActionAndBenefitRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistAppointmentRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineReservationRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistCounselingRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.StatisticService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.view.StatisticDTO;

@Service
public class StatisticServiceImpl implements StatisticService{
	
	@Autowired
	private PharmacyRepository pharmacyRepository;

	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	

	@Autowired
	private DermatologistAppointmentRepository dermatologistAppointmentRepository;
	
	@Autowired
	private PharmacistCounselingRepository pharmacistConselingRepository;
	
	@Autowired
	private PharmacistRepository pharmacistRepository;
	
	@Autowired
	private MedicineReservationRepository medicineReservationRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;

	@Override
	public List<StatisticDTO> getDermatologistAppoitmentByYear(String email) {
		List<StatisticDTO> statisticsPerYear = new ArrayList<StatisticDTO>();
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		List<DermatologistAppointment> allAppoimtments = this.dermatologistAppointmentRepository.findAll();
		
		
		List<DermatologistAppointment> result = new ArrayList<DermatologistAppointment>();
		
		for(DermatologistAppointment da : allAppoimtments) {
			if(da.getStartDateTime().isBefore(LocalDateTime.now())) {
				for(WorkingTime wt : da.getDermatologist().getWorkingTimes()) {
					if(wt.getPharmacy().equals(p)) {
						result.add(da);
					}
				}
			}
		}
		
		
		
		
		List<LocalDateTime> allDates = new ArrayList<LocalDateTime>();
		for(DermatologistAppointment d : result) {
			allDates.add(d.getStartDateTime());
		}
		
		Collections.sort(allDates);
		
		List<Integer> years = new ArrayList<Integer>(); 
		for(LocalDateTime l : allDates) {
			if(!years.contains(l.getYear())) {
				years.add(l.getYear());
			}
			
		}

		Collections.sort(years);
		for(Integer year : years) {
			StatisticDTO statistic = new StatisticDTO();
			statistic.setTime(year.toString());
			statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == year).count());
			statisticsPerYear.add(statistic);
		}
	
		return statisticsPerYear;
		
	}
	
	@Override
	public List<StatisticDTO> getDermatologistAppoitmentByMounth(String email) {
		List<StatisticDTO> statisticsPerMonth = new ArrayList<StatisticDTO>();
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		List<DermatologistAppointment> allAppoimtments = this.dermatologistAppointmentRepository.findAll();
		
		
		List<DermatologistAppointment> result = new ArrayList<DermatologistAppointment>();
		
		for(DermatologistAppointment da : allAppoimtments) {
			if(da.getStartDateTime().isBefore(LocalDateTime.now())) {
				for(WorkingTime wt : da.getDermatologist().getWorkingTimes()) {
					if(wt.getPharmacy().equals(p)) {
						result.add(da);
					}
				}
			}
		}
		List<LocalDateTime> allDates = new ArrayList<LocalDateTime>();
		for(DermatologistAppointment d : result) {
			allDates.add(d.getStartDateTime());
		}
		
		Collections.sort(allDates);
		
		List<String> months = new ArrayList<String>(); 
		
		for(LocalDateTime l : allDates) {
			if(!months.contains(String.valueOf(l.getMonthValue()) + "/" + l.getYear())) {
				months.add(String.valueOf(l.getMonthValue()) + "/" + l.getYear());
			}
			
		}

		Collections.sort(months);
		for(String month : months) {
			StatisticDTO statistic = new StatisticDTO();
			statistic.setTime(month.toString());
			statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == Integer.parseInt(month.split("/")[1]) && 
					r.getStartDateTime().getMonthValue() == Integer.parseInt(month.split("/")[0])
					
					).count());
			statisticsPerMonth.add(statistic);
		}
	
		return statisticsPerMonth;
		
	}
	
	
	@Override
	public List<StatisticDTO> getDermatologistAppoitmentByQuarter(String email) {
		List<StatisticDTO> statisticsPerQuarter = new ArrayList<StatisticDTO>();
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		List<DermatologistAppointment> allAppoimtments = this.dermatologistAppointmentRepository.findAll();
		
		
		List<DermatologistAppointment> result = new ArrayList<DermatologistAppointment>();
		
		for(DermatologistAppointment da : allAppoimtments) {
			if(da.getStartDateTime().isBefore(LocalDateTime.now())) {
				for(WorkingTime wt : da.getDermatologist().getWorkingTimes()) {
					if(wt.getPharmacy().equals(p)) {
						result.add(da);
					}
				}
			}
		}
		List<LocalDateTime> allDates = new ArrayList<LocalDateTime>();
		for(DermatologistAppointment d : result) {
			allDates.add(d.getStartDateTime());
		}
		
		Collections.sort(allDates);
		
		List<String> quarter = new ArrayList<String>(); 
		int current = 1;
		for(LocalDateTime l : allDates) {
			if(l.getMonthValue() > 0 && l.getMonthValue() < 4) {
				current = 1;
			} else if (l.getMonthValue() > 3 && l.getMonthValue() < 7) {
				current = 2;
			} else if (l.getMonthValue() > 6 && l.getMonthValue() < 10) {
				current = 3;
			} else if (l.getMonthValue() > 9 && l.getMonthValue() <= 12) {
				current = 4;
			}
			if(!quarter.contains(String.valueOf(current) + "/" + l.getYear())) {
				quarter.add(String.valueOf(current) + "/" + l.getYear());
			}
			
		}

		Collections.sort(quarter);
		for(String q : quarter) {
			StatisticDTO statistic = new StatisticDTO();
			statistic.setTime(q.toString());
			if(q.split("/")[0].equals("1")) {
				statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == Integer.parseInt(q.split("/")[1]) && 
						r.getStartDateTime().getMonthValue() > 0 && r.getStartDateTime().getMonthValue() < 4
						
						).count());
			} else if(q.split("/")[0].equals("2")) {
				statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == Integer.parseInt(q.split("/")[1]) && 
						r.getStartDateTime().getMonthValue() > 3 && r.getStartDateTime().getMonthValue() < 7
						
						).count());
			} else if(q.split("/")[0].equals("3")) {
				statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == Integer.parseInt(q.split("/")[1]) && 
						r.getStartDateTime().getMonthValue() > 6 && r.getStartDateTime().getMonthValue() < 10
						
						).count());
			} else if(q.split("/")[0].equals("4")) {
				statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == Integer.parseInt(q.split("/")[1]) && 
						r.getStartDateTime().getMonthValue() > 9 && r.getStartDateTime().getMonthValue() <= 12
						
						).count());
			}
			statisticsPerQuarter.add(statistic);
		}
	
		return statisticsPerQuarter;
		
	}
	
	
	@Override
	public List<StatisticDTO> getPharmacistConselingByYear(String email) {
		List<StatisticDTO> statisticsPerYear = new ArrayList<StatisticDTO>();
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		List<PharmacistCounseling> allAppoimtments = this.pharmacistConselingRepository.findAll();
		
		
		List<PharmacistCounseling> result = new ArrayList<PharmacistCounseling>();
		
		for(PharmacistCounseling da : allAppoimtments) {
			if(da.getStartDateTime().isBefore(LocalDateTime.now())) {
					if(da.getPharmacist().getWorkingTimes().getPharmacy().equals(p)) {
						result.add(da);
					}
				}
			
		}
		
		List<LocalDateTime> allDates = new ArrayList<LocalDateTime>();
		for(PharmacistCounseling d : result) {
			allDates.add(d.getStartDateTime());
		}
		
		Collections.sort(allDates);
		
		List<Integer> years = new ArrayList<Integer>(); 
		for(LocalDateTime l : allDates) {
			if(!years.contains(l.getYear())) {
				years.add(l.getYear());
			}
			
		}

		Collections.sort(years);
		for(Integer year : years) {
			StatisticDTO statistic = new StatisticDTO();
			statistic.setTime(year.toString());
			statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == year).count());
			statisticsPerYear.add(statistic);
		}
	
		return statisticsPerYear;
		
	}
	
	@Override
	public List<StatisticDTO> getPharmacistConselingByMounth(String email) {
		List<StatisticDTO> statisticsPerMonth = new ArrayList<StatisticDTO>();
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		List<PharmacistCounseling> allAppoimtments = this.pharmacistConselingRepository.findAll();
		
		
		List<PharmacistCounseling> result = new ArrayList<PharmacistCounseling>();
		
		for(PharmacistCounseling da : allAppoimtments) {
			if(da.getStartDateTime().isBefore(LocalDateTime.now())) {
					if(da.getPharmacist().getWorkingTimes().getPharmacy().equals(p)) {
						result.add(da);
					}
				}
			
		}
		
		List<LocalDateTime> allDates = new ArrayList<LocalDateTime>();
		for(PharmacistCounseling d : result) {
			allDates.add(d.getStartDateTime());
		}
		
		
		Collections.sort(allDates);
		
		List<String> months = new ArrayList<String>(); 
		
		for(LocalDateTime l : allDates) {
			if(!months.contains(String.valueOf(l.getMonthValue()) + "/" + l.getYear())) {
				months.add(String.valueOf(l.getMonthValue()) + "/" + l.getYear());
			}
			
		}

		Collections.sort(months);
		for(String month : months) {
			StatisticDTO statistic = new StatisticDTO();
			statistic.setTime(month.toString());
			statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == Integer.parseInt(month.split("/")[1]) && 
					r.getStartDateTime().getMonthValue() == Integer.parseInt(month.split("/")[0])
					
					).count());
			statisticsPerMonth.add(statistic);
		}
	
		return statisticsPerMonth;
		
	}
	
	
	@Override
	public List<StatisticDTO> getPharmacistConselingByQuarter(String email) {
		List<StatisticDTO> statisticsPerQuarter = new ArrayList<StatisticDTO>();
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		List<PharmacistCounseling> allAppoimtments = this.pharmacistConselingRepository.findAll();
		
		
		List<PharmacistCounseling> result = new ArrayList<PharmacistCounseling>();
		
		for(PharmacistCounseling da : allAppoimtments) {
			if(da.getStartDateTime().isBefore(LocalDateTime.now())) {
					if(da.getPharmacist().getWorkingTimes().getPharmacy().equals(p)) {
						result.add(da);
					}
				}
			
		}
		
		List<LocalDateTime> allDates = new ArrayList<LocalDateTime>();
		for(PharmacistCounseling d : result) {
			allDates.add(d.getStartDateTime());
		}
		
		
		Collections.sort(allDates);
		
		List<String> quarter = new ArrayList<String>(); 
		int current = 1;
		for(LocalDateTime l : allDates) {
			if(l.getMonthValue() > 0 && l.getMonthValue() < 4) {
				current = 1;
			} else if (l.getMonthValue() > 3 && l.getMonthValue() < 7) {
				current = 2;
			} else if (l.getMonthValue() > 6 && l.getMonthValue() < 10) {
				current = 3;
			} else if (l.getMonthValue() > 9 && l.getMonthValue() <= 12) {
				current = 4;
			}
			if(!quarter.contains(String.valueOf(current) + "/" + l.getYear())) {
				quarter.add(String.valueOf(current) + "/" + l.getYear());
			}
			
		}

		Collections.sort(quarter);
		for(String q : quarter) {
			StatisticDTO statistic = new StatisticDTO();
			statistic.setTime(q.toString());
			if(q.split("/")[0].equals("1")) {
				statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == Integer.parseInt(q.split("/")[1]) && 
						r.getStartDateTime().getMonthValue() > 0 && r.getStartDateTime().getMonthValue() < 4
						
						).count());
			} else if(q.split("/")[0].equals("2")) {
				statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == Integer.parseInt(q.split("/")[1]) && 
						r.getStartDateTime().getMonthValue() > 3 && r.getStartDateTime().getMonthValue() < 7
						
						).count());
			} else if(q.split("/")[0].equals("3")) {
				statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == Integer.parseInt(q.split("/")[1]) && 
						r.getStartDateTime().getMonthValue() > 6 && r.getStartDateTime().getMonthValue() < 10
						
						).count());
			} else if(q.split("/")[0].equals("4")) {
				statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == Integer.parseInt(q.split("/")[1]) && 
						r.getStartDateTime().getMonthValue() > 9 && r.getStartDateTime().getMonthValue() <= 12
						
						).count());
			}
			statisticsPerQuarter.add(statistic);
		}
	
		return statisticsPerQuarter;
		
	}
	
	@Override
	public List<StatisticDTO> getMedicineConsumptionByYear(String email) {
		List<StatisticDTO> statisticsPerYear = new ArrayList<StatisticDTO>();
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		Set<MedicineReservation> allReservations = p.getMedicineReservations();
		
		
		List<MedicineReservation> result = new ArrayList<MedicineReservation>();
		
		for(MedicineReservation da : allReservations) {
			if(da.getDueTo().isBefore(LocalDate.now()) && da.getStatus().equals(MedicineReservationStatus.TAKEN)) {
				result.add(da);
			}
		}
			
		List<LocalDate> allDates = new ArrayList<LocalDate>();
		for(MedicineReservation d : result) {
			allDates.add(d.getDueTo());
		}
		
		Collections.sort(allDates);
		
		List<Integer> years = new ArrayList<Integer>(); 
		for(LocalDate l : allDates) {
			if(!years.contains(l.getYear())) {
				years.add(l.getYear());
			}
			
		}

		Collections.sort(years);
		for(Integer year : years) {
			StatisticDTO statistic = new StatisticDTO();
			int quantity = 0;
			for(MedicineReservation mr : result) {
				if(mr.getDueTo().getYear() == year) {
					quantity += mr.getMedicineWithQuantity().getQuantity();
				}
			}
			statistic.setTime(year.toString());
			statistic.setData((int)quantity);
			statisticsPerYear.add(statistic);
		}
	
		return statisticsPerYear;
		
	}
	
	@Override
	public List<StatisticDTO> getMedicineConsumptionByMonth(String email) {
		List<StatisticDTO> statisticsPerMonth = new ArrayList<StatisticDTO>();
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		Set<MedicineReservation> allReservations = p.getMedicineReservations();
		
		
		List<MedicineReservation> result = new ArrayList<MedicineReservation>();
		
		for(MedicineReservation da : allReservations) {
			if(da.getDueTo().isBefore(LocalDate.now()) && da.getStatus().equals(MedicineReservationStatus.TAKEN)) {
				result.add(da);
			}
		}
			
		List<LocalDate> allDates = new ArrayList<LocalDate>();
		for(MedicineReservation d : result) {
			allDates.add(d.getDueTo());
		}
		
		Collections.sort(allDates);
		
		List<String> months = new ArrayList<String>(); 
		
		for(LocalDate l : allDates) {
			if(!months.contains(String.valueOf(l.getMonthValue()) + "/" + l.getYear())) {
				months.add(String.valueOf(l.getMonthValue()) + "/" + l.getYear());
			}
			
		}

		Collections.sort(months);
		for(String month : months) {
			StatisticDTO statistic = new StatisticDTO();
			int quantity = 0;
			for(MedicineReservation mr : result) {
				if(mr.getDueTo().getYear() == Integer.parseInt(month.split("/")[1]) && 
						mr.getDueTo().getMonthValue() == Integer.parseInt(month.split("/")[0])) {
					quantity += mr.getMedicineWithQuantity().getQuantity();
				}
			}
			statistic.setTime(month.toString());
			statistic.setData((int)quantity);
			statisticsPerMonth.add(statistic);
		}
	
		return statisticsPerMonth;
		
	}
	
	
	@Override
	public List<StatisticDTO> getMedicineConsumptionQuarter(String email) {
		List<StatisticDTO> statisticsPerQuarter = new ArrayList<StatisticDTO>();
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		Set<MedicineReservation> allReservations = p.getMedicineReservations();
		
		
		List<MedicineReservation> result = new ArrayList<MedicineReservation>();
		
		for(MedicineReservation da : allReservations) {
			if(da.getDueTo().isBefore(LocalDate.now()) && da.getStatus().equals(MedicineReservationStatus.TAKEN)) {
				result.add(da);
			}
		}
			
		List<LocalDate> allDates = new ArrayList<LocalDate>();
		for(MedicineReservation d : result) {
			allDates.add(d.getDueTo());
		}
		
		Collections.sort(allDates);
		
		List<String> quarter = new ArrayList<String>(); 
		int current = 1;
		for(LocalDate l : allDates) {
			if(l.getMonthValue() > 0 && l.getMonthValue() < 4) {
				current = 1;
			} else if (l.getMonthValue() > 3 && l.getMonthValue() < 7) {
				current = 2;
			} else if (l.getMonthValue() > 6 && l.getMonthValue() < 10) {
				current = 3;
			} else if (l.getMonthValue() > 9 && l.getMonthValue() <= 12) {
				current = 4;
			}
			if(!quarter.contains(String.valueOf(current) + "/" + l.getYear())) {
				quarter.add(String.valueOf(current) + "/" + l.getYear());
			}
			
		}

		Collections.sort(quarter);
		for(String q : quarter) {
			StatisticDTO statistic = new StatisticDTO();
			int quantity = 0;
			for(MedicineReservation mr : result) {
				if(q.split("/")[0].equals("1") && mr.getDueTo().getYear() == Integer.parseInt(q.split("/")[1]) && 
						mr.getDueTo().getMonthValue() > 0 && mr.getDueTo().getMonthValue() < 4) {
					quantity += mr.getMedicineWithQuantity().getQuantity();
				}
				else if(q.split("/")[0].equals("2") && mr.getDueTo().getYear() == Integer.parseInt(q.split("/")[1]) && 
						mr.getDueTo().getMonthValue() > 3 && mr.getDueTo().getMonthValue() < 7) {
					quantity += mr.getMedicineWithQuantity().getQuantity();
				}
				else if(q.split("/")[0].equals("3") && mr.getDueTo().getYear() == Integer.parseInt(q.split("/")[1]) && 
						mr.getDueTo().getMonthValue() > 6 && mr.getDueTo().getMonthValue() < 10) {
					quantity += mr.getMedicineWithQuantity().getQuantity();
				}
				else if(q.split("/")[0].equals("4") && mr.getDueTo().getYear() == Integer.parseInt(q.split("/")[1]) && 
						mr.getDueTo().getMonthValue() > 9 && mr.getDueTo().getMonthValue() <= 12) {
					quantity += mr.getMedicineWithQuantity().getQuantity();
				}
			}
			statistic.setTime(q.toString());
			statistic.setData((int)quantity);
		
			statisticsPerQuarter.add(statistic);
		}
	
		return statisticsPerQuarter;
		
	}

}
