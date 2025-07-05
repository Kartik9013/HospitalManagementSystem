package com.hospital.ui;

import javax.swing.*;
import java.awt.*;
import com.hospital.Dao.*;
import com.hospital.entity.Patient;

public class DeletePatient extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField idField;
    private JTextArea resultArea;
    private JButton deleteBtn;
    private PatientDao patientDao = new PatientDaoImpl();

    public DeletePatient() {
        setTitle("Delete Patient");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(220, 53, 69));  // Red

        JLabel titleLabel = new JLabel("Hospital Management System", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.NORTH);

     // Navbar
        JPanel navbar = new JPanel();
        navbar.setBackground(new Color(220, 53, 69));
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

        // Center Panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));
        JLabel idLabel = new JLabel("Enter Patient ID:");
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        idField = new JTextField(10);

        JButton loadBtn = new JButton("Load");
        loadBtn.setBackground(new Color(23, 162, 184));
        loadBtn.setForeground(Color.WHITE);
        loadBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        loadBtn.setFocusPainted(false);
        loadBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loadBtn.addActionListener(e -> loadPatient());

        deleteBtn = new JButton("Delete");
        deleteBtn.setBackground(new Color(220, 53, 69));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        deleteBtn.setFocusPainted(false);
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.setEnabled(false);
        deleteBtn.addActionListener(e -> deletePatient());

        centerPanel.add(idLabel);
        centerPanel.add(idField);
        centerPanel.add(loadBtn);
        centerPanel.add(deleteBtn);
        add(centerPanel, BorderLayout.CENTER);

        // Result Area
        resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton createNavButton(String text, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(220, 53, 69));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(action);
        return btn;
    }

    private void loadPatient() {
        String input = idField.getText().trim();
        resultArea.setText("");
        deleteBtn.setEnabled(false);

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a patient ID.");
            return;
        }

        try {
            int id = Integer.parseInt(input);
            Patient patient = patientDao.getPatientById(id);

            if (patient == null) {
                resultArea.setText("No patient found with ID: " + id);
                return;
            }

            // Show patient details
            StringBuilder sb = new StringBuilder();
            sb.append("Patient Details:\n");
            sb.append("ID: ").append(patient.getId()).append("\n");
            sb.append("Name: ").append(patient.getName()).append("\n");
            sb.append("Room: ").append(patient.getRoomNumber()).append("\n");
            sb.append("Gender: ").append(patient.getGender()).append("\n");
            sb.append("Address: ").append(patient.getAddress()).append("\n");
            sb.append("Phone: ").append(patient.getPhone()).append("\n");

            resultArea.setText(sb.toString());
            deleteBtn.setEnabled(true);  // Enable delete only after successful load

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID. Please enter a valid numeric ID.");
        }
    }

    private void deletePatient() {
        String input = idField.getText().trim();

        try {
            int id = Integer.parseInt(input);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete patient ID " + id + "?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean deleted = patientDao.deletePatient(id);
                if (deleted) {
                    resultArea.setText("Patient with ID " + id + " has been deleted successfully.");
                    deleteBtn.setEnabled(false);
                } else {
                    resultArea.setText("Failed to delete patient with ID: " + id);
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DeletePatient::new);
    }
}
