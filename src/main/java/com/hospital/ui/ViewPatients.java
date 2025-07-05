package com.hospital.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.hospital.entity.Patient;
import com.hospital.Dao.PatientDao;
import com.hospital.Dao.PatientDaoImpl;

public class ViewPatients extends JFrame {
	private static final long serialVersionUID = 1L;

	public ViewPatients() {
        setTitle("All Patients");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

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

        // ===== TABLE PANEL =====
        String[] columnNames = {"ID", "Name", "Room", "DOB", "Gender", "Address", "Phone", "Treated"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(173, 216, 230));
        table.setGridColor(Color.LIGHT_GRAY);

        // Center align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch data from DB
        PatientDao dao = new PatientDaoImpl();
        List<Patient> patients = dao.getAllPatients();

        for (Patient p : patients) {
            Object[] row = {
                p.getId(),
                p.getName(),
                p.getRoomNumber(),
                p.getDob().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                p.getGender(),
                p.getAddress(),
                p.getPhone(),
                p.isTreated() ? "Yes" : "No"
            };
            model.addRow(row);
        }

        setVisible(true);
    }

    private JButton createNavButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(30, 144, 255));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(action);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewPatients::new);
    }
}
