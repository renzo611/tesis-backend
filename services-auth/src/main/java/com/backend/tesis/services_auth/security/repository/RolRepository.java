package com.backend.tesis.services_auth.security.repository;


import com.backend.tesis.services_auth.security.entity.Rol;
import com.backend.tesis.services_auth.security.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
