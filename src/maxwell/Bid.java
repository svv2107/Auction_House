
package maxwell;

/**
 * Bid stores information about a single bid used in AuctionAPI and AuctionItem. 
 * No functionality for set() since a bid should not be changed
 * (they should make a new bid if they want to bid higher)
 *
 *
 * @author Steven Vuong
 *
 */
public class Bid {

	private AuctionItem itemRef = null;
	private double bidAmount;
	private String bidderID;
	
	/**
	 * Constructor.
	 *
	 * @param item AuctionItem related to this bid
	 * @param amount The amount of the bid
	 * @param id The bidder's id number
	 */
	public Bid(AuctionItem item,double amount,String id){
		itemRef=item;
		bidAmount=amount;
		bidderID=id;
	}
	
	/**
	 * @return Returns the amount of the bid.
	 *
	 */
	
	public double getAmount(){
		return bidAmount;
	}
	
	/**
	 * @return Returns the ID of the bidder
	 *
	 */
	
	public String getId(){
		return bidderID;
	}
	
	/**
	 * @return Returns the AuctionItem that the bid belongs to.
	 *
	 */
	
	public AuctionItem getItem(){
		return itemRef;
	}
	
	
}
