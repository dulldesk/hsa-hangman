/*
[omitted name]
January 14, 2020
This class is used to draw animated decoration onto the Console screen. It may or may not be Threaded, which would allow for concurrent thread animations.
    A single Decorations object may be used for multiple non-Thread animations or a single Thread animation. 
    Note that all instance variables are private. This is because properties of the objects of this class should not be 
    In general, private or public methods are based on the usage of the method. If a method is intended to be used as a Thread or solely in this class 
    (e.g. sleep and circle and line overloaded methods), then it is private. Otherwise, it is public.

Instance Variable Dictionary
=======================================================
NAME                DATATYPE                DESCRIPTION
c                   Console                 The output console. The game is executed here.
purpose             String                  The purpose of the object. This is only necessary if object is to be used as a Thread (for animation). 
BLACK               Color (final)           A black Color object.
WHITE               Color (final)           A white Color object.
BLUE                Color (final)           A custom blue Color object.
*/

import java.awt.*;
import hsa.Console;
import java.io.*;
import java.util.*;

public class Decorations extends Thread {
	// instance variables, as notated in the above dictionary
	private Console c;
	private String purpose;
	private final Color BLACK = Color.black;
	private final Color WHITE = Color.white;
	private final Color BLUE = new Color(25,50,175);
    
    // Overloaded constructors to serve two different purposes

    /*
    This overloaded constructor is used when Threaded animation is to be performed by the object.
    In such cases, a purpose must be initialized.
    
    Local Variable Dictionary
    =======================================
    NAME        DATATYPE        DESCRIPTION
    con         Console         The Console to be used by this class. Drawings performed by this class will appear on this Console.
    cmd         String          The purpose of the object, i.e. the Thread animation that the object will run. 
    */
	Decorations(Console con, String cmd) {
		c = con;
		purpose = cmd;
	}

    /*
    This overloaded constructor is used when no Threaded animation is to be performed by the object (no purpose is stated).
    
    Local Variable Dictionary
    =======================================
    NAME        DATATYPE        DESCRIPTION
    con         Console         The Console to be used by this class. Drawings performed by this class will appear on this Console.
    */
    Decorations(Console con) {
        c = con;
    }

    /*
    Sleeps the thread by a given amount of time. This delays the execution of the code flow that follows it. 
    The existence of this method is to maintain clean code; instead of typing the three lines of code for each usage of Thread.sleep(), a single method is called instead. 
    This method takes a single parameter but does not return any value. 

    Local Variable Dictionary
    =============================================
    NAME      DATATYPE                DESCRIPTION
    ms        int                     The number of milliseconds to delay the animation thread.
    e         InterruptedException    The error from the Thread thrown if it is interrupted. Exists for error trapping. 
    */
    private void sleep(int ms) {
        // This try.. catch block catches errors that are thrown from sleeping the Thread. 
        // Nothing is to occur if any errors are thrown. 
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {};
    }

    /*
    This method draws a special line under the title of the Console screen.
    
    Local Variable Dictionary
    =======================================
    NAME        DATATYPE        DESCRIPTION
    left        boolean         Whether the left (true) or the right(false) line is to be drawn.
    */
	private void underLine(boolean left) {
        // This if statement determines whether the left or right line is to be drawn based on the sole boolean parameter.
		if (left) {
			line(0,65,c.getWidth()/2-18,65,7,4,BLUE);
			circle(c.getWidth()/2-18,58,10,0,5,4,BLUE);
		} else {
			line(c.getWidth()+10,65,c.getWidth()/2+35,65,7,4,BLUE);
			circle(c.getWidth()/2+18,58,10,0,5,4,BLUE); 
		}	
	}

    /*
    Draws and animates a footer graphic onto the Console.
    To use the footer, the object of this class must be initialized with a "footer" purpose. 
    
    No variables are required.
    */
	private void footer() {
		line(0,601,376,601,10,4,BLUE);
		line(358,610,438,570,24,4,BLUE);
		line(438,570,c.getWidth()+10,570,10,4,BLUE);
		line(c.getWidth()+10,590,460,590,10,4,BLUE);
		circle(440,585,10,0,5,4,BLUE);
	}

