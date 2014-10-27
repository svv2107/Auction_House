
package maxwell;

import java.util.ArrayList;
import java.lang.IllegalArgumentException;

/**
 * AuctionItem stores information about an auction item, including a list 
 * of the history of bids and whether the item is currently in bidding or not.
 * 
 * 
 * @author Steven Vuong
 *
 */
public class AuctionItem {

	private double reservedPrice;
	private ArrayList<Bid> bidHistory = new ArrayList<Bid>();
	private String itemName; 							//Unique according to the specifications
	private boolean isBeingAuctioned = false;
	private boolean hasBeenAuctioned = false;
	private Bid lastBid = null;
	private double curBidPrice = 0.00;
	private String owner;
	
	/**
	 * Constructor.
	 *
	 * @param uniqueName A unique name to identify the item being auctioned
	 * @param rsPrice The reserved price of the item
	 * @param ownerID The id of the owner
	 * @throws IllegalArgumentException if the price is negative
	 */
	
	public AuctionItem(String uniqueName, double rsPrice, String ownerID){
		itemName = uniqueName;
		owner=ownerID;
		if(rsPrice>=0)
			reservedPrice=rsPrice;
		else
			throw new IllegalArgumentException();
				
	}
	
	/**
	 * Get the name of the owner
	 * 
	 * @return Returns the id of the owner
	 */
	
	public String getOwner(){
		return owner;
	}
	
	/**
	 * Change the reserved price. Returns false if setting was unsuccessful.
	 *
	 * @param newReservedPrice The new reserved price
	 * @return Returns true if the change was successful. Otherwise false.
	 */
	
	public boolean setReservedPrice(double newReservedPrice){
		if(newReservedPrice>=0){
			reservedPrice=newReservedPrice;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Start the auction on the item. Returns false if item has already been auctioned or is already being auctioned.
	 * @return Returns true if the auction was started by the function. Otherwise false.
	 */
	
	public boolean startAuction(){
		if((!isBeingAuctioned)&&(!hasBeenAuctioned)){
			isBeingAuctioned=true;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Stop the auction on the item. Returns false if item is not being auctioned.
	 * @return Returns true if the auction was stopped by the function. Otherwise false.
	 */
	
	public boolean stopAuction(){
		if(isBeingAuctioned){
			isBeingAuctioned=false;
			hasBeenAuctioned=true;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Check if the item is being auctioned.
	 * @return Returns true if the item is currently up for auction.
	 */
	
	public boolean isActive(){
		return isBeingAuctioned;
	}
	
	/**
	 * Check if the item has been auctioned.
	 * @return Returns true if the item has finished auction.
	 */
	
	public boolean isFinished(){
		return hasBeenAuctioned;
	}
	
	/**
	 * 
	 * @return Returns the name of the item.
	 */
	
	public String getItemName(){
		return itemName;
	}
	
	/**
	 * 
	 * @return Returns the reserved price of the item.
	 */
	
	public double getReservedPrice(){
		return reservedPrice;
	}
	
	/**
	 * Adds a new bid for the item.
	 *
	 * @param newBid a new Bid item to be added.
	 * @return Returns true if the new bid was added successfully.
	 */
	
	public boolean addBid(Bid newBid){
		
		if(isBeingAuctioned&&(newBid.getAmount()>curBidPrice)){ 						//Bid must be greater than the starting $0
			
				bidHistory.add(newBid);
				lastBid=newBid;
				curBidPrice=newBid.getAmount();
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Returns the last bid if it exists. Otherwise returns null (default value).
	 * @return The Bid item of the last bid.
	 */
	
	public Bid getLastBid(){
		return lastBid;
	}
	
	/**
	 * Returns the last successful bid amount.
	 * @return The amount of the last bid
	 */
	
	public double getLastBidAmount(){
		return curBidPrice;
	}
	
	/**
	 * @return Returns true if the auction reserve price was met and the auction finished.
	 * 		   Otherwise false.
	 */
	
	public boolean isSuccessful(){
		if(hasBeenAuctioned&&(curBidPrice>=reservedPrice))
			return true;
		else
			return false;
	}
	
}
