/*Author: Douglas Lai
 Student ID: 23SMD00408*/

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Item> items = new ArrayList<>();
    private List<Integer> quantity = new ArrayList<>();
    private User user;
    private static Order currentOrder = null; 
    private static User loggedInUser = null; 
    private static Payment payment = new Payment();

    // Constructor
    public Order(User user) {
        if (user != null) {
            loggedInUser = user;
            currentOrder = this;
        }
        this.user = user;
    }
    
    //Getters
    public List<Item> getItems(){
        return items;
    }
    
    public List<Integer> getQuantity(){
    	return quantity;
    }
    
    public User getUser() {
    	return this.user;
	}
	
	public static Order getCurrentOrder(){
		return currentOrder;
	}

        
    
    // Methods
    public void addItem(Item item, int qty) {
        items.add(item);
        quantity.add(qty); 
    }
    
    public void addItemToOrder(Item item, int quantity) {
        this.items.add(item);
        this.quantity.add(quantity); 
    }
    
    public void modifyOrder(int itemIndex, int newQty) {
        if (itemIndex >= 0 && itemIndex < this.items.size()) {
            this.quantity.set(itemIndex, newQty);
        } else {
            System.out.println("Invalid item index.");
        }
    }
    
    public void removeItem(int index) {
        items.remove(index);
        quantity.remove(index);
    }
    
	public static Order startNewOrder(User user) {
	    if (currentOrder == null || loggedInUser.getUserId() != user.getUserId()) {
	        loggedInUser = user;
	        currentOrder = new Order(user);
	    } else {
	        System.out.println("There is already an active order for the logged-in user.");
	        return currentOrder;
	    }
	    return currentOrder;
	}

    
    public void displayOrderSummary() {
        System.out.println("Order Summary:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i).getItemName() + " x " + quantity.get(i) + ": " + 
            (items.get(i).getItemPrice() * quantity.get(i)));
        }
    }
    
    public double calculateTotalPrice() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).getItemPrice() * quantity.get(i);
        }
        return total;
    }
    
	public double applyDiscount(double discountRate) {
	    double total = calculateTotalPrice();
	    double discountAmount = total * discountRate;
	    
	    return discountAmount;
	}
    
	public double applySST(double priceAfterDiscount) {
	    final double SST_RATE = 0.06;
	    double sstAmount = priceAfterDiscount * SST_RATE;
	    
	    return sstAmount;
	}
	
	private double applyDiscountsAndTaxes() {
        double discountRate = 0;
        if (loggedInUser instanceof Member) {
            discountRate = ((Member) loggedInUser).getMemberDiscount();
        } else if (loggedInUser instanceof Staff) {
            discountRate = ((Staff) loggedInUser).getStaffDiscount();
        }
        double total = calculateTotalPrice();
        double discountAmount = total * discountRate;
        double priceAfterDiscount = total - discountAmount;
        final double SST_RATE = 0.06;
        double sstAmount = priceAfterDiscount * SST_RATE;
        return priceAfterDiscount + sstAmount;
    }
    
	public double calculateFinalPrice(double discountRate) {
	    double total = calculateTotalPrice();
	    double discountAmount = applyDiscount(discountRate);
	    double priceAfterDiscount = total - discountAmount;
	    double sstAmount = applySST(priceAfterDiscount);
	    double finalTotal = priceAfterDiscount + sstAmount;
	    
	    return finalTotal;
	}
	
	public void checkoutOrder() {
	    if (currentOrder != null) {
	        double finalPrice = applyDiscountsAndTaxes();
	        currentOrder.displayOrderSummary();
	        System.out.printf("Final price after discounts and taxes: RM%.2f\n", finalPrice);
	        Payment.processPayment(finalPrice);
	        System.out.println("Thank you for your purchase!");
	        currentOrder = null; 
	        loggedInUser = null;
	    } else {
	        System.out.println("No order to checkout!");
	    }
	}

    


}