    /*
    Draws and animates a graphic onto the Console. It is located above the footer.
    To use this method, the object of this class must be initialized with a "above footer" purpose. 
    
    No variables are required.
    */
    private void aboveFooter() {
        line(0,575,360,575,10,4,BLUE);
        circle(360,570,10,0,5,4,BLUE);
        line(c.getWidth()+10,550,200,550,10,4,BLUE);
        line(198,558,98,518,24,4,BLUE);
        line(120,518,-10,518,10,4,BLUE);
        line(-10,545,115,545,10,4,BLUE);
        circle(115,540,10,180,5,4,BLUE);
    }

    /*
    When a Thread is started, the class calls its void run method. The purpose of this method is to commence a thread.
    Depending on the purpose of the object, this method calls a certain decoration to be drawn on the Console.
    The if statement checks what the purpose of the object is and runs the corresponding method.
    Note that for underline, there is the left and right underline. The purpose is either "left underline" or"right underline", 
    and the method called is underLine(boolean left). booleans are either true or false. The value passed in depends on whether 
    the right or left underline was passed (in the constructor argument) as the purpose.

    
    No variables nor parameters are required.
    */
    public void run() {
        if (purpose.equalsIgnoreCase("footer")) footer();
        else if (purpose.equalsIgnoreCase("above footer")) aboveFooter();
        else if (purpose.endsWith("underline")) underLine(purpose.startsWith("left"));
    }

    /*
    Draws and animates a lock clicking. 
    
    Local Variable Dictionary
    =======================================
    NAME        DATATYPE        DESCRIPTION
    i           int             An iterator variable. Used to change arguments passed into the line() methods.
    */
    public void lockClick() {
        /*
        This for loop iterates twice. Its sole purpose is to shorten code.
        The first iteration of the loop is to draw the initial line. This line is not animated, and is blue. i is equal to 0.
        In the 2nd iteration, an animated white line is to be drawn on top of the original blue one, makign it look as if it is disappearing. i is equal to 1.
        */
        for (int i=0;i<2;i++) {
            // The second last argument indicates the line colour. 
            // The last argument indicates whether the line is to be animated
            // The above comments describe the values passed in depending on the value of i.
            line(250,220,240,205,2,i==0 ? BLUE : WHITE,i==1); 
            line(245,225,230,225,2,i==0 ? BLUE : WHITE,i==1);

            // Delay the initial line drawing and the disappearing line to show the original line in its original size.
            sleep(100);
        }
    }

    /*
    Draws and animates a hangman graphic onto the Console.
    To use the hangman, the object of this class must call this method.
    
    No variables are required.
    */
	public void drawHangman() {
        // Draw the hangman
        c.setColor(Color.darkGray);
        line(251,180,251,200,3); // rope
        circle(218,200,33,90,2); // head
        line(251,265,251,365,2); // body
        line(251,365,231,430,2,40); // left leg
        line(251,365,271,430,2,40); // right leg
        line(251,290,236,355,2,40); // left arm
        line(251,290,266,355,2,40); // right arm
    }
    
