package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.ActionAndBenefit;
import rs.ac.uns.ftn.informatika.spring.security.model.Address;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.repository.ActionAndBenefitRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.view.ActionAndBenefitDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.EditPharmacyView;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacist;
import rs.ac.uns.ftn.informatika.spring.security.model.WorkingDay;
import rs.ac.uns.ftn.informatika.spring.security.model.WorkingTime;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistRepository;
import rs.ac.uns.ftn.informatika.spring.security.view.WorkingDayDTO;

@Service
public class PharmacyServiceImpl implements PharmacyService{

	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	@Autowired
	private PharmacistRepository pharmacistRepository;
	
	@Autowired
	private ActionAndBenefitRepository actionAndBenefitRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Override
	public Optional<Pharmacy> findById(Long id) {
		return pharmacyRepository.findById(id);
	}

	@Override
	public void editPharmacy(EditPharmacyView p) {
		Address a = new Address();
		a.setStreet(p.getStreet());
		a.setCity(p.getCity());
		Pharmacy pharmacy = findById(p.getId()).get();
		pharmacy.setName(p.getName());
		pharmacy.setAddress(a);
		pharmacy.setDescription(p.getDescription());
		
		pharmacyRepository.save(pharmacy);

	}
	
	@Override
	public ActionAndBenefit addNew(ActionAndBenefitDTO actionAndBenefit) {
		
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(actionAndBenefit.getPharmacyAdminEmail()));
		
		ActionAndBenefit ab = new ActionAndBenefit();

		ab.setStartDate(LocalDate.parse(actionAndBenefit.getStartDate()));
		ab.setEndDate(LocalDate.parse(actionAndBenefit.getEndDate()));
		ab.setDescription(actionAndBenefit.getDescription());
	
		
		Pharmacy pharmacy =pa.getPharmacy();
		pharmacy.getActionsAndBenefits().add(ab);
		
		this.pharmacyRepository.save(pharmacy);
		
		return ab;
		
	}

	@Override

	public List<Pharmacy> findAll() {
		List<Pharmacy> result = pharmacyRepository.findAll();
		return result;

	}
	public Set<Dermatologist> getDermatologistsByPharmacyAdmin(String email) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		
		Pharmacy p = pa.getPharmacy();
		
		List<Dermatologist> dermatologists = dermatologistRepository.findAll();
		Set<Dermatologist> result = new HashSet<Dermatologist>();
		for(Dermatologist d : dermatologists) {
			for(WorkingTime wt : d.getWorkingTimes()) {
				if(wt.getPharmacy().equals(p)) {
					result.add(d);
				}
			}
		}
		
		return result;
		
	}

	@Override
	public void addWorkingTimeForDermatologist(String dermatologistId, String email, WorkingDayDTO workingDay) {
		
		Dermatologist dermatologist = this.dermatologistRepository.findById(Long.parseLong(dermatologistId)).get();

		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		WorkingDay wd = new WorkingDay();
		wd.setDay(LocalDate.parse(workingDay.getWorkingDate()));
		wd.setStartTime(LocalTime.parse(workingDay.getStartTime()));
		wd.setEndTime(LocalTime.parse(workingDay.getEndTime()));
		
		//treba dodati uslov ako je prazno
		
		for(WorkingTime t : dermatologist.getWorkingTimes()) {
			System.out.println(t.getPharmacy());
			if(t.getPharmacy().equals(p)) {
				System.out.println("usao u if");
				t.getWorkingDays().add(wd);
			}
		}
		this.dermatologistRepository.save(dermatologist);
		
	}

	@Override
	public Set<WorkingDay> getWorkingDayForDermatolog(String id, String email) {
		Dermatologist dermatologist = this.dermatologistRepository.findById(Long.parseLong(id)).get();

		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		for(WorkingTime t : dermatologist.getWorkingTimes()) {
			System.out.println(t.getPharmacy());
			if(t.getPharmacy().equals(p)) {
				return t.getWorkingDays();
			}
		}
		return null;
		
		
	}

	@Override
	public void deleteDermatologistFromPharmacy(String id, String email) {
		Dermatologist dermatologist = this.dermatologistRepository.findById(Long.parseLong(id)).get();

		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		for(WorkingTime t : dermatologist.getWorkingTimes()) {
			System.out.println(t.getPharmacy());
			if(t.getPharmacy().equals(p)) {
				//dermatologist.setPharmacy(new Pharmacy());
				dermatologist.getWorkingTimes().remove(t);
			}
		}
		
		this.dermatologistRepository.save(dermatologist);
			
	}

	@Override
	public Set<Pharmacist> getPharmacistssByPharmacyAdmin(String email) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		
		Pharmacy p = pa.getPharmacy();
		
		List<Pharmacist> pharmacists = this.pharmacistRepository.findAll();
		Set<Pharmacist> result = new HashSet<Pharmacist>();
		for(Pharmacist d : pharmacists) {
			if(d.getWorkingTimes() != null) {
			if( d.getWorkingTimes().getPharmacy().equals(p)) {
				result.add(d);
			}	
			}
		}	
		return result;
	}

	@Override
	public void addWorkingTimeForPharmacist(String dermatologistId, String email, WorkingDayDTO workingDay) {
		Pharmacist pharmacist = this.pharmacistRepository.findById(Long.parseLong(dermatologistId)).get();

		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		WorkingDay wd = new WorkingDay();
		wd.setDay(LocalDate.parse(workingDay.getWorkingDate()));
		wd.setStartTime(LocalTime.parse(workingDay.getStartTime()));
		wd.setEndTime(LocalTime.parse(workingDay.getEndTime()));
		
		WorkingTime pharacistWorkingTime = pharmacist.getWorkingTimes();
		if(pharacistWorkingTime.getPharmacy().equals(p)) {
			pharmacist.getWorkingTimes().getWorkingDays().add(wd);
		}
		this.pharmacistRepository.save(pharmacist);
		//treba dodati uslov ako je prazno
		
	}

	@Override
	public Set<WorkingDay> getWorkingDayForPharmacist(String id, String email) {
		Pharmacist pharmacist = this.pharmacistRepository.findById(Long.parseLong(id)).get();

		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		System.out.println(p);
		
		WorkingTime pharacistWorkingTime = pharmacist.getWorkingTimes();
		if(pharacistWorkingTime.getPharmacy().equals(p)) {
			return pharacistWorkingTime.getWorkingDays();
		}
		return null;
		
	}

	@Override
	public void deletePharmacistFromPharmacy(String id, String email) {
		Pharmacist pharmacist = this.pharmacistRepository.findById(Long.parseLong(id)).get();

		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		WorkingTime pharacistWorkingTime = pharmacist.getWorkingTimes();
		if(pharacistWorkingTime.getPharmacy().equals(p)) {
			pharmacist.setWorkingTimes(null);
		}
		
		this.pharmacistRepository.save(pharmacist);

	}

	@Override
	public Collection<Pharmacy> searchPharmacy(String p) {
			   ArrayList<Pharmacy> pharmacies = new ArrayList<>();
			   for(Pharmacy pharamacy : pharmacyRepository.findAll()){
				   if(pharamacy.getName().toLowerCase().contains(p.toLowerCase())) {
					   pharmacies.add(pharamacy);
				   }
				   else if(pharamacy.getAddress().getCity().toLowerCase().contains(p.toLowerCase())){
					   pharmacies.add(pharamacy);
				   }
				   else if(pharamacy.getAddress().getStreet().toLowerCase().contains(p.toLowerCase())){
					   pharmacies.add(pharamacy);
				   }
			   }
		        return pharmacies;
		}

}
