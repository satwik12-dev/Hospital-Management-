package Projects;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class Hospital extends JFrame {

    private static final java.util.List<Appointment> appointments = new ArrayList<>();
    private static final String newline = "\n";
    private JTextArea displayArea;
    private static final Map<String, Patient> patientRecords = new HashMap<>();
    public Hospital() {
        super("Hospital Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        JLabel titleLabel = new JLabel("Hospital Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0, 102, 204));
        menuBar.setForeground(Color.WHITE);

        JMenu patientMenu = new JMenu("Patient");
        JMenu appointmentMenu = new JMenu("Appointment");
        patientMenu.setForeground(Color.WHITE);
        appointmentMenu.setForeground(Color.WHITE);

        menuBar.add(patientMenu);
        menuBar.add(appointmentMenu);
        setJMenuBar(menuBar);

        // Menu Items
        JMenuItem addPatientMenuItem = new JMenuItem("Add Patient");
        JMenuItem displayPatientsMenuItem = new JMenuItem("Display Patients");
        JMenuItem bookAppointmentMenuItem = new JMenuItem("Book Appointment");
        JMenuItem displayAppointmentsMenuItem = new JMenuItem("Display Appointments");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        patientMenu.add(addPatientMenuItem);
        patientMenu.add(displayPatientsMenuItem);
        appointmentMenu.add(bookAppointmentMenuItem);
        appointmentMenu.add(displayAppointmentsMenuItem);
        appointmentMenu.add(exitMenuItem);

        // Text Area for Display
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Information Display"));
        add(scrollPane, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(0, 102, 204));
        JLabel footerLabel = new JLabel("© 2025 Hospital Management System");
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);

        // Button Actions
        addPatientMenuItem.addActionListener(e -> addPatient());
        displayPatientsMenuItem.addActionListener(e -> displayPatients());
        bookAppointmentMenuItem.addActionListener(e -> bookAppointment());
        displayAppointmentsMenuItem.addActionListener(e -> displayAppointments());
        exitMenuItem.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void addPatient() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField medicalHistoryField = new JTextField();

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(phoneField);
        panel.add(new JLabel("Medical History:"));
        panel.add(medicalHistoryField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Patient",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                BigDecimal phoneNumber = new BigDecimal(phoneField.getText());
                String medicalHistory = medicalHistoryField.getText();

                Patient patient = new Patient(name, age, phoneNumber, medicalHistory);
                patientRecords.put(name, patient);
                displayArea.append("Patient added successfully!" + newline + patient + newline);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please check age and phone number.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void displayPatients() {
        displayArea.setText("");
        displayArea.append("-------- PATIENT DETAILS --------" + newline);
        if (patientRecords.isEmpty()) {
            displayArea.append("No patients found." + newline);
        } else {
            for (Patient patient : patientRecords.values()) {
                displayArea.append(patient + newline);
            }
        }
    }

    private void bookAppointment() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField patientNameField = new JTextField();
        JTextField specializationField = new JTextField();
        JTextField dateField = new JTextField();

        panel.add(new JLabel("Patient Name:"));
        panel.add(patientNameField);
        panel.add(new JLabel("Specialization:"));
        panel.add(specializationField);
        panel.add(new JLabel("Appointment Date (dd/mm/yyyy):"));
        panel.add(dateField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Book Appointment",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String patientName = patientNameField.getText();
            if (!patientRecords.containsKey(patientName)) {
                JOptionPane.showMessageDialog(this, "Patient not found. Please add the patient first.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String specialization = specializationField.getText();
            String appointmentDate = dateField.getText();

            Appointment appointment = new Appointment(patientName, specialization, appointmentDate);
            appointments.add(appointment);

            displayArea.append("Appointment booked successfully!" + newline + appointment + newline);
        }
    }

    private void displayAppointments() {
        displayArea.setText("");
        displayArea.append("-------- APPOINTMENTS --------" + newline);
        if (appointments.isEmpty()) {
            displayArea.append("No appointments found." + newline);
        } else {
            for (Appointment appointment : appointments) {
                displayArea.append(appointment + newline);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Hospital());
    }
}

class Patient {
    String name;
    int age;
    BigDecimal phoneNumber;
    String medicalHistory;

    public Patient(String name, int age, BigDecimal phoneNumber, String medicalHistory) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.medicalHistory = medicalHistory;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Phone: " + phoneNumber + ", Medical History: " + medicalHistory;
    }
}

class Appointment {
    String patientName;
    String specialization;
    String date;

    public Appointment(String patientName, String specialization, String date) {
        this.patientName = patientName;
        this.specialization = specialization;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Patient: " + patientName + ", Specialization: " + specialization + ", Date: " + date;
    }
}