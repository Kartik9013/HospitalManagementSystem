package com.hospital.main;

import javax.swing.SwingUtilities;

import com.hospital.ui.Login;

public class Main {
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            Login login = new Login();
	            login.setVisible(true);
	        });
	 }
}
