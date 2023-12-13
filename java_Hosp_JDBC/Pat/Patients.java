package Pat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Adm.Admin;
import Doc.Doctors;
import JDBCD.*;
import common.*;
public  class Patients extends AbstractDatabase{
    // public static Doctors doctor;
    public Patients(Connection con, Statement stmt) {
        super(con, stmt);
    }
    
    public static int appointmentsCount = 0;
    public static final Scanner scanner = new Scanner(System.in);
    public static int patientCount = 0;



    // @Override
    public void extractData() {
        try {
            // Replace this with your actual logic to extract patient data from the 'patients' table
            String sql = "SELECT * FROM patients";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                System.out.println("Patients data:");

                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") +
                            ", Name: " + rs.getString("name") +
                            ", Phone: " + rs.getString("phone") +
                            ", Address: " + rs.getString("address") +
                            ", ID Proof: " + rs.getString("id_proof"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public static void patientM() {

        System.out.print("Are you visiting our hospital for the first time? (Y or N): ");
        String firstTime = scanner.next();

        if (firstTime.equalsIgnoreCase("Y")) {
            patientRegistration();
        } else if (firstTime.equalsIgnoreCase("N")) {
            loginExistingPatient();
        }
    }

    private static void patientRegistration() {
        String ph;
        if (patientCount < 100) {
            System.out.println("Please fill your entries given below :");
            System.out.print("Enter your phone number: ");
            
            boolean input = false;

            do {
                try {
                    ph = scanner.next();
                    if (ph.length() != 10) {
                        throw new ArithmeticException("ENTER 10 DIGITS ONLY");
                    }
                    input = true;
                } catch (ArithmeticException e) {
                    System.out.println("Please enter the correct phone number: " + e.getMessage());
                    // Consume the remaining newline character
                    ph=scanner.nextLine();
                }
            } while (!input);

            System.out.println("Phone number entered successfully");
            scanner.nextLine();  // Consume the newline character
            
            System.out.println("Enter your name: ");
            String na = scanner.nextLine();

            System.out.println("Enter your address: ");
            String ad = scanner.nextLine();
            System.out.print("Enter any valid ID proof: ");
            String idp = scanner.next();

            // Insert patient data into the database
            JDBCD.insertPatientData(na, ph, ad, idp);
            patientCount++;

            System.out.println("Registration successful.");
            choice();
        } else {
            System.out.println("Patient limit reached. Cannot register new patient.");
        }
    }
public static void loginExistingPatient() {
    System.out.print("Enter your phone number to login: ");
    String ph = scanner.next();

    ResultSet patientResultSet = JDBCD.findPatientByPhoneNumber(ph);

    try {
        if (patientResultSet != null ) {
            System.out.println("Patient information:");
            System.out.println("Patient Name: " + patientResultSet.getString("name"));
            System.out.println("Phone No.: " + patientResultSet.getString("phone"));
            System.out.println("Address: " + patientResultSet.getString("address"));
            System.out.println("ID Proof: " + patientResultSet.getString("id_proof"));

            choice();
        } else {
            System.out.println("No such record exists. Please enter correct details or register as a new patient.");
            patientM();
        }
    } catch (SQLException e) {
        System.out.println(e);
        System.out.println("inside loginExisting patient");
    }
}

    // public static void loginExistingPatient() {
    //     System.out.print("Enter your phone number to login: ");
    //     String ph = scanner.nextLine();

    //     int patientIndex = JDBCD.findPatientByPhoneNumber(ph);
    //     if (patientIndex != -1) {
    //         String[] patient = patients[patientIndex];

    //         System.out.println("Patient information:");
    //         System.out.println("Patient Name: " + patient[0]);
    //         System.out.println("Phone No.: " + patient[1]);
    //         System.out.println("Address: " + patient[2]);
    //         System.out.println("ID Proof: " + patient[3]);

    //         choice();
    //     } else {
    //         System.out.println("No such record exists. Please enter correct details or register as a new patient.");
    //         patientRegistration();
    //     }
    // }

    // private static int findPatientByPhoneNumber(String phoneNumber) {
    //     for (int i = 0; i < patientCount; i++) {
    //         if (patients[i][1].equals(phoneNumber)) {
    //             return i;
    //         }
    //     }
    //     return -1; // Patient not found
    // }
    

    public static void choice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter A to get an appointment with a doctor or C for any medical test");
        String c = scanner.nextLine();

        if (c.equalsIgnoreCase("C") || c.equalsIgnoreCase("c")) {
            System.out.println("For BODY TEMPERATURE CHECK - PRESS 1");
            System.out.println("BLOOD PRESSURE - PRESS 2");
            System.out.println("BLOOD TEST - PRESS 3");
            System.out.println("EYESIGHT CHECKUP - PRESS 4");
            System.out.println("X-RAY - PRESS 5");
            System.out.println("ULTRASOUND - PRESS 6");
            System.out.println("MAGNETIC RESONANCE IMAGING (MRI) - PRESS 7");

            String test = scanner.nextLine();

            switch (test) {
                case "1":
                    System.out.println("YOUR TESTING FEES IS ₹150. Please proceed to the cashier to pay the fees and get the coupon for the test.");
                    System.out.println("You may proceed to room no. 7A to get your TEMPERATURE checked.");
                    break;

                case "2":
                    System.out.println("YOUR TESTING FEES IS ₹200. Please proceed to the cashier to pay the fees and get the coupon for the test.");
                    System.out.println("You may proceed to room no. 7B to get your BLOOD PRESSURE checked.");
                    break;

                case "3":
                    System.out.println("YOUR TESTING FEES IS ₹1000. Please proceed to the cashier to pay the fees and get the coupon for the test.");
                    System.out.println("You may proceed to room no. 7B to get your BLOOD TEST done.");
                    break;

                case "4":
                    System.out.println("YOUR TESTING FEES IS ₹350. Please proceed to the cashier to pay the fees and get the coupon for the test.");
                    System.out.println("You may proceed to room no. 7E for your EYESIGHT CHECKUP.");
                    break;

                case "5":
                    System.out.println("YOUR TESTING FEES IS ₹1200. Please proceed to the cashier to pay the fees and get the coupon for the test.");
                    System.out.println("You may proceed to room no. 7C to get your X-RAY done.");
                    break;

                case "6":
                    System.out.println("YOUR TESTING FEES IS ₹2500. Please proceed to the cashier to pay the fees and get the coupon for the test.");
                    System.out.println("You may proceed to room no. 7D to get your ULTRASOUND done.");
                    break;

                case "7":
                    System.out.println("YOUR TESTING FEES IS ₹6000. Please proceed to the cashier to pay the fees and get the coupon for the test.");
                    System.out.println("You may proceed to room no. 7F to get your MRI done.");
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } else if (c.equalsIgnoreCase("A") ) {
            Appointments(); // Call the appointments method
        } else {
            System.out.println("Invalid choice.");
        }
    }
static void AppointmentRegister(String ph){
    System.out.println("Please enter with which doctor you want an appointment:");
                System.out.println("1. Neurologist");
                System.out.println("2. Psychiatrist");
                System.out.println("3. Cardiologist");
                System.out.println("4. Audiologist");
                System.out.println("5. Radiologist");
                System.out.println("6. Surgeon");
                    
                int choice = scanner.nextInt();
                System.out.println(choice);
                String doctorType;
    
                switch (choice) {
                    case 1:
                        doctorType = "Neurologist";
                        break;
                    case 2:
                        doctorType = "Psychiatrist";
                        break;
                    case 3:
                        doctorType = "Cardiologist";
                        break;
                    case 4:
                        doctorType = "Audiologist";
                        break;
                    case 5:
                        doctorType = "Radiologist";
                        break;
                    case 6:
                        doctorType = "Surgeon";
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid doctor.");
                        AppointmentRegister(ph);
                        return;
                }
    
                // Assuming you have a method to insert an appointment in your JDBC class
                 ResultSet patientdata = JDBCD.findPatientByPhoneNumber(ph);
                    if (JDBCD.checkExistingAppointment(ph, doctorType)) {
                        System.out.println("You already have an appointment with this doctor.");
                    } else {
                        // Insert appointment data into the database
                        try {
                            JDBCD.insertAppointmentData(choice, doctorType, patientdata.getString("phone"), patientdata.getString("address"), patientdata.getString("id_proof"));
                        } catch (SQLException e) {
                            System.out.println(e);
                            System.out.println("line 221 in patients");
                        }
                        System.out.println("Your appointment with the " + doctorType + " is confirmed.");
                        String m = "";
                        while (!m.equalsIgnoreCase("M")) {
                            System.out.println("Press M to see the hospital map, otherwise press any other key:");
                            m = scanner.next();
                            if (m.equalsIgnoreCase("M")) {
                                displayMap();
                                mexit();
                            } else {
                                mexit();
                            }
                        }
                    }
                }

static void Appointments() {
        System.out.print("Enter your phone number to book appointment: ");
         String ph = scanner.next();
        ResultSet patientdata = JDBCD.findPatientByPhoneNumber(ph);
        
        if (patientdata != null) {
        // Assuming you have a method to find appointments by phone number in your JDBC class
        ResultSet appointmentResultSet = JDBCD.checkExistingAppointment(ph);
    
        try {
            if (appointmentResultSet != null) {
                if (appointmentResultSet.next()) {
                    System.out.println("Your existing appointments:");
            
                    do {
                        String doctorType = appointmentResultSet.getString("doctorType");
                        System.out.println("Doctor Type: " + doctorType);
                        // You can print other appointment details as needed
                    } while (appointmentResultSet.next());
                    System.out.println("To book appointment with some other doctor Proceed further ");
                    AppointmentRegister(ph);
                } else {
                    System.out.println("No existing appointments found. Proceed to schedule a new appointment.");
                    AppointmentRegister(ph);
                }
            } else {
                System.out.println("No existing appointments found. Proceed to schedule a new appointment.");
                AppointmentRegister(ph);
                
            }
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("line 268 in patients");
        }}
         else {
                    System.out.println("No such patient found. Please enter correct phone number ");
                    Appointments();;
                }
    }
       
//     public static void Appointments() {
//         System.out.print("Enter your phone number to login: ");
        
//         String ph = scanner.nextLine();

//         System.out.println("Please enter with which doctor you want an appointment:");
//         System.out.println("1. Neurologist");
//         System.out.println("2. Psychiatrist");
//         System.out.println("3. Cardiologist");
//         System.out.println("4. Audiologist");
//         System.out.println("5. Radiologist");
//         System.out.println("6. Surgeon");
            
//         int choice = scanner.nextInt();
//         System.out.println(choice);
//         String da ;
                
//         switch (choice) {
//             case 1:
//                 da = "Neurologist";
//                 break;
//             case 2:
//                 da = "Psychiatrist";
//                 break;
//             case 3:
//                 da = "Cardiologist";
//                 break;
//             case 4:
//                 da = "Audiologist";
//                 break;
//             case 5:
//                 da = "Radiologist";
//                 break;
//             case 6:
//                 da = "Surgeon";
//                 break;
//             default:
//                 System.out.println("Invalid choice. Please select a valid doctor.");
//                 Appointments();
//                 return;
//         }

//         int appointmentKey = choice-1;
//         if (appointments[appointmentKey][0] != null) {
//             System.out.println("You already have an appointment for this type.");
//         } else {
           
//             appointments[appointmentKey][0] = String.valueOf(choice); 
//             appointments[appointmentKey][1] = patients[patientCount-1][2];
//             appointments[appointmentKey][2] = patients[patientCount-1][3];
            
//             System.out.println("Your appointment is confirmed.");
//             String m = "";
//             while (!m.equalsIgnoreCase("M")) {
//                 System.out.println("Press M to see the hospital map, otherwise press any other key:");
//                 m = scanner.nextLine();
//                 if (m.equalsIgnoreCase("M")) {
//                     displayMap();
//                     mexit();
//                 } else {
//                     mexit();
//                 }
//             }
        
//     }
// }
public static void displayMap() {
        System.out.println("Hospital Map: ");
        String hospitalMap = "          __________________________________________________________________________\n" +
            "        │PHARMACY│   │7A│7B│7C│7D│7E│7F│       ____________  │          │         │\n" +
            "        │________║   ║__│__│__│__│__│__│      │            │ │CAFETERIA │         │\n" +
            "        │        │    _____________________   ║            │ │__________│         │\n" +
            "        │     ___│   ║DC1 │ DC3 │ DC5 │ DC7│  │  SURGERY   │            ║  PATIENT│\n" +
            "        │ICU  │      │____│_____│_____│____║  │            │            │    WARD │\n" +
            "        │     │      ║DC2 │ DC4 │ DC6 │ DC8│  ║            │            │         │\n" +
            "        │     │      │____│_____│_____│____║  │____________│            ║         │\n" +
            "        │_____│______                                                   │         │\n" +
            "        │            ║______________                                    │         │\n" +
            "........│            │      │       │              _________________    │         │\n" +
            ". DROP  │  EMERGENCY │      │       │            │CASHIER │        ║    ║         │\n" +
            ". OF    ║            │      │WAITING│            │________│___ADMIN│____│         │\n" +
            ".CANOPY ║            │X-RAY │ROOM   ║            │REGISTRATION║    │ IT │         │\n" +
            "........│____________│______│_______│            │____________│____│____│_________│\n" +
            "                                    │                   │\n" +
            "                                    │    MAIN LOBBY     │\n" +
            "                                    │_______     _______│\n" +
            "                                           /     \n";
        
        System.out.println(hospitalMap);

    }

    public static void menu() {
        System.out.print("IF YOU ARE ADMIN(PRESS 1)(Passwd:1234)\n" +
                "IF YOU ARE A DOCTOR(PRESS 2)\n" +
                "IF YOU ARE A PATIENT(PRESS 3)\n");

        int choice = scanner.nextInt();

        if (choice == 1) {
            char x = 'Y';
            while (x == 'Y' || x == 'y') {
                System.out.print("ENTER THE PASSWORD: ");
                String password = scanner.next();

                if (password.equalsIgnoreCase("1234")) {
                   Admin.admin();
                    
                    x = 'n';
                } else {
                    System.out.println("INCORRECT PASSWORD");
                }
            }
            mexit();
        } else if (choice == 2) {
            // Doctors D= new Doctors();
            Doctors.doctorProcess();
            mexit();
        } else if (choice == 3) {
            patientM();
            mexit();
        } else {
            System.out.println("Please select a valid option");
            menu();
        }
    }

    



    public static void mexit() {
        System.out.print("To see Hospital map press 1 ,To go to MAIN MENU press 2 , press 3 to exit: ");
        int response = scanner.nextInt();
        if (response==1) {
            displayMap(); 
        }
        else if(response==2){
            menu();
        }
        else {
            feedback();
            quit();
        }
    }    


    public static void feedback() {
        System.out.print("PLEASE RATE OUR PROGRAM IN 1 TO 5: ");
        int rating = scanner.nextInt();

        if (rating == 1) {
            System.out.println("Ouch! , it hurts (ಥ﹏ಥ), we will try our best to improve it.");
        } else if (rating == 2) {
            System.out.println("Expecting better next time ~(´•︵•`)~.");
        } else if (rating == 3) {
            System.out.println("We will try to meet your expectations next time ~(⌣_⌣”)~.");
        } else if (rating == 4) {
            System.out.println("We will try to make it 5 soon ~°(ᴖ◡ᴖ)°~.");
        } else if (rating == 5) {
            System.out.println("It was a great pleasure ~(⌐■_■)ノ♪♬.");
        } else {
            System.out.println("Please enter a valid rating.");
        }
    }

    public static void quit() {
        System.out.println("Thank you for using our hospital management system!");
        System.exit(0);
    }

}

