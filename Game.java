import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class Game extends JComponent
{
	private static final long serialVersionUID = 1L;




	//Array holds the properties in an array
	public static Property[] propertyArr= new Property[Constants.NUM_PROPERTIES];

	//Array holds the tax squares
	public static TaxSquare[] taxArr = new TaxSquare[Constants.NUM_TAX];

	public static void payRent(Board board)
	{
		if( Values.rentpay == false)
		{
			// the same for loop always used to get the current property
			Property pRent = null;
			for(int i=0;i<Constants.NUM_PROPERTIES;i++)
			{
				if(Values.playerArr[Values.playersGo].currentPosition() == Game.propertyArr[i].getPosition())
				{
					pRent = Game.propertyArr[i];
				}
			}

			// if the property is owned the player can pay rent if he is not the owner of the property
			if (pRent.isOwned() == true && !pRent.getOwner().getName().equals(Values.playerArr[Values.playersGo].getName()) )
			{
				if(Values.playerArr[Values.playersGo].getBalance() > pRent.getRent(Values.gameDice.totalValue()))
				{
					// rent is taken from the players balance
					Values.playerArr[Values.playersGo].spend(pRent.getRent(Values.gameDice.totalValue()));
					board.setInfoText("Game","You have payed "+pRent.getOwner().getName()+" "+pRent.getRent(Values.gameDice.totalValue())+" in rent", Color.magenta);
					Values.rentpay = true;
					// the rent is added tom the owners balance 
					pRent.getOwner().receive(pRent.getRent(Values.gameDice.totalValue()));
					return;
				}
				else 
				{
					board.setInfoText("Game", "You currently done have enough money to pay rent", Color.magenta);
				}
				return;
			}

			//Checks if player owns property
			if(pRent.isOwned() == true && pRent.getOwner().getName().equals(Values.playerArr[Values.playersGo].getName()))
			{
				board.setInfoText("Game", "You are the owner you dont need to pay rent!", Color.magenta);
			}
		}
		else 
		{
			//If the player has already payed rent prints warning
			board.setInfoText("Game", "You do not owe any rent", Color.magenta);
		}
	}

	public static void gameStart(Board board)
	{
		Jail [] jails = new Jail[Values.noOfPlayers];
		for(int i = 0;i<Values.noOfPlayers;i++)
		{
			jails[i] = new Jail();
		}
		//This function gets the order from the command window from the user
		Runnable getOrder = new Runnable()
		{
			//This is what runs
			public void run() 
			{

				//Checks to see that something has been entered
				if(!board.getInputText().equals("") && !board.getInputText().equals("Enter Commands here"))
				{
					//It then gets the input and prints it to the info pane
					Values.input=board.getInputText();
					board.setInfoText(Values.playerArr[Values.playersGo].getName(), Values.input, Values.playerArr[Values.playersGo].getColor());
					String[] inputWords = new String[2];

					int wordLen;
					for(wordLen = 0;wordLen<Values.input.length();wordLen++)
					{
						if(Values.input.charAt(wordLen) == ' ')
						{
							break;
						}
					}

					//Strings are broken up at the first space and the first word is entered into the switch statement
					if(wordLen == (Values.input.length()))
					{
						inputWords[0] = Values.input;
						inputWords[1] = null;
					}
					else if(Values.input.equalsIgnoreCase("pay rent"))
					{
						inputWords[0] = Values.input;
						inputWords[1] = null;
					}
					else
					{
						inputWords[0] = Values.input.substring(0, wordLen);
						inputWords[1] = Values.input.substring(wordLen+1);
					}




					//The switch statement then checks what should be done
					switch(inputWords[0])
					{
					//If "roll" or "Roll" are entered the dice is rolled and the player is moved
					case "Roll":
					case "roll":
						if(Values.playerArr[Values.playersGo].getJailed() == true && !Values.rolled)
						{
							Values.gameDice.roll();
							jails[Values.playerArr[Values.playersGo].getJailArrNum()].jailRoll();
							if(Values.gameDice.isSame()) 
							{
								Values.rolled = true;
								board.setInfoText("Game", "Player rolled a double and is now out of jail!", Color.MAGENTA);
								Values.playerArr[Values.playersGo].outJail(board);
								jails[Values.playerArr[Values.playersGo].getJailArrNum()].getPrisoner().outJail(board);
								jails[Values.playerArr[Values.playersGo].getJailArrNum()].crimOut();
								Values.newPos = (Values.gameDice.totalValue() + Values.playerArr[Values.playersGo].currentPosition());
								Values.newPos = Values.newPos%40;
								int x = Constants.SQUARE_CORDS[Values.newPos][0]-(Constants.RADIUS/2);
								int y = Constants.SQUARE_CORDS[Values.newPos][1]-(Constants.RADIUS/2);
								Values.playerArr[Values.playersGo].icon.setLocation(x,y);  
								Values.playerArr[Values.playersGo].setCurrentPosition(Values.newPos);
							}
							else
							{
								board.setInfoText(Values.playerArr[Values.playersGo].getName(), "Rolled a "+Values.gameDice.totalValue() + " not a double",   Values.playerArr[Values.playersGo].getColor());
								if(jails[Values.playerArr[Values.playersGo].getJailArrNum()].getJailRolls() == 3)
								{
									board.setInfoText("Game", "You've been in Jail for 3 goes pay a fine and your out!" ,Color.MAGENTA);
								}
							}
							Values.rolled = true;
							break;
						}

						//Stops user rolling twice unless they rolled doubles
						if(!Values.rolled && Values.rentpay && !Values.mustDraw && !Values.fineOwed && Values.playerArr[Values.playersGo].getBalance() >= 0)
						{
							Values.gameDice.roll();
							//Prints the value of the dice to the info pane
							board.setInfoText(Values.playerArr[Values.playersGo].getName(), "Rolled a "+Values.gameDice.totalValue(), Values.playerArr[Values.playersGo].getColor());
							//Moves player to new position and sets that as their location
							Values.newPos = (Values.gameDice.totalValue() + Values.playerArr[Values.playersGo].currentPosition());
							if(Values.newPos >= 40)
							{
								Values.playerArr[Values.playersGo].receive(200);
								board.setInfoText("Game","You have passed go and recieved 200 from the bank",Color.magenta);
							}

							Values.newPos = Values.newPos%40;
							int x = Constants.SQUARE_CORDS[Values.newPos][0]-(Constants.RADIUS/2);
							int y = Constants.SQUARE_CORDS[Values.newPos][1]-(Constants.RADIUS/2);
							Values.playerArr[Values.playersGo].icon.setLocation(x,y);  
							Values.playerArr[Values.playersGo].setCurrentPosition(Values.newPos);

							//If they roll a double allows them to roll again
							if(Values.gameDice.isSame()) 
							{
								Values.doublecount++;
								Values.rolled = false;
								Values.rolledDouble = true;
								board.setInfoText("Game", "Player rolled a double "+Values.gameDice.dice1()+" and can roll again", Color.MAGENTA);
							}
							else Values.rolled = true;


							//if the player lands on a tax square fineOwed is set to true
							if((Values.playerArr[Values.playersGo].currentPosition() == 4) || (Values.playerArr[Values.playersGo].currentPosition() == 38))
							{
								board.setInfoText("Game","You have landed on a tax square and must pay your fine!",Color.magenta);
								Values.fineOwed = true;

								if(Values.playerArr[Values.playersGo].currentPosition() == 4)
								{
									Values.fineAmount = Constants.TAX_FINES[0];
								}
								else if(Values.playerArr[Values.playersGo].currentPosition() == 38)
								{
									Values.fineAmount = Constants.TAX_FINES[1];
								}

								Values.playerArr[Values.playersGo].spend(Values.fineAmount);
								board.setInfoText("Game", Values.playerArr[Values.playersGo].getName()+" has a new balance of £"+Values.playerArr[Values.playersGo].getBalance(), Color.magenta);

								Values.fineOwed = false;
							}


							if(Values.doublecount == 3)
							{
								board.setInfoText("Game", "Player" + Values.playerArr[Values.playersGo].getName() +  " rolled three doubles in a row " , Color.MAGENTA);
								Values.newPos = 11;
								x = Constants.SQUARE_CORDS[Values.newPos][0]-(Constants.RADIUS/2);
								y = Constants.SQUARE_CORDS[Values.newPos][1]-(Constants.RADIUS/2);
								Values.playerArr[Values.playersGo].icon.setLocation(x,y);  
								Values.playerArr[Values.playersGo].setCurrentPosition(Values.newPos);
								Values.doublecount = 0;
								jails[Values.playerArr[Values.playersGo].getJailArrNum()].entry();
								jails[Values.playerArr[Values.playersGo].getJailArrNum()].occupant(Values.playerArr[Values.playersGo]);
								jails[Values.playerArr[Values.playersGo].getJailArrNum()].getPrisoner().inJail();
								jails[Values.playerArr[Values.playersGo].getJailArrNum()].jailInfo(board);
							}

							if(Values.newPos%40 == 30)
							{
								board.setInfoText("Game", "Player  landed on go to Jail!", Color.MAGENTA);
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
							}

							// the same for loop always used to get the current property
							Property pRent = null;
							for(int i=0;i<Constants.NUM_PROPERTIES;i++)
							{
								if(Values.playerArr[Values.playersGo].currentPosition() == propertyArr[i].getPosition())
								{
									pRent = propertyArr[i];
								}
							}
							if(pRent != null && pRent.isOwned() && pRent.getOwner() != Values.playerArr[Values.playersGo] && !pRent.isMortgaged())
							{
								board.setInfoText("Board","You have landed on "+pRent.getOwner().getName()+"'s property and owe "+pRent.getOwner().getName()+" "+pRent.getRent(Values.gameDice.totalValue())+" in rent",Color.magenta);
								Values.rentpay = false;
								Game.payRent(board);
							}

							//Draws Chance card
							if(Constants.SQUARE_TYPES[Values.playerArr[Values.playersGo].currentPosition()] == Constants.TYP_CHANCE)
							{
								board.setInfoText("Game", "Player has drawn a chance card!", Color.magenta);
								Values.mustDraw = true;
								Values.chanceDeck.drawCard();
								board.setInfoText("Chance",Values.chanceDeck.currentCard().getTitle(),Color.ORANGE);
								Values.chanceDeck.cardEffect(Values.chanceDeck.currentCard(), Values.playerArr[Values.playersGo], board, jails);
								Values.mustDraw = false;
								break;
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



						}

						//Prints warning if the user has already rolled and it was'nt a double
						else if(Values.rolled && !Values.gameDice.isSame())
						{
							board.setInfoText("Game", "Player has used all available rolls", Color.MAGENTA);
						}
						else if(!Values.rentpay)
						{
							board.setInfoText("Game", "Player has to pay rent before they can move on", Color.MAGENTA);
						}
						else if(Values.mustDraw)
						{
							board.setInfoText("Game", "Player must draw card before they can continue", Color.MAGENTA);
						}
						else if(Values.fineOwed)
						{
							board.setInfoText("Game", "Player has to pay fine before they can move on", Color.MAGENTA);
						}
						else if(Values.playerArr[Values.playersGo].getBalance() < 0)
						{
							board.setInfoText("Game", "Player has a negative balance and cannot roll", Color.magenta);
							if(Values.playerArr[Values.playersGo].hasHousesOrHotels(board)){
								break;
							}
							else if(Values.playerArr[Values.playersGo].hasUnmorgageProperties(board)){
								break;
							}
						}

						Values.doublecount = 0;
						break;

						//Allows user to pay fine (from card or from tax)
					case "pay":
					case "Pay": 	
						if(Values.paidGo == true)
						{
							board.setInfoText("Game",Values.playerArr[Values.playersGo].getName()+" you have already paid your jail fine for this go",Color.magenta);
							break;
						}

						if(Values.playerArr[Values.playersGo].getJailed() == true && Values.paidGo == false)
						{
							jails[Values.playerArr[Values.playersGo].getJailArrNum()].takefine(board);
							Values.paidGo = true;
							if(jails[Values.playerArr[Values.playersGo].getJailArrNum()].checkfinenum() == 2)
							{
								jails[Values.playerArr[Values.playersGo].getJailArrNum()].getPrisoner().outJail(board);
								jails[Values.playerArr[Values.playersGo].getJailArrNum()].crimOut();
							}

							if(jails[Values.playerArr[Values.playersGo].getJailArrNum()].checkfinenum() >= 1 && jails[Values.playerArr[Values.playersGo].getJailArrNum()].getJailRolls() == 3)
							{
								jails[Values.playerArr[Values.playersGo].getJailArrNum()].getPrisoner().outJail(board);
								jails[Values.playerArr[Values.playersGo].getJailArrNum()].crimOut();
							}
							break;
						}
						break;

					case "card":
					case "Card": 	
						if( Values.playerArr[Values.playersGo].getJailed() == false)
						{
							board.setInfoText(Values.playerArr[Values.playersGo].getName()," you are not in jail!", Values.playerArr[Values.playersGo].getColor());
							break;
						}
						if(Values.playerArr[Values.playersGo].jailCard() == false)
						{
							board.setInfoText(Values.playerArr[Values.playersGo].getName()," you have no jail card! ", Values.playerArr[Values.playersGo].getColor());
						}
						else if(Values.playerArr[Values.playersGo].getJailed() == true && Values.playerArr[Values.playersGo].jailCard() == true)
						{
							Values.playerArr[Values.playersGo].usedJailCard();
							Values.playerArr[Values.playersGo].outJail(board);
							jails[Values.playerArr[Values.playersGo].getJailArrNum()].crimOut();
							break;
						}

						break;

					case "Available":
					case "available": 	for(int i=0;i<Constants.NUM_PROPERTIES;i++)
					{
						if(!propertyArr[i].isOwned())
						{
							board.setInfoText(propertyArr[i].getName(),"(Unowned)", propertyArr[i].getColor());
						}
					}
					break;

					//If user enters "Done" or "done" their go is ended if they don't owe rent and have rolled at least once 
					case "Done":				
					case "done": 		if(Values.rolled == false && !Values.rolledDouble)
					{
						board.setInfoText("Game", "Please roll first before you end your go !", Color.MAGENTA);
						break;
					}
					else if(!Values.rentpay)
					{
						board.setInfoText("Game", "Player must pay rent before they can end their go!", Color.MAGENTA);
						break;
					}
					else if(Values.fineOwed)
					{
						board.setInfoText("Game", "Player must pay fine before they can end their go!", Color.MAGENTA);
						break;
					}
					else if(Values.mustDraw)
					{
						board.setInfoText("Game", "Player must draw a card before they can end their go!", Color.MAGENTA);
						break;
					}
					else if(Values.playerArr[Values.playersGo].getBalance() < 0)
					{
						board.setInfoText("Game", "Player cannot end there go with a negative balance", Color.magenta);
						if(Values.playerArr[Values.playersGo].hasHousesOrHotels(board))
						{
							break;
						}
						else if(Values.playerArr[Values.playersGo].hasUnmorgageProperties(board))
						{
							break;
						}
					}
					else
					{
						Values.gameDice.resetDiceIcons();
						Values.endGo = true;
					}
					break;


					//If the user enters "Help" or "help" it should display all the different commands
					case "Help" :
					case "help" :		for(int i=0; i<Constants.HELP_COMMANDS.length;i++)
					{
						board.setInfoText("Help", Constants.HELP_COMMANDS[i], Color.ORANGE);
					}
					break;


					//if user types "quit" or "Quit"
					case 	"Quit":
					case 	"quit":	// Player variable biggest to help sort the playerArr based on balance + total property values 
						Player biggest;
						int draw = 0;
						//Goes through the array sorting the players based on total assests
						for(int i=0;i<Values.noOfPlayers;i++)
						{
							for(int j=0;j<Values.noOfPlayers;j++)
							{
								if(Values.playerArr[i].calculateAssets() > Values.playerArr[j].calculateAssets())
								{
									biggest = Values.playerArr[i];
									Values.playerArr[i] = Values.playerArr[j];
									Values.playerArr[j] = biggest;
								}

							}
						}

						//Goes through the sorted array and sees if there is a  draw or an overall winner
						biggest = Values.playerArr[0];
						for(int i=1;i<Values.noOfPlayers;i++)
						{


							if(Values.playerArr[i].calculateAssets() == biggest.calculateAssets())
							{
								draw++;

							}

						}

						//Winner/ Winners is displayed followed by the rankings of the game
						if(draw == 0)
						{
							// If there no draw
							board.setInfoText("The winner is: " ,  Values.playerArr[0].getName() + " " , Color.MAGENTA);

						}

						else
						{
							// If there is a draw
							board.setInfoText("The Following PLayers have drawn the game: " , " " , Color.MAGENTA);
							for(int i=0;i<=draw;i++)
							{
								board.setInfoText("" , i+1 + " " + Values.playerArr[i].getName() + " Total wealth: " + Values.playerArr[i].calculateAssets() , Color.MAGENTA);
							}


						}
						// Final Leaderboard
						board.setInfoText("The Final Rankings: " , " " , Color.MAGENTA);
						for(int i=0;i<Values.noOfPlayers;i++)
						{
							board.setInfoText("" , i+1 + " " + Values.playerArr[i].getName() + "Total wealth: " + Values.playerArr[i].calculateAssets() , Color.MAGENTA);
						}


						Values.endGo = true;
						Values.gameOver =true;
						Values.endGame = true;
						break;


					case "Property":
					case "property":	// the players linked list properties are displayed
						Values.playerArr[Values.playersGo].checkPropertyColors();
						Values.playerArr[Values.playersGo].propertList(board, Values.gameDice.totalValue());
						break;



					case "Balance":
					case "balance":		// Players balance is shown
						board.setInfoText(Values.playerArr[Values.playersGo].getName(), "balance is: " +  Values.playerArr[Values.playersGo].getBalance(), Values.playerArr[Values.playersGo].getColor());
						break;


						//If user types buy or Buy they can buy the property theyre are on
					case "Buy":
					case "buy":			switch(Constants.SQUARE_TYPES[Values.playerArr[Values.playersGo].currentPosition()])
					{
					//If the player tries to buy  the go square this message is printed
					case 0:		board.setInfoText("Info", "You can't buy the Go square!", Color.darkGray);
					break;

					//If it is a type of property a loop finds the corresponding property based on position then prints the information on it
					case 1:
					case 2:
					case 3:
						Property tobuy = null;
						for(int i=0;i<Constants.NUM_PROPERTIES;i++)
						{
							if(Values.playerArr[Values.playersGo].currentPosition() == propertyArr[i].getPosition())
							{
								tobuy = propertyArr[i];
							}
						}
						// the property owed is set to true and the owner is set 
						// the property is then added to the players property linked list 
						if(tobuy.isOwned() == true && tobuy.getOwner().getName().equals(Values.playerArr[Values.playersGo].getName()))
						{
							board.setInfoText("Info", "You already own this Property", Color.magenta);
						}

						if(tobuy.isOwned() == false && Values.playerArr[Values.playersGo].getBalance() >= tobuy.getValue())
						{
							tobuy.own();
							tobuy.onwer(Values.playerArr[Values.playersGo]);
							Values.playerArr[Values.playersGo].addproperty(tobuy);
							board.setInfoText("Info", Values.playerArr[Values.playersGo].getName()+" now owns "+tobuy.getName(), Color.darkGray);
						}

						else if(Values.playerArr[Values.playersGo].getBalance() < tobuy.getValue())
						{
							board.setInfoText("Game", "Player does not have enough money to purchase this property", Color.magenta);
						}

						if(tobuy.isOwned() == true && !tobuy.getOwner().getName().equals(Values.playerArr[Values.playersGo].getName()))
						{
							board.setInfoText("Info" , tobuy.getOwner().getName() +  " Owns this Propert already", Color.magenta);
						}
						Values.playerArr[Values.playersGo].checkPropertyColors();
						break;

						//if it is of a different type the following messages are printed
					case 4:		board.setInfoText("Info", "You  can't buy the community chest square", Color.darkGray);
					break;
					case 5:		board.setInfoText("Info", "You  can't buy the chance square", Color.darkGray);
					break;
					case 6:		board.setInfoText("Info", "You  can't buy Jail", Color.darkGray);
					break;
					case 7:		board.setInfoText("Info", "You  can't buy the Free Parking", Color.darkGray);
					break;
					case 8:		board.setInfoText("Info", "You  can't buy this square", Color.darkGray);
					break;
					case 9:		board.setInfoText("Info", "You  can't buy the this square", Color.darkGray);
					break;
					}
					break;


					//Allows players to declare themselves as bankrupt					
					case "Bankrupt":
					case "bankrupt":		if(Values.playerArr[Values.playersGo].getBalance() > 0)
					{
						board.setInfoText("Game", "Player has avilable funds and can continue playing", Color.magenta);
						break;
					}
					else if(Values.playerArr[Values.playersGo].hasHousesOrHotels(board))
					{
						break;
					}
					else if(Values.playerArr[Values.playersGo].hasUnmorgageProperties(board))
					{
						break;
					}
					else
					{
						board.setInfoText("Game",""+Values.playerArr[Values.playersGo].getName()+ " is declared bankrupt",Color.magenta);
						Values.playerArr[Values.playersGo].DeclareBankruptcy();
						Values.endGo = true;
						break;
					}



					//Demolishes a given number of properties from the named square
					case "Demolish":
					case "demolish":	board.setInfoText("Game", "Demolish - "+inputWords[1], Color.magenta);
					String propName, numAsString;
					int num;

					//splits the rest of the input into the property name and the number of properties
					int space;
					for(space = 0;space<inputWords[1].length();space++)
					{
						if(inputWords[1].charAt(space) == ' ')
						{
							break;
						}
					}

					//If its only one word it prints a warning
					if(space == inputWords[1].length())
					{
						board.setInfoText("Game", "User must supply a property name and number of units", Color.magenta);
						break;
					}
					else
					{
						propName = inputWords[1].substring(0, space);
						numAsString = inputWords[1].substring(space+1);

						int propDemolish;
						for(propDemolish=0;propDemolish<Constants.NUM_PROPERTIES;propDemolish++)
						{
							if(propertyArr[propDemolish].getName().equalsIgnoreCase(propName) || propertyArr[propDemolish].getNickname().equalsIgnoreCase(propName))
							{
								break;
							}
						}

						//Checks the input matches a property name
						if(propDemolish == Constants.NUM_PROPERTIES)
						{
							board.setInfoText("Game", "Invalid Property Name", Color.MAGENTA);
							break;
						}
						//Checks that the current player owns the property they are trying to demolish properties on
						else if(propertyArr[propDemolish].getOwner() != Values.playerArr[Values.playersGo])
						{
							board.setInfoText("Game", Values.playerArr[Values.playersGo].getName()+" does not own "+propertyArr[propDemolish].getName(), Color.magenta);
							break;
						}
						//Checks that the property has any properties to demolish
						else if(propertyArr[propDemolish].getImprovementLevel() == 0)
						{
							board.setInfoText("Game", propertyArr[propDemolish].getName()+" Has no houses or hotels built on it", Color.magenta);
							break;
						}
						//If passes all checks it makes sure a vald number of properties has been entered
						else
						{
							try
							{
								num = Integer.parseInt(numAsString);
								if(propertyArr[propDemolish].getColour().equals("white"))
								{
									board.setInfoText("Game","Utilities cannot have property",Color.magenta);
								}
								else if(propertyArr[propDemolish].getColour().equals("grey"))
								{
									board.setInfoText("Game","Stations cannot have property",Color.magenta);
								}
								//If the valid then the property are destroyed
								else if(num < 1 || num > 4)
								{
									board.setInfoText("Game","Invalid number",Color.magenta);
									break;
								}
								else if(propertyArr[propDemolish].getImprovementLevel() == 5 && num > 1)
								{
									board.setInfoText("Game","Hotel is only property to be demolished",Color.magenta);
									break;
								}
								else if(propertyArr[propDemolish].getImprovementLevel() == 5 && num == 1)
								{
									propertyArr[propDemolish].sellHotel(board);
								}
								else if(num <=  propertyArr[propDemolish].getImprovementLevel() && num >= 1 && num <= 4)
								{
									propertyArr[propDemolish].sellHouses(board, num);
									break;
								}
								//Otherwise a warning is printed to the info pane
								else
								{
									board.setInfoText("Game",propertyArr[propDemolish].getName()+" only has "+propertyArr[propDemolish].getImprovementLevel()+" properties constructed on it",Color.magenta);
								}
							}
							//If the input throws an exception when parsed a warning is printed to the info pane
							catch (NumberFormatException e1)
							{
								board.setInfoText("Game","You have entered an ivalid number of properties",Color.magenta);
							}
							break;
						}
					}

					case "build" :
					case "Build" :
						String propertyname, unitnum;
						int number;

						// when a space is reach the space variable is set at the length 
						if(inputWords[1] == null)
						{
							board.setInfoText("Game", "User must supply a property name and number of units", Color.magenta);
							break;
						}
						int spacE;
						for(spacE = 0;spacE<inputWords[1].length();spacE++)
						{
							if(inputWords[1].charAt(spacE) == ' ')
							{
								break;
							}
						}
						//If its only one word it prints a warning/ if the length of space is equal to the input
						if(spacE == inputWords[1].length())
						{
							board.setInfoText("Game", "User must supply a property name and number of units", Color.magenta);
							break;
						}
						else
						{
							// the property name is the first word and the unit number is the second
							propertyname = inputWords[1].substring(0, spacE);
							unitnum = inputWords[1].substring(spacE+1);


							int propertybuilt; // this variable is set to the number in the array the property entered by the user is located
							for(propertybuilt=0;propertybuilt<Constants.NUM_PROPERTIES;propertybuilt++)
							{
								if(propertyArr[propertybuilt].getName().equalsIgnoreCase(propertyname) || propertyArr[propertybuilt].getNickname().equalsIgnoreCase(propertyname))
								{
									break;
								}
							}

							//Checks the input matches a property name
							if(propertybuilt == Constants.NUM_PROPERTIES)
							{
								board.setInfoText("Game", "Invalid Property Name", Color.MAGENTA);
								break;
							}

							try
							{
								number = Integer.parseInt(unitnum);
								if(number > 5 || number < 0)
								{
									board.setInfoText("Game", "Invalid amount of houses to build", Color.MAGENTA);
									break;
								}

								// my part of the code that builds ordoesn't build the house - methods are used
								// if 4 houses have been built the houses are destroyed and a hotel is built
								if(Values.playerArr[Values.playersGo] == propertyArr[propertybuilt].getOwner())
								{
									Values.playerArr[Values.playersGo].checkPropertyColors();

									for(int i= 0;i<number;i++)
									{
										propertyArr[propertybuilt].buildHouse(board);
									}
								}
								else
								{
									board.setInfoText("Game", "Player doesnt own "+propertyArr[propertybuilt].getName(), Color.magenta);
								}
							}
							//If the input throws an exception when parsed a warning is printed to the info pane
							catch (NumberFormatException e1)
							{
								board.setInfoText("Game","You have entered an ivalid number of properties",Color.magenta);
							}

						}
						break;

						//Allows players to redeem mortgaged properties
					case "Redeem":
					case "redeem":		int propRedeem;
					//checks that a valid property name has been entered
					for(propRedeem=0;propRedeem<Constants.NUM_PROPERTIES;propRedeem++)
					{
						if(propertyArr[propRedeem].getName().equalsIgnoreCase(inputWords[1]) || propertyArr[propRedeem].getNickname().equalsIgnoreCase(inputWords[1]))
						{
							break;
						}
					}
					//If not warning is printed
					if(propRedeem == Constants.NUM_PROPERTIES)
					{
						board.setInfoText("Game", "Invalid Property Name", Color.MAGENTA);
						break;
					}
					else
					{
						Property prop = propertyArr[propRedeem];
						//Checks player owns the entered property
						if(prop.getOwner() != Values.playerArr[Values.playersGo])
						{
							board.setInfoText("Game", "Player does not own "+prop.getName(), Color.MAGENTA);
						}
						//Checks that the property is currently morgaged
						else if(!prop.isMortgaged())
						{
							board.setInfoText("Game", prop.getName()+" hasn't been mortgaged", Color.MAGENTA);
						}
						//If it passes the checks the property is redeemed
						else
						{
							//Player pays the mortgage value + 10%
							int redeemVal = ((propertyArr[propRedeem].getMortgageValue()/10)*11);
							propertyArr[propRedeem].getOwner().spend(redeemVal);
							propertyArr[propRedeem].setMortgaged(false);
							board.setInfoText("Game",prop.getName()+" has been redeem at an expense of £"+redeemVal, Color.magenta);
						}
						break;
					}

					//Allows the players to mortgage their properties			
					case "mortgage":
					case "Mortgage":	int propC;
					//checks that a valid property name has been entered
					for(propC=0;propC<Constants.NUM_PROPERTIES;propC++)
					{
						if(propertyArr[propC].getName().equalsIgnoreCase(inputWords[1]) || propertyArr[propC].getNickname().equalsIgnoreCase(inputWords[1]))
						{
							break;
						}
					}
					//If not then warning is printed
					if(propC == Constants.NUM_PROPERTIES)
					{
						board.setInfoText("Game", "Invalid Property Name", Color.MAGENTA);
						break;
					}
					else
					{

						Property prop = propertyArr[propC];
						//Checks that property is currently not morgaged
						if(prop.isMortgaged())
						{
							board.setInfoText("Game", prop.getName()+" has already been mortgaged", Color.MAGENTA);
						}
						else
						{
							//Checks player owns property
							if(Values.playerArr[Values.playersGo] == prop.getOwner())
							{
								Color curr = prop.getColor();
								if(prop.getColour().equals("grey"))
								{
									board.setInfoText("Game",propertyArr[propC].getName()+" has been mortgaged and "+Values.playerArr[Values.playersGo].getName()+" has received £"+propertyArr[propC].getMortgageValue(),Color.magenta);
									propertyArr[propC].getOwner().receive(propertyArr[propC].getMortgageValue());
									propertyArr[propC].setMortgaged(true);
									break;
								}
								//Prints warnings if the property has been developed
								switch(prop.getImprovementLevel())
								{
								case 1:	board.setInfoText("Game", "The house on "+prop.getName()+" must be sold before property can be mortgaged", Color.MAGENTA);
								break;
								case 2:	board.setInfoText("Game", "Both houses on "+prop.getName()+" must be sold before property can be mortgaged", Color.MAGENTA);
								break;
								case 3:	board.setInfoText("Game", "All three houses on "+prop.getName()+" must be sold before property can be mortgaged", Color.MAGENTA);
								break;
								case 4:	board.setInfoText("Game", "All four houses on "+prop.getName()+" must be sold before property can be mortgaged", Color.MAGENTA);
								break;
								case 5:	board.setInfoText("Game", "Hotel on "+prop.getName()+" must be sold before property can be mortgaged", Color.MAGENTA);
								break;


								case 0: boolean morgagable = true;
								//Checks that all other properties in the color group are not developed if so it prints warning message
								for(int i=0;i<Constants.NUM_PROPERTIES;i++)
								{
									if(propertyArr[i] != prop && propertyArr[i].getColor() == curr && propertyArr[i].getImprovementLevel() > 0)
									{
										board.setInfoText("Game","All properties on "+propertyArr[i].getName()+" must be sold before "+prop.getName()+" can be mortgaged",Color.magenta);
										morgagable = false;
									}
								}
								//If it passes all checks
								if(morgagable)
								{
									//Player receives mortgage value and the property is set to mortgaged 
									board.setInfoText("Game",propertyArr[propC].getName()+" has been mortgaged and "+Values.playerArr[Values.playersGo].getName()+" has received £"+propertyArr[propC].getMortgageValue(),Color.magenta);
									propertyArr[propC].getOwner().receive(propertyArr[propC].getMortgageValue());
									propertyArr[propC].setMortgaged(true);
								}
								break;

								}


							}
							//Prints warning if player does not own property
							else
							{
								board.setInfoText("Game", "You do not own "+prop.getName()+" and therefore cannot mortgage it", Color.MAGENTA);
							}
							break;
						}
						break;
					}

					//If the user enters "Info" or "info" the information of the square they are currently in is printed ot the info pane
					case "Info":
					case "info": 	//The players position is entered in the array of square types to see what kind of square it is
						Values.playerArr[Values.playersGo].checkPropertyColors();
						switch(Constants.SQUARE_TYPES[Values.playerArr[Values.playersGo].currentPosition()])
						{
						//If its "go" this message is printed
						case 0:		board.setInfoText("Info", "Go - Collect 200 When passing go", Color.darkGray);
						break;
						//If it is a type of property a loop finds the corresponding property based on position then prints the information on it
						case 1:
						case 2:
						case 3:		Property curr = null;
						for(int i=0;i<Constants.NUM_PROPERTIES;i++)
						{
							if(Values.playerArr[Values.playersGo].currentPosition() == propertyArr[i].getPosition())
							{
								curr = propertyArr[i];
							}
						}
						board.setInfoText("Info", "Property - "+curr.getName(), Color.darkGray);
						board.setInfoText("Info", "Value    - "+curr.getValue(), Color.darkGray);
						board.setInfoText("Info", "Colour    - "+curr.getColour(), Color.darkGray);
						if(curr.isOwned() == true)
						{
							board.setInfoText("Info", "Owned By - "+curr.getOwner().getName(), Color.darkGray);
						}

						else board.setInfoText("Info", "The property is currently not owned", Color.darkGray);
						break;

						//if it is of a different type the following messages are printed
						case 4:		board.setInfoText("Info", "Community Chest - User takes Community Chest card", Color.darkGray);
						break;
						case 5:		board.setInfoText("Info", "Chance - User takes Chance card", Color.darkGray);
						break;
						case 6:		board.setInfoText("Info", "Jail - Users can be sent to jail or can just visit", Color.darkGray);
						break;
						case 7:		board.setInfoText("Info", "Free Parking - The user can stay here as long as the wish", Color.darkGray);
						break;
						case 8:		board.setInfoText("Info", "Go To Jail - The user must proceed to jail", Color.darkGray);
						break;
						case 9:		board.setInfoText("Info", "Tax - User must pay the fine listed on the position", Color.darkGray);
						break;
						}
						break;

						//If the user enters an invalid command the following is printed to the command window
					default: board.setInfoText("Game", "Invalid Command", Color.MAGENTA);


					}
				}
				//command field is reset
				board.scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
					public void adjustmentValueChanged(AdjustmentEvent e) {  
						e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
					}
				});
				board.commandPanelField.setText("");
			}


		};


		//Creates action listener to implement the getOrder runnable
		ActionListener commandListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SwingUtilities.invokeLater(getOrder);
			}
		};
		board.commandPanelButton.addActionListener(commandListener);
		board.commandPanelField.addActionListener(commandListener);
		Values.gameDice = new Dice(board);
		//Loops that allow players to play
		while(!Values.endGame && !Values.gameOver)
		{
			if(Values.playerArr[Values.playersGo].isBankrupt())
			{
				Values.playersGo = (Values.playersGo+1)%Values.noOfPlayers; 
			}

			board.setInfoText("Game", Values.playerArr[Values.playersGo].getName()+"'s go ("+Values.playerArr[Values.playersGo].getPiece()+")", Color.magenta);
			if(Values.playerArr[Values.playersGo].getJailed() == true)
			{
				board.setInfoText("Jail", Values.playerArr[Values.playersGo].getName() + " is in Jail!", Color.orange);
				board.setInfoText("Jail", "Player has been in jail for " + jails[Values.playerArr[Values.playersGo].getJailArrNum()].getJailRolls() + " rolls", Color.orange);
				board.setInfoText("Jail", "Player has paid " + jails[Values.playerArr[Values.playersGo].getJailArrNum()].checkfinenum() + " fines", Color.orange);

			}
			Values.endGo = false;
			Values.newPos = 0;
			Values.rolled = false;
			Values.rolledDouble = false;
			Values.rentpay = true;
			Values.paidGo = false;

			while(!Values.endGo)
			{
				System.out.print("");
			}
			Values.playersGo = (Values.playersGo+1)%Values.noOfPlayers;
			board.setInfoText("", "", Color.white);
			int bankruptCount = 0;
			for(int i = 0;i<Values.noOfPlayers;i++)
			{
				if(Values.playerArr[i].isBankrupt())
				{
					bankruptCount++;
				}
			}
			if(bankruptCount == (Values.noOfPlayers-1)) 
			{
				Player biggest;
				int draw = 0;
				//Goes through the array sorting the players based on total assests
				for(int i=0;i<Values.noOfPlayers;i++)
				{
					for(int j=0;j<Values.noOfPlayers;j++)
					{
						if(Values.playerArr[i].calculateAssets() > Values.playerArr[j].calculateAssets())
						{
							biggest = Values.playerArr[i];
							Values.playerArr[i] = Values.playerArr[j];
							Values.playerArr[j] = biggest;
						}

					}
				}

				//Goes through the sorted array and sees if there is a  draw or an overall winner
				biggest = Values.playerArr[0];
				for(int i=1;i<Values.noOfPlayers;i++)
				{


					if(Values.playerArr[i].calculateAssets() == biggest.calculateAssets())
					{
						draw++;

					}

				}

				//Winner/ Winners is displayed followed by the rankings of the game
				if(draw == 0)
				{
					// If there no draw
					board.setInfoText("The winner is: " ,  Values.playerArr[0].getName() + " " , Color.MAGENTA);

				}

				else
				{
					// If there is a draw
					board.setInfoText("The Following PLayers have drawn the game: " , " " , Color.MAGENTA);
					for(int i=0;i<=draw;i++)
					{
						board.setInfoText("" , i+1 + " " + Values.playerArr[i].getName() + " Total wealth: " + Values.playerArr[i].calculateAssets() , Color.MAGENTA);
					}


				}
				// Final Leaderboard
				board.setInfoText("The Final Rankings: " , " " , Color.MAGENTA);
				for(int i=0;i<Values.noOfPlayers;i++)
				{
					board.setInfoText("" , i+1 + " " + Values.playerArr[i].getName() + "Total wealth: " + Values.playerArr[i].calculateAssets() , Color.MAGENTA);
				}


				Values.endGo = true;
				Values.gameOver =true;
				Values.endGame = true;
				break;
			}
		}
		//When the game is over the command listener is removed
		board.commandPanelButton.removeActionListener(commandListener);
		board.commandPanelField.removeActionListener(commandListener);

	}

	public static void main(String[] args) throws IOException
	{

		//Sets up a board, displays welcome message and adds each icon/ board piece image into an array of JLabels
		String input = "United Kingdom";
		Board temp = new Board(input);
		new Values(input);
		//Sets up the Property Array
		for(int i=0;i<Constants.NUM_PROPERTIES;i++)
		{
			propertyArr[i] = new Property(Values.PROPERTY_NAMES[i],Values.PROPERTY_NICKNAMES[i],
					Constants.PROPERTY_VALUES[i],Constants.PROPERTY_RENTS[i],
					Constants.PROPERTY_POSITIONS[i], Constants.PROPERTY_COLOURS[i], 
					Constants.PROPERTY_COLS[i],Constants.MORTGAGE_VALUES[i],
					Constants.HOUSE_COSTS[i], Constants.PROPERTY_HOUSE_COSTS[i],
					Constants.PROPERTY_COLOURS_NUMBERS[i]);
		}

		temp.setInfoText("Game","Welcome to Monopoly",Color.magenta);


		new NewGame(temp);
	}
}