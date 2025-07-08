package com.hospital.ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

import com.hospital.entity.Patient;
import com.hospital.Dao.PatientDao;
import com.hospital.Dao.PatientDaoImpl;

public class ViewPatients extends JFrame {
    private static final long serialVersionUID = 1L;

    public ViewPatients() {
        setTitle("All Patients");
        setSize(1000, 500);
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
        String[] columnNames = {"ID", "Name", "Room", "DOB", "Gender", "Address", "Phone", "Treated","Disease", "Action"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(173, 216, 230));
        table.setGridColor(Color.LIGHT_GRAY);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch data
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
                p.isTreated() ? "Yes" : "No",
                p.getDisease(),
                "Delete"
            };
            model.addRow(row);
        }

        // Add delete button column
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), model, dao));

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

// ===== Custom Button Renderer =====
@SuppressWarnings("serial")
class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
        setForeground(Color.WHITE);
        setBackground(new Color(220, 20, 60));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
        setText("Delete");
        return this;
    }
}

// ===== Custom Button Editor =====
@SuppressWarnings("serial")
class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private boolean clicked;
    private int row;
    private DefaultTableModel model;
    private PatientDao dao;

    public ButtonEditor(JCheckBox checkBox, DefaultTableModel model, PatientDao dao) {
        super(checkBox);
        this.model = model;
        this.dao = dao;
        button = new JButton("Delete");
        button.setOpaque(true);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(220, 20, 60));
        button.addActionListener(e -> fireEditingStopped()); // Don't do delete here directly
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                  boolean isSelected, int row, int column) {
        this.row = row;
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            // Delay the deletion until after editing is completely stopped
            SwingUtilities.invokeLater(() -> {
                if (row < model.getRowCount()) {
                    int id = (int) model.getValueAt(row, 0);
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this patient?",
                            "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        if (dao.deletePatient(id)) {
                            model.removeRow(row);
                            JOptionPane.showMessageDialog(null, "Patient deleted successfully.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to delete patient.");
                        }
                    }
                }
            });
        }
        clicked = false;
        return "Delete";
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

    @Override
    public void cancelCellEditing() {
        clicked = false;
        super.cancelCellEditing();
    }
}
