package g26.eDucaApp;

import g26.eDucaApp.Model.Sch_Admin;
import g26.eDucaApp.Repository.Sch_AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@EnableScheduling
public class EDucaAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EDucaAppApplication.class, args);
	}

	@Autowired
	private Sch_AdminRepository schAdminRepository;

	@Autowired
    private PasswordEncoder passwordEncoder;

	// Create a default school administrator
	// This is for testing purposes only
	public void run(String... args) throws Exception {
		if (schAdminRepository.findByEmail("admin@gmail.com").isPresent()) {
			return;
		}
		Sch_Admin schAdmin = new Sch_Admin();
		schAdmin.setName("Admin");
		schAdmin.setEmail("admin@gmail.com");
		schAdmin.setPassword(passwordEncoder.encode("admin"));
		schAdmin.setSchool("SampleSchool");
		schAdminRepository.save(schAdmin);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {

		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200", "http://deti-ies-12.ua.pt:4200")
						.allowedMethods("GET", "POST", "PUT", "DELETE")
						.allowCredentials(true);

			}
		};
	}
}
