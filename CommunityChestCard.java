/***
 * This creates a community chest card class that holds 
 * the cards number and the string from the card
 */
public class CommunityChestCard
{
	private String title;
	private int type;
		
	CommunityChestCard(String title, int type)
	{
		this.title = title;
		this.type = type;
	}
		
	public String getTitle() {return title;}
	public int getType() {return type;}
		
}

