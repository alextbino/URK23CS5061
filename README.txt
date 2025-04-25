code : import javax.swing.*;
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

aim :To design and implement a simple GUI-based Hospital Management System using Java Swing and MySQL that allows the user to add and view doctor information, thereby improving the management and accessibility of hospital records.
procedure: 
Set Up MySQL Database:

Install MySQL Server and create a database named hospital_db.

Ensure the table doctor exists or is created via the application.

Develop Java Application:

Use JFrame and Swing components (JTextField, JButton, JTextArea, etc.) to design the user interface.

Establish a connection to the MySQL database using JDBC.

Implement Core Functionalities:

Add Doctor: Takes name and specialization as input and inserts them into the database.

Load Doctors: Fetches and displays all doctor records from the database.

Error Handling and UI Improvements:

Use JOptionPane for user feedback.

Handle SQL exceptions and edge cases (e.g., empty inputs).

Testing:

Run the application to verify data is correctly inserted and retrieved.




purpose: The purpose of this project is to:

Demonstrate how to create a simple database-driven application using Java.

Provide a practical tool that can help manage doctor records in a hospital setting.

Help students or learners understand how to integrate Swing for UI and JDBC for backend database connectivity.







key features Simple GUI Interface:

Easy-to-use layout with labeled fields and action buttons.

Doctor Registration:

Allows the addition of doctor details (name and specialization).

View Doctor List:

Displays all existing doctor records in a scrollable text area.

Database Integration:

Uses MySQL as a persistent backend to store and retrieve data.

Automatic Table Creation:

Automatically creates the doctor table if it doesn't exist.

Data Validation and Feedback:

Notifies the user after successfully adding a doctor.
