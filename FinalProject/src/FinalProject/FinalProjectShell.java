package FinalProject;
import java.util.InputMismatchException;
import java.nio.file.Paths;
import java.util.Scanner;

public class FinalProjectShell 
{
	private Scanner input = new Scanner(System.in);
	private Item[] items = new Item[100];
	boolean exit = false;
	boolean found = false;
	String id = " ";
	String name = " ";
	double price = 0.00;
	int quantity = 0;
	
	public FinalProjectShell()
	{
		try(Scanner file = new Scanner(Paths.get("./textfile/input.txt")))
		{
			int count = 0;
			while(file.hasNext())
			{
				items[count++] = new Item(file.next(), file.next(), file.nextDouble(), file.nextInt());	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{	
		
		FinalProjectShell foo = new FinalProjectShell();
		foo.mainMenu();
	}
	
	public void mainMenu() 
	{
		
		do
		{
		// display menu and ask for user selection (validate)
		System.out.print("1) Admin\n" + 
				"2) User\n" + 
				"3) Exit\n" + 
				"");
		
		int option = input.nextInt();
		
			switch (option) 
			{	
				case 1:
					
					System.out.println("Admin Menu:");
					
					//username and password protect for admin
					loginVarified();
				
					break;
			
				case 2:
				
					System.out.println("User Menu:");
					
					// entering user menu
					userMenu();
				
					break;
			
				case 3:
					
					// confirm they want to exit and then exit
					System.out.println("Are you sure you want to exit?");
				
					System.out.println("Enter -1 for yes or 0 for no.");		

					int decision = input.nextInt();
						
					if (decision == 0)
					{
						System.out.println("What would you like to do next?");
					}
					else 
					{
						exit = true;
						System.out.println("Good bye.");
					}		
				}
		} while (!exit);
	}
	
	public void adminMenu() {
		
		// display menu and ask for user selection (validate)
		System.out.print("1) Add a new item\n" + 
				"2) Search and update item\n" + 
				"3) Search and delete item\n" + 
				"");
		int option = input.nextInt();
		
		int found = -1;
		
		switch (option) 
		{
		
			case 1:
				
				//admin adding an item 
				addItem();
				
				break;
				
			case 2:
				
				//admin searching for an item and then updating it
				searchForItem();
				updateItem(found);
				break;
				
				
			case 3:
				
				//admin searching for an item and deleting it
				searchForItem();
				deleteItem(found);
				
				break;
				
			default:return;
				
		}
	}


	public void userMenu() {
		// display menu and ask for user selection (validate)
		System.out.print("1) Show items\n" + 
				"2) Purchase items\n" + 
				"");
		int option = input.nextInt();

		int found = -1;
		
		switch (option) 
		{
		
			case 1:
				
				//displaying all the items in the item array
				displayAllItems();
				
				break;
				
			case 2:
				
				//search for item, purchase, then update item array quantity 
				searchForItem();
				purchaseItem(found);
				
				break;
				
			default:return;
			
		}	
	}
	
	private void displayAllItems() 
	{
		System.out.println("Product Information");
		System.out.println("--------------------");
		
		// loop through array
		for (int i = 0; i < items.length; i++)
		{
			// if this is a valid object
			if (items[i] != null)
			{
				// display all info about object. 
				System.out.println("Name: " + items[i].getProductName());
				System.out.println("ID: " + items[i].getProductID());
				System.out.println("Price: " + items[i].getProductPrice());
				System.out.println("Quantity: " + items[i].getProductQuantity() + "\n");				
			}
		}	
	}
	
	public int searchForItem() 
	{ 
		String searchText;
		
		// get a string to search from the user 
		System.out.println("Enter the product name.");
		searchText = input.nextLine().toLowerCase(); 
		
		// loop through the array looking for items which contain the search text 
		boolean[] matches = new boolean[100];
		
		boolean found = false; 
		
		for (int i = 0; i < items.length; i++) 
		{ 

			if (items[i] != null) 
			{ 
				// if this product has a match 
				if (items[i].getProductName().toLowerCase().contains(searchText)) 
				{ 
					matches[i] = true; 
					found = true; 
				} 
			} 
		} 

		if (!found) 
		{ 
			System.out.println("Item not found."); 
			return -1; 
		} 
		
		// Displays the titles of all the items that matched. 
		for (int i = 0; i < items.length; i++) 
		{ 
			if (matches[i]) 
			{ 
				System.out.println(i + ". " + items[i].getProductName()); 
			} 
		} 

		do 
		{ 
			try 
			{ 
				// User chooses one of the titles. 
				System.out.println("\nEnter the number next to the item you're looking for: "); 
				int selectedItem = input.nextInt(); 
				
				System.out.println(items[selectedItem]); 
				return selectedItem; 		
			} 
		
			catch (InputMismatchException e) 
			{ 
				System.out.println("Enter a number next time."); 
				input.next(); 
			} 
		} while (true); 
	}
	
	private void deleteItem(int index) 
	{
		//deleting the products information
		items[index] = null;
		
	}
	
	private void updateItem(int index) 
	{
		//getting updated information from admin
		System.out.println("\nEnter the product's name."); 
		name = input.nextLine(); 
		
		input.hasNextLine();
		
		System.out.println("Enter the product's ID."); 
		id = input.nextLine(); 
		
		input.hasNextLine();
		
		System.out.println("\nEnter the product's price."); 
		price = input.nextDouble(); 
		
		input.hasNextLine();
		
		System.out.println("\nEnter the product's quantity."); 
		quantity = input.nextInt(); 
		
		input.hasNextLine();
		
		// calling the set methods
		items[index].setProductName(name);
		items[index].setProductID(id);
		items[index].setProductPrice(price);
		items[index].setProductQuantity(quantity);	
	}
	
	private void addItem() 
	{
		// get name of product 
		System.out.println("Enter the name of the new product.");			
		name = input.nextLine();
		
		System.out.println("Enter the ID of the new product.");			
		id = input.nextLine();
		
		// get price of product
		System.out.println("Enter the product's price in dollars.");			
		price = input.nextDouble();
		
		//get the product quantity
		System.out.println("Enter the quantity of the product in whole numbers.");			
		quantity = input.nextInt();
		
		// set found to false// loop through array
		for (int i = 0; i < items.length; i++)
		{
			// if this is an empty spot
			if (items[i] == null)
			{
				// create new product object and assign to current array element
				items[i] = new Item(id, name, price, quantity);
				
				// set found to true
				found = true;		
				
				// break out of loop
				break;
			}
		}
		
		// if not found, give error message
		if (found = false) 
		{
			System.out.println("Sorry, there is no more space in memory.");
		}
		
	}
	
	private void purchaseItem(int index) 
	{
		final double SALESTAX = .07;
		double quantityPrice;
		double totalPrice;
		
		//asking for the quantity of the item
		System.out.println("\nEnter the quantity you want to purchase."); 
		quantity = input.nextInt(); 
		
		input.hasNextLine();
		
		//checking to make sure that quantity is in stock
		if(quantity > items[index].getProductQuantity())
		{
			System.out.println("We do not have that much in stock.");
			System.out.println("Currently we have " + items[index].getProductQuantity() + ".");
			System.out.println("Please enter the quantity you want.");
			
			quantity = input.nextInt(); 
			
			input.hasNextLine();
		}
		
		//calculating the prices
		quantityPrice = items[index].getProductPrice() * quantity;
		
		totalPrice = quantityPrice + (quantityPrice * SALESTAX);
		
		//Telling the user the total cost
		System.out.print("The total comes to " + totalPrice + ".");
		
		// calling the set methods
		items[index].setProductQuantity(items[index].getProductQuantity() - quantity);	
	}
	
	private void loginVarified()
	{
		// verify the admin login
		final String NAME = "name"; 
		String userName;
		final String PASSWORD = "password";
		String userPassword;
		
		System.out.println("You must enter a username and password.");
	
		System.out.println("Username: ");
		userName = input.nextLine();
		
		input.nextLine();
	
		System.out.println("Password: ");
		userPassword = input.nextLine();
			
	
		// if login correct go to the admin menu
		if (NAME.equals(userName) || PASSWORD.equals(userPassword))
		{
			adminMenu();
		}
		else
		{
			System.out.println("Invalid Username or Password. Please try again.");
		}	
	}
	
}

class Item 
{

	private String id;
	private String name;
	private double price;
	private int quantity;
	
	//add constructors
	public Item(String productID, String productName, double productPrice, int productQuantity) 
	{
		setProductID(productID);
		setProductName(productName);
		setProductPrice(productPrice);
		setProductQuantity(productQuantity);
	}
	
	
	//add get and set methods
	public void setProductID(String productID) 
	{
		id = productID;
	}
	
	public void setProductName(String productName) 
	{
		name = productName;
	}
	
	public void setProductPrice(double productPrice) 
	{
		price = productPrice;
	}
	
	public void setProductQuantity(int productQuantity) 
	{
		quantity = productQuantity;
	}
	
	public String getProductID() 
	{
		return id;
	}
	
	public String getProductName() 
	{
		return name;
	}
	
	public double getProductPrice() 
	{
		return price;
	}
	
	public int getProductQuantity() 
	{
		return quantity;
	}
	
}

