
package maxwell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.lang.IllegalArgumentException;

/**
 * AuctionAPI is a simple auction system that provides access to items (AuctionItem) and bids (Bid). 
 * 
 * 
 * @author Steven Vuong
 * 
 */
public class AuctionAPI {

    private static HashMap<String,AuctionItem> allItems = new HashMap<String,AuctionItem>();
	private String clientID;
	private String clientSecret;
	boolean debug=true;

	/**
	 * Constructor. Would have to implement a real identification system. For now it just accepts everything.
	 *
	 * @param clientID  	User's username
	 * @param clientSecret	User's password
	 * 						A user may or may not have access to an Auction item based on this authentication.
	 */
	
	public AuctionAPI(String clientID, String clientSecret){
												
		this.clientID=clientID;
		this.clientSecret=clientSecret;
	}
	
	/**
	 * Return an ArrayList of the names of items currently being auctioned.
	 * 
	 * @return Return an ArrayList of the names of items currently being auctioned.
	 *
	 */
	
	public ArrayList<String> getActiveItems(){
		
		ArrayList<String> activeItems = new ArrayList<String>();
		AuctionItem curItem = null;
		if(allItems.isEmpty())
			return activeItems;
		else{
			Iterator<AuctionItem> itemList = allItems.values().iterator(); //List of all items
			while(itemList.hasNext()){
				curItem = itemList.next();
				if(curItem.isActive())
					activeItems.add(curItem.getItemName());
			}
				
			return activeItems;
		}
			
		
	}
	
	/**
	 * Return an ArrayList of the names of items that have finished being auctioned.
	 * 
	 * @return Return an ArrayList of the names of items that have finished being auctioned.
	 *
	 */
	
	public ArrayList<String> getFinishedItems(){
		
		ArrayList<String> finishedItems = new ArrayList<String>();
		AuctionItem curItem = null;
		if(allItems.isEmpty())
			return finishedItems;
		else{
			Iterator<AuctionItem> itemList = allItems.values().iterator(); //List of all items
			while(itemList.hasNext()){
				curItem = itemList.next();
				if(curItem.isFinished())
					finishedItems.add(curItem.getItemName());
			}
				
			return finishedItems;
		}
			
		
	}
	
	/**
	 * Return an ArrayList of the names of items that are listed but is not being auctioned (includes finished items).
	 * 
	 * @return Return an ArrayList of the names of items that are listed but is not being auctioned (includes finished items).
	 *
	 */
	
	public ArrayList<String> getInactiveItems(){
		
		ArrayList<String> inactiveItems = new ArrayList<String>();
		AuctionItem curItem = null;
		if(allItems.isEmpty())
			return inactiveItems;
		else{
			Iterator<AuctionItem> itemList = allItems.values().iterator(); //List of all items
			while(itemList.hasNext()){
				curItem = itemList.next();
				if(!curItem.isActive())
					inactiveItems.add(curItem.getItemName());
			}
				
			return inactiveItems;
		}
			
		
	}
	
	/**
	 * Return an ArrayList of all items in the system.
	 * 
	 * @return Return an ArrayList of all items in the system.
	 *
	 */
	
	public ArrayList<String> getAllItems(){
		ArrayList<String> fullItems = new ArrayList<String>();
		AuctionItem curItem = null;
		if(allItems.isEmpty())
			return fullItems;
		else{
			Iterator<AuctionItem> itemList = allItems.values().iterator(); //List of all items
			while(itemList.hasNext()){
				curItem = itemList.next();
				fullItems.add(curItem.getItemName());
			}
				
			return fullItems;
		}
	}
	
	/**
	 * Adds an item to be auctioned. 
	 *
	 * @param itemName 		Unique item name for the auctioned item.
	 * @param reservedPrice The reserved price for the item.
	 * @return Returns true if the add was successful. Otherwise false.
	 */
	
	public boolean addItem(String itemName,double reservedPrice){
		if(allItems.containsKey(itemName)){
			System.out.println("Error: Item name is already exists");
			return false;
		}
		try{
			AuctionItem newItem = new AuctionItem(itemName,reservedPrice,clientID);
			allItems.put(itemName,newItem);
			if(debug)
				System.out.println("Added " + itemName);
			return true;
		}catch(IllegalArgumentException e){
			System.out.println("Error: price is negative");
			return false;
		}
	}
	
	/**
	 * Starts auction on an item. 
	 *
	 * @param itemName 		Item name to start the auction on
	 * @return Returns true if successful. Returns false if 
	 * 			item is already being auctioned, has finished auctioning, 
	 * 			does not exist, or the client does not have permission to start it.
	 */
	
