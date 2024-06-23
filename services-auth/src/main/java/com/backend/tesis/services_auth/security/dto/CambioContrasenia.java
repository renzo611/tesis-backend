package com.backend.tesis.services_auth.security.dto;

import jakarta.validation.constraints.NotBlank;

public class CambioContrasenia {

    @NotBlank(message = "Contraseña actual obligatoria")
    private String currentPassword;

    @NotBlank(message = "Nueva contraseña obligatoria")
    private String newPassword;


    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