    /*
    Draws and animates a hangman graphic onto the Console.
    To use the hangman, the object of this class must call this method.
    The value returned is whether the last part of the hangman was drawn.

    Local Variable Dictionary
    ====================================================
    NAME              DATATYPE               DESCRIPTION
    partNo            int                    The nth part of the stickman to be drawn. A parameter.
    refX              int                    The x-coordinate of the top left corner of the box of blanks. Many of the other drawn components are positioned in relation to this coordinate. A parameter.
    refY              int                    The y-coordinate of the top left corner of the box of blanks. Many of the other drawn components are positioned in relation to this coordinate. A parameter.
    standX            int                    The x-coordinate of the top left corner of the stand. Many of the other drawn components are positioned in relation to this coordinate. A parameter.
    standY            int                    The y-coordinate of the top left corner of the stand. Many of the other drawn components are positioned in relation to this coordinate. A parameter.
    */
	public boolean drawHangman(int partNo, int refX, int refY, int standX, int standY) {
        // Set a font and a dark red colour for the eyes, if the user reaches that number of incorrect guesses
        c.setColor(new Color(175,0,0));
        c.setFont(new Font("Calibri",Font.PLAIN,20));

        // The part of the stickman that corresponds to the given part number is drawn.
        // A switch statement is used to control which part is drawn. 
		switch (partNo) {
            case 1: // hanging rope
                line(-59+refX+standX-60,10+refY+5+standY,-59+refX+standX-60,30+refY+5+standY,3,40);
                break;
            case 2: // head
                circle(-92+refX+standX-60,30+refY+5+standY,33,90,2);
                break;
            case 3: // body
                line(-59+refX+standX-60,95+refY+5+standY,-59+refX+standX-60,195+refY+5+standY,2);
                break;
            case 4: // left leg
                line(-59+refX+standX-60,195+refY+5+standY,-79+refX+standX-60,260+refY+5+standY,2,40);
                break;
            case 5: // right leg
                line(-59+refX+standX-60,195+refY+5+standY,-39+refX+standX-60,260+refY+5+standY,2,40);
                break;
            case 6: // left arm
                line(-59+refX+standX-60,120+refY+5+standY,-74+refX+standX-60,185+refY+5+standY,2,40);
                break;
            case 7: // right arm
                line(-59+refX+standX-60,120+refY+5+standY,-44+refX+standX-60,185+refY+5+standY,2,40);
                break;
            case 8: // left eye
                c.drawString("X",-75+refX+standX-60,65+refY+5+standY);
                break;
            case 9: // right eye
                c.drawString("X",-52+refX+standX-60,65+refY+5+standY);
                // Briefly delay the Thread, showing that the final piece has been drawn, before exiting the method. The user has lost. Thus, return false as they did not win.
                sleep(1000);
                return false;
        }
        return true;
    }

    /*
    Draws a stand for the hangman.
    
    Local Variable Dictionary
    =======================================
    NAME        DATATYPE        DESCRIPTION
    x           int             The x-coordinate of the top left corner of the stand. 
    y           int             The y-coordinate of the top left corner of the stand. 
    animate     boolean         Whether or not to animate the drawing of the stand. If the value is false, then regular lines simply appear. 
    BROWN       Color (final)   A custom brown colour for the stand.
    */
    public void drawStand(int x, int y, boolean animate) {
        // A local variable, as described in the dictionary. 
    	final Color BROWN = new Color(175,130,100);

        // Draw each component of the stand. 
        // The parameters fed into each line command are the coordinates of the ends of the line, the width of the line, the colour, and whether to animate it or not.
        line(x,y+330,x+140,y+330,5,BROWN,animate); // the base
        line(x+70,y+330,x+70,y,5,BROWN,animate); // the "stand"/"body"
        line(x+70,y,x+180,y,5,BROWN,animate); // the support
        line(x+180,y,x+180,y+30,5,BROWN,animate); // the "rope connector" piece
    }

    /*
    Overloaded method for circle(). All the overloaded methods lead to this method, and this is the only circle() overloaded method that does not call other circle() methods. 
    Draws a circle onto the console. The drawing process is animated. The circle is an outline; it is not filled.
    
    Local Variable Dictionary
    =======================================
    NAME        DATATYPE        DESCRIPTION
    x           int             The x-coordinate of the top left "corner" of the circle (or, if the circle is circumcised in a square, the top left corner of the square).
    y           int             The y-coordinate of the top left "corner" of the circle (or, if the circle is circumcised in a square, the top left corner of the square).
    r           int             The radius of the circle. 
    sa          int             The angle at which the circle should start to be drawn at.
    weight      int             The thickness of the circle. 
    rate        int             The rate, in milliseconds, which each segment of the circle is drawn.
    col         Color           The color of the circle.
    a           int             The angle at which each segment of the circle (an arc) starts to be drawn.
    */
    private void circle(int x, int y, int r, int sa, int weight, int rate, Color col) {
        // Set the circle colour
    	c.setColor(col);

        // The diamater, which is twice the radius, is needed as the oval arguments takes the width and height, which is equal to the diameter.
    	r *= 2; 

        /*
        The following loop is used to animate the drawing of the circle.
        These for loops are separated as drawing arsc

        The outer for loop:
            Both follow a similar format, with the difference being in the boolean expression and the initialized value of a. 
            a indicates the angle to start drawing the arc at; 15 indicates that a 15 degree arc is to be drawn each time, and as thus, each arc starting point is 15 degrees apart. The other arguments are positional and described below (in "inner for loops").
            Once a full circle has been drawn (i.e. a is greater than or equal to sa+360 degrees), then the loop is broken by the boolean expression. it has fulfilled its purpose.
            Note that the starting angle parameter can exceed 360 degrees. Ready to Program more or less interprets it as (the angle measure)%360. 
        The inner for loop:
            The thickness of the circle (end result) is the number of smaller, adjacent circles drawn.
            This for loop draws multiple adjacent arcs to make them appear thicker. 
            The number of arcs drawn is equivalent to the value of the weight parameter. 
            This is accomplished by using a for loop where i is the offset of the arc from its original marked coordinates of the top left corner (x,y).
            x+i and y+i indicates that a new arc is drawn inside the original one, shifted i pixels right and down. 
        As the arc is smaller, the width and height are to be modified. i*2 indicates that 1 pixel from each end (top/bottom, left/right) of the arc is chopped off to form the inner arc.
        */
        for (int a=sa;a<sa+360;a+=15) {
        	for (int i=0;i<weight;i++) {
        		c.drawArc(x+i,y+i,r-i*2,r-i*2,a,15);
        	}

            // Delay the drawing of each segment to make the drawing of the circle appear to be animated
        	sleep(rate);
        }
    }

