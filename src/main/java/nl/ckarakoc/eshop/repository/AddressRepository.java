package nl.ckarakoc.eshop.repository;

import nl.ckarakoc.eshop.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
