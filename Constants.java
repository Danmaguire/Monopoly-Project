import java.awt.Color;


//Class holds the constants such as the property names positions and rents
class Constants
{	
	public static final int NUM_SQUARES = 40;
	public static final int NUM_PROPERTIES = 28;
	public static final int NUM_TAX = 2;

	public static final int RADIUS = 20;

	public static final String[] TAX_NAMES = 
		{
				"Income Tax",
				"Super Tax"
		};

	public static final int[] TAX_FINES =
		{
				200,100	
		};

	public static final int[] TAX_POS = 
		{
				4,38	
		};
	public static final String[] PROPERTY_NAMES = 
		{
				"Old Dan Rd","Whitechapel Rd","King's Cross Station", "The Angel Islington","Euston Rd","Pentonville Rd",
				"Pall Mall","Electric Co","Whitehall","Northumberland Ave","Marylebone Station","Bow St","Marlborough St",
				"Vine St","Strand","Fleet St","Trafalgar Sq","Fenchurch St Station","Leicester Sq","Coventry St","Water Works",
				"Piccadilly","Regent St","Oxford St","Bond St","Liverpool St Station","Park Lane","Maguire Avenue"
		};

	public static final String[] PROPERTY_NICKNAMES =
		{
				"Dan", "Whitechapel","Kings", "Angel", "Euston", 
				"Pentonville","Mall", "Electric","Whitehall","Northumberland",
				"Marylebone","Bow", "Marlborough","Vine", "Strand",
				"Fleet", "Trafalgar", "Fenchurch", "Leicester", "Coventry",
				"Water","Piccadilly","Regent","Oxford", "Bond",
				"Liverpool", "Park", "Maguire"
		};

	public static final String[] PROPERTY_COLOURS = 
		{
				// Colors of each property 
				// utilities are white
				"brown","brown","grey", "blue","blue","blue",
				"pink","white","pink","pink","grey","orange","orange",
				"orange","red","red","red","grey","yellow","yellow","white",
				"yellow","green","green","green","grey","navy","navy"
		};

	public static Color brown = Color.decode("#8B4513");
	public static Color grey = Color.gray;
	public static Color blue = Color.blue;
	public static Color orange = Color.decode("#FFA500");
	public static Color pink = Color.decode("#e200f0");
	public static Color red = Color.red;
	public static Color yellow = Color.yellow;
	public static Color green = Color.green;
	public static Color navy = Color.decode("#2A044A");

	public static final Color[] PROPERTY_COLS = 
		{
				brown,brown,grey, blue,blue,blue,
				pink,grey,pink,pink,grey,orange,orange,
				orange,red,red,red,grey,yellow,yellow,grey,
				yellow,green,green,green,grey,navy,navy	
		};

	public static final int[] MORTGAGE_VALUES = 
		{
				50,50,100,50,50,
				60,70,75,70,80,
				100,90,90,100,110,
				110,120,100,150,150,
				75,150,200,200,200,
				100,175,200
		};

	public static final int[] HOUSE_COSTS =
		{
				30,30,0,50,50,
				50,100,0,100,100,
				0,100,100,100,150,
				150,150,0,150,150,
				0,140,150,150,160,
				0,200,200
		};

	public static final int[] PROPERTY_VALUES = 
		{
				60,60,200,100,100,120,
				140,150,140,160,200,180,
				180,200,220,220,240,200,
				260,260,150,280,300,300,
				320,200,350,400
		};

