import java.awt.Dimension;

import javax.swing.JLabel;

class Dice
{
	//Holds the value for the two die
	private int dice1Value;
	private int dice2Value;
	private Board board;


	//Constructor for Dice rolls them
	Dice(Board board)
	{
		this.board = board;
		Board.Die1.setSize(new Dimension(60,60));
		Board.Die2.setSize(new Dimension(60,60));
		roll();
	}

	//Rolls the Dice
	public void roll()
	{
		//cast to int as dice values would be a double, multiply by 6 as there is 6 possibilities
		dice1Value = 1 + (int)(Math.random()*6.0);
		die1Icon(dice1Value);
		dice2Value = 1 + (int)(Math.random()*6.0);
		die2Icon(dice2Value);
	}

	//Returns value for the first die
	public int dice1()
	{
		return dice1Value;
	}

	//Returns value for the second die
	public int dice2()
	{
		return dice2Value;
	}

	////Returns the sum of the two dice
	public int totalValue()
	{
		return dice1Value + dice2Value;
	}

	//Returns true if the dice are the same number
	public boolean isSame()
	{
		if(dice1Value == dice2Value)
		{
			return true;
		}
		else return false;
	}

	public void die1Icon(int a)
	{
		board.boardPicPanel.remove(Board.Die1);
		switch(a)
		{
		case 1: Board.Die1 = new JLabel(board.Dice1);
		break;
		case 2: Board.Die1 = new JLabel(board.Dice2);
		break;
		case 3:Board.Die1 = new JLabel(board.Dice3);
		break;
		case 4:Board.Die1 = new JLabel(board.Dice4);
		break;
		case 5:Board.Die1 = new JLabel(board.Dice5);
		break;
		case 6:Board.Die1 = new JLabel(board.Dice6);
		break;
		}
		board.boardPicPanel.add(Board.Die1);
		Board.Die1.setLocation(400,100);
		Board.Die1.setSize(new Dimension(60,60));
	}


	public void resetDiceIcons()
	{
		board.boardPicPanel.remove(Board.Die1);
		board.boardPicPanel.remove(Board.Die2);
		Board.Die1 = new JLabel(board.DieB);
		Board.Die2 = new JLabel(board.DieB);
		board.boardPicPanel.add(Board.Die1);
		Board.Die1.setLocation(400,100);
		Board.Die1.setSize(new Dimension(60,60));
		board.boardPicPanel.add(Board.Die2);
		Board.Die2.setLocation(465,100);
		Board.Die2.setSize(new Dimension(60,60));
	}

	public void die2Icon(int a)
	{
		board.boardPicPanel.remove(Board.Die2);
		switch(a)
		{
		case 1: Board.Die2 = new JLabel(board.Dice1);
		break;
		case 2: Board.Die2 = new JLabel(board.Dice2);
		break;
		case 3:Board.Die2= new JLabel(board.Dice3);
		break;
		case 4:Board.Die2 = new JLabel(board.Dice4);
		break;
		case 5:Board.Die2 = new JLabel(board.Dice5);
		break;
		case 6:Board.Die2 = new JLabel(board.Dice6);
		break;
		}
		board.boardPicPanel.add(Board.Die2);
		Board.Die2.setLocation(465,100);
		Board.Die2.setSize(new Dimension(60,60));

	}
}