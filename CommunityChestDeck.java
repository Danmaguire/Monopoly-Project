
import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JOptionPane;

/***
 * Holds the community chest card deck array and has methods to draw a cad and also get its effect
 */
public class CommunityChestDeck
{
	CommunityChestCard[] communityChestDeck;
	int currentCard;

	//Passes in an array of communitychest cards and sets the current card to 0 before shuffling deck
	CommunityChestDeck(CommunityChestCard[] communityChestDeck)
	{
		this.communityChestDeck = communityChestDeck;
		currentCard = 0;
		shuffleDeck();
	}

	//Shuffles the card array
	public void shuffleDeck()
	{
		Collections.shuffle(Arrays.asList(communityChestDeck));
	}

	//Allows player to draw card, if the deck has all been used the current card is rest to 0 and the deck is shuffled
	public CommunityChestCard drawCard()
	{
		if(currentCard == communityChestDeck.length)
		{
			shuffleDeck();
			currentCard = 0;
		}
		return communityChestDeck[currentCard++];
	}

	//Returns the current card
	public CommunityChestCard currentCard()
	{
		return communityChestDeck[currentCard];
	}

	//Function takes in card, player, board and jail array and then implements the effect of the current card on the game
	public void cardEffect(CommunityChestCard card, Player player, Board board, Jail [] jails)
	{
		switch(card.getType())
		{
		case 0: player.receive(20);
		board.setInfoText("Game", player.getName()+" has a new balance of £"+player.getBalance(), Color.magenta);
		break;

		case 1: player.receive(50);
		board.setInfoText("Game", player.getName()+" has a new balance of £"+player.getBalance(), Color.magenta);
		break;

		case 2: int totalAmount = 0;
		for(int i =0;i< Values.noOfPlayers;i++)
		{
			if(Values.playerArr[i] != player)
			{
				Values.playerArr[i].spend(10);
				board.setInfoText(Values.playerArr[i].getName(),"has given £10", Values.playerArr[i].getColor());
				totalAmount += 10;
			}
		}
		player.receive(totalAmount);
		board.setInfoText("Game", player.getName()+" has received £"+totalAmount, Color.magenta);
		break;


		case 3: player.receive(25);
		board.setInfoText("Game", player.getName()+" has a new balance of £"+player.getBalance(), Color.magenta);
		break;

		case 4: board.setInfoText("Game", "Player now has a get out of Jail Free card", Color.MAGENTA);
		Values.playerArr[Values.playersGo].gotJailCard();

		break;

		case 5: int pos = 0;
		int x = Constants.SQUARE_CORDS[pos][0]-(Constants.RADIUS/2);
		int y = Constants.SQUARE_CORDS[pos][1]-(Constants.RADIUS/2);
		Values.playerArr[Values.playersGo].icon.setLocation(x,y);  
		Values.playerArr[Values.playersGo].setCurrentPosition(pos);
		player.receive(200);
		board.setInfoText("Game", player.getName()+" has passed go and received £200", Color.magenta);
		break;

		case 6: board.setInfoText("Game", player.getName()+" owes £"+100, Color.magenta);
		player.spend(100);
		board.setInfoText("Game", player.getName()+"'s new balance is £"+player.getBalance(), Color.magenta);
		break;

		case 7:	player.receive(10);
		board.setInfoText("Game", player.getName()+" has a new balance of £"+player.getBalance(), Color.magenta);
		break;

		case 8: player.receive(200);
		board.setInfoText("Game", player.getName()+" has a new balance of £"+player.getBalance(), Color.magenta);
		break;

		case 9:	player.receive(100);
		board.setInfoText("Game", player.getName()+" has a new balance of £"+player.getBalance(), Color.magenta);
		break;

		case 10:Values.newPos = 10;
		x = Constants.SQUARE_CORDS[Values.newPos][0]-(Constants.RADIUS/2);
		y = Constants.SQUARE_CORDS[Values.newPos][1]-(Constants.RADIUS/2);
		Values.playerArr[Values.playersGo].icon.setLocation(x,y);  
		Values.playerArr[Values.playersGo].setCurrentPosition(Values.newPos);
		Values.doublecount = 0;
		jails[Values.playerArr[Values.playersGo].getJailArrNum()].entry();
		jails[Values.playerArr[Values.playersGo].getJailArrNum()].occupant(Values.playerArr[Values.playersGo]);
		jails[Values.playerArr[Values.playersGo].getJailArrNum()].getPrisoner().inJail();
		jails[Values.playerArr[Values.playersGo].getJailArrNum()].jailInfo(board);
		break;

		case 11:board.setInfoText("Game", player.getName()+" owes £"+50, Color.magenta);
		player.spend(50);
		board.setInfoText("Game", player.getName()+"'s new balance is £"+player.getBalance(), Color.magenta);
		break;

		case 12:String[] buttons = {"Chance","Fine"}; 
		int input = JOptionPane.showOptionDialog(null, "Chance or £10 fine", "Choice",
				JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[0]);
		if(input == 0)
		{
			Values.chanceDeck.drawCard();
			board.setInfoText("Chance",Values.chanceDeck.currentCard().getTitle(),Color.ORANGE);
			Values.chanceDeck.cardEffect(Values.chanceDeck.currentCard(), Values.playerArr[Values.playersGo], board, jails);
			break;
		}
		else
		{
			board.setInfoText("Game", player.getName()+" owes £"+10, Color.magenta);
			player.spend(10);
			board.setInfoText("Game", player.getName()+"'s new balance is £"+player.getBalance(), Color.magenta);
			break;
		}

		case 13:board.setInfoText("Game", player.getName()+" owes £"+50, Color.magenta);
		player.spend(50);
		board.setInfoText("Game", player.getName()+"'s new balance is £"+player.getBalance(), Color.magenta);
		break;

		case 14:pos = 1;
		x = Constants.SQUARE_CORDS[pos][0]-(Constants.RADIUS/2);
		y = Constants.SQUARE_CORDS[pos][1]-(Constants.RADIUS/2);
		Values.playerArr[Values.playersGo].icon.setLocation(x,y);  
		Values.playerArr[Values.playersGo].setCurrentPosition(pos);
		Property pRent = null;
		for(int i=0;i<Constants.NUM_PROPERTIES;i++)
		{
			if(Values.playerArr[Values.playersGo].currentPosition() == Game.propertyArr[i].getPosition())
			{
				pRent = Game.propertyArr[i];
			}
		}
		if(pRent != null && pRent.isOwned() && pRent.getOwner() != Values.playerArr[Values.playersGo] && !pRent.isMortgaged())
		{
			board.setInfoText("Board","You have landed on "+pRent.getOwner().getName()+"'s property and owe "+pRent.getOwner().getName()+" "+pRent.getRent(Values.gameDice.totalValue())+" in rent",Color.magenta);
			Values.rentpay = false;
			Game.payRent(board);
		}
		break;

		case 15:player.receive(100);
		board.setInfoText("Game", player.getName()+" has a new balance of £"+player.getBalance(), Color.magenta);
		break;
		}

	}

	public String toString()
	{
		String output = "< ";
		for(int i=0;i<communityChestDeck.length;i++)
		{
			output += communityChestDeck[i].getTitle()+" - "+communityChestDeck[i].getType()+"\n";
		}
		output+= ">";
		return output;
	}
}
