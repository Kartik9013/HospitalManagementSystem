package com.hospital.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;

import javax.swing.*;

import com.hospital.Dao.*;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField usernameField;
	private JPasswordField passwordField;
	private UserDao userDao = new UserDaoImpl();
	
	public Login() {
		setTitle("Hospital Management System");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        
        URL imageUrl = getClass().getClassLoader().getResource("hospital.png");

        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);
            Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            logoLabel.setBounds(200, 10, 100, 100);
            add(logoLabel);
        } else {
            System.err.println("Image not found!");
        }
		
		//Title
        JLabel title = new JLabel("Admin Login");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(180, 120, 200, 30);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);
        
     // ======= Username =======
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(100, 170, 80, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(190, 170, 200, 25);
        add(usernameField);
        
        // ======= Password =======
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(100, 210, 80, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(190, 210, 200, 25);
        add(passwordField);
        
        // ======= Login Button =======
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(190, 260, 200, 30);
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        add(loginButton);
        
        loginButton.addActionListener(e->{
        	String username = usernameField.getText().trim();
        	String password = new String(passwordField.getPassword()).trim();
        	
        	if(userDao.validateUser(username, password)) {
        		JOptionPane.showMessageDialog(this, "Login successfull");
        		 dispose(); // close login window
        		 new Dashboard().setVisible(true); // open dashboard
        	}else {
        		JOptionPane.showMessageDialog(this, "Invalid username or password","Error",JOptionPane.ERROR_MESSAGE);
        	}
        });
	}
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(() -> {
	        Login loginFrame = new Login();
	        loginFrame.setVisible(true);  
	    });
	}

}
