package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.WorkingTime;
import rs.ac.uns.ftn.informatika.spring.security.repository.WorkingTimeRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.WorkingTimeService;

@Service
public class WorkingTimeServiceImpl implements WorkingTimeService{
	
	@Autowired
	private WorkingTimeRepository workingTimeReposiroty;
	
	@Override
	public List<WorkingTime> findAll() {
		return workingTimeReposiroty.findAll();
	}

}