	public boolean startAuction(String itemName){
		if(!allItems.containsKey(itemName)){
			System.out.println("Item name does not exist.");
			return false;
		}
		else if(!allItems.get(itemName).getOwner().equals(clientID)){
			System.out.println("Sorry. You do not have permission to start the auction on this item.");
			return false;
		} else{
			boolean auctionStart =  allItems.get(itemName).startAuction();
			if(auctionStart==true){
				if(debug)
					System.out.println("Auction on " + itemName + " started successfully!");
				return true;
			}
			else{
				if(debug)
					System.out.println("Unable to start auction on " + itemName);
				return false;
			}
				
		}
			
			
	}
	
	/**
	 * Stops auction on an item. 
	 *
	 * @param itemName 		Item name to stop the auction on
	 * @return Returns true if successful. Returns false if 
	 * 			item is not being auctioned, already finished auctioning, 
	 * 			does not exist, or the client does not have permission to start it.
	 */
	
	public boolean stopAuction(String itemName){
		if(!allItems.containsKey(itemName)){
			System.out.println("Item name does not exist.");
			return false;
		}
		else if(!allItems.get(itemName).getOwner().equals(clientID)){
			System.out.println("Sorry. You do not have permission to start the auction on this item.");
			return false;
		} else{
			boolean auctionStop =  allItems.get(itemName).stopAuction();
			if(auctionStop==true){
				if(debug)
					System.out.println("Auction on " + itemName + " stopped successfully!");
				return true;
			}
			else{
				if(debug)
					System.out.println("Unable to stop auction on " + itemName);
				return false;
			}
		}
			
			
	}
	
	/**
	 * Bid on an item. 
	 *
	 * @param itemName 		Item name to bid on
	 * @param bidAmount 	The amount of the bid
	 * @return Returns true if successful in bidding. 
	 */
	
	public boolean bidOnItem(String itemName,double bidAmount){
		if(!allItems.containsKey(itemName)){
			System.out.println("Item name does not exist.");
			return false;
		}
		else{ 																		//Item exists
				AuctionItem curItem = allItems.get(itemName);
			
				if(curItem.getOwner().equals(clientID)){
		
					System.out.println("You can't bid on your own item!");
					return false;
				}
				else
				{
					boolean returnStatus = curItem.addBid(new Bid(curItem,bidAmount,clientID));
					if(returnStatus==true){
						if(debug)
							System.out.println("Bid on " + itemName + " successful!");
						return true;
					}
					else{
						if(curItem.isActive())
							System.out.println("Bid amount needs to be higher than " + Double.toString(curItem.getLastBidAmount()));
						else
							System.out.println("Item is not currently up for bidding");
						return false;
					}
				}
		    
			}
		
	}
	
	/**
	 * Check whether an item is being auctioned.
	 *
	 * @param itemName 		Item name being checked
	 * @return Returns true if item is being auctioned. 
	 */
	
	public boolean isBeingAuctioned(String itemName){
		if(!allItems.containsKey(itemName))
			return false;
		else
			return allItems.get(itemName).isActive();
	}
	
	/**
	 * Check whether an item has finished auctioning.
	 *
	 * @param itemName 		Item name being checked
	 * @return Returns true if item has finished auctioning. 
	 */
	
	public boolean isFinishedAuctioning(String itemName){
		if(!allItems.containsKey(itemName))
			return false;
		else
			return allItems.get(itemName).isFinished();
	}
	
	/**
	 * Check whether an item successfully auctioned (reserved price met). 
	 *
	 * @param itemName 		Item name being checked
	 * @return Returns true if item's reserve price was met. 
	 */
	
	public boolean isAuctionSuccessful(String itemName){
		if(!allItems.containsKey(itemName))
			return false;
		else
			return allItems.get(itemName).isSuccessful();
	}
	
	/**
	 * Query latest action on the item. Prints the information about the status of the item
	 * including whether it is being auctioned, whether it has been sold, the sold amount,
	 * and who it was sold to.
	 *
	 * @param itemName 		Item name being checked
	 */
	
	public static void latestAction(String itemName){
		if(!allItems.containsKey(itemName))
			System.out.println("Item does not exist");
		else{
			
			AuctionItem curItem = allItems.get(itemName);
			
			System.out.println("Item Name: " + itemName);
			System.out.println("Seller: " + curItem.getOwner());
			
			if(curItem.isFinished()){
				System.out.println("Item has finished auctioning.");
				if(curItem.isSuccessful())
					System.out.println("The auction was a success! The item was sold.");
				else
					System.out.println("The auction was a failure...");
				
				System.out.println("The reserved price was: " + Double.toString(curItem.getReservedPrice()));
				System.out.println("The final bid amount was: " + Double.toString(curItem.getLastBidAmount()));
				System.out.println("Sold to: " + curItem.getLastBid().getId());
			}
			else{
				if(curItem.isActive()){
					System.out.println("The item is currently being auctioned.");
					
					System.out.println("Top bidder is: " + curItem.getLastBid().getId());
					System.out.println("The top bid amount is: " + Double.toString(curItem.getLastBidAmount()));
																										//Reserved price not shown
					
				}
				else{
					System.out.println("The item is not being auctioned at this time.");
																										//Show nothing
				}

			}
		}
	}
	
}
