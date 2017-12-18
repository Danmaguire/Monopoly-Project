//import java.awt.Color;
import java.awt.Color;
import javax.swing.JLabel;

class Player
{
	private int playerPosition;
	private int playerBalance;
	private String playerName;
	private String playerPiece;
	private int playerNumber;
	private boolean bankrupt;
	private boolean getOutOfJail;
	// Linked list variables 
	private Link front = new Link();
	private Link rear = new Link();
	private int size; 
	private boolean jailed = false;
	private int jailAnum;
	private static int numberOfPlayers = 0;

	private static Color[] colors = {Color.red,Color.blue,Color.yellow,Color.green,Color.pink,Color.orange};
	//private static String[] playerColor = {"Red","Blue","Yellow","Green","Pink","Orange"};
	public JLabel icon;

	// Property color counter variables
	private String [] Player_Property_Colors = 
		{
				"brown","grey","blue","pink","orange",
				"red","yellow","green","navy","white"
		};
	private int [] Player_Property_Colors_Counter = 
		{
				0,0,0,0,0,0,0,0,0,0
		};

	//Constructor for player taking in the players name and jlabel
	Player(String name)
	{

		//Initialize players balance as 1500€
		playerBalance = 1500;

		//starts the player from position 0
		playerPosition = 0;

		playerName = name;
		playerNumber = numberOfPlayers;

		switch(playerNumber)
		{
		case 0: icon = Board.icon1;
		playerPiece = "Thimble";
		break;
		case 1: icon = Board.icon2;
		playerPiece = "Boat";
		break;
		case 2: icon = Board.icon3;
		playerPiece = "Dog";
		break;
		case 3: icon = Board.icon4;
		playerPiece = "Car";
		break;
		case 4: icon = Board.icon5;
		playerPiece = "Top-hat";
		break;
		case 5: icon = Board.icon6;
		playerPiece = "Shoe";
		break;
		}
		icon.setSize(40,40);
		bankrupt = false;
		//Increments the number of players when Player class is called
		numberOfPlayers++;

	}

	public String getPiece(){return playerPiece;}
	public void jailArrNum(int position)
	{
		jailAnum = position;
	}
	public int getJailArrNum()
	{
		return jailAnum;
	}
	public boolean getJailed()
	{
		return jailed;
	}
	public void inJail()
	{
		jailed = true;
	}
	public void outJail(Board board)
	{
		board.setInfoText("Game", "Player is now out of jail!" , Color.MAGENTA);
		jailed = false;
	}
	public void gotJailCard()
	{
		getOutOfJail = true;
	}
	public boolean jailCard()
	{
		return getOutOfJail;
	}
	public void usedJailCard()
	{
		getOutOfJail = false;
	}
	public Link getFront()
	{
		return front;
	}
	//Returns Balance
	public int getBalance()
	{
		return playerBalance;
	}

	//Returns Name
	public String getName()
	{
		return playerName;
	}

	//Returns Player Number
	public int getPlayerNumber()
	{
		return playerNumber;
	}

	//Returns the color of players piece
	public Color getColor()
	{
		return colors[playerNumber];
	}

	//Returns players position and increments it by one
	public int movePos()
	{
		int temp = playerPosition;
		//circular array like algorithm, which makes a player go back to 0 when it hits 40
		playerPosition = (playerPosition+1)%40;
		return temp;
	}	

	//Reduce balance by amount
	public String spend(int amount)
	{

		playerBalance -= amount;
		String bal = "Remaining balance: "+this.getBalance();
		return bal;
	}
	// Checks for Bankruptcy
	public boolean isBankrupt()
	{
		return this.bankrupt;
	}

	// Gets current Position
	public int currentPosition()
	{
		return playerPosition;
	}

	public void setCurrentPosition(int pos)
	{
		playerPosition = pos;
	}

	//Adds amount to balance used for receiving rent
	public String receive(int amount)
	{
		playerBalance += amount;
		String bal = "Balance: "+this.getBalance();
		return bal;
	}

