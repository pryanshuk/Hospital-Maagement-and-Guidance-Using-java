package JDBCD;
// import java.util.Scanner;
import Pat.*;
import Adm.*;
// import Doc.*;


import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
// import java.sql.ResultSet;

public class JDBCD{
    static Connection con;
    static Statement stmt;
    static PreparedStatement pstmt;
    
    static final String DB_URL="jdbc:mysql://localhost:3306/hospitaljava";
    static final String USER="root";
    static final String PWD="9457";


// 1. Load and register Driver

static void  loadDriver(){
    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Driver loaded successfully");

    }
    catch(ClassNotFoundException e){
        
        System.out.println(e);
    }
}

// 2.create connection

static Connection createConnection(){
    Connection con=null;
    try{
        con=DriverManager.getConnection(DB_URL, USER, PWD);
        // System.out.println("connection successfull");
    }
    catch(SQLException e){
        System.out.println(e);
    }
    return con;
}

// 3.Create Statement
static Statement createStatement(){
    try{
        stmt=con.createStatement();}
    catch(SQLException e){
        System.out.println(e);
        
    }
    return stmt;
}
    // 8. Create Patients Table
static  void createPatientsTable() {
        String sqlquery = "CREATE TABLE IF NOT EXISTS patients (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), phone VARCHAR(20), address VARCHAR(255), id_proof VARCHAR(255))";
        try {
            int affectedRows = stmt.executeUpdate(sqlquery);
            System.out.println("Patients table created and " + affectedRows + " are affected ");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

// 9. Create Appointments Table
static void createAppointmentsTable() {
    String sqlquery = "CREATE TABLE IF NOT EXISTS appointments (id INT AUTO_INCREMENT PRIMARY KEY, doctor_id INT,doctorType varchar(100),patient_phone VARCHAR(20), patient_address VARCHAR(255), patient_id_proof VARCHAR(255))";
    try {
        int affectedRows = stmt.executeUpdate(sqlquery);
        System.out.println("Appointments table created and " + affectedRows + " are affected ");
    } catch (SQLException e) {
        System.out.println(e);
        System.out.println("line 83 in JDBCD");
    }
}

//  Insert Appointment Data
static public void insertAppointmentData(int doctorId, String doctorType,String patientPhone, String patientAddress, String patientIdProof) {
    String sqlquery = "INSERT INTO appointments (doctor_id, doctorType,patient_phone, patient_address, patient_id_proof) VALUES (?, ?, ?, ?,?)";
    try {
        pstmt = con.prepareStatement(sqlquery);
        pstmt.setInt(1, doctorId);
        pstmt.setString(2, doctorType);
        pstmt.setString(3, patientPhone);
        pstmt.setString(4, patientAddress);
        pstmt.setString(5, patientIdProof);
        int affectedRows = pstmt.executeUpdate();
        System.out.println(affectedRows + " rows inserted into Appointments table");
    } catch (SQLException e) {
        System.out.println(e);
        System.out.println("line 101 in JDBCD");
    }
}


    // 10. Insert Patient Data
    static public void insertPatientData(String name, String phone, String address, String id_proof) {
        String sqlquery = "INSERT INTO patients (name, phone, address, id_proof) VALUES (?, ?, ?, ?)";
        try {
            pstmt = con.prepareStatement(sqlquery);
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, address);
            pstmt.setString(4, id_proof);
            int affectedRows = pstmt.executeUpdate();
            System.out.println(affectedRows + " rows inserted into Patients table");
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("line 119 in JDBCD");
        }
    }

    


public static ResultSet findPatientByPhoneNumber(String phoneNumber) {
    String sqlquery = "SELECT * FROM patients WHERE phone = ?";
    try {
        pstmt = con.prepareStatement(sqlquery);
        pstmt.setString(1, phoneNumber);
        ResultSet rs = pstmt.executeQuery();

        if (rs!=null && rs.next()) {
            return rs;
        }
    } catch (SQLException e) {
        System.out.println(e);
        System.out.println("line 157 in JDBCD");
    }

    return null; // Patient not found
}


// 13. Extract Appointment Data
// static void extractAppointmentData() {
//     String sqlquery = "SELECT * FROM appointments";
//     try {
//         ResultSet rs = stmt.executeQuery(sqlquery);
//         System.out.println("Appointments data:");

//         while (rs.next()) {
//             System.out.println("ID: " + rs.getInt("id") +
//                     ", Doctor ID: " + rs.getInt("doctor_id") +
//                     ", Specialist: " + rs.getString("doctorType") +
//                     ", Patient Address: " + rs.getString("patient_address") +
//                     ", Patient ID Proof: " + rs.getString("patient_id_proof"));
//         }
//     } catch (SQLException e) {
//         System.out.println(e);
//         System.out.println("line 179 in JDBCD");
//     }
// }
// We are using Overloading with function checkExistingAppointment

static public boolean checkExistingAppointment(String patientPhone, String doctorType) {
    String sqlquery = "SELECT * FROM appointments WHERE patient_phone = ? AND doctorType = ?";
    try {
        pstmt = con.prepareStatement(sqlquery);
        pstmt.setString(1, patientPhone);
        pstmt.setString(2, doctorType);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    } catch (SQLException e) {
        System.out.println(e);
        System.out.println("line 194 in JDBCD");
        return false;
    }
}

public static ResultSet checkExistingAppointment(String phoneNumber) {
    ResultSet rs = null;
        String sqlquery = "SELECT * FROM appointments WHERE patient_phone = ?";
        
        try {
            pstmt = con.prepareStatement(sqlquery);
            pstmt.setString(1, phoneNumber);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("line 209 in JDBCD");
        }

        return rs;
}
public static ResultSet checkExistingAppointment(int doctorId) {
    ResultSet rs = null;
    String sqlquery = "SELECT * FROM appointments WHERE doctor_id = ?";
    
    try {
        pstmt = con.prepareStatement(sqlquery);
        pstmt.setInt(1, doctorId);
        rs = pstmt.executeQuery();
    } catch (SQLException e) {
        System.out.println(e);
        System.out.println("line 224 in JDBCD");
    }

    return rs;
}





// 7,8.Update and delete Data




public static void JDBC() {

    loadDriver();
    con = createConnection();
    stmt=createStatement();
    Admin admin = new Admin(con, stmt);
    createPatientsTable();
    createAppointmentsTable();
    Patients.menu();
}


}