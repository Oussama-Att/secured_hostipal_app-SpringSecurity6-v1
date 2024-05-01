package att.g5.hopital_app_attouch;

import att.g5.hopital_app_attouch.entities.*;
import att.g5.hopital_app_attouch.repositories.PatientRepository;
import att.g5.hopital_app_attouch.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;
@SpringBootApplication
public class HopitalAppAttouchApplication {

	public static void main(String[] args) {
		SpringApplication.run(HopitalAppAttouchApplication.class, args);
	}

	@Bean
	CommandLineRunner start(PatientRepository patientRepository){
		return args -> {
			patientRepository.save(new Patient(null, "oussama", new Date(), false, 10));
			patientRepository.save(new Patient(null, "attouch", new Date(), false, 20));
			patientRepository.save(new Patient(null, "elhouari", new Date(), true, 30));
		};
	}

	//@Bean
	CommandLineRunner commandLineRunnerJDBC(JdbcUserDetailsManager jdbcUserDetailsManager){
		return args -> {
			UserDetails U1 = jdbcUserDetailsManager.loadUserByUsername("user11");
			if (U1 == null)
				jdbcUserDetailsManager.createUser(
						User.withUsername("user11").password(passwordEncoder().encode("1234")).roles("USER").build()
				);
			UserDetails U2 = jdbcUserDetailsManager.loadUserByUsername("user22");
			if (U2 == null)
				jdbcUserDetailsManager.createUser(
						User.withUsername("user22").password(passwordEncoder().encode("1234")).roles("USER").build()
				);
			UserDetails U3 = jdbcUserDetailsManager.loadUserByUsername("admin22");
			if (U3 == null)
				jdbcUserDetailsManager.createUser(
						User.withUsername("admin22").password(passwordEncoder().encode("1234")).roles("ADMIN","USER").build()
				);
		};
	}

	//@Bean
	CommandLineRunner commandLineRunnerUserDetails(AccountService accountService){
		return args -> {
			accountService.addNewRole("ADMIN");
			accountService.addNewRole("USER");
			accountService.addNewUser("o12","1234","oss11@gmail.com","1234");
			accountService.addNewUser("o22","1234","oss22@gmail.com","1234");
			accountService.addNewUser("admin121","1234","admin1@gmail.com","1234");

			accountService.addRoleToUser("o12","USER");
			accountService.addRoleToUser("o22","USER");
			accountService.addRoleToUser("admin121","USER");
			accountService.addRoleToUser("admin121","ADMIN");
		};
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
/*Key Differences:

InMemory Authentication stores user credentials in memory statically, while JDBC Authentication retrieves user credentials from a database dynamically.
UserDetails Service provides a customizable way to retrieve user details from different data sources,
whereas InMemory Authentication and JDBC Authentication have specific implementations for storing and retrieving user credentials.*/