    /*
    Calls circle(), which draws a circle, but sets the Color parameter to black and the rate to 20 ms. 
    
    Local Variable Dictionary
    =======================================
    NAME        DATATYPE        DESCRIPTION
    x           int             The x-coordinate of the top left "corner" of the circle (or, if the circle is circumcised in a square, the top left corner of the square).
    y           int             The y-coordinate of the top left "corner" of the circle (or, if the circle is circumcised in a square, the top left corner of the square).
    r           int             The radius of the circle. 
    sa          int             The angle at which the circle should start to be drawn at.
    weight      int             The thickness of the circle. 
    animate     boolean         Whether or not to animate the drawing of the circle. 
    i           int             A loop variable. Used to help draw the circle, in terms of thickness. 
    */
    private void circle(int x, int y, int r, int sa, int weight, Color col, boolean animate) {
        // This if structure controls whether to animate the circle, depending on the animate parameter.
        // If so, the circle method is called. Otherwise, a static circle is drawn.
    	if (animate) circle(x,y,r,sa,20,col);
    	else {
            // Set the circle colour
    		c.setColor(BLACK);

            // The diamater, which is twice the radius, is needed as the oval arguments takes the width and height, which is equal to the diameter.
    		r *= 2; 

            /*
            The thickness of the circle is the number of smaller, adjacent circles drawn.
            This for loop draws multiple adjacent circles to make them appear thicker. 
            The number of circles drawn is equivalent to the value of the weight parameter. 
            This is accomplished by using a for loop where i is the offset of the circle from its original marked coordinates of the top left corner (x,y).
            x+i and y+i indicates that a new circle is drawn inside the original one, shifted i pixels right and down. 
            As the circle is smaller, the width and height are to be modified. i*2 indicates that 1 pixel from each end (top/bottom, left/right) of the circle is chopped off to form the inner circle.
            */
            for (int i=0;i<weight;i++) {
            	c.drawOval(x+i,y+i,r-i*2,r-i*2);
            }
        }
    }

    /*
    Calls circle(), which draws a circle, but sets the Color parameter to black and rate to 20 ms.

    Local Variable Dictionary
    ======================================= 
    NAME        DATATYPE        DESCRIPTION
    x           int             The x-coordinate of the top left "corner" of the circle (or, if the circle is circumcised in a square, the top left corner of the square).
    y           int             The y-coordinate of the top left "corner" of the circle (or, if the circle is circumcised in a square, the top left corner of the square).
    r           int             The radius of the circle. 
    sa          int             The angle at which the circle should start to be drawn at.
    weight      int             The thickness of the circle. 
    rate        int             The rate, in milliseconds, which each segment of the circle is drawn.
    */
    private void circle(int x, int y, int r, int sa, int weight, int rate) {
    	circle(x,y,r,sa,weight,20,BLACK);
    }

