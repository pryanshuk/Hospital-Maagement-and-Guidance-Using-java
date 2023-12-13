import java.util.Scanner;
import Pat.*;
import Adm.*;
import Doc.*;
import JDBCD.*;


public class MainB {

    public static void main(String[] args) {
        System.out.println("-------------\"WELCOME TO OUR VIRTUAL PSP HOSPITAL\"--------------");
        String hospital = 
                "                       |----HOSPITAL---|\n" +
                "                       I_I_I_I_I_I_I_I_I\n" +
                "                  +    |---------------|    +\n" +
                "                |---|  ||-| |-| |-| |-||  |---|\n" +
                "      _________|-----|_|---------------|_|-----|_________\n" +
                "      I_I_I_I_I_I_I_I|I_I_I_I_I_I_I_I_I_I|I_I_I_I_I_I_I_|\n" +
                "      |-------|------|-------------------|------|-------|\n" +
                "      ||-| |-||  |-| ||-| |-| |-| |-| |-|| |-|  ||-| |-||\n" +
                "    ((|-------|------|-------------------|------|-------|))\n" +
                "    ()|  |_|  |  |_| |::::: ------- :::::| |_|  |  |_|  |()\n" +
                "    ))|  |_|  |  |_| | |_| |_.-\"-.| |_| | |_|  |  |_|  |((\n" +
                "    ()|-------|------| |_| | | | | | |_| |------|-------() \n" +
                "@@@@@@@@@@@@@@@@@|-----|_|_|_|_|-----|@@@@@@@@@@@@@@@@@\n" +
                "PSP             @@@@/=============\\@@@@";
    
            System.out.println(hospital);
        // Doctors.doctorfile();
        JDBCD.JDBC();
        
    }
}
