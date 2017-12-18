import java.awt.Color;
/*
 * Class holds a player in prison under certain conditions
 */
public class Jail 
{

	private Player prisoner; 
	private boolean occupied;
	private int finespaid;
	private int jailRolls;



	public void entry()
	{
		occupied = true;
	}

	public boolean full()
	{
		return occupied;
	}

	public void occupant(Player crim)
	{
		prisoner = crim;
	}

	public Player getPrisoner()
	{
		return prisoner; 
	}

	public int checkfinenum()
	{
		return finespaid;
	}

	//Allows player pay fine
	public void takefine(Board board)
	{
		board.setInfoText("Game", Values.playerArr[Values.playersGo].getName()+" has paid a fine! ",Color.cyan);
		if(finespaid < 2)
		{
			prisoner.spend(50);
			finespaid++;
		}

	}
	
	//Releases the prisoner
	public void crimOut()
	{
		finespaid = 0;
		prisoner = null;
		occupied = false;
	}
	
	//Allows player to use a get out of jail free card
	public void useJailCard()
	{
		this.crimOut();
	}
	
	//Allows player to roll in jail
	public void jailRoll()
	{
		jailRolls++;
	}
	
	//Sees how many times player has rolled in jail
	public int getJailRolls()
	{
		return jailRolls;
	}
	
	//Prints informaton to player about being in jail
	public void jailInfo(Board board)
	{
		board.setInfoText("Jail Info", "Player " + this.prisoner.getName() + " is now in prison " , Color.ORANGE);
		board.setInfoText("Jail Info", "Player " + this.prisoner.getName() + " Can get out in 3 ways" , Color.ORANGE);
		board.setInfoText("Jail Info", "1) Roll a double on 1 of his next three rolls in Jail" , Color.ORANGE);
		board.setInfoText("Jail Info", "2) Use a get out of Jail Free card" , Color.ORANGE);
		board.setInfoText("Jail Info", "3) Pay two £50 fines before you roll the dice for the next two turns" , Color.ORANGE);
		board.setInfoText("Jail Info", "Or just stay in Jail for three turns and then pay a £50 fine" , Color.ORANGE);

	}

}
