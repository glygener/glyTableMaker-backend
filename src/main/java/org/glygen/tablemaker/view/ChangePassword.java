package org.glygen.tablemaker.view;

import org.glygen.tablemaker.view.validation.Password;

import jakarta.validation.constraints.NotEmpty;

public class ChangePassword {
    private String currentPassword;
    private String newPassword;
    
    /**
     * @return the current password
     */
    @NotEmpty
    @Password(message="min 5, max 20 characters, at least 1 lowercase, 1 uppercase letter, 1 numeric and 1 special character")
    public String getCurrentPassword() {
        return currentPassword;
    }
    
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    
    /**
     * @return the new password
     */
    @NotEmpty
    @Password(message="min 5, max 20 characters, at least 1 lowercase, 1 uppercase letter, 1 numeric and 1 special character")
    public String getNewPassword() {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
