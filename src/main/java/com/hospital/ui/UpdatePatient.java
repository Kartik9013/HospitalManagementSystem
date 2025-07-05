package com.hospital.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.hospital.Dao.PatientDao;
import com.hospital.Dao.PatientDaoImpl;
import com.hospital.entity.Patient;

public class UpdatePatient extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JTextField idField, nameField, roomField, dobField, addressField, phoneField;
    private JComboBox<String> genderBox;
    private JCheckBox treatedCheck;
    private JButton loadBtn, updateBtn, refreshBtn;

    public UpdatePatient() {
        setTitle("Update Patient");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // ===== HEADER =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 144, 255));

        JLabel titleLabel = new JLabel("Hospital Management System", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        headerPanel.add(titleLabel, BorderLayout.NORTH);

     // Navbar
        JPanel navbar = new JPanel();
        navbar.setBackground(new Color(30, 144, 255));
        navbar.setLayout(new GridLayout(1, 7, 15, 10));
        navbar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        navbar.add(createNavButton("Home", e -> { new Dashboard(); dispose(); }));
        navbar.add(createNavButton("Add", e -> { new AddPatient(); dispose(); }));
        navbar.add(createNavButton("View", e -> { new ViewPatients(); dispose(); }));
        navbar.add(createNavButton("Search", e -> { new SearchPatient(); dispose(); }));
        navbar.add(createNavButton("Update", e -> { new UpdatePatient(); dispose(); }));
        navbar.add(createNavButton("Delete", e -> { new DeletePatient(); dispose(); }));
        navbar.add(createNavButton("Logout", e -> { new Login(); dispose(); }));

        headerPanel.add(navbar, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        // === FORM PANEL ===
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 12));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.setBackground(new Color(245, 250, 255));

        idField = new JTextField();
        nameField = new JTextField();
        roomField = new JTextField();
        dobField = new JTextField();
        genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        addressField = new JTextField();
        phoneField = new JTextField();
        treatedCheck = new JCheckBox("Treated");

     // Panel for Patient ID input
        formPanel.add(new JLabel("Enter Patient ID:"));

        // ID Field + Buttons
        JPanel idPanel = new JPanel(new BorderLayout(10, 0));
        idPanel.setOpaque(false); // transparent background
        idField = new JTextField();
        idPanel.add(idField, BorderLayout.CENTER);

        // Button Panel (Load + Refresh)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttonPanel.setOpaque(false);

        loadBtn = new JButton("Load");
        refreshBtn = new JButton("Refresh");

        // Style Buttons
        styleButton(loadBtn, new Color(0, 123, 255));     // Blue
        styleButton(refreshBtn, new Color(40, 167, 69));  // Green

        buttonPanel.add(loadBtn);
        buttonPanel.add(refreshBtn);

        idPanel.add(buttonPanel, BorderLayout.EAST);
        formPanel.add(idPanel);


        // Other fields
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

        // Update Button
        updateBtn = new JButton("Update");
        updateBtn.setBackground(new Color(34, 139, 34));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        updateBtn.setEnabled(false);

        formPanel.add(new JLabel()); // empty
        formPanel.add(updateBtn);

        add(formPanel, BorderLayout.CENTER);

        // === Action Listeners ===

        // Load button
        loadBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                PatientDao dao = new PatientDaoImpl();
                Patient patient = dao.getPatientById(id);

                if (patient == null) {
                    JOptionPane.showMessageDialog(this, "No patient found with ID " + id);
                } else {
                    nameField.setText(patient.getName());
                    roomField.setText(patient.getRoomNumber());
                    dobField.setText(patient.getDob().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    genderBox.setSelectedItem(patient.getGender());
                    addressField.setText(patient.getAddress());
                    phoneField.setText(patient.getPhone());
                    treatedCheck.setSelected(patient.isTreated());

                    idField.setEnabled(false);
                    loadBtn.setEnabled(false);
                    updateBtn.setEnabled(true);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID or error: " + ex.getMessage());
            }
        });

        // Update button
        updateBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                String room = roomField.getText().trim();
                LocalDate dob = LocalDate.parse(dobField.getText().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String gender = genderBox.getSelectedItem().toString();
                String address = addressField.getText().trim();
                String phone = phoneField.getText().trim();
                boolean treated = treatedCheck.isSelected();

                Patient updated = new Patient(name, room, dob, gender, address, phone, treated);
                updated.setId(id);  // Needed for update

                PatientDao dao = new PatientDaoImpl();
                boolean success = dao.updatePatient(updated);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Patient updated successfully!");
                    updateBtn.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update patient. Record may not exist.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        // Refresh button
        refreshBtn.addActionListener(e -> {
            idField.setText("");
            nameField.setText("");
            roomField.setText("");
            dobField.setText("");
            genderBox.setSelectedIndex(0);
            addressField.setText("");
            phoneField.setText("");
            treatedCheck.setSelected(false);

            idField.setEnabled(true);
            loadBtn.setEnabled(true);
            updateBtn.setEnabled(false);
        });

        setVisible(true);
    }

    private JButton createNavButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(30, 144, 255));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(action);
        return button;
    }
    
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(UpdatePatient::new);
    }
}
