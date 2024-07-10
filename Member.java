/*Author: Liew Wei Wei
 Student ID: 23SMD03391*/

import java.util.ArrayList;
import java.util.List;

public class Member extends User {
    // Data Types
    private final double MEMBER_DISCOUNT = 0.1;
    private int memberId;
    
    // Constructor
    public Member(String name, String phoneNumber, String email, int userId, String password) {
        super(name, phoneNumber, email, userId, password);
        this.memberId = memberId;
    }

	//Getter
	public double getMemberDiscount(){
		return MEMBER_DISCOUNT;
	}
	
    public boolean login(String password) {
        return this.password.equals(password);
    }
}