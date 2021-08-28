package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sun.xml.bind.v2.schemagen.xmlschema.Appinfo;

import rs.ac.uns.ftn.informatika.spring.security.model.AppoitmentPrice;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicinePrice;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservation;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservationStatus;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounseling;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounselingPrice;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.PriceList;
import rs.ac.uns.ftn.informatika.spring.security.model.PriceListSort;
import rs.ac.uns.ftn.informatika.spring.security.model.WorkingTime;
import rs.ac.uns.ftn.informatika.spring.security.repository.ActionAndBenefitRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.AppointmentPriceRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistAppointmentRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineReservationRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistCounselingPriceRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistCounselingRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PriceListRepository;
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
	private AppointmentPriceRepository appointmentPriceRespository;
	
	@Autowired
	private DermatologistAppointmentRepository dermatologistAppointmentRepository;
	
	@Autowired
	private PharmacistCounselingRepository pharmacistConselingRepository;
	
	@Autowired
	private PharmacistCounselingPriceRepository pharmacistConselingPriceRepository;
	
	@Autowired
	private PharmacistRepository pharmacistRepository;
	
	@Autowired
	private PriceListRepository priceListRepository;
	
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
		String current = "";
		for(LocalDateTime l : allDates) {
			if(l.getMonthValue() > 0 && l.getMonthValue() < 4) {
				current = "First quarter";
			} else if (l.getMonthValue() > 3 && l.getMonthValue() < 7) {
				current = "Second quarter";
			} else if (l.getMonthValue() > 6 && l.getMonthValue() < 10) {
				current = "Third quarter";
			} else if (l.getMonthValue() > 9 && l.getMonthValue() <= 12) {
				current = "Fourth quarter";
			}
			if(!quarter.contains(current + "/" + l.getYear())) {
				quarter.add(current + "/" + l.getYear());
			}
			
		}

		Collections.sort(quarter);
		for(String q : quarter) {
			StatisticDTO statistic = new StatisticDTO();
			statistic.setTime(q.toString());
			if(q.split("/")[0].equals("First quarter")) {
				statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == Integer.parseInt(q.split("/")[1]) && 
						r.getStartDateTime().getMonthValue() > 0 && r.getStartDateTime().getMonthValue() < 4
						
						).count());
			} else if(q.split("/")[0].equals("Second quarter")) {
				statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == Integer.parseInt(q.split("/")[1]) && 
						r.getStartDateTime().getMonthValue() > 3 && r.getStartDateTime().getMonthValue() < 7
						
						).count());
			} else if(q.split("/")[0].equals("Third quarter")) {
				statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == Integer.parseInt(q.split("/")[1]) && 
						r.getStartDateTime().getMonthValue() > 6 && r.getStartDateTime().getMonthValue() < 10
						
						).count());
			} else if(q.split("/")[0].equals("Fourth quarter")) {
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
		String current = "";
		for(LocalDateTime l : allDates) {
			if(l.getMonthValue() > 0 && l.getMonthValue() < 4) {
				current = "First quarter";
			} else if (l.getMonthValue() > 3 && l.getMonthValue() < 7) {
				current = "Second quarter";
			} else if (l.getMonthValue() > 6 && l.getMonthValue() < 10) {
				current = "Third quarter";
			} else if (l.getMonthValue() > 9 && l.getMonthValue() <= 12) {
				current = "Fourth quarter";
			}
			if(!quarter.contains(current + "/" + l.getYear())) {
				quarter.add(current + "/" + l.getYear());
			}
			
		}

		Collections.sort(quarter);
		for(String q : quarter) {
			StatisticDTO statistic = new StatisticDTO();
			statistic.setTime(q.toString());
			if(q.split("/")[0].equals("First quarter")) {
				statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == Integer.parseInt(q.split("/")[1]) && 
						r.getStartDateTime().getMonthValue() > 0 && r.getStartDateTime().getMonthValue() < 4
						
						).count());
			} else if(q.split("/")[0].equals("Second quarter")) {
				statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == Integer.parseInt(q.split("/")[1]) && 
						r.getStartDateTime().getMonthValue() > 3 && r.getStartDateTime().getMonthValue() < 7
						
						).count());
			} else if(q.split("/")[0].equals("Third quarter")) {
				statistic.setData((int)result.stream().filter(r -> r.getStartDateTime().getYear() == Integer.parseInt(q.split("/")[1]) && 
						r.getStartDateTime().getMonthValue() > 6 && r.getStartDateTime().getMonthValue() < 10
						
						).count());
			} else if(q.split("/")[0].equals("Fourth quarter")) {
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
				System.out.println(da);
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
		String current = "";
		for(LocalDate l : allDates) {
			if(l.getMonthValue() > 0 && l.getMonthValue() < 4) {
				current = "First quarter";
			} else if (l.getMonthValue() > 3 && l.getMonthValue() < 7) {
				current = "Second quarter";
			} else if (l.getMonthValue() > 6 && l.getMonthValue() < 10) {
				current = "Third quarter";
			} else if (l.getMonthValue() > 9 && l.getMonthValue() <= 12) {
				current = "Fourth quarter";
			}
			if(!quarter.contains(current + "/" + l.getYear())) {
				quarter.add(current + "/" + l.getYear());
			}
			
		}

		Collections.sort(quarter);
		for(String q : quarter) {
			StatisticDTO statistic = new StatisticDTO();
			int quantity = 0;
			for(MedicineReservation mr : result) {
				if(q.split("/")[0].equals("First quarter") && mr.getDueTo().getYear() == Integer.parseInt(q.split("/")[1]) && 
						mr.getDueTo().getMonthValue() > 0 && mr.getDueTo().getMonthValue() < 4) {
					quantity += mr.getMedicineWithQuantity().getQuantity();
				}
				else if(q.split("/")[0].equals("Second quarter") && mr.getDueTo().getYear() == Integer.parseInt(q.split("/")[1]) && 
						mr.getDueTo().getMonthValue() > 3 && mr.getDueTo().getMonthValue() < 7) {
					quantity += mr.getMedicineWithQuantity().getQuantity();
				}
				else if(q.split("/")[0].equals("Third quarter") && mr.getDueTo().getYear() == Integer.parseInt(q.split("/")[1]) && 
						mr.getDueTo().getMonthValue() > 6 && mr.getDueTo().getMonthValue() < 10) {
					quantity += mr.getMedicineWithQuantity().getQuantity();
				}
				else if(q.split("/")[0].equals("Fourth quarter") && mr.getDueTo().getYear() == Integer.parseInt(q.split("/")[1]) && 
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
	
	@Override
	public List<StatisticDTO> getPharmacyIncome(String email,LocalDate from, LocalDate to) {
		List<StatisticDTO> statisticsPerMonth = new ArrayList<StatisticDTO>();
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		List<DermatologistAppointment> allAppoimtments = this.dermatologistAppointmentRepository.findAll();
		List<AppoitmentPrice> allPrices = this.appointmentPriceRespository.findAll();
		List<AppoitmentPrice> appointmentPrices = new ArrayList<AppoitmentPrice>();
		for(DermatologistAppointment da : allAppoimtments) {
			if(da.getStartDateTime().isBefore(LocalDateTime.now())) {
				for(WorkingTime wt : da.getDermatologist().getWorkingTimes()) {
					if(wt.getPharmacy().equals(p)) {
						for(AppoitmentPrice ap : allPrices) {
							if(ap.getAppoitment().equals(da)) {
								appointmentPrices.add(ap);
							}
						}
						}
				}
			}
		}
		System.out.println(appointmentPrices);
		List<LocalDate> allDates = new ArrayList<LocalDate>();
		for(AppoitmentPrice a : appointmentPrices) {
			if(a.getAppoitment().getStartDateTime().toLocalDate().isAfter(from) &&
					a.getAppoitment().getStartDateTime().toLocalDate().isBefore(to)) {
			allDates.add(a.getAppoitment().getStartDateTime().toLocalDate());
			}
		}
		
		Collections.sort(allDates);
		
		List<String> days = new ArrayList<String>(); 
		
		for(LocalDate l : allDates) {
			if(!days.contains(String.valueOf(l.getDayOfMonth()) + "." + String.valueOf(l.getMonthValue()) + "." + l.getYear())) {
				days.add(String.valueOf(l.getDayOfMonth()) + "." + String.valueOf(l.getMonthValue()) + "." + l.getYear());
			}
			
		}

		Collections.sort(days);
		for(String day : days) {
			System.out.println(day);
			StatisticDTO statistic = new StatisticDTO();
			int income = 0;
			for(AppoitmentPrice ap : appointmentPrices) {
				if(ap.getAppoitment().getStartDateTime().getYear() == Integer.parseInt(day.split("\\.")[2]) && 
						ap.getAppoitment().getStartDateTime().getMonthValue() == Integer.parseInt(day.split("\\.")[1]) &&
						ap.getAppoitment().getStartDateTime().getDayOfMonth() == Integer.parseInt(day.split("\\.")[0])) {
					income += ap.getPrice();
				}
			}
			statistic.setTime(day.toString());
			statistic.setData((int)income);
			statisticsPerMonth.add(statistic);
		}
		return statisticsPerMonth;
		
	}
	
	@Override
	public List<StatisticDTO> getPharmacyIncomeFromPharmacistCouseling(String email,LocalDate from, LocalDate to) {
		List<StatisticDTO> statisticsPerMonth = new ArrayList<StatisticDTO>();
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		List<PharmacistCounseling> allAppoimtments = this.pharmacistConselingRepository.findAll();
		List<PharmacistCounselingPrice> allPrices = this.pharmacistConselingPriceRepository.findAll();
		List<PharmacistCounselingPrice> appointmentPrices = new ArrayList<PharmacistCounselingPrice>();
		for(PharmacistCounseling da : allAppoimtments) {
			if(da.getStartDateTime().isBefore(LocalDateTime.now())) {
			{
					if(da.getPharmacist().getWorkingTimes().getPharmacy().equals(p)) {
						for(PharmacistCounselingPrice ap : allPrices) {
							if(ap.getCounseling().equals(da)) {
								appointmentPrices.add(ap);
							}
						}
						}
				}
			}
		}
		System.out.println(appointmentPrices);
		List<LocalDate> allDates = new ArrayList<LocalDate>();
		for(PharmacistCounselingPrice a : appointmentPrices) {
			if(a.getCounseling().getStartDateTime().toLocalDate().isAfter(from) &&
					a.getCounseling().getStartDateTime().toLocalDate().isBefore(to)) {
			allDates.add(a.getCounseling().getStartDateTime().toLocalDate());
			}
		}
		
		Collections.sort(allDates);
		
		List<String> days = new ArrayList<String>(); 
		
		for(LocalDate l : allDates) {
			if(!days.contains(String.valueOf(l.getDayOfMonth()) + "." + String.valueOf(l.getMonthValue()) + "." + l.getYear())) {
				days.add(String.valueOf(l.getDayOfMonth()) + "." + String.valueOf(l.getMonthValue()) + "." + l.getYear());
			}
			
		}

		Collections.sort(days);
		for(String day : days) {
			System.out.println(day);
			StatisticDTO statistic = new StatisticDTO();
			int income = 0;
			for(PharmacistCounselingPrice ap : appointmentPrices) {
				if(ap.getCounseling().getStartDateTime().getYear() == Integer.parseInt(day.split("\\.")[2]) && 
						ap.getCounseling().getStartDateTime().getMonthValue() == Integer.parseInt(day.split("\\.")[1]) &&
						ap.getCounseling().getStartDateTime().getDayOfMonth() == Integer.parseInt(day.split("\\.")[0])) {
					income += ap.getPrice();
				}
			}
			statistic.setTime(day.toString());
			statistic.setData((int)income);
			statisticsPerMonth.add(statistic);
		}
		return statisticsPerMonth;
		
	}
	
	
	@Override
	public List<StatisticDTO> getPharmacyIncomeFromMedicineConsumption(String email,LocalDate from, LocalDate to) {
		List<StatisticDTO> statisticsPerMonth = new ArrayList<StatisticDTO>();
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		Set<MedicineReservation> medicineReservations = p.getMedicineReservations();
		Set<PriceList> allPrices = p.getPriceList();
		
		List<PriceList> list = new ArrayList<PriceList>(allPrices);
		Collections.sort(list, new PriceListSort(-1));
		
		Set<PriceList> resultSet = new LinkedHashSet<PriceList>(list);
		
		for(PriceList plist : resultSet) {
			System.out.println(plist.getStartDate());
		}
		
		List<LocalDate> allDates = new ArrayList<LocalDate>();
		for(MedicineReservation a : medicineReservations) {
			if(a.getDueTo().isBefore(LocalDate.now()) && a.getDueTo().isAfter(from) &&
					a.getDueTo().isBefore(to) && a.getStatus().equals(MedicineReservationStatus.TAKEN)) {
			allDates.add(a.getDueTo());
			}
		}
		
		Collections.sort(allDates);
		System.out.println(allDates);
		
		List<String> days = new ArrayList<String>(); 
		
		for(LocalDate l : allDates) {
			if(!days.contains(String.valueOf(l.getDayOfMonth()) + "." + String.valueOf(l.getMonthValue()) + "." + l.getYear())) {
				days.add(String.valueOf(l.getDayOfMonth()) + "." + String.valueOf(l.getMonthValue()) + "." + l.getYear());
			}
			
		}
		for(String day : days) {
			System.out.println("day" + day);
			StatisticDTO statistic = new StatisticDTO();
			int income = 0;
			for(MedicineReservation r : medicineReservations) {
				if(r.getDueTo().isBefore(LocalDate.now()) && r.getStatus().equals(MedicineReservationStatus.TAKEN)) {
						for(PriceList ap : resultSet){
							if(ap.getStartDate().isBefore(LocalDate.now()) && ap.getStartDate().isBefore(r.getDueTo())) {
								for(MedicinePrice mp : ap.getMedicinePriceList()) {
									if(mp.getMedicine().equals(r.getMedicineWithQuantity().getMedicine())) {
										if(r.getDueTo().getYear() == Integer.parseInt(day.split("\\.")[2]) && 
												r.getDueTo().getMonthValue() == Integer.parseInt(day.split("\\.")[1]) &&
														r.getDueTo().getDayOfMonth() == Integer.parseInt(day.split("\\.")[0])) {
											income += mp.getPrice() * r.getMedicineWithQuantity().getQuantity();
											break;
										}
									}
								}
								break;
							}
					}
				}
			}
		
			statistic.setTime(day.toString());
			statistic.setData((int)income);
			statisticsPerMonth.add(statistic);
		}
		return statisticsPerMonth;
		
	}
	
	

}
