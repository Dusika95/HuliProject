package huli.example.huliwebshop;

import huli.example.huliwebshop.DTOs.UserRegisterDTO;
import huli.example.huliwebshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class HuliWebShopApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HuliWebShopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String email = "admin@example.com";
		if (!userService.isEmailTaken(email)) {
			String name = "AdminUser";
			String password = "adminpassword";

			UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
			userRegisterDTO.setName(name);
			userRegisterDTO.setEmail(email);
			userRegisterDTO.setPassword(password);
			userRegisterDTO.setAddress("Admin Address");
			userRegisterDTO.setZipCode("12345");
			userRegisterDTO.setCity("Admin City");

			userService.registerUser(userRegisterDTO);

			System.out.println("Admin user created.");
		} else {
			System.out.println("Admin user already exists. Skipping user creation.");
		}
	}

}