	// adds a property to the players linked list
	public void addproperty(Property element)
	{
		Link temp = new Link();
		temp.data = element;
		temp.nextlink = null;

		// if the list is empty the front and rear are set to the first member
		if(true == isEmpty())
		{
			front = rear = temp;
			front.nextlink = temp;
		}
		// otherwise the front stays put at the first variable and rear points to the next
		size++;
		rear.nextlink = temp;                                                                                                                                                                              
		rear = temp;
	}	

	// checks if the list is empty / if the player has any properties
	public boolean isEmpty()
	{
		if(front.data == null && rear.data == null)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}

	// returns the front/ first property of the list probaly never needed
	public Property firstP()
	{
		if(true == isEmpty())
		{
			return null;
		}
		else 
		{
			return front.data;
		}
	}




	// prints out the list of the players owned properties 
	public void propertList(Board temp, int ditotal)
	{
		Link current = new Link();
		current = front;
		if( this.propertyNum() == 0)
		{
			temp.setInfoText(this.getName(),"Player has no properties",this.getColor());
		}

		else
		{
			temp.setInfoText(this.getName(), "Properties", this.getColor());
			for(int i=0;i<propertyNum();i++)
			{
				// then the queue is printed out by call the front method followed by the dequeue method
				if(current.data.getImprovementLevel() >= 1 && !current.data.getColour().equals("grey"))
				{
					switch(current.data.getImprovementLevel())
					{
					case 1: temp.setInfoText(current.data.getName(),"Propert rent: £" + current.data.getRent(ditotal) + "  ("+current.data.getImprovementLevel()+" house)" , current.data.getColor());
					break;

					case 2: temp.setInfoText(current.data.getName(),"Propert rent: £" + current.data.getRent(ditotal) + "  ("+current.data.getImprovementLevel()+" houses)" , current.data.getColor());
					break;

					case 3: temp.setInfoText(current.data.getName(),"Propert rent: £" + current.data.getRent(ditotal) + "  ("+current.data.getImprovementLevel()+" houses)" , current.data.getColor());
					break;

					case 4: temp.setInfoText(current.data.getName(),"Propert rent: £" + current.data.getRent(ditotal) + "  ("+current.data.getImprovementLevel()+" houses)" , current.data.getColor());
					break;

					case 5: temp.setInfoText(current.data.getName(),"Propert rent: £" + current.data.getRent(ditotal) + "  (Hotel)" , current.data.getColor());
					break;
					}
				}
				else if(current.data.getImprovementLevel() >= 1 && current.data.getColour().equals("grey"))
				{
					temp.setInfoText(current.data.getName(),"Propert rent: £" + current.data.getRent(ditotal) + "  ("+(current.data.getImprovementLevel()+1)+" Stations)" , current.data.getColor());
				}
				else if(!current.data.isMortgaged())
				{
					temp.setInfoText(current.data.getName(),"Propert rent: £" + current.data.getRent(ditotal) , current.data.getColor());
				}
				else
				{
					temp.setInfoText(current.data.getName(),"Propert rent: £" + current.data.getRent(ditotal) + "  (Mortgaged)" , current.data.getColor());
				}
				current = current.nextlink;
			}
		}
	}

	// returns the amount of the players properties
	public int propertyNum()
	{
		if(true == isEmpty())
		{
			return 0;
		}

		else
		{
			return size;
		}
	}

	// this method checks the amount of properties of each color and updates the color counter array
	public void checkPropertyColors()
	{
		for(int i=0;i<Player_Property_Colors.length;i++)
		{
			Player_Property_Colors_Counter[i] = 0;
		}
		// if there is no properties
		if(isEmpty())
		{
			return;
		}

		Link current = new Link();
		current = front;
		//nested for loop to compare the property linked list and the Player_Property_Color array
		for(int i=0;i<propertyNum();i++)
		{	

			for(int j=0;j<Player_Property_Colors.length;j++)
			{
				// if a property color in the linked list matches the color of the property color array 
				if(current.data.getColour().equals(Player_Property_Colors[j] ) )
				{
					// then the counter in this member of the array is incremented
					Player_Property_Colors_Counter[j]++;
				}

			}
			current = current.nextlink;
		}

	}

