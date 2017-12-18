import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.Timer;


public class NewGame
{

	NewGame(Board board)
	{
		board.setInfoText("Game","Enter number of Players: ",Color.cyan);
		//Runnable checks that the user has inputed a valid number between 2 and 6
		Runnable doGetNum = new Runnable() 
		{
			public void run() 
			{
				//Checks for user input
				if(!board.getInputText().equals("") && !board.getInputText().equals("Enter Commands here"))
				{
					Values.input=board.getInputText();
					board.setInfoText("User Input",Values.input,Color.MAGENTA);
					int inputAsNum = 0;
					//Try to parse integer from the user input
					try
					{
						inputAsNum= Integer.parseInt(Values.input);
						//If it is between 2 and 6 the input is valid
						if(inputAsNum <= 6 && inputAsNum >= 2)
						{
							Values.noOfPlayers = inputAsNum;
							Values.validInput = true;
						}
						//Otherwise a warning is printed to the info pane
						else
						{
							board.setInfoText("Game","Please enter a number between 2 - 6",Color.cyan);
						}
					}
					//If the input throws an exception when parsed a warning is printed to the info pane
					catch (NumberFormatException e1)
					{
						board.setInfoText("Game","Please enter a number between 2 - 6",Color.cyan);
					}


				}
				board.commandPanelField.setText("Enter Commands here");
			}
		};

		//Runnable checks that the user has inputed a valid number between 2 and 6
		Runnable doGetNumEnterBtn = new Runnable() 
		{
			public void run() 
			{
				//Checks for user input
				if(!board.getInputText().equals("") && !board.getInputText().equals("Enter Commands here"))
				{
					Values.input=board.getInputText();
					board.setInfoText("User Input",Values.input,Color.MAGENTA);
					int inputAsNum = 0;
					//Try to parse int from the user input
					try
					{
						inputAsNum= Integer.parseInt(Values.input);
						//If it is between 2 and 6 the input is valid
						if(inputAsNum <= 6 && inputAsNum >= 2)
						{
							Values.noOfPlayers = inputAsNum;
							Values.validInput = true;
						}
						//Otherwise a warning is printed to the info pane
						else
						{
							board.setInfoText("Game","Please enter a number between 2 - 6",Color.cyan);
						}
					}
					//If the input throws an exception when parsed a warning is printed to the info pane
					catch (NumberFormatException e1)
					{
						board.setInfoText("Game","Please enter a number between 2 - 6",Color.cyan);
					}


				}
				board.commandPanelField.setText("");
			}
		};

		//Runnable gets a valid name
		Runnable doGetName = new Runnable()
		{

			@Override
			public void run() 
			{
				boolean sameName = false;
				String id = "User Input";
				//Checks for user input
				if(!board.getInputText().equals("") && !board.getInputText().equals("Enter Commands here"))
				{
					Values.input=board.getInputText();
					board.setInfoText(id,Values.input,Color.magenta);
					if(Values.count == 0)
					{
						Values.playerArr[Values.count] = new Player(Values.input);
						String icon = "Thimble";
						board.setInfoText("player","Playing piece "+icon,Values.playerArr[Values.count].getColor());
						Values.validInput = true;
					}
					else 
					{	
						//Checks name has not already been taken
						for(int i=0; i<Values.count;i++)
						{
							if(Values.playerArr[i].getName().equals(Values.input))
							{
								sameName = true;
								//If the name is taken a warning is printed to the info pane
								board.setInfoText("Game", "Please enter a different name - "+Values.input+" has already been taken", Color.cyan);

							}
						}
						//If name not taken the players name is set to the input
						if(!sameName)
						{
							Values.playerArr[Values.count] = new Player(Values.input);
							String icon = "";
							switch(Values.count)
							{
							case 1:	icon = "Boat";
							break;
							case 2:	icon = "Dog";
							break;
							case 3:	icon = "Car";
							break;
							case 4:	icon = "Top-hat";
							break;
							case 5:	icon = "Shoe";
							break;
							}
							board.setInfoText("player","Playing piece "+icon,Values.playerArr[Values.count].getColor());
							Values.validInput = true;
						}
					}
				}
				//
				board.commandPanelField.setText("Enter Commands here");
			}
		};

		//Runnable gets a valid name
		Runnable doGetNameEnterBtn = new Runnable()
		{

			@Override
			public void run() 
			{
				boolean sameName = false;
				String id = "User Input";
				//Checks for user input
				if(!board.getInputText().equals("") && !board.getInputText().equals("Enter Commands here"))
				{
					Values.input=board.getInputText();
					board.setInfoText(id,Values.input,Color.magenta);
					if(Values.count == 0)
					{
						Values.playerArr[Values.count] = new Player(Values.input);
						String icon = "Thimble";
						board.setInfoText(Values.playerArr[Values.count].getName(),"Playing piece "+icon,Values.playerArr[Values.count].getColor());
						Values.validInput = true;
					}
					else 
					{	
						//Checks name has not already been taken
						for(int i=0; i<Values.count;i++)
						{
							if(Values.playerArr[i].getName().equals(Values.input))
							{
								sameName = true;
								//If the name is taken a warning is printed to the info pane
								board.setInfoText("Game", "Please enter a different name - "+Values.input+" has already been taken", Color.cyan);
							}
						}
						//If name not taken the players name is set to the input
						if(!sameName)
						{
							Values.playerArr[Values.count] = new Player(Values.input);
							String icon = "";
							switch(Values.count)
							{
							case 1:	icon = "Boat";
							break;
							case 2:	icon = "Dog";
							break;
							case 3:	icon = "Car";
							break;
							case 4:	icon = "Top-hat";
							break;
							case 5:	icon = "Shoe";
							break;
							}
							board.setInfoText(Values.playerArr[Values.count].getName(),"Playing piece "+icon,Values.playerArr[Values.count].getColor());
							Values.validInput = true;
						}
					}
				}
				//
				board.commandPanelField.setText("");
			}
		};

		//numListener implements the doGetNum runnable
		ActionListener numListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SwingUtilities.invokeLater(doGetNum);
			}
		};
		ActionListener numListener2 = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SwingUtilities.invokeLater(doGetNumEnterBtn);
			}
		};


		//Ads the numListener to the command field and button
		board.commandPanelField.addActionListener(numListener2);
		board.commandPanelButton.addActionListener(numListener);
		//Needs to be redone
		while(!Values.validInput)
		{
			System.out.print("");
		}
		//Removes the numListner from the field and button
		board.commandPanelButton.removeActionListener(numListener);
		board.commandPanelField.removeActionListener(numListener2);

		//Checks For valid input from user
		Values.validInput = false;

		//Creates player array for specified number of Players
		Values.playerArr = new Player[Values.noOfPlayers];

		//Creates an action listener that runs doGetName
		ActionListener nameListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SwingUtilities.invokeLater(doGetName);
			}
		};
		ActionListener nameListener2 = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SwingUtilities.invokeLater(doGetNameEnterBtn);
			}
		};


		//Adds the nameListner to the button and field and gets the names for all the players
		board.commandPanelField.addActionListener(nameListener2);
		board.commandPanelButton.addActionListener(nameListener);
		for(int i=0;i<Values.noOfPlayers;i++)
		{
			Values.count = i;
			board.setInfoText("Game", "Please enter name for Player "+(i+1), Color.cyan);
			while(!Values.validInput)
			{
				System.out.println("");
			}
			Values.validInput =false;
		}
		//Removes the name listener
		board.commandPanelButton.removeActionListener(nameListener);
		board.commandPanelField.removeActionListener(nameListener2);

		//Prints blank space to the info pane
		board.setInfoText("", "", Color.white);
		board.setInfoText("", "", Color.white);

		Values.count = 0;



		//The dice is rolled for each player to establish who will go first
		board.setInfoText("Game", "Players will now roll the dice to see who goes first", Color.cyan);
		Timer timer = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Dice newGameDie = new Dice(board);
				board.setInfoText(Values.playerArr[Values.count].getName(), ""+newGameDie.totalValue(), Values.playerArr[Values.count].getColor());

				if(newGameDie.totalValue() > Values.MaxRoll)
				{
					Values.MaxRoll = newGameDie.totalValue();
					Player temp;
					temp = Values.playerArr[0];
					Values.playerArr[0] = Values.playerArr[Values.count];
					Values.playerArr[Values.count] = temp;
				}
				Values.count++;
				if(Values.count == Values.noOfPlayers)
				{
					Values.timerDone = true;
					((Timer)e.getSource()).stop();
				}
			}
		});

		timer.start();
		while(!Values.timerDone)
		{
			System.out.print("");
		}
		//Game is started
		board.boardPicPanel.remove(Board.Die1);
		board.boardPicPanel.remove(Board.Die2);
		board.setInfoText("Game", Values.playerArr[0].getName()+" rolled the highest number and will go first", Color.cyan);
		board.setInfoText("", "", Color.white);
		board.setInfoText("", "", Color.white);
		board.setInfoText("Game", "Order of Players", Color.cyan);
		for(int i = 0;i< Values.noOfPlayers;i++)
		{
			board.setInfoText(Values.playerArr[i].getName()," The bank has given "+Values.playerArr[i].getBalance(), Values.playerArr[i].getColor());
		}
		for(int j=0;j<Values.noOfPlayers;j++)
		{
			Values.playerArr[j].jailArrNum(j);
		}
		board.setInfoText("", "", Color.white);
		board.setInfoText("", "", Color.white);
		Game.gameStart(board);


	}
}