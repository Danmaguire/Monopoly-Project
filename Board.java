import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.awt.Toolkit;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.*;
import javax.swing.text.*;
import javax.swing.text.StyleConstants;

/*This is the Board class*/
class Board extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//The Frame 
	private JFrame gameFrame;
	
	//JPanels 
	private JPanel leftPanel;
	private JPanel commandPanel;
	public JPanel boardPanel;
	private JPanel gamePanel;
	public JButton commandPanelButton;
	public String lastInput;
	
	JTextPane infoPane;
	public JTextField commandPanelField;
	
	public String InputUser;
	public PicPanel boardPicPanel;
	public JScrollPane scrollPane;
	
	
	private ImageIcon frameIcon = new ImageIcon("images/Logo.png");
	
	//Jlabels for player pieces
	public static JLabel icon1;
	public static JLabel icon2;
	public static JLabel icon3;
	public static JLabel icon4;
	public static JLabel icon5;
	public static JLabel icon6;
	
	public static JLabel Die1;
	public static JLabel Die2;
	
	public ImageIcon DieB = new ImageIcon(ImageIO.read(new File("src/image/DieB.png")));
	public ImageIcon Dice1 = new ImageIcon(ImageIO.read(new File("src/image/Die1.png")));
	public ImageIcon Dice2 = new ImageIcon(ImageIO.read(new File("src/image/Die2.png")));
	public ImageIcon Dice3 = new ImageIcon(ImageIO.read(new File("src/image/Die3.png")));
	public ImageIcon Dice4 = new ImageIcon(ImageIO.read(new File("src/image/Die4.png")));
	public ImageIcon Dice5 = new ImageIcon(ImageIO.read(new File("src/image/Die5.png")));
	public ImageIcon Dice6 = new ImageIcon(ImageIO.read(new File("src/image/Die6.png")));
	
	//Gets Width and Height of screen
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension d = tk.getScreenSize();
	public int screenH = d.height;
	public int screenW = d.width;
	
	public String Input="";
	//Constructor
	Board(String input) throws IOException
	{
		
		Panels(input);
		gameFrame = new JFrame();
		gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gameFrame.setVisible(true);
		gameFrame.add(boardPanel, BorderLayout.EAST);
		gameFrame.add(leftPanel, BorderLayout.WEST);
		gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		gameFrame.setTitle("Monopoly by Mag Lenny and Cobert");
		gameFrame.setIconImage(frameIcon.getImage());
	}
	
	
	//Function creates and sets up content for the panel
	public void Panels(String input) throws IOException
	{
		//gamePanel holds all other panels
		gamePanel = new JPanel(new BorderLayout());
		gamePanel.setPreferredSize(new Dimension(screenW,screenH));
		
		//Board Panel creation
		boardPanel = new JPanel( new BorderLayout() );
		
		// Setting panel size
		boardPanel.setPreferredSize(new Dimension(screenH, screenH));
	
		//Creates the picPanel that holds the board image and player Pieces
		int picPanelH = 705;
		int picPanelW = 766;
		boardPicPanel = new PicPanel("src/image/"+input+"/Board.png",picPanelW,picPanelH);
		boardPicPanel.setBackground(Color.decode("#D5EFB5"));
		boardPicPanel.setPreferredSize(new Dimension(picPanelW,picPanelW));
		boardPanel.add(boardPicPanel,BorderLayout.CENTER);
		boardPicPanel.setLayout(null);
		
		
		//Icons for holding player pieces
		
		ImageIcon P1 = new ImageIcon(ImageIO.read(new File("src/image/"+input+"/Icon1.png")));
		ImageIcon P2 = new ImageIcon(ImageIO.read(new File("src/image/"+input+"/Icon2.png")));
		ImageIcon P3 = new ImageIcon(ImageIO.read(new File("src/image/"+input+"/Icon3.png")));
		ImageIcon P4 = new ImageIcon(ImageIO.read(new File("src/image/"+input+"/Icon4.png")));
		ImageIcon P5 = new ImageIcon(ImageIO.read(new File("src/image/"+input+"/Icon5.png")));
		ImageIcon P6 = new ImageIcon(ImageIO.read(new File("src/image/"+input+"/Icon6.png")));
		
		//icons are placed on jlabels
		icon1 = new JLabel(P1);
		icon2 = new JLabel(P2);
		icon3 = new JLabel(P3);
		icon4 = new JLabel(P4);
		icon5 = new JLabel(P5);
		icon6 = new JLabel(P6);
		
		//Sets up dice
		Die1 = new JLabel(DieB);
		Die2 = new JLabel(DieB);
		
		//Jlabels are added to the board
		boardPicPanel.add(icon1);
		boardPicPanel.add(icon2);
		boardPicPanel.add(icon3);
		boardPicPanel.add(icon4);
		boardPicPanel.add(icon5);
		boardPicPanel.add(icon6);
		
		boardPicPanel.add(Die1);
		boardPicPanel.add(Die2);
		
		
		//Places the player pieces at the starting position
		int x = Constants.SQUARE_CORDS[0][0]-(Constants.RADIUS/2);
		int y = Constants.SQUARE_CORDS[0][1]-(Constants.RADIUS/2);
		icon1.setLocation(x,y);
		icon2.setLocation(x,y);
		icon3.setLocation(x,y);
		icon4.setLocation(x,y);
		icon5.setLocation(x,y);
		icon6.setLocation(x,y);
		
		Die1.setLocation(400,100);
		Die2.setLocation(465,100);
		
		//BoardPanel is added to the gamePanel
		gamePanel.add(boardPanel,BorderLayout.EAST);	
		
		//Sets up the left panel
		leftPanel = new JPanel(new BorderLayout());
		leftPanel.setPreferredSize(new Dimension(screenW-screenH, screenH));
		gamePanel.add(leftPanel, BorderLayout.WEST);
		
		//Sets up the info panel
		EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));
		infoPane = new JTextPane();
		infoPane.setBorder(eb);
		infoPane.setBackground(Color.white);
		infoPane.setEditable(false);
		
		//Puts the text pane in a scrollable panel
		scrollPane = new JScrollPane(infoPane); 
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(250, screenH*3/4));
		
		/*
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
	        public void adjustmentValueChanged(AdjustmentEvent e) {  
	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
	        }
	    });
	    */
		
		//adds the scroll panel to the left panel
		leftPanel.add(scrollPane, BorderLayout.NORTH);
		
		//Creates the commandPanel panel
		commandPanel = new JPanel();
		commandPanel.setLayout(new BorderLayout());
		commandPanel.setPreferredSize(new Dimension(250,screenH*11/64));
		leftPanel.add(commandPanel,BorderLayout.SOUTH);
		
		//Sets up the text field and button and adds them to the commandPanel panel
		commandPanelField = new JTextField("Enter Commands here",30);
		Font font1 = new Font("SansSerif", Font.BOLD, 20);
		commandPanelField.setFont(font1);
		commandPanelField.setHorizontalAlignment(JTextField.CENTER);
		commandPanelButton = new JButton("Enter");
		commandPanel.add(commandPanelField,BorderLayout.CENTER);
		commandPanel.add(commandPanelButton,BorderLayout.SOUTH);
		
		
		//Clears the commandPanel input when its clicked
		commandPanelField.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e)
			{
				commandPanelField.setText("");
			}
		});
		
	}
	
	public String getLastInput()
	{
		return lastInput;
	}
	
	//gets the text from the commandPanel text field
	public String getInputText()
	{
		String input = commandPanelField.getText();
		return input;
	}

	//Adds text to the info panel with a given colour
	//The id is the name of where the text is from
	//The color will be the color of the id text
	public void setInfoText(String id, String text, Color c)
	{
		//Sets the color for the id string then inserts it to the info pane
		StyledDocument doc = infoPane.getStyledDocument();
		Style style = infoPane.addStyle("a", null);
		StyleConstants.setForeground(style, c);
		try
		{
			doc.insertString(doc.getLength(), id+": ",style);
		}
		catch (BadLocationException e){}
		
		//Then inserts the text string followed by the return character
		StyledDocument doc1 = infoPane.getStyledDocument();
		Style style1 = infoPane.addStyle("a", null);
		StyleConstants.setForeground(style1, Color.black);
		try
		{
			doc1.insertString(doc1.getLength(), text+"\n",style1);
		}
		catch (BadLocationException e){}
	}
}