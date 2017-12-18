import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.awt.geom.Point2D;
import java.lang.*;
import java.awt.Toolkit;
import java.awt.*;
import java.awt.Container;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.*;
import javax.swing.JComponent;

import javax.swing.text.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/*This is the Board class*/
class Board extends JFrame implements MouseListener
{
	//The Frame and components
	private JPanel p;
	private JPanel CMD;
	public JPanel boardP;
	private JPanel GAMEPANEL;
	public JButton cmdButton;
	public JLabel lab;
	private JTextPane infoPane;
	private JTextField cmdField;

	public String InputUser;
	
	
	//Gets Width and Height of screen
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension d = tk.getScreenSize();
	int screenH = d.height;
	int screenW = d.width;
	
	//Constructor
	Board()
	{
		Panels();
		JFrame temp = new JFrame();
		temp.setExtendedState(JFrame.MAXIMIZED_BOTH);
		temp.setVisible(true);
		temp.add(boardP, BorderLayout.EAST);
		temp.add(p, BorderLayout.WEST);
		temp.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//Function creates and sets up content for the panel
	public void Panels()
	{
		GAMEPANEL = new JPanel(new BorderLayout());
		GAMEPANEL.setPreferredSize(new Dimension(screenW,screenH));
		
		//Board Panel creation
		boardP = new JPanel( new BorderLayout() );
		
		// Setting panel size and colour 
		boardP.setPreferredSize(new Dimension(screenH, screenH));
		ImageIcon imageIcon = new ImageIcon("Board.jpg.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(screenH*17/20,screenH*17/20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way 
		ImageIcon  BoardImage = new ImageIcon(newimg); //Chaning the imag back to MiageIcon so it can be added to a label
		

		//Adding the label to the panel and centring it
		JLabel label = new JLabel(BoardImage,JLabel.CENTER);
		boardP.add(label, BorderLayout.CENTER);			
		
		
		
		//and MouseListener to get the cords
		boardP.addMouseListener(this);
		GAMEPANEL.add(boardP,BorderLayout.EAST);
		
		//Sets up the left panel
		p = new JPanel(new BorderLayout());
		p.setPreferredSize(new Dimension(screenW-screenH, screenH));
		p.setBackground(Color.YELLOW);
		GAMEPANEL.add(p, BorderLayout.WEST);
		
		//Sets up the info panel
		EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));
		infoPane = new JTextPane();
		infoPane.setBorder(eb);
		infoPane.setBackground(Color.white);
		infoPane.setEditable(false);
		
		//Puts the text pane in a scrollable panel
		JScrollPane scrollPane = new JScrollPane(infoPane); 
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(250, screenH*3/4));
		
		//adds the scroll panel to the left panel
		p.add(scrollPane, BorderLayout.NORTH);
		
		//Creates the cmd panel
		CMD = new JPanel();
		CMD.setLayout(new BorderLayout());
		CMD.setPreferredSize(new Dimension(250,screenH*11/64));
		p.add(CMD,BorderLayout.SOUTH);
		
		//Sets up the text field and button and adds them to the cmd panel
		cmdField = new JTextField("Enter Commands here",30);
		cmdButton = new JButton("Enter");
		CMD.add(cmdField,BorderLayout.CENTER);
		CMD.add(cmdButton,BorderLayout.SOUTH);
		
