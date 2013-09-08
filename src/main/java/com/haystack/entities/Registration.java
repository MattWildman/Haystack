package com.haystack.entities;

public class Registration extends User {

	private String confirmPassword;
	private String newPassword;

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String confirmPassword2) {
		this.newPassword = confirmPassword2;
	}

}
