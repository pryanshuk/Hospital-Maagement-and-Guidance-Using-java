package Doc;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Pat.Patients;
import common.*;
import JDBCD.*;

public  class Doctors extends  AbstractDatabase{
    public Doctors(Connection con, Statement stmt) {
        super(con, stmt);
    }
    static Scanner  scanner = new Scanner(System.in);
    public static int doctorsCount = 0;
    private static final String DOCTORS_FILE_PATH = "Doc/doctors.txt";
// to create new file
public static void createNewFile(String filePath) {
        try {
            File file = new File(filePath);

            if (file.createNewFile()) {
                System.out.println("File created successfully!");
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
public static void doctorfile() {
    String[] doctor1 = {"1", "Dr. Kiran", "Neurologist", "USA"};
    String[] doctor2 = {"2", "Dr. Pryanshu", "Psychiatrist", "UK"};
    String[] doctor3 = {"3", "Dr. Ashish", "Cardiologist", "Canada"};
    String[] doctor4 = {"4", "Dr. Kalra", "Audiologist", "Australia"};
    String[] doctor5 = {"5", "Dr. Bhuwan", "Radiologist", "Germany"};
    String[] doctor6 = {"6", "Dr. Brown", "Surgeon", "France"};

    writeDoctorData(doctor1);
    writeDoctorData(doctor2);
    writeDoctorData(doctor3);
    writeDoctorData(doctor4);
    writeDoctorData(doctor5);
    writeDoctorData(doctor6);
    }
public static void writeDoctorData(String[] doctorData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DOCTORS_FILE_PATH, true))) {
            for (String data : doctorData) {
                writer.write(data + ",");
            }
            writer.newLine(); // Add a new line for each doctor
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
@Override
    public  void extractData() {
        try (Scanner scanner = new Scanner(new File(DOCTORS_FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String[] doctorData = scanner.nextLine().split(",");
                // Process doctor data as needed
                for (String data : doctorData) {
                    System.out.print(data + " ");
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Doctors file not found.");
        }
    }
    public static void doctorProcess() {
        System.out.print("Enter your ID: ");
        String id = scanner.next();
        String[] doctorData = getDoctorById(id);

        if (doctorData == null) {
            System.out.println("No such record exists. Please enter the correct ID.");
            Patients.menu();
            return;
        }

        String doctorName = doctorData[1];
        System.out.println("WELCOME " + doctorName);

        String x = "Y";
        while (x.equalsIgnoreCase("Y")) {
            System.out.print("To see your PROFILE details, press 1\nTo see your APPOINTMENTS, press 2: ");
            String choice = scanner.next();
            // scanner.next();
            if (choice.equalsIgnoreCase("1")) {
                showDoctorProfile(id);
            } else if (choice.equalsIgnoreCase("2")) {
                ResultSet doctorDetails=JDBCD.checkExistingAppointment(Integer.parseInt(id));
            if(doctorDetails!=null){
                try {
        
                    System.out.println("Here are your Appointments:");
                    while (doctorDetails.next()) {
                        System.out.println(      
                                " Specialist: " + doctorDetails.getString("doctorType") +
                                " Patient_phone: " + doctorDetails.getString("patient_phone") +
                                ", Patient Address: " + doctorDetails.getString("patient_address") +
                                ", Patient ID Proof: " + doctorDetails.getString("patient_id_proof"));
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                    System.out.println("line 96 in Doctors");
                }
                    }
            else{
                System.out.println("No current appointment for you");
            }}
             else {
                System.out.println("Invalid choice.");
            }

            System.out.print("Do you want to see your profile or appointments? (Y/N): ");
            x = scanner.next();
        }

        Patients.mexit();
    }
    public static String[] getDoctorById(String id) {
        try (Scanner scanner = new Scanner(new File(DOCTORS_FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String[] doctorData = scanner.nextLine().split(",");
                if (doctorData.length > 0 && doctorData[0].equals(id)) {
                    return doctorData;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Doctors file not found.");
        }
        return null;
    }

    private static void showDoctorProfile(String id) {
        String[] doctorData = getDoctorById(id);
    
        if (doctorData != null) {
            System.out.println("Doctor Profile Details:");
            System.out.println("ID: " + id);
            System.out.println("Name: " + doctorData[1]); // Assuming index 1 is for the name
            System.out.println("Speciality: " + doctorData[2]); // Assuming index 2 is for the speciality
            System.out.println("Country: " + doctorData[3]); // Assuming index 3 is for the country
        } else {
            System.out.println("Doctor not found.");
        }
    }

public static void updateDoctorById(String id, String[] newDoctorData) {
        File file = new File(DOCTORS_FILE_PATH);
        File tempFile = new File("Doc/tempDoctors.txt");

        try (Scanner scanner = new Scanner(file);
             PrintWriter writer = new PrintWriter(new FileWriter(tempFile, true))) {
            while (scanner.hasNextLine()) {
                String[] doctorData = scanner.nextLine().split(",");
                if (doctorData.length > 0 && doctorData[0].equals(id)) {
                    // Update the doctor data with the new data
                    writer.println(String.join(",", newDoctorData));
                } else {
                    writer.println(String.join(",", doctorData));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Doctors file not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rename the temp file to the original file
        tempFile.renameTo(file);
    }

    public static void deleteDoctorById(String id) {
        File file = new File(DOCTORS_FILE_PATH);
        File tempFile = new File("Doc/tempDoctors.txt");

        try (Scanner scanner = new Scanner(file);
             PrintWriter writer = new PrintWriter(new FileWriter(tempFile, true))) {
            while (scanner.hasNextLine()) {
                String[] doctorData = scanner.nextLine().split(",");
                if (doctorData.length > 0 && doctorData[0].equals(id)) {
                    // Skip the line for the doctor to be deleted
                    continue;
                } else {
                    writer.println(String.join(",", doctorData));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Doctors file not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rename the temp file to the original file
        tempFile.renameTo(file);
    }
    public static void updateDoctorDetails() {

        // Use a loop to update multiple records
        String response = "Y";
        while ("Y".equalsIgnoreCase(response)) {
            // Take user input for doctor ID
            System.out.print("Enter the ID of the doctor to update: ");
            String id = scanner.next();

            // Check if the doctor with the given ID exists
            String[] existingDoctorData = getDoctorById(id);
            if (existingDoctorData == null) {
                System.out.println("No record found with the provided ID.");
                continue; // Continue to the next iteration of the loop
            }

            // Display the current details of the doctor
            System.out.println("Current Details:");
            System.out.println("ID: " + existingDoctorData[0]);
            System.out.println("Name: " + existingDoctorData[1]);
            System.out.println("Speciality: " + existingDoctorData[2]);
            System.out.println("Country: " + existingDoctorData[3]);

            // Take user input for the new details
            System.out.print("Enter the updated name: ");
            String updatedName = scanner.next();

            System.out.print("Enter the updated speciality: ");
            String updatedSpeciality = scanner.next();

            System.out.print("Enter the updated country: ");
            String updatedCountry = scanner.next();

            // Update the doctor record with the new details
            updateDoctorById(id, new String[]{id, updatedName, updatedSpeciality, updatedCountry});

            System.out.print("Do you want to update more records? (Y/N): ");
            response = scanner.next().toUpperCase();
        }

        System.out.println("Doctor details updated successfully.");
    }

    // public  void doctorProcess() {
    //     System.out.print("Enter your ID: ");
    //     String id = scanner.next();
    //     String[] doctorData = getDoctorById(id);

    //     if (doctorData == null) {
    //         System.out.println("NOTE : To access the Doctor Details, Please contact the Admin");
    //         System.out.println("No such record exists. Please enter the correct ID.");
    //         menu();
    //         // doctorProcess();
    //         return;
    //     }

    //     String doctorName = doctorData[1];
    //     System.out.println("WELCOME " + doctorName);

    //     String x = "Y";
    //     while (x.equalsIgnoreCase("Y")) {
    //         System.out.print("To see your PROFILE details, press 1\nTo see your APPOINTMENTS, press 2: ");
    //         String choice = scanner.next();

    //         if (choice.equalsIgnoreCase("1")) {
    //             showDoctorProfile(id);
    //         } else if (choice.equalsIgnoreCase("2")) {
    //             showDoctorAppointments(id, doctorName);
    //         } else {
    //             System.out.println("Invalid choice.");
    //         }

    //         System.out.print("Do you want to see your profile or appointments? (Y/N): ");
    //         x = scanner.next();
    //     }

    //     mexit();
    // }

    // private String[] getDoctorById(String id) {
    //     for (int i = 0; i < doctorsCount; i++) {
    //         if (doctors[i][0].equals(id)) {
    //             return doctors[i];
    //         }
    //     }
    //     return null; 
    // }

    // private void showDoctorProfile(String id) {
    //     for (int i = 0; i < doctorsCount; i++) {
    //         if (doctors[i][0].equals(id)) {
    //             System.out.println("Doctor Profile Details:");
    //             System.out.println("ID: " + id);
    //             System.out.println("Name: " + doctors[i][0]);
    //             System.out.println("Speciality: " + doctors[i][1]);
    //             System.out.println("Country: " + doctors[i][2]);
    //             return;
    //         }
    //     }
    //     System.out.println("Doctor not found.");
    // }

    // private void showDoctorAppointments(String doctorId) {
    //     String[] doctorAppointments = getAppointmentsForDoctor(doctorId, doctorName);
    
    //     if (doctorAppointments == null || doctorAppointments.length == 0) {
    //         System.out.println("No appointments for this doctor.");
    //     } else {
    //         System.out.println("Appointments for " + doctorName + ":");
    //         for (String patientName : doctorAppointments) {
    //             System.out.println("Patient Name: " + patientName);
    //         }
    //     }
    // }
    
    
}

// class Appointment {
//     public String patientName;
//     public String patientPhoneNumber;

//     public Appointment(String patientName, String patientPhoneNumber) {
//         this.patientName = patientName;
//         this.patientPhoneNumber = patientPhoneNumber;
//     }

//     public String getPatientName() {
//         return patientName;
//     }
// }
