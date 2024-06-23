package com.backend.tesis.services_auth.util;

import com.backend.tesis.services_auth.security.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * MUY IMPORTANTE: ESTA CLASE SÓLO SE EJECUTARÁ UNA VEZ PARA CREAR LOS ROLES.
 * UNA VEZ CREADOS SE DEBERÁ ELIMINAR O BIEN COMENTAR EL CÓDIGO
 *
 */

@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;

    @Override
    public void run(String... args) throws Exception {
        /** Rol rol1 = new Rol(RolNombre.ROLE_ADMIN);
        Rol rol2 = new Rol(RolNombre.ROLE_DIRECTOR_CARRERA);
         Rol rol3 = new Rol(RolNombre.ROLE_BEDELIA);
        rolService.save(rol1);
        rolService.save(rol2);
         rolService.save(rol3);
         **/
    }
}
