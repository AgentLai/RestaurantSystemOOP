/*Author: Douglas Lai, Lester Liau, Liew Wei Wei, Edwin Chin, Queenny
 Student ID: 23SMD00408, 23SMD11178, 23SMD03391, 23SMD01778 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.lang.InterruptedException;
import java.util.InputMismatchException;

public class POSSystem {

    //Data types
    private User currentUser;
    private Order currentOrder;
    private List<User> users = new ArrayList<>();
    private List<Item> menuItems = new ArrayList<>();
    private Scanner input = new Scanner(System.in);

    public POSSystem(){
        initializeMenuItems();
        createDefaultStaffAccount();
    }

    //Methods
    public static void main(String[] args){
    	POSSystem posSystem = new POSSystem();
    	posSystem.mainMenu();
    }
    
    private void createDefaultStaffAccount() {
        Staff defaultStaff = new Staff("Default Staff", "0000", "staff@borenos.com", 9999, "Borenos2015", 9999);
        users.add(defaultStaff);
    }    

    public void setCurrentUser(User user){
        this.currentUser = user;
        this.currentOrder = Order.startNewOrder(user);
    }

    private void handleItemSelection(int itemCode) {
        Item selectedItem = menuItems.stream()
            .filter(item -> item.getItemCode() == itemCode)
            .findFirst()
            .orElse(null);

        if (selectedItem != null) {
            System.out.print("Enter quantity: ");
            int quantity = input.nextInt();
            input.nextLine(); 
            currentOrder.addItemToOrder(selectedItem, quantity);
            System.out.println(quantity + " x " + selectedItem.getItemName() + " added to order.");
            pauseScreen(1000);
        } else {
            System.out.println("Invalid item code. Please try again.");
            pauseScreen(1000);
        }
    }

    private void handleCartEditing() {
        if (currentOrder != null) {
            currentOrder.displayOrderSummary();
            System.out.println("Enter the item number you wish to remove or 0 to return to ordering:");
            int itemNumber = input.nextInt();
            if (itemNumber == 0) {
                return;
            }
            currentOrder.removeItem(itemNumber - 1);
            System.out.println("Item removed.");
        } else {
            System.out.println("There is no current order to edit.");
	    }
	}
        
    
    private void initializeMenuItems() {
        menuItems.add(new Item("One piece chicken rice", 14.0, 1));
        menuItems.add(new Item("Two piece chicken rice", 18.0, 2));
        menuItems.add(new Item("Fish n Chips", 19.0, 3));
        menuItems.add(new Item("Half-Spring chicken meal", 24.0, 4));
        menuItems.add(new Item("Full-Spring chicken meal", 45.0, 5));
    }

	public List<Item> getMenuItems() {
	    return menuItems;
	}
	
	public void mainMenu() {
	    while (true) {
	        clearScreen();
	        System.out.println("Welcome to Borenos POS System");
	        System.out.println("1. Login");
	        System.out.println("2. Start Ordering");
	        System.out.println("3. View Reports");
	        System.out.println("4. Register New Member");
	        System.out.println("0. Exit");
	
	        System.out.print("Please select an option: ");
	        try {
	            int option = input.nextInt();
	            input.nextLine();
	
	            clearScreen();
	
	            switch (option) {
	                case 1:
	                    loginUser();
	                    break;
	                case 2:
	                    startOrdering();
	                    break;
	                case 3:
	                    viewReports();
	                    break;
	                case 4:
	                    registerNewMember();
	                    break;
	                case 0:
	                    System.out.println("Thank you for using Borenos POS System!");
	                    exitSystem();
	                    return; 
	                default:
	                    System.out.println("Invalid option, please try again.");
	                    pauseScreen(1000);
	            }
	        } catch (InputMismatchException e) {
	            input.nextLine();
	            System.out.println("Invalid input. Please enter a number.");
	        }
	    }
	}
	
	public void loginUser() {
	    clearScreen();
	    System.out.print("Enter User ID: ");
	    int userId = input.nextInt();
	    input.nextLine();
	    System.out.print("Enter Password: ");
	    String password = input.nextLine();
	
	    for (User user : users) {
	        if (user.getUserId() == userId && user.login(password)) {
	            System.out.println("Login successful, you will be redirected in three seconds.");
	            setCurrentUser(user);
	            pauseScreen(3000);
	            clearScreen();
	            return;
	        }
	    }
	    System.out.println("Login failed. You will be redirected in three seconds.");
	    pauseScreen(3000);
	    clearScreen();
	}
	
	private void registerNewMember() {
	    if (!(currentUser instanceof Staff)) {
	        System.out.println("Only staffs can register new members.");
	        pauseScreen(1000);
	        return;
	    }
	
	    System.out.print("Enter new member's name: ");
	    input.nextLine();
	    String name = input.nextLine();
	
	    System.out.print("Enter new member's phone number: ");
	    String phoneNumber = input.nextLine();
	
	    System.out.print("Enter new member's email: ");
	    String email = input.nextLine();
	
	    System.out.print("Enter new member's ID: ");
	    int userId = input.nextInt();
	
	    input.nextLine();
	    System.out.print("Enter new member's password: ");
	    String password = input.nextLine();
	
	    Member newMember = new Member(name, phoneNumber, email, userId, password);
	    users.add(newMember);
	    System.out.println("New member registered successfully!");
	    pauseScreen(2000);
	}
	
	    
    
	public void startOrdering() {
	    if (currentUser == null) {
	        System.out.println("Please login to start ordering.");
	        return;
	    }
	
	    this.currentOrder = Order.startNewOrder(currentUser);
	    if (this.currentOrder == null) {
	        System.out.println("Could not start a new order. Please complete any active orders first.");
	        return;
	    }
	
	    boolean inCart = false;
	
	    while (true) {
	        clearScreen();
	        if (!inCart) {
	            displayAvailableMenuItems();
	            System.out.println("");
	            System.out.println("C. View Cart");
	            System.out.println("0. Cancel Order");
	            System.out.print("Please select an item or option: ");
	        } else {
	            currentOrder.displayOrderSummary();
	            System.out.println("\nEdit items or proceed to checkout.");
	            System.out.println("1. Proceed to checkout");
	            System.out.println("2. Add more items");
	            System.out.println("0. Cancel Order");
	            System.out.print("Choose an option: ");
	        }
	
	        String action = input.nextLine().trim().toUpperCase();
	
	        if ("0".equals(action)) {
	            System.out.println("Order cancelled.");
	            currentOrder = null; 
	            pauseScreen(1000);
	            return;
	        } else if ("C".equals(action) && !inCart) {
	            inCart = true;
	            currentOrder.displayOrderSummary();
	        } else if ("1".equals(action) && inCart) {
	        	clearScreen();
	            currentOrder.checkoutOrder();
	            pauseScreen(2000); 
	            return;
	        } else if ("2".equals(action) && inCart) {
	            inCart = false;
	        } else if (!inCart) {
	            try {
	                int itemCode = Integer.parseInt(action);
	                handleItemSelection(itemCode);
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid input. Please enter a number.");
	                pauseScreen(1000);
	            }
	        }
	    }
	}
		
		private void displayAvailableMenuItems() {
		    for (Item item : menuItems) {
		        System.out.println(item.getItemCode() + ". " + item.getItemName() + " (RM" + item.getItemPrice() + ")");
		    }
		}
		
		public void viewReports() {
		    if (currentUser instanceof Staff) {
		        clearScreen();
		        Payment.generateDetailedReport();
		        System.out.println("You will return to main menu in 15 seconds.");
		        pauseScreen(15000);
		    }
		}
		
		public static void clearScreen() {
		    try {
		        if (System.getProperty("os.name").contains("Windows")) {
		            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		        } else {
		            System.out.print("\033[H\033[2J");
		            System.out.flush();
		        }
		    } catch (IOException | InterruptedException ex) {
		        ex.printStackTrace();
		    }
		}
		
		public static void pauseScreen(int time){
		    try {
		        Thread.sleep(time);
		    } catch (InterruptedException e) {
		        Thread.currentThread().interrupt();
		        System.out.println("Failed to pause the thread.");
		    }
		}

    
    public void exitSystem(){
    	System.exit(0);
    }
    
    
}