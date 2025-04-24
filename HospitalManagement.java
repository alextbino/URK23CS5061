import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class HospitalManagement extends JFrame {
    private JTextField nameField, specializationField;
    private JTextArea displayArea;
    private Connection connection;

    public HospitalManagement() {
        setTitle("Hospital Management System");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        nameField = new JTextField(20);
        specializationField = new JTextField(20);
        JButton addButton = new JButton("Add Doctor");
        JButton loadButton = new JButton("Load Doctors");
        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);

        add(new JLabel("Doctor Name:"));
        add(nameField);
        add(new JLabel("Specialization:"));
        add(specializationField);
        add(addButton);
        add(loadButton);
        add(new JScrollPane(displayArea));

        // Button actions
        addButton.addActionListener(e -> addDoctor());
        loadButton.addActionListener(e -> loadDoctors());

        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_db", "root", "yourpassword");
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS doctor (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), specialization VARCHAR(255))");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addDoctor() {
        try {
            String name = nameField.getText();
            String spec = specializationField.getText();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO doctor (name, specialization) VALUES (?, ?)");
            ps.setString(1, name);
            ps.setString(2, spec);
            ps.executeUpdate();
            nameField.setText("");
            specializationField.setText("");
            JOptionPane.showMessageDialog(this, "Doctor added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDoctors() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM doctor");
            displayArea.setText("");
            while (rs.next()) {
                displayArea.append("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Specialization: " + rs.getString("specialization") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HospitalManagement().setVisible(true);
        });
    }
}