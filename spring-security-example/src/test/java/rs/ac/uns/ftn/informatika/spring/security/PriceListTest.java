package rs.ac.uns.ftn.informatika.spring.security;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicinePriceRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PriceListRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.PriceListService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@SpringJUnitWebConfig
@Transactional
@SpringBootTest
class PriceListTest {
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
		
	@Autowired
	private PriceListRepository priceListRepository;
	
	@Autowired
	private MedicinePriceRepository medicinePriceListRepository;
	
	@Autowired
	private PriceListService priceListService;

    @Autowired
	private MedicineService medicineService;
	
	@Rollback(true)
    @Test
    public void medicationQuantityEditing() throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        final boolean[] res1 = new boolean[1];
        final boolean[] res2 = new boolean[1];
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            @Transactional
            public void run() {
                System.out.println("Startovan Thread 1");
                try { Thread.sleep(3000); } catch (InterruptedException e) {}
                try { Thread.sleep(3000); } catch (InterruptedException e) {}

                
                res1[0] = medicineService.editMedicineWithQuatityInPharmacy("adminpharmacy@gmail.com",1,300);

            }
        });
        Future<?> future2 = executor.submit(new Runnable() {

            @Override
            @Transactional
            public void run() {
                System.out.println("Startovan Thread 2");
                
                try { Thread.sleep(3000); } catch (InterruptedException e) {}


                res2[0] = medicineService.editMedicineWithQuatityInPharmacy("adminpharmacy@gmail.com",1,300);

            }
        });
        try {
            future1.get();
            future2.get();
            if(res1[0] && res2[0]){
                throw new InterruptedException("Lose");
            }

        } catch (Exception e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
            throw e.getCause();
        }
        executor.shutdown();
    }
	
	
}
