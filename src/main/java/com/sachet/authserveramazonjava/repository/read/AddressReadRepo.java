package com.sachet.authserveramazonjava.repository.read;

import com.sachet.authserveramazonjava.model.read.AddressRead;
import com.sachet.authserveramazonjava.model.write.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressReadRepo extends JpaRepository<Address, Integer> {
}
