import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
/***
 * Class holds the chance card deck and the functions to draw a card,
 *  and to get the effect the chance card has on the game
 */
public class ChanceCardDeck
{
	ChanceCard[] chanceDeck;
	int currentCard;
	
	//constructor creates the chance deck and shuffles the card array;
	ChanceCardDeck(ChanceCard[] chanceDeck)
	{
		this.chanceDeck = chanceDeck;
		currentCard = 0;
		shuffleDeck();
	}
	
	//Shuffles the card array
	public void shuffleDeck()
	{
		Collections.shuffle(Arrays.asList(chanceDeck));
	}
	
	//Draws a card if the deck is fully used the current card is reset to 0 and the deck is shuffled
	public ChanceCard drawCard()
	{
		if(currentCard == chanceDeck.length)
		{
			shuffleDeck();
			currentCard = 0;
		}
		return chanceDeck[currentCard++];
	}
	
	//Returns the current card
	public ChanceCard currentCard()
	{
		return chanceDeck[currentCard];
	}
	
	//This function causes the effect on the chance card to come into play by figuring out the card then applying the cards effect
	public void cardEffect(ChanceCard card, Player player, Board board, Jail [] jails)
	{
		switch(card.getType())
		{
			case 0: int pos = 39;
					int x = Constants.SQUARE_CORDS[pos][0]-(Constants.RADIUS/2);
					int y = Constants.SQUARE_CORDS[pos][1]-(Constants.RADIUS/2);
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
					
			case 1: pos = 0;
					x = Constants.SQUARE_CORDS[pos][0]-(Constants.RADIUS/2);
					y = Constants.SQUARE_CORDS[pos][1]-(Constants.RADIUS/2);
					Values.playerArr[Values.playersGo].icon.setLocation(x,y);  
					Values.playerArr[Values.playersGo].setCurrentPosition(pos);
					player.receive(200);
					board.setInfoText("Game", player.getName()+" has passed go and received £200", Color.magenta);
					break;
			
			case 2: int totalFine = 0;
					Link curr = new Link();
					curr = player.getFront();
					for(int i = 0;i<player.propertyNum();i++)
					{
						if(curr.data.getImprovementLevel() > 0 && curr.data.getImprovementLevel() <= 4)
						{
							totalFine += curr.data.getImprovementLevel()*40;
						}
						else if(curr.data.getImprovementLevel() == 5)
						{
							totalFine += 115;
						}
						curr = curr.nextlink;
					}
					if(totalFine > 0)
					{
						board.setInfoText("Game", player.getName()+" owes a fine of £"+totalFine, Color.magenta);
						player.spend(totalFine);
						board.setInfoText("Game", player.getName()+"'s new balance is £"+player.getBalance(), Color.magenta);
					}
					else board.setInfoText("Game", "Player has no houses or hotels and owes no money", Color.magenta);
					break;
			
			case 3:
					Values.newPos = 10;
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
			
			case 4: player.receive(50);
					board.setInfoText("Game", player.getName()+" has a new balance of £"+player.getBalance(), Color.magenta);
					break;
		
			case 5: pos = player.currentPosition()-3;
					if(pos < 0)
					{
						pos = 40 + pos;
					}
					x = Constants.SQUARE_CORDS[pos][0]-(Constants.RADIUS/2);
					y = Constants.SQUARE_CORDS[pos][1]-(Constants.RADIUS/2);
					Values.playerArr[Values.playersGo].icon.setLocation(x,y);  
					Values.playerArr[Values.playersGo].setCurrentPosition(pos);
					pRent = null;
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
					//if the user lands on the first chance square it will be put back 3 spaces meaning it will land on the tax square
					else if((Values.playerArr[Values.playersGo].currentPosition() == 4)){
						board.setInfoText("Board","You have landed on a tax square and must pay your fine! ",Color.magenta);
						Values.fineOwed = true;
						if(Values.fineAmount > Values.playerArr[Values.playersGo].getBalance())
						{
							board.setInfoText("Game", Values.playerArr[Values.playersGo].getName()+" does not have enough money to pay the fine", Color.magenta);
							break;
						}
						else
						{
							if(Values.playerArr[Values.playersGo].currentPosition() == 4){
								Values.fineAmount = Constants.TAX_FINES[0];
							}
							}
							Values.playerArr[Values.playersGo].spend(Values.fineAmount);
							board.setInfoText("Game", Values.playerArr[Values.playersGo].getName()+" has a new balance of £"+Values.playerArr[Values.playersGo].getBalance(), Color.magenta);
						
						
							
							Values.fineOwed = false;
						}
					//Draws Community Chest Card
					else if(Constants.SQUARE_TYPES[Values.playerArr[Values.playersGo].currentPosition()] == Constants.TYP_COMMUNITY)
					{
						board.setInfoText("Game", "Player has drawn a community chest card!", Color.magenta);
						Values.mustDraw = true;
						Values.communityDeck.drawCard();
						board.setInfoText("Community Chest",Values.communityDeck.currentCard().getTitle(),Color.PINK);
						Values.communityDeck.cardEffect(Values.communityDeck.currentCard(), Values.playerArr[Values.playersGo], board, jails);
						Values.mustDraw = false;
						break;
					}
					break;
			
			case 6: board.setInfoText("Game", player.getName()+" owes a fine of £"+150, Color.magenta);
					player.spend(150);
					board.setInfoText("Game", player.getName()+"'s new balance is £"+player.getBalance(), Color.magenta);
					break;
			
			case 7: totalFine = 0;
					curr = new Link();
					curr = player.getFront();
					for(int i = 0;i<player.propertyNum();i++)
					{
						if(curr.data.getImprovementLevel() > 0 && curr.data.getImprovementLevel() <= 4)
						{
							totalFine += curr.data.getImprovementLevel()*25;
						}
						else if(curr.data.getImprovementLevel() == 5)
						{
							totalFine += 100;
						}
						curr = curr.nextlink;
					}
					if(totalFine > 0)
					{
						board.setInfoText("Game", player.getName()+" owes a fine of £"+totalFine, Color.magenta);
						player.spend(totalFine);
						board.setInfoText("Game", player.getName()+"'s new balance is £"+player.getBalance(), Color.magenta);
					}
					else board.setInfoText("Game", "Player has no houses or hotels and owes no money", Color.magenta);
					break;
			
			case 8: board.setInfoText("Game", player.getName()+" owes a fine of £"+15, Color.magenta);
					player.spend(15);
					board.setInfoText("Game", player.getName()+"'s new balance is £"+player.getBalance(), Color.magenta);
					break;
			
			case 9: player.receive(100);
					board.setInfoText("Game", player.getName()+" has a new balance of £"+player.getBalance(), Color.magenta);
					break;	
					
			case 10:player.receive(150);
					board.setInfoText("Game", player.getName()+" has a new balance of £"+player.getBalance(), Color.magenta);
					break;	
			
			case 11:board.setInfoText("Game", "Player now has a get out of Jail Free card", Color.MAGENTA);
					Values.playerArr[Values.playersGo].gotJailCard();
					break;
			
			case 12:pos = 24;
					if(player.currentPosition() > pos)
					{
						player.receive(200);
						board.setInfoText("Game", player.getName()+" has passed go and received £200", Color.MAGENTA);
					}
					x = Constants.SQUARE_CORDS[pos][0]-(Constants.RADIUS/2);
					y = Constants.SQUARE_CORDS[pos][1]-(Constants.RADIUS/2);
					Values.playerArr[Values.playersGo].icon.setLocation(x,y);  
					Values.playerArr[Values.playersGo].setCurrentPosition(pos);
					pRent = null;
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
			
			case 13:pos = 15;
					if(player.currentPosition() > pos)
					{
						player.receive(200);
						board.setInfoText("Game", player.getName()+" has passed go and received £200", Color.MAGENTA);
					}
					x = Constants.SQUARE_CORDS[pos][0]-(Constants.RADIUS/2);
					y = Constants.SQUARE_CORDS[pos][1]-(Constants.RADIUS/2);
					Values.playerArr[Values.playersGo].icon.setLocation(x,y);  
					Values.playerArr[Values.playersGo].setCurrentPosition(pos);
					pRent = null;
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
					
			case 14:pos = 11;
					if(player.currentPosition() > pos)
					{
						player.receive(200);
						board.setInfoText("Game", player.getName()+" has passed go and received �200", Color.MAGENTA);
					}
					x = Constants.SQUARE_CORDS[pos][0]-(Constants.RADIUS/2);
					y = Constants.SQUARE_CORDS[pos][1]-(Constants.RADIUS/2);
					Values.playerArr[Values.playersGo].icon.setLocation(x,y);  
					Values.playerArr[Values.playersGo].setCurrentPosition(pos);
					pRent = null;
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
			
			case 15:board.setInfoText("Game", player.getName()+" owes a fine of £"+20, Color.magenta);
					player.spend(20);
					board.setInfoText("Game", player.getName()+"'s new balance is £"+player.getBalance(), Color.magenta);
					break;

		}
		
	}
}