	//this method checks how many of the players properties of a specific color have house built on them
	public int colorHouseCheck(String propC)
	{
		int housecount = 0;
		Link current = new Link();
		current = front;

		for(int i=0;i<propertyNum();i++)
		{	
			if(current.data.getColour().equals(propC))
			{
				// checks if the houseCheck() methods boolean is true .i.e the property has a house
				housecount = housecount + current.data.houseCheck();
			}
			current = current.nextlink;	
		}
		// returns the number of properties with houses
		return housecount;
	}

	// this method returns the amount of properties of a given color the player has 
	public int propColorCount(String color)
	{
		for(int i=0;i<Player_Property_Colors_Counter.length;i++)
		{
			if(Player_Property_Colors[i].equals(color) )
			{
				return Player_Property_Colors_Counter[i];
			}
		}
		return -1;
		// if they type in an invalid color -1 will be returned....ther'll be another if statement somewhere else for this
	}

	//this method destroys all the houses of a given color
	public void destroyAllHouses(String color)
	{
		Link current = new Link();
		current = front;
		for(int i=0;i<propertyNum();i++)
		{	
			if(current.data.getColour().equals(color))
			{
				if(current.data.houseCheck() > 0)
				{
					// decrements the house count
					current.data.destroyHouse();
				}
			}
			current = current.nextlink;	
		}
	}

	// calculates the players total assets which is the balance + property values atm
	public int calculateAssets()
	{
		if(isEmpty())
		{
			return this.getBalance();
		}

		Link current = new Link();
		current = front;
		int total = 0;

		// Loops through the players properties and adds the value of them together
		for(int i=0;i<propertyNum();i++)
		{	

			total += current.data.getValue();
			current = current.nextlink;
		}
		// adds the balance to the total property value
		total = total + this.getBalance();
		return total;
	}

	//Work out if the player has unmortgaged properties
	public boolean hasUnmorgageProperties(Board board)
	{
		boolean props = false;
		Link current = new Link();
		current = front;

		for(int i =0; i< propertyNum();i++)
		{
			if(!current.data.isMortgaged())
			{
				props = true;
			}
			current = current.nextlink;
		}
		if(props)
		{
			current = front;
			board.setInfoText("Game", "The following properties can be morgaged", Color.magenta);
			for(int i =0; i< propertyNum();i++)
			{
				if(!current.data.isMortgaged())
				{
					board.setInfoText(current.data.getName(),"for £"+current.data.getMortgageValue(),current.data.getColor() );
				}
				current = current.nextlink;
			}
		}
		return props;
	}

	//Sells off properties and maes player bankrupt
	public void DeclareBankruptcy()
	{
		Link current = new Link();
		current = front;

		for(int i =0; i< propertyNum();i++)
		{
			current.data.removeOwner();
			current = current.nextlink;
		}
		icon.setSize(0,0);
		bankrupt = true;
	}

	//to check to see if a player has a house or a hotel
	public boolean hasHousesOrHotels(Board board)
	{
		boolean housesorhotels = false;
		Link current = new Link();
		current = front;
		for(int i =0; i< propertyNum();i++)
		{
			if(current.data.getImprovementLevel()>0)
			{
				housesorhotels = true;
			}
			current = current.nextlink;
		}
		if(housesorhotels)
		{
			board.setInfoText("Game", "Player can sell houses on the following properties", Color.magenta);
			current = front;
			for(int i =0; i< propertyNum();i++)
			{
				if(current.data.getImprovementLevel()>0)
				{
					board.setInfoText(current.data.getName(), "", current.data.getColor());
					switch(current.data.getImprovementLevel())
					{
					case 1:board.setInfoText(current.data.getName(), "("+current.data.getImprovementLevel()+" house)", current.data.getColor());
					break;
					case 2:
					case 3:
					case 4: board.setInfoText(current.data.getName(), "("+current.data.getImprovementLevel()+" houses)", current.data.getColor());
					break;
					case 5: board.setInfoText(current.data.getName(), "("+current.data.getImprovementLevel()+" hotel)", current.data.getColor());
					break;
					}
				}
				current = current.nextlink;
			}
		}
		return housesorhotels;
	}

}