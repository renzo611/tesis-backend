package com.backend.tesis.services_auth.security.service;

import com.backend.tesis.services_auth.security.dto.*;
import com.backend.tesis.services_auth.security.entity.Rol;
import com.backend.tesis.services_auth.security.entity.Usuario;
import com.backend.tesis.services_auth.security.enums.RolNombre;
import com.backend.tesis.services_auth.security.exceptions.CustomException;
import com.backend.tesis.services_auth.security.jwt.JwtProvider;
import com.backend.tesis.services_auth.security.repository.UsuarioRepository;
import com.backend.tesis.services_auth.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public Optional<Usuario> getByNombreUsuarioOrEmail(String nombreOrEmail){
        return usuarioRepository.findByNombreUsuarioOrEmail(nombreOrEmail, nombreOrEmail);
    }

    public Optional<Usuario> getByTokenPassword(String tokenPassword){
        return usuarioRepository.findByTokenPassword(tokenPassword);
    }

    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public JwtDto login(LoginUsuario loginUsuario){
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        return new JwtDto(jwt);
    }

    public JwtDto refresh(JwtDto jwtDto) throws ParseException {
        String token = jwtProvider.refreshToken(jwtDto);
        return new JwtDto(token);
    }

    public Mensaje save(NuevoUsuario nuevoUsuario){
        if(usuarioRepository.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "ese nombre de usuario ya existe");
        if(usuarioRepository.existsByEmail(nuevoUsuario.getEmail()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "ese email de usuario ya existe");
        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(),nuevoUsuario.getApellido(),nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
        if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        if(nuevoUsuario.getRoles().contains("director_carrera"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_DIRECTOR_CARRERA).get());
        if(nuevoUsuario.getRoles().contains("bedelia"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_BEDELIA).get());
        usuario.setRoles(roles);
        usuarioRepository.save(usuario);
        return new Mensaje(usuario.getNombreUsuario() + " ha sido creado");
    }

    public Mensaje update(Integer id, ActualizarUsuario actualizarUsuario) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() ->
                new CustomException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (usuarioRepository.existsByNombreUsuario(actualizarUsuario.getNombreUsuario()) &&
                !usuario.getNombreUsuario().equals(actualizarUsuario.getNombreUsuario())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Ese nombre de usuario ya existe");
        }

        if (usuarioRepository.existsByEmail(actualizarUsuario.getEmail()) &&
                !usuario.getEmail().equals(actualizarUsuario.getEmail())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Ese email de usuario ya existe");
        }

        usuario.setNombre(actualizarUsuario.getNombre());
        usuario.setApellido(actualizarUsuario.getApellido());
        usuario.setNombreUsuario(actualizarUsuario.getNombreUsuario());
        usuario.setEmail(actualizarUsuario.getEmail());

        Set<Rol> roles = new HashSet<>();
        if (actualizarUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        if (actualizarUsuario.getRoles().contains("director_carrera"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_DIRECTOR_CARRERA).get());
        if (actualizarUsuario.getRoles().contains("bedelia"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_BEDELIA).get());
        usuario.setRoles(roles);

        usuarioRepository.save(usuario);
        return new Mensaje(usuario.getNombreUsuario() + " ha sido actualizado");
    }


    public List<Usuario> list() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findAll().stream()
                .filter(usuario -> !usuario.getNombreUsuario().equals(currentUsername))
                .collect(Collectors.toList());
    }

    public Usuario getOne(int id){
        return usuarioRepository.findById(id)
                .orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "el usuario no existe"));
    }

    public Mensaje delete(int id){
        Usuario usuario = getOne(id);
        usuarioRepository.delete(usuario);
        return new Mensaje("el usuario ha sido eliminado");
    }

    public Mensaje changePassword(String nombreUsuario, CambioContrasenia cambioContrasenia) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario).orElseThrow(() ->
                new CustomException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!passwordEncoder.matches(cambioContrasenia.getCurrentPassword(), usuario.getPassword())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "La contraseña actual es incorrecta");
        }

        usuario.setPassword(passwordEncoder.encode(cambioContrasenia.getNewPassword()));
        usuarioRepository.save(usuario);

        return new Mensaje("La contraseña ha sido cambiada exitosamente");
    }
}
