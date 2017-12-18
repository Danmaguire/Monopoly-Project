
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/*
 * Holds a number of values used throughout the game
 */
public class Values 
{

	public static ChanceCard[] chanceArr = new ChanceCard[Constants.NUM_CHANCE];
	public static CommunityChestCard[] communityArr = new CommunityChestCard[Constants.NUM_COMMUNITY];

	public static ChanceCardDeck chanceDeck;
	public static CommunityChestDeck communityDeck;

	public static Player[] playerArr;
	public static String[] PROPERTY_NAMES;
	public static String[] TAX_NAMES;
	public static int[] TAX_FINES = new int[2];
	public static String[] PROPERTY_NICKNAMES;
	public static int noOfPlayers = 0;
	static boolean validInput = false;
	static String input;
	public static int count = 0;
	public static int doublecount = 0;
	public static int MaxRoll = 0;
	public static int playersGo = 0;
	public static Dice gameDice;
	public static int newPos;


	//Booleans involoved in the actual game
	public static boolean paidGo = false;
	public static boolean moved = false;
	public static boolean gameOver = false;
	public static boolean endGame = false;
	public static boolean endGo   = false;
	public static boolean rolled = false;
	public static boolean rolledDouble = false;
	public static boolean timerDone = false;
	public static boolean rentpay = false; 
	public static boolean mustDraw = false;

	public static boolean fineOwed = false;
	public static int fineAmount = 0;

	//A few of the values are read from the txt file "input".txt 
	Values(String input) throws FileNotFoundException
	{
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(new File("src/"+input+".txt"));
		ArrayList<String> lines = new ArrayList<String>();
		int count = 0;
		while (count < Constants.NUM_PROPERTIES && sc.hasNextLine()) {
			lines.add(sc.nextLine());
			count++;
		}
		PROPERTY_NAMES = lines.toArray(new String[0]);
		lines = new ArrayList<String>();
		count = 0;
		while (count < Constants.NUM_TAX && sc.hasNextLine()) {
			lines.add(sc.nextLine());
			count++;
		}
		TAX_NAMES = lines.toArray(new String[0]);

		lines = new ArrayList<String>();
		count = 0;
		while (count < Constants.NUM_TAX && sc.hasNextLine()) {
			TAX_FINES[count] = Integer.parseInt(sc.nextLine());
			count++;
		}

		lines = new ArrayList<String>();
		count = 0;
		while (count < Constants.NUM_PROPERTIES && sc.hasNextLine()) {
			lines.add(sc.nextLine());
			count++;
		}
		PROPERTY_NICKNAMES = lines.toArray(new String[0]);

		for(int i=0;i<Constants.NUM_CHANCE;i++)
		{
			chanceArr[i] = new ChanceCard(Constants.CHANCE_CARDS[i],i);
		}

		for(int i=0;i<Constants.NUM_CHANCE;i++)
		{
			communityArr[i] = new CommunityChestCard(Constants.COMMUNITY_CARDS[i],i);
		}

		chanceDeck = new ChanceCardDeck(chanceArr);
		communityDeck = new CommunityChestDeck(communityArr);

	}


}