    /*
    Overloaded method for circle().
    Calls circle(), which draws a circle, but sets the rate parameter to 20 ms.

    Local Variable Dictionary
    =======================================
    NAME        DATATYPE        DESCRIPTION
    x           int             The x-coordinate of the top left "corner" of the circle (or, if the circle is circumcised in a square, the top left corner of the square).
    y           int             The y-coordinate of the top left "corner" of the circle (or, if the circle is circumcised in a square, the top left corner of the square).
    r           int             The radius of the circle. 
    sa          int             The angle at which the circle should start to be drawn at.
    weight      int             The thickness of the circle. 
    col         Color           The color of the circle.
    */
    private void circle(int x, int y, int r, int sa, int weight, Color col) {
    	circle(x,y,r,sa,weight,20,col);
    }

    /*
    Overloaded method for circle().
    Calls circle(), which draws a circle, but sets the rate parameter to 20 ms.

    Local Variable Dictionary
    =======================================
    NAME        DATATYPE        DESCRIPTION
    x           int             The x-coordinate of the top left "corner" of the circle (or, if the circle is circumcised in a square, the top left corner of the square).
    y           int             The y-coordinate of the top left "corner" of the circle (or, if the circle is circumcised in a square, the top left corner of the square).
    r           int             The radius of the circle. 
    sa          int             The angle at which the circle should start to be drawn at.
    col         Color           The color of the circle.
    */
    private void circle(int x, int y, int r, int sa, Color col) {
    	circle(x,y,r,sa,20,col);
    }

    /*
    Overloaded method for circle().
    Calls circle(), which draws a circle, but sets the Color parameter to black and rate to 20 ms.

    Local Variable Dictionary
    ======================================= 
    NAME        DATATYPE        DESCRIPTION
    x           int             The x-coordinate of the top left "corner" of the circle (or, if the circle is circumcised in a square, the top left corner of the square).
    y           int             The y-coordinate of the top left "corner" of the circle (or, if the circle is circumcised in a square, the top left corner of the square).
    r           int             The radius of the circle. 
    sa          int             The angle at which the circle should start to be drawn at.
    weight      int             The thickness of the circle. 
    */
    private void circle(int x, int y, int r, int sa, int weight) {
    	circle(x,y,r,sa,weight,20,BLACK);
    }

