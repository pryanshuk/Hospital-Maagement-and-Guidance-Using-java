package Staff;
import java.sql.Connection;
import java.sql.Statement;
import java.util.*;

import common.AbstractDatabase;

public class Staff extends AbstractDatabase{
    public Staff(Connection con, Statement stmt) {
        super(con, stmt);
    }
    String name, department, staffid, deptid;
    static Scanner sc = new Scanner(System.in);
    int brrcount=0;
    String[][] brr;

    public void extractData() {
        brr = new String[100][4];

        System.out.println("Here are the details of currently available Staff members");
        System.out.println("\n");

	newstaff("Rajesh Kumar", "Cardiology", "101", "501");
	newstaff("Priya Sharma", "Pediatrics", "102", "502");
	// newstaff("Amit Patel", "Emergency Medicine", "103", "503");
	// newstaff("Sneha Singh", "Surgery", "104", "504");
	
   
        for (int row = 0; row < brrcount; row++) {
            for (int col = 0; col < brr[row].length; col++) {
                if (col == 0) {
                    System.out.println("Name: " + brr[row][col] + " \t");
                }

                if (col == 1) {
                    System.out.println("Department: " + brr[row][col] + " \t");
                }

                if (col == 2) {
                    System.out.println("StaffId: " + brr[row][col] + " \t");
                }

                if (col == 3) {
                    System.out.println("DeptId: " + brr[row][col] + " \t");
                }
            }
            System.out.println(" ");
        }
    }

   
    public void newstaff(String name, String department, String staffid, String deptid) {
        brr[brrcount] = new String[]{name, department, staffid, deptid};
        brrcount++;
    }

    // public void print() {
    //     for (int row = 0; row < brrcount; row++) {
    //         for (int col = 0; col < brr[row].length; col++) {
    //             if (col == 0) {
    //                 System.out.println("Name: " + brr[row][col] + " \t");
    //             }

    //             if (col == 1) {
    //                 System.out.println("Department: " + brr[row][col] + " \t");
    //             }

    //             if (col == 2) {
    //                 System.out.println("StaffId: " + brr[row][col] + " \t");
    //             }

    //             if (col == 3) {
    //                 System.out.println("DeptId: " + brr[row][col] + " \t");
    //             }
    //         }
    //         System.out.println(" ");
    //     }
    // }



 public void getdetstaff(){
        String C="Y";
        while (C.equalsIgnoreCase("Y")) {
             System.out.println("Enter the name of the new Staff member: ");
        String w = sc.nextLine();
        System.out.println("Enter the department of the new Staff member: ");
        String xx = sc.nextLine();
        System.out.println("Enter the staffid of the new Staff member: ");
        String y = sc.nextLine();
        System.out.println("Enter the deptid of the new Staff member: ");
        String z = sc.nextLine();
        newstaff(w,xx,y,z);
        System.out.println("Do you add more staff members (Y or N)");
        C=sc.next();
        }
       

}

}






     




