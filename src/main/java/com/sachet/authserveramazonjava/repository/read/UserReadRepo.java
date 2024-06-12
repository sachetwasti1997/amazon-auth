package com.sachet.authserveramazonjava.repository.read;

import com.sachet.authserveramazonjava.model.read.UserRead;
import com.sachet.authserveramazonjava.model.write.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserReadRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
