package Control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import model.User;
import repository.UserRepository;


@RestController 
public class UserContro implements CommandLineRunner  {

	@Autowired
	private UserRepository repository;
	
	@Override
	public void run(String... args) throws Exception {
	repository.save(new User("Jack", "Bauer"));
	repository.save(new User("Chloe", "O'Brian"));
	repository.save(new User("Kim", "Bauer"));
	repository.save(new User("David", "Palmer"));
	repository.save(new User("Michelle", "Dessler"));

	}
	
}
