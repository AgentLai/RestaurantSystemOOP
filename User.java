/*Author: Liew Wei Wei
 Student ID: 23SMD03391*/

import java.util.ArrayList;
import java.util.List;

public abstract class User {
	//Data types
	protected String name;
	protected String phoneNumber;
    protected String email;
    protected int userId;
    protected String password;
    
    public User(String name, String phone, String email, int id, String password){
    	this.name = name;
    	this.phoneNumber = phone;
    	this.email = email;
    	this.userId = id;
    	this.password = password;
    }
    
     public String getName() {
        return this.name;
    }
    
     public int getUserId() {
        return this.userId;
    }
    
    public abstract boolean login(String password);
}