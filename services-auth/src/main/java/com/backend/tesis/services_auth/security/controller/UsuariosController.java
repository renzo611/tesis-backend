package com.backend.tesis.services_auth.security.controller;

import com.backend.tesis.services_auth.security.dto.ActualizarUsuario;
import com.backend.tesis.services_auth.security.dto.CambioContrasenia;
import com.backend.tesis.services_auth.security.dto.NuevoUsuario;
import com.backend.tesis.services_auth.security.entity.Usuario;
import com.backend.tesis.services_auth.security.exceptions.CustomException;
import com.backend.tesis.services_auth.security.service.UsuarioService;
import com.backend.tesis.services_auth.util.Mensaje;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsuariosController {

    @Autowired
    UsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/nuevo")
    public ResponseEntity<Mensaje> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario){
        return ResponseEntity.ok(usuarioService.save(nuevoUsuario));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody ActualizarUsuario actualizarUsuario) {
        try {
            Mensaje mensaje = usuarioService.update(id, actualizarUsuario);
            return ResponseEntity.ok(mensaje);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lista")
    public ResponseEntity<List<Usuario>> list(){
        return ResponseEntity.ok(usuarioService.list());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/detalle/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable("id") int id){
        return ResponseEntity.ok(usuarioService.getOne(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Mensaje> delete(@PathVariable("id")int id){
        return ResponseEntity.ok(usuarioService.delete(id));
    }

    @PutMapping("/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@RequestBody CambioContrasenia cambioContrasenia) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();

        try {
            Mensaje mensaje = usuarioService.changePassword(nombreUsuario, cambioContrasenia);
            return ResponseEntity.ok(mensaje);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
}