    /*
    Overloaded method for line(). All the overloaded methods lead to this method, and this is the only line() overloaded method that does not call other line() methods. 
    Draws a line on the console. The drawing part is animated.
    
    Local Variable Dictionary
    =======================================
    NAME        DATATYPE        DESCRIPTION
    startX      int             The x-coordinate of the starting coordinate of the line. 
    startY      int             The y-coordinate of the starting coordinate of the line. 
    endX        int             The x-coordinate of the ending coordinate of the line. 
    endY        int             The y-coordinate of the ending coordinate of the line. 
    weight      int             The thickness of the line. 
    rate        int             The rate, in milliseconds, which each segment of the line is drawn.
    col         Color           The color of the circle.
    rise        int             The rise of the slope of the line. The line will grow in width by this many pixels per drawing. 
    run         int             The run of the slope of the line. The line will grow in height by this many pixels per drawing. 
    gcf         int             The greatest common factor of the rise and the run. Used to simplify the two values.
    */
    private void line(int startX, int startY, int endX, int endY, int weight, int rate, Color col) {
        // Local variables, as notated in the dictionary
    	int rise = endY-startY;
    	int run = endX-startX;

        // The purpose of this if structure is to find the greatest common factor of the rise and run so that they are at their simplified values. If these simplified values are small, then they are scaled. 
    	if (rise == 0) {
    		run = 5*(run/Math.abs(run));
    	} else if (run == 0) {
    		rise = 5*(rise/Math.abs(rise));
    	} 
    	else {
            // A local variable, as notated in the dictionary
            int gcf = 1; // 1 is guaranteed to be a factor of both the rise and the run

            /*
            The purpose of this for loop is to find the GCF of the rise and the run. 
            Initialization: i is initialized to the minimum of the absolute values of the rise and the run; this is because the GCF cannot be greater than the absolute value of the rise or run.
            Decrementation (typically called the Incrementation): i is decremented as potential GCF values are checked from greatest to least; this is because the first number found that is divisible by the rise and the run is the greatest of its kind; the loop can break upon its discovery.
            Boolean expression: Once i is not greater than 1, then the GCF is 1. This is already initialized to the gcf variable. The loop can break. 
            */
            for (int i=Math.min(Math.abs(rise),Math.abs(run));i>1;i--) {
                // If both the rise adn run are divisible by i, then the GCF is i and it has been found. Otherwise, move on to the next number (the next iteration of the for loop).
            	if (rise % i == 0 && run % i == 0) {
            		gcf = i;
            		break;
            	}
            }

            // Divide the rise and run by the gcf to get the simplified form
            rise /= gcf;
            run /= gcf;

            // The purpose of this while loop is to ensure that at least either the rise or the run is greater than 3. Once either is, the loop terminates.
            // Otherwise, drawing the line becomes slower than expected as it is drawn segment by segment based on the rise and the run [Math.sqrt(run*run + rise*rise) is drawn each time]
            while (Math.max(Math.abs(rise),Math.abs(run)) < 3) {
            	rise *= 2; 
            	run *= 2;
            }
        }

        // Set the line colour
        c.setColor(col);

        // This while loop animates the drawing of the line from (startX,startY) to (endX,endY) pair of coordinates, based on a given width.
        // The while loop runs so long as startX is not at endX. 
        while ((rise == 0 && (run > 0 ? startX < endX : startX > endX)) || (rise > 0 ? startY < endY : startY > endY)) {
            // In each iteration of this loop, a segment of the line is drawn. This makes it appear to be drawn out. 

            /*
            For all lines except horizontal lines, the "thickness" is a horizontal stretch. For horizontal lines, the "thickness" is a vertical stretch. 
            This if statement executes the aforementioned condition. 
            
            Each of the inner for loops draws multiple adjacent lines to make them appear thicker. 
            The number of lines drawn is equivalent to the value of the weight parameter. 
            This is accomplished by using a for loop where the altered value is the x/y-coordinate, by 1 pixel each time.
            */
            if (rise == 0) { // a rise of 0 indicates that the line is horizontal
            	for (int y=startY;y<startY+weight;y++) 
            		c.drawLine(startX,y,startX+run,y);
            } else {
            	for (int x=startX;x<startX+weight;x++) 
            		c.drawLine(x,startY,x+run,startY+rise);
            }

            // Delay the drawing of each segment to make the drawing of the line appear to be animated
            sleep(rate);

            // The following two lines increment startX and startY to the coordinate starting position of the next segment to be drawn.
            startX += run;
            startY += rise;
        }
    }

    /*
    Calls line() with an option to not animate the drawing of the line. The default rate of 20 ms is used. 
    
    Local Variable Dictionary
    ======================================= 
    NAME        DATATYPE        DESCRIPTION
    startX      int             The x-coordinate of the starting coordinate of the line. 
    startY      int             The y-coordinate of the starting coordinate of the line. 
    endX        int             The x-coordinate of the ending coordinate of the line. 
    endY        int             The y-coordinate of the ending coordinate of the line. 
    weight      int             The thickness of the line.
    col         Color           The color of the line.
    animate     boolean         Whether or not to animate the drawing of the line. 
    */
    private void line(int startX, int startY, int endX, int endY, int weight, Color col, boolean animate) {
        // This if structure controls whether to animate the line, depending on the animate parameter.
        // If so, the line method is called. Otherwise, a static line is drawn.
    	if (animate) line(startX,startY,endX,endY,weight,20,col);
    	else {
            // Set the line colour
    		c.setColor(col);

            /*
            For all lines except horizontal lines, the "thickness" is a horizontal stretch. For horizontal lines, the "thickness" is a vertical stretch. 
            This if statement executes the aforementioned condition. 
            
            Each of the inner for loops draws multiple adjacent lines to make them appear thicker. 
            The number of lines drawn is equivalent to the value of the weight parameter. 
            This is accomplished by using a for loop where the altered value is the x/y-coordinate, by 1 pixel each time.
            */
            if (startY == endY) { // an equal starting and ending y-coordinate indicates that the line is horizontal
            	for (int y=0;y<weight;y++) 
            		c.drawLine(startX,startY+y,endX,endY+y);
            } else {
            	for (int x=0;x<weight;x++) 
            		c.drawLine(startX+x,startY,endX+x,endY);
            }
        }
    }

