/*Author: Lester Liau
 Student ID: 23SMD11178*/

import java.util.ArrayList;
import java.util.List;

public class Staff extends User {
    // Data Types
    private final double STAFF_DISCOUNT = 0.25;
    private int staffId;
    
    // Constructor
    public Staff(String name, String phoneNumber, String email, int userId, String password, int staffId) {
        super(name, phoneNumber, email, userId, password);
        this.staffId = staffId;
    }
    
    //Getters
    public double getStaffDiscount(){
    	return STAFF_DISCOUNT;
    }
    
    
    //Methods
    public boolean login(String password) {
        return this.password.equals(password);
    }
    
    public void registerMember(Member member, List<User> users) { 
        users.add(member);
    }
    
    public void viewReport() { 
        Payment.generateDetailedReport();
    }
}