/*Author: Edwin Chin
 Student ID: 23SMD01778 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Payment {
    //Data Type
    private static int totalPaymentsMade = 0;
    private static double amountPaid = 0.0;
    private static double change = 0.0;
    private static List<Order> orders = new ArrayList<>();

    //Setters
    public static void addPaymentsMade(int payment) {
        totalPaymentsMade += payment;
    }

    //Getter
    public static int getPayment() {
        return totalPaymentsMade;
    }

    //Methods
	public static void processPayment(double totalPrice) {
	    Scanner input = new Scanner(System.in);
	    boolean isPaymentSuccessful = false;
	
	    while (!isPaymentSuccessful) {
	        System.out.print("Enter payment amount: RM");
	        try {
	            amountPaid = input.nextDouble();
	            if (amountPaid < totalPrice) {
	                System.out.printf("Insufficient amount. Your bill is RM%.2f.\n", totalPrice);
	            } else {
	                isPaymentSuccessful = true;
	                change = amountPaid - totalPrice;
	                System.out.printf("Payment successful. Your change is RM%.2f.\n", change);
	                addPaymentsMade(1); 
	                Payment.addOrder(Order.getCurrentOrder()); 
	            }
	        } catch (java.util.InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a valid amount.");
	            input.next();
	        }
	    }
	}

    public static void addOrder(Order order) {
        orders.add(order);
    }

    public static void generateDetailedReport() {
        if (orders.isEmpty()) {
            System.out.println("No transactions have been recorded.");
            return;
        }

        for (Order order : orders) {
            System.out.println("Order details:");
            
            double discountRate;
            if (order.getUser() instanceof Member) {
                discountRate = ((Member) order.getUser()).getMemberDiscount();
            } else if (order.getUser() instanceof Staff) {
                discountRate = ((Staff) order.getUser()).getStaffDiscount();
            } else {
                discountRate = 0;
            }

            double total = order.calculateTotalPrice();
            double discountAmount = order.applyDiscount(discountRate);
            double totalPriceAfterDiscount = total - discountAmount;
            double sstAmount = order.applySST(totalPriceAfterDiscount);
            double finalTotal = order.calculateFinalPrice(discountRate);

            System.out.println("User: " + order.getUser().getName());
            List<Item> items = order.getItems();
            List<Integer> quantities = order.getQuantity();
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                int qty = quantities.get(i);
                System.out.println(item.getItemName() + " x " + qty + " = " + (item.getItemPrice() * qty));
            }
            System.out.println("");
            System.out.println("Total customer: " + getPayment());
            System.out.println("Total before discounts: " + String.format("%.2f", total));
            System.out.println("Discount: " + String.format("%.2f", discountAmount));
            System.out.println("SST: " + String.format("%.2f", sstAmount));
            System.out.println("Total after discounts and SST: " + String.format("%.2f", finalTotal));
            System.out.println("--------------");
        }
    }
}