    /*
    Overloaded method for line().
    Calls line() with an option to not animate the drawing of the line. The default rate (20 ms) and colour (black) are used.
    
    Local Variable Dictionary
    ======================================= 
    NAME        DATATYPE        DESCRIPTION
    startX      int             The x-coordinate of the starting coordinate of the line. 
    startY      int             The y-coordinate of the starting coordinate of the line. 
    endX        int             The x-coordinate of the ending coordinate of the line. 
    endY        int             The y-coordinate of the ending coordinate of the line. 
    weight      int             The thickness of the line.
    animate     boolean         Whether or not to animate the drawing of the line. 
    */
    private void line(int startX, int startY, int endX, int endY, int weight, boolean animate) {
    	if (animate) line(startX,startY,endX,endY,weight,20,BLACK);
    	else {
    		c.setColor(BLACK);
    		if (startY==endY) {
    			for (int y=0;y<weight;y++) 
    				c.drawLine(startX,startY+y,endX,endY+y);
    		} else {
    			for (int x=0;x<weight;x++) 
    				c.drawLine(startX+x,startY,endX+x,endY);
    		}
    	}
    }

    /*
    Overloaded method for line().
    Calls line() but sets the colour parameter to black.

    Local Variable Dictionary
    ======================================= 
    NAME        DATATYPE        DESCRIPTION
    startX      int             The x-coordinate of the starting coordinate of the line. 
    startY      int             The y-coordinate of the starting coordinate of the line. 
    endX        int             The x-coordinate of the ending coordinate of the line. 
    endY        int             The y-coordinate of the ending coordinate of the line. 
    weight      int             The thickness of the line. 
    rate        int             The rate, in milliseconds, which each segment of the line is drawn.
    */
    private void line(int startX, int startY, int endX, int endY, int weight, int rate) {
    	line(startX,startY,endX,endY,weight,rate,BLACK);
    }

    /*
    Overloaded method for line().
    Calls line() but sets the rate parameter to 20 ms.

    Local Variable Dictionary
    ======================================= 
    NAME        DATATYPE        DESCRIPTION
    startX      int             The x-coordinate of the starting coordinate of the line. 
    startY      int             The y-coordinate of the starting coordinate of the line. 
    endX        int             The x-coordinate of the ending coordinate of the line. 
    endY        int             The y-coordinate of the ending coordinate of the line. 
    weight      int             The thickness of the line. 
    col         Color           The color of the circle.
    */
    private void line(int startX, int startY, int endX, int endY, int weight, Color col) {
    	line(startX,startY,endX,endY,weight,20,col);
    }

    /*
    Overloaded method for line().
    Calls line() but sets the colour parameter to black.

    Local Variable Dictionary
    ======================================= 
    NAME        DATATYPE        DESCRIPTION
    startX      int             The x-coordinate of the starting coordinate of the line. 
    startY      int             The y-coordinate of the starting coordinate of the line. 
    endX        int             The x-coordinate of the ending coordinate of the line. 
    endY        int             The y-coordinate of the ending coordinate of the line. 
    rate        int             The rate, in milliseconds, which each segment of the line is drawn.
    */
    private void line(int startX, int startY, int endX, int endY, int rate) {
    	line(startX,startY,endX,endY,rate,BLACK);
    }

    /*
    Overloaded method for line().
    Calls line() but sets the colour parameter to black, the weight to 5, and the rate parameter to 20 ms. 
    
    Local Variable Dictionary
    =======================================
    NAME        DATATYPE        DESCRIPTION
    startX      int             The x-coordinate of the starting coordinate of the line. 
    startY      int             The y-coordinate of the starting coordinate of the line. 
    endX        int             The x-coordinate of the ending coordinate of the line. 
    endY        int             The y-coordinate of the ending coordinate of the line. 
    */
    private void line(int startX, int startY, int endX, int endY) {
    	line(startX,startY,endX,endY,5,BLACK);
    }
}