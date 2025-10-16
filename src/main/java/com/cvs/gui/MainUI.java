package com.cvs.gui;

import com.cvs.utils.DBConnector;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(139, 69, 19);
    private static final Color SECONDARY_COLOR = new Color(210, 180, 140);
    private static final Color ACCENT_COLOR = new Color(160, 82, 45);

    public MainUI() {
        initializeUI();
        testDatabaseConnection();
    }

    private void initializeUI() {
        setTitle("☕ Coffee Vending System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(0, 0, SECONDARY_COLOR, 0, getHeight(), PRIMARY_COLOR);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center content
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("☕ Coffee Vending System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        return headerPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome to the Coffee Vending System!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        centerPanel.add(welcomeLabel, gbc);

        // User Login Button
        JButton userLoginBtn = createStyledButton("👤 User Login", ACCENT_COLOR);
        userLoginBtn.addActionListener(e -> openUserLogin());
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        centerPanel.add(userLoginBtn, gbc);

        // Admin Login Button
        JButton adminLoginBtn = createStyledButton("🔧 Admin Login", PRIMARY_COLOR);
        adminLoginBtn.addActionListener(e -> openAdminLogin());
        gbc.gridx = 1; gbc.gridy = 1;
        centerPanel.add(adminLoginBtn, gbc);

        // Register Button
        JButton registerBtn = createStyledButton("📝 Register New User", new Color(34, 139, 34));
        registerBtn.addActionListener(e -> openRegister());
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        centerPanel.add(registerBtn, gbc);

        // Test DB Connection Button
        JButton testDbBtn = createStyledButton("🔗 Test Database Connection", new Color(70, 130, 180));
        testDbBtn.addActionListener(e -> testDatabaseConnection());
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        centerPanel.add(testDbBtn, gbc);

        return centerPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setOpaque(false);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JLabel footerLabel = new JLabel("Phase 0 - JDBC Initialization Complete | Version 1.0.0", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel);

        return footerPanel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(250, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void openUserLogin() {
        dispose();
        new LoginUI().setVisible(true);
    }

    private void openAdminLogin() {
        dispose();
        new LoginUI().setVisible(true);
    }

    private void openRegister() {
        dispose();
        new RegisterUI().setVisible(true);
    }

    private void testDatabaseConnection() {
        SwingUtilities.invokeLater(() -> {
            JDialog progressDialog = new JDialog(this, "Testing Connection", true);
            progressDialog.setSize(300, 100);
            progressDialog.setLocationRelativeTo(this);
            
            JLabel statusLabel = new JLabel("Testing database connection...", JLabel.CENTER);
            progressDialog.add(statusLabel);
            
            SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    return DBConnector.testConnection();
                }
                
                @Override
                protected void done() {
                    try {
                        boolean success = get();
                        progressDialog.dispose();
                        
                        if (success) {
                            JOptionPane.showMessageDialog(MainUI.this,
                                "✅ Database connection successful!\nJDBC driver loaded and connection established.",
                                "Connection Test",
                                JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(MainUI.this,
                                "❌ Database connection failed!\nPlease check your MySQL server and configuration.",
                                "Connection Test",
                                JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception e) {
                        progressDialog.dispose();
                        JOptionPane.showMessageDialog(MainUI.this,
                            "❌ Connection test error: " + e.getMessage(),
                            "Connection Test",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            
            worker.execute();
            progressDialog.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("🚀 Starting Coffee Vending System...");
            System.out.println("📋 Phase 0: JDBC Initialization");
            
            new MainUI().setVisible(true);
        });
    }
}