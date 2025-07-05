package com.hospital.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Dashboard extends JFrame {
	private static final long serialVersionUID = 1L;
	private BufferedImage backgroundImage;

    @SuppressWarnings("serial")
	public Dashboard() {
        setTitle("Hospital Management System");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            backgroundImage = ImageIO.read(new File("src/main/resources/dashboard.jpg")); // Adjust path
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        contentPanel.setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Hospital Management System", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        contentPanel.add(headerLabel, BorderLayout.NORTH);

        // Sidebar (button panel)
        JPanel sidebar = new JPanel();
        sidebar.setOpaque(false);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 30));

        // Center buttons using glue
        sidebar.add(Box.createVerticalGlue());

        // Create and add buttons
        sidebar.add(createStyledButton("➕ Add New Patient", e -> {
            new AddPatient();
            dispose();
        }));
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));

        sidebar.add(createStyledButton("📋 View Patients", e -> {
            new ViewPatients();
        	dispose();
        }));
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));

        sidebar.add(createStyledButton("🔍 Search Patient", e -> {
            new SearchPatient();
            dispose();
        }));
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));

        sidebar.add(createStyledButton("✏️ Update Patient", e -> {
            JOptionPane.showMessageDialog(this, "Update Patient feature coming soon!");
        }));
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));

        sidebar.add(createStyledButton("❌ Delete Patient", e -> {
            JOptionPane.showMessageDialog(this, "Delete Patient feature coming soon!");
        }));

        sidebar.add(Box.createVerticalGlue());

        contentPanel.add(sidebar, BorderLayout.EAST);
        setContentPane(contentPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text, java.awt.event.ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setPreferredSize(new Dimension(200, 40));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setBackground(new Color(30, 144, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btn.addActionListener(listener);
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Dashboard::new);
    }
}
