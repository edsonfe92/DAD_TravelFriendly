package repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import model.User;


public interface UserRepository extends JpaRepository <User,Long>{

	Optional<User> findByName(String name);
}
