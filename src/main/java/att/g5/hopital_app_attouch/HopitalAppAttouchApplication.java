package att.g5.hopital_app_attouch;

import att.g5.hopital_app_attouch.entities.*;
import att.g5.hopital_app_attouch.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;;

@SpringBootApplication
public class HopitalAppAttouchApplication implements CommandLineRunner {
	@Autowired
	private PatientRepository patientRepository;

	public static void main(String[] args) {
		SpringApplication.run(HopitalAppAttouchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		patientRepository.save(new Patient(null,"oussama",new Date(),false,34));
		patientRepository.save(new Patient(null,"attouch",new Date(),false,434));
		patientRepository.save(new Patient(null,"youssef",new Date(),true ,34));

	}
}