	public static final int[][] PROPERTY_RENTS = 
		{
				{2,10,30,90,160,250},{4,20,60,180,360,450},
				{25,50,100,200,200,200},{6,30,90,270,400,550},
				{6,30,90,270,400,550},{8,40,100,300,450,600},
				{10,50,150,450,625,750},{4,10,0,0,0,0},
				{10,50,150,450,625,750},{12,60,180,500,700,900},
				{25,50,100,200,200,200},
				{14,70,200,550,750,950},{14,70,200,550,750,950},
				{16,80,220,600,800,1000},{18,90,250,700,875,1050},
				{18,90,250,700,875,1050},{20,100,300,750,925,1100},
				{25,50,100,200,200,200},{22,110,330,800,975,1150},
				{22,110,330,800,975,1150},{4,10,0,0,0,0},
				{22,120,360,850,1025,1200},
				{26,130,390,900,1100,1275},{26,130,390,900,1100,1275},
				{28,150,450,1000,1200,1400},{25,50,100,200,200,200},
				{35,175,500,1100,1300,1500}, {50,200,600,1400,1700,2000}
		};

	//{w,h}
	public static final int[][] SQUARE_CORDS = 
		{
				{725, 660}, {645, 660}, {585, 660}, {527, 660}, {460, 660}, {384, 660}, {307, 660}, {238, 660}, {180, 660}, {110, 660}, 
				{40 , 655}, {40 , 590}, {40 , 540}, {40 , 475}, {40 , 415}, {40 , 345}, {40 , 285}, {40 , 235}, {40 , 175}, {40 , 115}, 
				{40 , 30}, {110 ,30},	{180 ,30}, 	{238,30}, 	{307 ,30}, 	{384 ,30}, 	{460 ,30}, 	{527 ,30}, 	{590 ,30}, {645 ,30}, 
				{725 ,30}, {725, 115}, {725, 175}, {725, 235}, {725, 285}, {725, 345}, {725, 415}, {725, 475}, {725, 540}, {725, 590}
		};


	public static final int TYP_GO = 0;
	public static final int TYP_SITE = 1;
	public static final int TYP_STATION = 2;
	public static final int TYP_UTILITY = 3;
	public static final int TYP_COMMUNITY = 4;
	public static final int TYP_CHANCE = 5;
	public static final int TYP_JAIL = 6;
	public static final int TYP_PARKING = 7;
	public static final int TYP_GOTO_JAIL = 8;
	public static final int TYP_TAX = 9;


	public static final String[] SQUARE_NAMES =
		{
				"Go",PROPERTY_NAMES[0],"Community",PROPERTY_NAMES[1],"Tax",
				PROPERTY_NAMES[2],PROPERTY_NAMES[3],"Chance",PROPERTY_NAMES[4],PROPERTY_NAMES[5],
				"Jail",PROPERTY_NAMES[6],PROPERTY_NAMES[7],PROPERTY_NAMES[8],PROPERTY_NAMES[9],
				PROPERTY_NAMES[10],PROPERTY_NAMES[11],"Community",PROPERTY_NAMES[12],PROPERTY_NAMES[13],
				"Parking",PROPERTY_NAMES[14],"Chance",PROPERTY_NAMES[15],PROPERTY_NAMES[16],
				PROPERTY_NAMES[17],PROPERTY_NAMES[18],PROPERTY_NAMES[19],PROPERTY_NAMES[20],PROPERTY_NAMES[21],
				"Go to Jail",PROPERTY_NAMES[22],PROPERTY_NAMES[23],"Community",PROPERTY_NAMES[24],
				PROPERTY_NAMES[25],"Chance",PROPERTY_NAMES[26],"Tax",PROPERTY_NAMES[27]
		};

	public static final int[] PROPERTY_COLOURS_NUMBERS =
		{
				2,2,4,3,3,3,
				3,2,3,3,4,3,3,
				3,3,3,3,4,3,3,2,
				3,3,3,3,4,2,2
		};

	public static final int[] PROPERTY_HOUSE_COSTS = 
		{
				50,50,0,50,50,50,100,0,100,100,0,100,100,
				100,150,150,150,0,150,150,0,150,200,200,200,
				0,200,200
		};

	public static final int[] PROPERTY_POSITIONS = 
		{
				1,3,5,6,
				8,9,11,12,
				13,14,15,16,
				18,19,21,23,
				24,25,26,27,
				28,29,31,32,
				34,35,37,39
		};