		//Gets the inputs from the cmd Input 
		cmdButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String id = "INPUT";
				if(!getInputText().equals("") && !getInputText().equals("Enter Commands here"))
				{
					String input=getInputText(); 
					setInfoText(id,getInputText(),Color.red);
				}
				cmdField.setText("Enter Commands here");
			}
		});
		
		//Clears the cmd input when its clicked
		cmdField.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e)
			{
				cmdField.setText("");
			}
		});
		
	}
	
	//gets the text from the cmd text field
	public String getInputText()
	{
		String input = cmdField.getText();
		return input;
	}

	//Adds text to the info panel with a given colour
	//The id is the name of where the text is from
	//The color will be the color of the id text
	public void setInfoText(String id, String text, Color c)
	{
		StyledDocument doc = infoPane.getStyledDocument();
		Style style = infoPane.addStyle("a", null);
		StyleConstants.setForeground(style, c);
		try
		{
			doc.insertString(doc.getLength(), id+": ",style);
		}
		catch (BadLocationException e){}
		
		StyledDocument doc1 = infoPane.getStyledDocument();
		Style style1 = infoPane.addStyle("a", null);
		StyleConstants.setForeground(style1, Color.black);
		try
		{
			doc1.insertString(doc1.getLength(), text+"\n",style1);
		}
		catch (BadLocationException e){}
	}

	public void mouseClicked(MouseEvent e)
	{}

	public void mouseEntered(MouseEvent e)
	{}

	public void mouseExited(MouseEvent e)
	{}

	public void mousePressed(MouseEvent e)
	{
		int xLocation = e.getX();
		int yLocation = e.getY();
		repaint(); 	
		System.out.println(xLocation + " " + yLocation);
		setInfoText("COORDS",xLocation + " " + yLocation,Color.green);
	}

	public void mouseReleased(MouseEvent e)
	{}
	   
	public void mouseDragged(MouseEvent e)
	{}
	   
	public void mouseMoved(MouseEvent e)
	{}


}

/*This is the main class*/
public class DBoard
{
	public static void newGame(Board board)
	{
		int numP=0;
		String id = "CONSOLE";
		Color c = Color.orange;
		board.setInfoText(id,"Welcome to Monopoly",c);
		board.setInfoText(id,"Please Input the number of Players: ",c);
		
		String[] values = {"2", "3", "4", "5", "6"};
		Object selected;
		do{
			selected = JOptionPane.showInputDialog(null, "How many People will play the game:", "Selection", JOptionPane.DEFAULT_OPTION, null, values, "0");
		}while(selected == null);
		

		String selectedString = selected.toString();
		numP = Integer.parseInt(selectedString);
		
		Player[] players = new Player[numP];
		for(int i=0;i<numP;i++)
		{
			board.setInfoText(id,"Please Enter Player "+i+1+"'s name:",c);
			
		}
		
	}


	public static void main(String[] args)
	{
		Board temp = new Board();
		//newGame(temp);
	}
}

class CounterDraw extends JComponent
{
	int x;
	int y;
	int r;
	Color CircleColor;
	
	CounterDraw(int a, int b, int c,Color col)
	{
		x = a;
		y = b;
		r = c;
		CircleColor = col;
	}
	
	public void paintComponent(Graphics g) 
	{
		x = x-(r/2);
		y = y-(r/2);
		super.paintComponent(g);
		g.setColor(CircleColor);
		g.fillOval(x,y,r,r);
		
	}
}

class Player
{
	private int playerPosition;
	private int playerBalance;
	private int[] propertyPos = new int[28];
	private String playerName;
	private int playerNumber;
	
	private static int numberOfPlayers = 0;
	
	private Color[] colors = new Color[6];
	private Color[] colors2 = {Color.red,Color.blue,Color.yellow,Color.green,Color.pink,Color.orange};
	private String[] playerColor = {"Red","Blue","Yellow","Green","Pink","Orange"};
	
	//Constructor
	Player(String name)
	{
		playerBalance = 1500;
		playerPosition = 0;
		playerName = name;
		playerNumber = numberOfPlayers;
		numberOfPlayers++;
		
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
	
	//Retuns Player Number
	public int getPlayerNumber()
	{
		return playerNumber;
	}
	
	//Returns the color of players piece
	public String getColor()
	{
		return playerColor[playerNumber];
	}
	
	//Redude balance by amount
	public String spend(int amount)
	{
		if(playerBalance < amount)
		{
			return "Not enough in account";
		}
		else
		{
			playerBalance -= amount;
			String bal = "Remaining balance: "+this.getBalance();
			return bal;
		}
	}
	
	//Adds amount to balance
	public String receive(int amount)
	{
		playerBalance += amount;
		String bal = "Balance: "+this.getBalance();
		return bal;
	}
	
	public void buyProperty(int propertyPos, int propertyCost)
	{
		//Not a bean
		
	}
}
