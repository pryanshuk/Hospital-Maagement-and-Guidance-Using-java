package Adm;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import Doc.*;
import Staff.Staff;
import JDBCD.JDBCD;
import Pat.Patients;




public class Admin  {
    private static final Scanner scanner = new Scanner(System.in);
    private static Patients patient;
    private static Doctors doctor;
    private static Staff staff;
     public Admin(Connection con, Statement stmt) {
        this.patient = new Patients(con, stmt);
        this.doctor = new Doctors(con, stmt);
        this.staff = new Staff(con, stmt);
    }
    
    public static void admin() {
       
        System.out.print("To see all records of PATIENTS press P\n" +
                "To see all records of DOCTORS press D\n" +
                "To see all records of Staff press S\n" +
                "To add a new record of doctors press A\n" +
                "To add a new record of staff press SA\n" +
                "To search a record press sh\n"+
                "To Update records of doctors press X \n"+
                "To delete records of doctors press X \n" 
                 );
                

        String v = scanner.next().toUpperCase();
        switch (v) {
            case "P":
                showPatients();
                break;
            case "D":
                showDoctors();
                break;
            case "A":
                addDoctor();
                break;
            case "SA":
                staff.newstaff("Priya Sharma", "Pediatrics", "102", "502");
                break;
            case "S":
                showStaff();
                break;
            case "AS":
                staff.getdetstaff();
            case "SH":
                searchRecord();
                break;
            case "X":
                System.out.println("Enter id of doctor to be deleted ");
                String i=scanner.next();
                Doctors.deleteDoctorById(i);
            default:
                System.out.println("Invalid option.");
                admin();
                break;
        }
    }

    public static void addDoctor() {
       
        System.out.print("Enter ID of the new doctor: \n");
        String id = scanner.next();
        if ((Doctors.getDoctorById(id))!=null) {
            System.out.println("Doctor with the given ID already exists.");
                System.out.println("To go to Admin menu: press 1 To Exit out of Admin press 2");
                int x= scanner.nextInt();
                if(x==1){
                    admin();
                }
                else if(x==2){
                    Patients.mexit();
                }
                else{
                    System.out.println("enter valid input");
                }
        }
        System.out.println("Enter name of the doctor: ");
        String name = scanner.nextLine();
        // scanner.nextLine();
        System.out.print("Enter specialist: ");
        String specialist = scanner.next();
        System.out.print("Enter phone number: ");
        String phone = scanner.next();
        System.out.print("Enter address: ");
        String address = scanner.next();
        String[] doctorDetails = {id, name,specialist, address};
        Doctors.writeDoctorData(doctorDetails);
        System.out.println("To go to Admin menu: press 1 To Exit out of Admin press 2");
        int x= scanner.nextInt();
        if(x==1){
            admin();
        }
        else if(x==2){
            Patients.mexit();
        }
        else{
            System.out.println("enter valid input");
        }
    }
    // private boolean isDoctorIdExists(String id) {
    //     for (int i = 0; i < doctorsCount; i++) {
    //         if (doctors[i][0].equals(id)) {
    //             return true; 
    //         }
    //     }
    //     return false;
    // }
    // private void addNewDoctor(String id, String name, String specialist, String phone, String address) {
    //     if (doctorsCount < doctors.length) {
    //         doctors[doctorsCount] = new String[]{id, name, specialist, phone, address};
    //         doctorsCount++;
    //         System.out.println("Doctor added successfully.");
    //     } else {
    //         System.out.println("Doctor limit reached. Cannot add more doctors.");
    //         System.out.println("To go to Admin menu: press 1 To Exit out of Admin press 2");
    //     int x= scanner.nextInt();
    //     if(x==1){
    //         admin();
    //     }
    //     else if(x==2){
    //         mexit();
    //     }
    //     else{
    //         System.out.println("enter valid input");
    //     }
    //     }
    // }
    
    private static void showPatients() {
        
        patient.extractData();
        System.out.println("To go to Admin menu: press 1 To Exit out of Admin press 2");
        int x= scanner.nextInt();
        if(x==1){
            admin();
        }
        else if(x==2){
            Patients.mexit();
        }
        else{
            System.out.println("enter valid input");
        }
        
    }
    private static void showStaff() {
        
        staff.extractData();
        System.out.println("To go to Admin menu: press 1 To Exit out of Admin press 2");
        int x= scanner.nextInt();
        if(x==1){
            admin();
        }
        else if(x==2){
            Patients.mexit();
        }
        else{
            System.out.println("enter valid input");
        }
        
    }
    private static void showDoctors() {
        doctor.extractData();
         System.out.println("To go to Admin menu: press 1 To Exit out of Admin press 2");
        int x= scanner.nextInt();
        if(x==1){
            admin();
        }
        else if(x==2){
            Patients.mexit();
        }
        else{
            System.out.println("enter valid input");
        }
    }

    private static void searchRecord() {
        System.out.print("To search a record of a PATIENT press P\n" +
                "To search a record of a DOCTOR press D\n");

        String s = scanner.next().toUpperCase();
        if ("P".equalsIgnoreCase(s)) {
            searchPatient();
        } else if ("D".equalsIgnoreCase(s)) {
            searchDoctor();
        } else {
            System.out.println("Invalid option.");
            searchRecord();
        }
    }

    private static void searchPatient() {
        System.out.println("Enter phone number of the patient to be searched");
        String phone = scanner.next();
        ResultSet patientdetails = JDBCD.findPatientByPhoneNumber(phone);
        if (patientdetails != null) {
            String patientName;
            String phoneNumber;
            String address;
            String idProof;
            try {
                patientName = patientdetails.getString("name");
                phoneNumber = patientdetails.getString("phone");
                address = patientdetails.getString("address");
                idProof = patientdetails.getString("id_proof");
                System.out.println("Patient Name: " + patientName);
                System.out.println("Phone No.: " + phoneNumber);
                System.out.println("Address: " + address);
                System.out.println("ID Proof: " + idProof);
            } catch (SQLException e) {
              System.out.println(e);
            }
    
            
    
        } else {
            System.out.println("No record found. Please enter correct details.");
        }
        continueSearching();
    }

    private static void searchDoctor() {
        System.out.println("Enter ID of the doctor to be searched");
        String id = scanner.next();
        String[] doctorDetail = Doctors.getDoctorById(id);
        if (doctorDetail!=null) {
            System.out.println("ID: " + doctorDetail[0] +
            " Name: " + doctorDetail[1]  +
            " Specialist: " + doctorDetail[2] +
            " Address: " + doctorDetail[3] );
        } else {
            System.out.println("No record found. Please enter correct details.");
        }
        continueSearching();
    }

    private static void continueSearching() {
        System.out.print("Do you want to search more records? (Y/N): ");
        String response = scanner.next().toUpperCase();
        if ("Y".equalsIgnoreCase(response)) {
            searchRecord();
        } else {
        System.out.println("To go to Admin menu: press 1 To Exit out of Admin press 2");
        int x= scanner.nextInt();
        if(x==1){
            admin();
        }
        else if(x==2){
            Patients.mexit();
        }
        else{
            System.out.println("enter valid input");
        }
        }
    }
}

 