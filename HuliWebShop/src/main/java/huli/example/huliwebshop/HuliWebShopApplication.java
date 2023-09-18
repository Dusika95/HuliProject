package huli.example.huliwebshop;

import huli.example.huliwebshop.DTOs.UserDTO;
import huli.example.huliwebshop.models.User;
import huli.example.huliwebshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class HuliWebShopApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(HuliWebShopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String email = "admin@example.com";
		if (!userService.isEmailTaken(email)) {
			String firstName = "Admin";
			String lastName = "User";
			String password = "adminpassword";

			UserDTO userDTO = new UserDTO();
			userDTO.setFirstName(firstName);
			userDTO.setLastName(lastName);
			userDTO.setEmail(email);
			userDTO.setPassword(password);
			userDTO.setAddress("Admin Address");
			userDTO.setZipCode("12345");
			userDTO.setCity("Admin City");

			userService.registerUser(userDTO);

			System.out.println("Admin user created.");
		} else {
			System.out.println("Admin user already exists. Skipping user creation.");
		}
	}

}
