package com.sachet.authserveramazonjava.repository.write;

import com.sachet.authserveramazonjava.model.write.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

}
