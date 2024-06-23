package com.backend.tesis.services_auth.emailpassword.controller;

import com.backend.tesis.services_auth.emailpassword.dto.ChangePasswordDTO;
import com.backend.tesis.services_auth.emailpassword.dto.EmailValuesDTO;
import com.backend.tesis.services_auth.emailpassword.service.EmailService;
import com.backend.tesis.services_auth.util.Mensaje;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/email-password")
@CrossOrigin
public class EmailController {

    @Autowired
    EmailService emailService;


    @PostMapping("/send-email")
    public ResponseEntity<Mensaje> sendEmailTemplate(@RequestBody EmailValuesDTO dto) {
        return ResponseEntity.ok(emailService.sendEmailTemplate(dto));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Mensaje> changePassword(@Valid @RequestBody ChangePasswordDTO dto) {
        return ResponseEntity.ok(emailService.changePassword(dto));
    }

}
