package com.hospital.ui;

import javax.swing.*;
import java.awt.*;
import com.hospital.Dao.*;
import com.hospital.entity.Patient;

public class SearchPatient extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField searchField;
    private JTextArea resultArea;
    private PatientDao patientDao = new PatientDaoImpl();

    public SearchPatient() {
        setTitle("Search Patient");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

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

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(Color.decode("#f0f2f5")); // light background

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.setBackground(Color.decode("#f0f2f5"));

        JLabel searchLabel = new JLabel("Search Patient by ID:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        searchField = new JTextField(10);

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(34, 139, 34));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        searchButton.setFocusPainted(false);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        searchButton.addActionListener(e -> searchPatient());

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        centerPanel.add(searchPanel);
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
        btn.setForeground(new Color(30, 144, 255));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 35));
        btn.addActionListener(action);
        return btn;
    }

    private void searchPatient() {
        String input = searchField.getText().trim();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a patient ID.");
            return;
        }

        try {
            int id = Integer.parseInt(input);
            Patient patient = patientDao.getPatientById(id);

            if (patient != null) {
                resultArea.setText(
                    "Patient Details:\n" +
                    "ID         : " + patient.getId() + "\n" +
                    "Name       : " + patient.getName() + "\n" +
                    "Gender     : " + patient.getGender() + "\n" +
                    "Room No.   : " + patient.getRoomNumber() + "\n" +
                    "DOB        : " + patient.getDob() + "\n" +
                    "Phone      : " + patient.getPhone() + "\n" +
                    "Address    : " + patient.getAddress() + "\n" +
                    "Disease    : " + patient.getDisease() + "\n" +
                    "Is Treated : " + (patient.isTreated() ? "Yes" : "No")
                );
            } else {
                resultArea.setText("No patient found with ID: " + id);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric ID.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SearchPatient::new);
    }
}
