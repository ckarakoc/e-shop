package nl.ckarakoc.eshop.repository;

import nl.ckarakoc.eshop.model.AppRole;
import nl.ckarakoc.eshop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByRoleName(AppRole roleName);
}