	public static final int[] SQUARE_TYPES = 
		{
				TYP_GO, TYP_SITE, TYP_COMMUNITY, TYP_SITE, TYP_TAX, 
				TYP_STATION, TYP_SITE,TYP_CHANCE, TYP_SITE, TYP_SITE,
				TYP_JAIL, TYP_SITE, TYP_UTILITY, TYP_SITE, TYP_SITE, 
				TYP_STATION, TYP_SITE,TYP_COMMUNITY, TYP_SITE, TYP_SITE,
				TYP_PARKING, TYP_SITE, TYP_CHANCE, TYP_SITE, TYP_SITE, 
				TYP_STATION, TYP_SITE,TYP_SITE, TYP_UTILITY, TYP_SITE,
				TYP_GOTO_JAIL, TYP_SITE, TYP_SITE, TYP_COMMUNITY, TYP_SITE, 
				TYP_STATION,TYP_CHANCE, TYP_SITE, TYP_TAX, TYP_SITE
		};

	public static String[] CHANCE_CARDS = 
		{
				"Advance to Maguire Avenue",
				"Advance to Go",
				"You are Assessed for Street Repairs £40 per House £115 per Hotel ",
				"Go to Jail. Move Directly to Jail. Do not pass \"Go\" Do not Collect £200",
				"Bank pays you Dividend of £50",
				"Go back 3 Spaces" ,
				"Pay School Fees of £150",
				"Make General Repairs on all of Your Houses - For each House pay £25 - For each Hotel pay £100",
				"Speeding Fine £15",
				"You have won a Crossword Competition Collect £100",
				"Your Building and Loan Matures Collect £150",
				"Get out of Jail Free",
				"Advance to Trafalgar Square If you Pass \"Go\" Collect £200", 
				"take a Trip to Marylebone Station and if you Pass \"Go\" Collect £200",
				"Advance to Pall Mall If you Pass \"Go\" Collect £200",
				"\"Drunk in Charge\" Fine £20",
		};

	public static int NUM_CHANCE = 16;

	public static String[] COMMUNITY_CARDS = 
		{
				"Income Tax refund Collect £20",
				"From Sale of Stock you get £50",
				"It is Your Birthday Collect £10 from each Player",
				"Receive Interest on 7% Preference Shares, £25",
				"Get out of Jail Free" ,
				"Advance to \"Go\"" ,
				"Pay Hospital £100" ,
				"You have Won Second Prize in a Beauty Contest Collect £10", 
				"Bank Error in your Favour Collect £200",
				"You Inherit £100",
				"Go to Jail. Move Directly to Jail. Do not Pass \"Go\". Do not Collect £200" ,
				"Pay your Insurance Premium £50",
				"Pay a £10 Fine or Take a \"Chance\"",
				"Doctor's Fee Pay £50" ,
				"Go Back to Old Dan Road", 
				"Annuity Matures Collect £100", 
		};

	public static int NUM_COMMUNITY = 16;

	public static final String[] HELP_COMMANDS = 
		{
				"Roll : to roll the dice if it is your turn",
				"Info : to find out information about what property you've landed on",
				"Balance : to find out how much money you have left",
				"Property : to show which person owns the property you have landed on",
				"Pay : Allows player to pay fine whie in prison",
				"Done : is to finish the persons turn",
				"Quit : exits the game but first shows who has the most assets",
				"Mortgage 'Property Name': allows player to mortgage the specified property",
				"Redeem 'Property Name': Allows a player to redeem a previously mortgage property",
				"Demolish 'Property Name' 'Number of properties' : Demolishes the number of property from the named square",
				"Bankrupt : Allows a player to declare bankruptcy if they don't have enough assest to continue playing the game",
				"Available : Prints currently available properties",
				"Card : Allows layer to use a get out f jail free card to exit prison"
				

		};
}