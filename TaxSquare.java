/**
 * Creates a class to hold the tax squares which have a title and a fine and position
 */
public class TaxSquare 
{
	int fine;
	String title;
	int pos;

	TaxSquare(int fine, String title,int pos)
	{
		this.fine = fine;
		this.title = title;
		this.pos = pos;
	}


	int getPos() {return pos;}
	String getTitle(){return title;}
	int getFine(){return fine;}

}
