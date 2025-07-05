package com.hospital.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.hospital.Dao.PatientDao;
import com.hospital.Dao.PatientDaoImpl;
import com.hospital.entity.Patient;

public class AddPatient extends JFrame {
	private static final long serialVersionUID = 1L;

	public AddPatient() {
        setTitle("Add New Patient");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

     // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 144, 255));
        JLabel titleLabel = new JLabel("Hospital Management System", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        // Navbar
        JPanel navbar = new JPanel();
        navbar.setBackground(new Color(30, 144, 255));
        navbar.setLayout(new GridLayout(1, 5, 10, 0));
        navbar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        navbar.add(createNavButton("Dashboard", e -> {
            new Dashboard();
            dispose();
        }));
        navbar.add(createNavButton("Add Patient", e -> {
            new AddPatient();
            dispose();
        }));
        navbar.add(createNavButton("View Patients", e -> {
            new ViewPatients();
            dispose();
        }));
        navbar.add(createNavButton("Search", e -> {
            new SearchPatient(); // reload
            dispose();
        }));
        navbar.add(createNavButton("Logout", e -> {
            new Login();
            dispose();
        }));

        headerPanel.add(navbar, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);


        // === FORM PANEL ===
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 12));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.setBackground(new Color(245, 250, 255));

        JTextField nameField = new JTextField();
        JTextField roomField = new JTextField();
        JTextField dobField = new JTextField();
        JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        JTextField addressField = new JTextField();
        JTextField phoneField = new JTextField();
        JCheckBox treatedCheck = new JCheckBox("Treated");

        formPanel.add(new JLabel("Patient Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Room Number:"));
        formPanel.add(roomField);

        formPanel.add(new JLabel("Date of Birth (dd/MM/yyyy):"));
        formPanel.add(dobField);

        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderBox);

        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);

        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Treated?"));
        formPanel.add(treatedCheck);

        JButton submitBtn = new JButton("Submit");
        submitBtn.setBackground(new Color(34, 139, 34));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        formPanel.add(new JLabel());
        formPanel.add(submitBtn);

        // === SUBMIT ACTION ===
        submitBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String room = roomField.getText().trim();
            String dobStr = dobField.getText().trim();
            String gender = genderBox.getSelectedItem().toString();
            String address = addressField.getText().trim();
            String phone = phoneField.getText().trim();
            boolean treated = treatedCheck.isSelected();

            if (name.isEmpty() || room.isEmpty() || dobStr.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dob = LocalDate.parse(dobStr, formatter);

                Patient patient = new Patient(name, room, dob, gender, address, phone, treated);
                PatientDao dao = new PatientDaoImpl();
                dao.savePatient(patient);

                JOptionPane.showMessageDialog(this, "Patient added successfully.");
                clearFields(nameField, roomField, dobField, addressField, phoneField, treatedCheck);

            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid DOB format. Use dd/MM/yyyy.", "Date Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // === ADD PANELS TO FRAME ===
        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void clearFields(JTextField name, JTextField room, JTextField dob,
                             JTextField address, JTextField phone, JCheckBox treated) {
        name.setText("");
        room.setText("");
        dob.setText("");
        address.setText("");
        phone.setText("");
        treated.setSelected(false);
    }
    
    private JButton createNavButton(String text, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(30, 144, 255));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(action);
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddPatient::new);
    }
}
