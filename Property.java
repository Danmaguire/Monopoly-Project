import java.awt.Color;

public class Property 
{
	private String name;
	private String colour;
	private Color col;
	private int value;
	private int[] rent;
	private int position;
	private boolean owned = false ;
	private Player owner = null;
	private int mortgageValue;
	private int propertyValue;
	private String nickname;
	private boolean mortgaged;

	// all the house, hotel and improvement variables
	private int housecount = 0;
	private boolean hotelbool = false;
	private int housecost;
	private int houseColournum;
	private int hotel = 0;
	private int improve = 0;	// this variable changes as the site is upgraded


	Property(String name,String nickname,int value, int[] rent, int position, 
			String colour,Color col,int morgageValue, int propertyValue, 
			int housecost, int houseColournum)
	{
		this.name = name;
		this.value = value;
		this.rent = rent;
		this.position = position;
		this.colour = colour;
		this.col = col;
		this.mortgageValue = morgageValue;
		this.nickname = nickname;
		mortgaged = false;
		this.propertyValue = propertyValue;
		this.housecost = housecost;
		this.houseColournum = houseColournum;
	}


	//Sets mortgaged
	public void setMortgaged(boolean val)
	{
		mortgaged = val;
	}

	//returns mortgage value
	public int getMortgageValue()
	{
		return mortgageValue;
	}

	//returns boolean for if house has been morgaged
	public boolean isMortgaged()
	{
		return mortgaged;
	}

	//Gets Property color
	public Color getColor()
	{
		return col;
	}

	// Gets Property Position
	public int getPosition()
	{
		return position;
	}

	//Gets Porpert Value
	public int getValue()
	{
		return value;
	}

	// Gets Property Name
	public String getName()
	{
		return name;
	}

	// Gets Property Name
	public String getNickname()
	{
		return nickname;
	}

	//Changes the property owned boolean to true
	public void own()
	{	// if the property is bought
		owned = true; 
	}

	//Sells properties on property
	public void sellHouses(Board board , int toSell)
	{
		this.improve -= toSell;
		this.housecount -= toSell;
		this.owner.receive(this.propertyValue*toSell);
		board.setInfoText("Game", this.getOwner().getName()+" has sold "+toSell+" properties on "+this.getName()+" and has received £"+this.propertyValue*toSell, Color.magenta);
	}
	public void sellHotel(Board board)
	{
		this.improve = 0;
		this.hotel = 0;
		this.hotelbool = false;
		this.owner.receive(this.propertyValue);
		board.setInfoText("Game", this.getOwner().getName()+" has sold a hotel on "+this.getName()+" and has received £"+this.propertyValue, Color.magenta);
	}
	//Checks if the property he is owned
	public boolean isOwned()
	{	
		return owned;
	}

	// Gets the onwnr
	public Player getOwner()
	{	// get the owner of the property
		return owner;
	}
	// Sets the onwer
	public void onwer(Player P)
	{	// makes a player the owner of this property
		// also takes the value of the property from the players balance
		owner = P;
		P.spend(this.value);
	}

	public void removeOwner()
	{
		this.owner = null;
		this.owned = false;
		this.improve = 0;
	}

	//Gets the rent of the property
	public int getRent(int ditotal)
	{	// for all properties but station and utilities
		if(!this.getColour().equals("white") && !this.getColour().equals("grey"))
		{
			return rent[improve];
		}
		//for utilities
		if(this.getColour().equals("white"))
		{
			if(this.getOwner().propColorCount(this.getColour()) == this.thisPropColourAmount() - 1)
			{
				return 4 * ditotal;
			}

			if(this.getOwner().propColorCount(this.getColour()) == this.thisPropColourAmount())
			{
				return 10 * ditotal;
			}
		}
		// for stations
		if(this.getColour().equals("grey"))
		{
			improve = (this.getOwner().propColorCount(this.getColour()) - 1);
			return rent[improve];
		}

		return 0; 
	}

	//returns currentLevel of improvement on property
	public int getImprovementLevel()
	{
		if(this.getColour().equals("grey"))
		{
			improve = (this.getOwner().propColorCount(this.getColour()) - 1);
			return improve;
		}
		return improve;
	}
	//Gets the color of the property
	public String getColour()
	{	// gets the color of the property
		return colour;
	}
	public void destroyHouse()
	{
		housecount--;
	}
	// this method returns the number of properties in the color of this property
	public int thisPropColourAmount()
	{
		return houseColournum;
	}
	// returns the cost to build a house on this property
	public int thisPropertHouseCost()
	{
		return housecost;
	}
	//if the owner owns all properties of the same color as this current property
	public boolean checkFourProp()
	{
		if(getOwner().propColorCount(this.getColour()) == thisPropColourAmount())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	// builds a house if the owner owns all four properties of the same color as this current property if there are 4 houses in this prop a hotel is built
	public void buildHouse(Board temp)
	{
		// if the player tries to build a house on a station or water works etc
		if(this.getColour().equals("grey") || this.getColor().equals("white"))
		{
			temp.setInfoText("Game" , "You can't build a house on this property" ,Color.magenta);
			return;
		}
		// if they don't have enough money 
		if( this.getOwner().getBalance() - this.thisPropertHouseCost() < 0 )
		{
			temp.setInfoText("Game" , "You don't have enough to build this property" ,Color.magenta);
			return;
		}
		if(this.houseCheck() == 4)
		{
			temp.setInfoText("Game" , "You've built the max number of houses already on this property" ,Color.magenta);
			this.buildHotel(temp);
			return;
		}
		// this is where the house is built 
		if(this.houseCheck() < 4 && this.checkFourProp() && !this.hotelCheck())
		{
			this.getOwner().spend(this.thisPropertHouseCost());
			this.housecount++;
			this.improve++;
			temp.setInfoText("Game" , "A house has been built on " + this.getName() ,Color.magenta);
		}
		if(this.hotelCheck())
		{
			temp.setInfoText("Game" , "A hotel has been built you've reached max improvement on " + this.getName() ,Color.magenta);
		}
		if(!this.checkFourProp())
		{
			temp.setInfoText("Game" , "You don't own all the properties of this color" ,Color.magenta);
		}
	}

	//checks if this current property has a house on it
	public int houseCheck()
	{
		return housecount;
	}

	// checks if the all the property with the same color as this current property have houses built on them
	public boolean checkBuildHotel()
	{
		if(this.houseCheck() == 4)
		{
			return true;
		}
		else
		{
			return false;
		}
	}




	// if there is a house on each property with the same color as this a hotel can be build on it 
	public void buildHotel(Board temp)
	{
		if(hotel == 1)
		{
			temp.setInfoText("Game" , "You've already built a hotel on this property" ,Color.magenta);
			return;
		}
		if( this.getOwner().getBalance() - this.thisPropertHouseCost() < 0 )
		{
			temp.setInfoText("Game" , "You don't have enough to build a hotel on this property" ,Color.magenta);
			return;
		}
		// if a player has a house built on all of the properties of a certain color they can build a hotel
		if(this.checkBuildHotel())
		{
			// all the houses are destroyed and a hotel is built 
			for(int i=0;i<this.housecount;i++)
			{
				this.destroyHouse();
			}
			hotelbool = true;
			improve++;
			temp.setInfoText("Game" , "All the houses on this propert have been destroyed and a hotel built on " + this.getName() ,Color.magenta);
		}
		else
		{
			temp.setInfoText("Game" , "Your not allowed to build a hotel on this property yet!" ,Color.magenta);
		}
	}

	//checks if there is a hotel on the property 
	public boolean hotelCheck()
	{
		return hotelbool;
	}
}