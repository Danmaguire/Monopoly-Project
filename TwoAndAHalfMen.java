
import java.util.ArrayList;

public class TwoAndAHalfMen implements Bot {
	
	// The public API of YourTeamName must not change
	// You cannot change any other classes
	// YourTeamName may not alter the state of the board or the player objects
	// It may only inspect the state of the board and the player objects
	private boolean rolled = false;
	private boolean rollDouble = false;
	private int rollCount = 0;
	private int doubleCount=0;
	private BoardAPI board;
	private PlayerAPI player;
	private DiceAPI dice;
	
	//delete
	int mortgaged = 0;
	
	 TwoAndAHalfMen (BoardAPI board, PlayerAPI player, DiceAPI dice) {
		this.board = board;
		this.player = player;
		this.dice = dice;
		return;
		
	}
	
	public String getName () {
		return "done";
	}
	
	//Roll doubles? -- avoid rolling three doubles
	//Deal with jail -- different for early and late
	//If no money? (mortgage properties and sell houses)
	//Buying strategy -- whether to buy stations and utilities and to try get all houses in color group and stops others doing that
	//Build 3 houses on each of the properties
	
	public String getCommand () {
		if(!rolled)
		{
			//if the player rolls a double and to whether to roll again or not
			if(dice.isDouble()){
				rollCount++;
				doubleCount++;
				rollDouble = true;
				rolled = true;
				//if its early we dont want to go to jail so we stop rolling if we get two doubles in a row
				if(isEarly()){
					if(doubleCount<3){
						
						return "roll";
					}
					else{
						
						return "done";
					}
				}
				//if its late in the game we dont mind if we got to jail on the third double roll so we roll regardless
					else if(!isEarly()){
						return "roll";
					}
			}
			else {
				//reset double count back to 0 if a double wasnt rolled
				doubleCount =0;
				rolled = true;
				rollCount++;
				return "roll";
			}
		}

		if(board.isProperty(player.getPosition()))
		{
			if(board.isStation(board.getProperty(player.getPosition()).getShortName()))
			{
				if(isEarly() && player.getNumStationsOwned() > 1 && !board.getProperty(player.getPosition()).isOwned() && (player.getBalance()-300) > board.getProperty(player.getPosition()).getPrice())
				{
					return "buy";
				}
			}
			if(!board.isUtility(board.getProperty(player.getPosition()).getShortName()) && !board.getProperty(player.getPosition()).isOwned() && (player.getBalance()-300) > board.getProperty(player.getPosition()).getPrice())
			{
				return "buy";
			}
		}
		if(canBuild())
		{
			//Makes a list of properties that can be built on
			ArrayList<Property> buildableProps = new ArrayList<Property>();
			for(int i=0;i<player.getProperties().size();i++){
				if(!board.isStation(player.getProperties().get(i).getShortName()) && !board.isUtility(player.getProperties().get(i).getShortName()) && !player.getProperties().get(i).isMortgaged())
				{
					Site site = (Site) player.getProperties().get(i);
					if (player.isGroupOwner(site))
					{
						buildableProps.add(player.getProperties().get(i));
					}
				}
			}
			//Builds up to 3 houses on any of the available properties
			for(int j=0;j<buildableProps.size();j++){
				Site temp = (Site) buildableProps.get(j);
				if(temp.getNumBuildings() < 3)
				{
					for(int build=0;build<3-temp.getNumBuildings();build++){
						if((player.getBalance()-300) > temp.getBuildingPrice() && temp.canBuild(1)){
							return "build "+buildableProps.get(j).getShortName()+" "+1;
						}
					}
				}
				
				
			}
		}
		
		//if player goes to jail
		if(player.isInJail()){
		//if player has get out of free card use it if its early in the game otherwise wait it out
			if(isEarly() && player.hasGetOutOfJailCard()){
				return "card";
				}
			else if(isEarly() && !(player.hasGetOutOfJailCard())){
				return "pay";
				}
			else{
				if(!rolled){
					return "roll";
					}	
				}
		}
				
		if(player.getBalance() > 300 && mortgagedProperties().size() > 0)
		{
			for(int i=0;i<mortgagedProperties().size();i++){
				if(mortgagedProperties().get(i).getMortgageValue() < player.getBalance()-300){
					mortgaged--;
					return "redeem "+mortgagedProperties().get(i).getShortName();
				}
				
			}
		}
		if(player.getBalance() < 0)
		{
			if(player.getNumHousesOwned() > 0){
				int curr = 0;
				while(player.getBalance() < 300 && !demolishableProperties().get(curr).equals(null)){
					Site site = (Site) demolishableProperties().get(curr);
					while(player.getBalance() < 300 && site.hasBuildings() && !demolishableProperties().get(curr).isMortgaged()){
						System.out.println("demolsih");
						return "demolish "+demolishableProperties().get(curr).getShortName()+" 1";
					}
					curr++;
				}
			}
			if(mortgageableProperties().size() > 0){
				int i=0;
				while(player.getBalance() < 300 && !mortgageableProperties().get(i).equals(null)){
					mortgaged++;
					return "mortgage "+mortgageableProperties().get(i++).getShortName();
				}
			}
			if(player.getBalance() < 0){
				return "bankrupt";
			}
		}

		System.out.println(rollCount + " balance  = "+player.getBalance()+" unmortgaged properties = "+(player.getNumProperties()-mortgaged)+"  build = "+canBuild()+"  houses "+player.getNumHousesOwned());
		rolled = false; 
		rollDouble = false;
		return "done";
	}

	//Checks if the player is able to build any houses 
	private boolean canBuild()
	{ 
		for(int i=0;i<player.getProperties().size();i++){
			if(!board.isStation(player.getProperties().get(i).getShortName()) && !board.isUtility(player.getProperties().get(i).getShortName()))
			{
				Site site = (Site) player.getProperties().get(i);
				if (player.isGroupOwner(site))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	private ArrayList<Property> mortgageableProperties()
	{
		ArrayList<Property> temp = new ArrayList<Property>();
		for(int i=0;i<player.getProperties().size();i++){
			if(!player.getProperties().get(i).isMortgaged()){
				temp.add(player.getProperties().get(i));
			}
		}
		return temp;
	}
	
	private ArrayList<Property> mortgagedProperties()
	{
		ArrayList<Property> temp = new ArrayList<Property>();
		for(int i=0;i<player.getProperties().size();i++){
			if(player.getProperties().get(i).isMortgaged()){
				temp.add(player.getProperties().get(i));
			}
		}
		return temp;
	}
	
	private ArrayList<Property> demolishableProperties()
	{
		ArrayList<Property> temp = new ArrayList<Property>();
		for(int i=0;i<player.getProperties().size();i++){
			if(!board.isStation(player.getProperties().get(i).getShortName()) && !board.isUtility(player.getProperties().get(i).getShortName()))
			{
				Site site = (Site) player.getProperties().get(i);
				if(site.getNumBuildings() > 0){
					temp.add(player.getProperties().get(i));
				}
			}
		}
		return temp;
	}
	
	private boolean isEarly()
	{
		//player.
		double threshold = .70;
		double totalProps = 28;
		double owned = 0;
		for(int i=0;i<39;i++){
			if(board.isProperty(i)){
				if(board.getProperty(i).isOwned()){
					owned++;
				}
			}
		}
		double percentOwned = owned/totalProps;
		if(percentOwned >= threshold){
			return false;
		}
		return true;
	}
	
	public String getDecision () 
	{
		if(isEarly()){
			return "chance";
		}
		else{
			return "pay";
		}
	}
	
}

