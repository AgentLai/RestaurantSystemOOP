/*Author: Queenny
 Student ID: */

import java.util.ArrayList;
import java.util.List;

public class Item {
	//Data Types
	private String itemName;
	private double itemPrice;
	private int itemCode;
	private static Order orders = new Order();
	
    //Constructor
    public Item(String name, double price, int code){
    	this.itemName = name;
    	this.itemPrice = price;
    	this.itemCode = code;
    }
    
    //Getters
    public String getItemName(){
    	return itemName;
    }
    
    public double getItemPrice(){
    	return itemPrice;
    }
    
    public int getItemCode(){
    	return itemCode;
    }
    
    
}