
package maxwell;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * An example that uses the AuctionAPI
 *
 *
 * @author Steven Vuong
 *
 */
public class AuctionHouseExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Testing the Auction system
		System.out.println("");
		System.out.println("Adding stuff to auction");
		System.out.println("");
		
		AuctionAPI seller = new AuctionAPI("Ebay","Ebaypassword");
		
		seller.addItem("Maxwell Coffee", 3.99);
		seller.addItem("Starbucks Coffee", 4.99);
		seller.addItem("Iphone 6", 599.99);
		seller.addItem("Glitch", -4.23); 			//Should not appear in inventory
		
		seller.startAuction("Maxwell Coffee");
		seller.startAuction("Starbucks Coffee");
		seller.startAuction("Pizza"); 				//Should print error because it doesn't exist
		
		AuctionAPI buyer = new AuctionAPI("Consumer1","ConsumerPassword");
		
		//Check out the inventory
		System.out.println("");
		System.out.println("Checking the inventory");
		System.out.println("");
		
		System.out.println("Active Items:");
		ArrayList<String> tempItems = buyer.getActiveItems();
		Iterator<String> tempIter = tempItems.iterator();
		while(tempIter.hasNext())
			System.out.println("\t"+tempIter.next());
		
		System.out.println("Inactive Items:");
		tempItems = buyer.getInactiveItems();
		tempIter = tempItems.iterator();
		while(tempIter.hasNext())
			System.out.println("\t"+tempIter.next());
		
		System.out.println("Finished Items:");
		tempItems = buyer.getFinishedItems();
		tempIter = tempItems.iterator();
		while(tempIter.hasNext())
			System.out.println("\t"+tempIter.next());
		
		//Checking the bidding system
		System.out.println("");
		System.out.println("Attempting to bid on things");
		System.out.println("");
		
		buyer.bidOnItem("Samsung S5", 400); 		//Should print error because it dosen't exist
		buyer.bidOnItem("Iphone 6", 300);			//Not active
		buyer.bidOnItem("Maxwell Coffee", 6.00);
		buyer.bidOnItem("Starbucks Coffee",3.00);   
		
		AuctionAPI buyer2 = new AuctionAPI("Consumer2","Consumer2Password");
		
		buyer2.bidOnItem("Maxwell Coffee",5.00); 	//Should get error because it's lower than the current bid
		buyer2.bidOnItem("Maxwell Coffee", 7.00);
		
		seller.stopAuction("Maxwell Coffee");
		seller.stopAuction("Starbucks Coffee");
		seller.stopAuction("Pizza"); 				//Should print error because it doesn't exist
		
		buyer2.bidOnItem("Starbucks Coffee", 8.00); //Auction is over, should get error
		
		//Checking the LatestAction function
		System.out.println("");
		System.out.println("Checking the latest actions on the auction items");
		System.out.println("");
		
		AuctionAPI.latestAction("Pizza"); 				//Should get error does not exist
		System.out.println("");
		AuctionAPI.latestAction("Maxwell Coffee");
		System.out.println("");
		AuctionAPI.latestAction("Starbucks Coffee");
		System.out.println("");
		AuctionAPI.latestAction("Iphone 6");
		
		//Conclude by checking the items lists
		System.out.println("");
		System.out.println("Checking the items lists");
		System.out.println("");
		
		System.out.println("Active Items:");
		tempItems = buyer.getActiveItems();
		tempIter = tempItems.iterator();
		while(tempIter.hasNext())
			System.out.println("\t"+tempIter.next());
		
		System.out.println("Inactive Items:");
		tempItems = buyer.getInactiveItems();
		tempIter = tempItems.iterator();
		while(tempIter.hasNext())
			System.out.println("\t"+tempIter.next());
		
		System.out.println("Finished Items:");
		tempItems = buyer.getFinishedItems();
		tempIter = tempItems.iterator();
		while(tempIter.hasNext())
			System.out.println("\t"+tempIter.next());
		
		
		
		
	}

}
