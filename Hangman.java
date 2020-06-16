/*
[omitted name]
January 14, 2020
This ISP is a hangman game, cybersecurity themed. 
    The program introduces the user with an animated splash screen of a hangman being drawn. 
    They are then brought to the main menu, where they can choose to view the instructions, scoreboard, the Level 1 and 2 games, and the goodbye screen.
    In the instructions, the user can flip between the instruction pages with the arrow keys ('<' or '>') or press any other key to return to the main menu.
    In the scoreboard viewing screen, the user can choose to view the top 10 scores for Level 1, Level 2, clear either of those scoreboards, or quit the scoreboard viewing. 
    For either the level 1 or level 2 game option, the user is first presented with a level introduction. Then, in the game, they are presented with an empty hangman stand; 
    a series of blanks on the screen, with one blank per letter of the word; and a letters box, indicating which letters they have guessed so far. they can guess characters
    of the word, get a hint if in Level 1, get a cheat if in Level 2, or give up. For each character guessed, it is removed from a letters bank. If the character guessed 
    exists in the word, then where it occurs would appear among the blanks. Otherwise, a part of the hangman is drawn. If they win the game, then the winning screen is seen
    and the user can enter their name for the scoreboard. If they lose, either by giving up or guessing enough incorrect characters to draw the enteir hangmnan, the losing 
    screen is presented. In both, a fact about the word is presented.
    In the goodbye screen, a goodbye message is presented. Upon key press, a padlock is locked (in an animation) the program terminates.     

Instance Variable Dictionary
=======================================================
NAME                DATATYPE                DESCRIPTION
c                   Console                 The output console. The game is executed here.
choice              char                    The user's choice of "screen" (e.g. instructions, level) to proceed to.
wordBank            String [][]             Hosts the words, hints, and facts to be used in the game.
usedWord            boolean []              Hosts whether the word at that index in wordBank has been used or not.
scoreboardNames     String [][]             Hosts the names in the game's scoreboard. The index of each name matches that of the 
                                            corresponding score in scoreboardNos. One "column" is for level 1; the other is for level 2. 
scoreboardNos       int [][]                Hosts the scores in the game's scoreboard. The index of each score matches that of the 
                                            corresponding name in scoreboardNames. One "column" is for level 1; the other is for level 2. 
userScore           int                     The current score of the user currently playing the game.
wordIndex           int                     The index of the array where the word for the current game is found.
BLACK               Color (final)           A black Color object.
WHITE               Color (final)           A white Color object.
BLUE                Color (final)           A custom blue Color object.
*/

import java.awt.*;
import hsa.Console;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class Hangman {
    // Instance variables, as described in the dictionary
    Console c;
    char choice;
    String [][] wordBank;
    boolean [] usedWord;
    String [][] scoreboardNames;
    int [][] scoreboardNos;
    int userScore;
    int wordIndex;
    final Color BLACK = Color.black;
    final Color WHITE = Color.white;
    final Color BLUE = new Color(25,50,175);

    /*
    The Hangman Constructor
    Constructor in the class to create the Hangman object.
    It creates a new Console object, which will be the display console.
    The usedWord, wordBank, and scoreboard (scoreboardNames and scoreboardNos) arrays are initialized. 
    */
    Hangman() {
        c = new Console(31,81,"Hangman");
        usedWord = new boolean[100];
        wordBank = new String[100][3];
        scoreboardNames = new String[2][10];
        scoreboardNos = new int[2][10];
    }

    /*
    Sleeps the thread by a given amount of time. This delays the execution of the code flow that follows it. 
    The existence of this method is to maintain clean code; instead of typing the three lines of code for each usage of Thread.sleep(), a single method is called instead. 
    This method takes a single parameter but does not return any value. 

    Local Variable Dictionary
    =============================================
    NAME       DATATYPE                DESCRIPTION
    ms         int                     The number of milliseconds to delay the animation thread.
    e          InterruptedException    The error from the Thread thrown if it is interrupted. Exists for error trapping. 
    */
    private void sleep(int ms) {
        // This try.. catch block catches errors that are thrown from sleeping the Thread. 
        // Nothing is to occur if any errors are thrown. 
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {};
    }

    /* 
    The title method
    Clears the console and draws the title at the top of the console window. 
    This method does not return any value nor takes any parameters.  
    */
    public void title() {
        // Clear the console
        c.clear();

        // The title
        c.setFont(new Font("Calibri",Font.BOLD,38));
        c.setColor(BLUE);
        c.drawString("Hangman",c.getWidth()/2-65,40); // this is drawn centre-aligned
    }

    /* 
    The splash screen of the program. A hangman stand is drawn, the title of the game ("Hangman") is typed, and the subtitle ("Cybersecurity Edition") fades in. 
    This method does not take any parameters. 
    
    Local Variable Dictionary
    =============================================
    NAME        DATATYPE            DESCRIPTION
    HEADING     String (final)      A string consisting of the game title. Used for animated display purposes. 
    stickman    Decorations         An object of the Decorations class; it is used to draw the stand and the stickman on the hangman.
    i           int                 A loop variable; used to draw the title, letter by letter.
    a           int                 A loop variable; used to draw the fading appearance of the subheading.
    */
    public void splashScreen() {
        // Local variables, as described in the dictionary.
        final String HEADING = "Hangman";
        Decorations stickman = new Decorations(c);

        // Draw the stand; true indicates that it should be animated
        stickman.drawStand(70,150,true);

        // Draw the hangman. 
        stickman.drawHangman();

        // Delay the appearance of the text
        sleep(500);

        // Draw the title, letter by letter
        c.setColor(BLUE);
        c.setFont(new Font("Calibri",Font.PLAIN,60));

        /*
        This for loop draws each letter of the heading ("Hangman"), one by one. 
        This is accomplished by increasing the length of the substring of the title that is drawn each time by one. 
        Note that substring parameters include the start index and exclude the end index. Thus, i is initialized to 1, initially drawing one character.
        As the characters are drawn one at a time, i is incremented by 1 each time.
        The substring from 0 to HEADING.length() is the entire string. After it is drawn, then continuing the loop would incur a StringIndexOutOfBounds exception. Moreover, it is redundant to continue the loop as the entire String has already been drawn. The boolean expression makes the loop break after this point. 
        */
        for (int i=1;i<=HEADING.length();i++) {
            // Draw the substring of the HEADING string. 
            c.drawString(HEADING.substring(0,i),350,290);

            // Delay the appearance of each character to make it appear typed
            sleep(120);
        }

        // Delay the appearance of the subtitle
        sleep(1000);

        // Draw the subtitle
        c.setFont(new Font("Calibri",Font.ITALIC,30));

        /*
        The following for loop animates the subtitle ("Cybersecurity Edition") fading in.
        This is accomplished by repeatedly drawing a translucent string at the same coordinates. The translucent "layers" overlap and make the colour stronger with each string drawn (and accordingly, each iteration of the loop).
        Drawing 26 layes of the string, one by one, results in a translucent shade after the for loop is finished. This is an optimal result.
        The boolean expression makes the loop break - and the strings cease to be drawn - once the variable a is greater than or equal to 26. a is initialized to 0 and incremented by 1 each time. 
        (26-0)/1 is 26. Thus, 26 layes of the string are drawn. 
        */
        c.setColor(new Color(25,50,175,10)); // This colour is a translucent blue shade. The 4th parameter indicates the opacity of the colour, from a scale of 0-255. 
        for (int a=0;a<26;a++) {
            c.drawString("Cybersecurity Edition",340,340);

            // Delay the loop to make it appear to gradually fade in.
            sleep(5);
        }

        // Delay the appearance of the main menu
        sleep(2000);
    }


    /*
    The main menu of the program. It displays the options of screen for the user and takes in the user's choice of said screen. Error trapping exists for invalid options, in which an error message is shown to the user.
    
    Local Variable Dictionary
    =======================================================
    NAME            DATATYPE                      DESCRIPTION
    OPTIONS         String (final)                Contains the options for the main menu. Exists to make option drawing simpler.
    y               int                           The y coordinate of each option of the main menu.
    footer          Decorations                   Draws a decorative footer on the Console.
    underLineLeft   Decorations                   Draws a decorative line under the title to the left on the Console.
    underLineRight  Decorations                   Draws a decorative line under the title to the right on the Console.
    ie              InterruptedException          The error thrown when the Thread is interrupted. 
    ae              IllegalArgumentException      The error thrown when the user enters an invalid choice.
    */
    public void mainMenu() {
        // A local variable, as notated in the dictionary
        final String [] OPTIONS = {"? - Instructions","S - Scoreboard","1 - Level 1","2 - Level 2","Q - Quit the Program"};

        // Clears the console and prints the title
        title();
        
        /*
        This block of code draws decorations onto the Console screen.
        These decorations come from methods of the Decorations class
        The try..catch block catches any InterruptedException that occurs upon thread interruption. Nothing is to occur if that happens.
        */
        try {
            Decorations footer = new Decorations(c,"footer");
            Decorations underLineLeft = new Decorations(c,"left underline");
            Decorations underLineRight = new Decorations(c,"right underline");

            footer.start(); // run the footer decoration
            underLineLeft.start(); // run the title left underline decoration
            underLineRight.start(); // run the title right underline decoration
            underLineRight.join(); // join the threads; they are to run concurrently
        } catch (InterruptedException ie) {}

        // Set the text colour and font
        c.setColor(BLUE);
        c.setFont(new Font("Calibri",Font.PLAIN,30));

        /*
        This for loop draw the main menu options to the console.
        The y-coordinate of the first option is 200, and the index of the first option is 0. The loop runs so long as there are options remaining to draw. 
        ind is incremented to move to the next index, y is incremented to space out the options. There are 50 pixels in between each option.
        */
        for (int y=200,ind=0;ind<OPTIONS.length;ind++,y+=50) {
            // Draw the option
            c.drawString(OPTIONS[ind],c.getWidth()/2-OPTIONS[ind].length()*5,y);
        }

        c.setFont(new Font("Calibri",Font.PLAIN,25));
        c.drawString("Enter the key denoting your desired option",130,480);

        /*
        This while loop gets the user's choice of screen on the main menu. 
        So long as the user enters invalid input, the loop will iterate. 
        */
        while (true) {
            // This try... catch structure checks whether the user has entered valid input. If not, an IllegalArgumentException is thrown.
            try {
                // Get and store the user's choice
                choice = c.getChar();

                // This if structure checks if the user's choice was a valid option. If it is not, then an exception is thrown.
                if (choice != '?' && choice != 's' && choice != 'S' && choice != '1' && choice != '2' && choice != 'q' && choice != 'Q') throw new IllegalArgumentException();
                // The program will reach this line of code if the option is correct; otherwise an exception would have been thrown.
                // Here, the execution of this method will terminate. Its purpose has been fulfilled. 
                return;
            } catch (IllegalArgumentException ae) { // The program will reach this block if an invalid option has been thrown.
                // Display an error message to the user. 
                JOptionPane.showMessageDialog(null, "You must enter a valid option:\n  ? (instructions)\n  S (scoreboard)\n  1 (Level 1)\n  2 (Level 2)\n  Q (quit).", "Error", JOptionPane.ERROR_MESSAGE);
            } 
        }
    }

    /*
    Displays the game instructions.

    Local Variable Dictionary
    ===========================================
    NAME        DATATYPE            DESCRIPTION
    y           int                 The y coordinate of the box outlining the instructions.
    page        int                 The current page of the instructions that the Console is displaying.
    */
    public void instructions() {
        // Local variables, as described in the above dictionary.
        int y = c.getHeight()/2+100;
        int page = 1;

        // Erase the main menu text, but not the decorations
        c.setColor(WHITE);
        c.fillRect(0,85,c.getWidth(),475);

        /*
        Animate the instructions box "opening".
        This is done by animating the drawing of a box that increases in size and moves from the centre to near the top.
        The box moves up; this is controlled by the value of y. 
        The uppermost y-coordinate that the box will be at is greater than 100, hence the boolean expression.
        Each iteration, the box moves up 15 pixels, hence the increment.
        y is not local to the loop scope as it is needed elsewhere in this method, hence why the initialization of the loop is empty.
        */
        for (;y>100;y-=15) {
            // Erase the previously drawn box
            c.setColor(WHITE);
            c.fillRoundRect(50,y,c.getWidth()-70,c.getHeight()-2*y+5,45,45);

            // Draw the box
            c.setColor(BLUE);
            c.drawRoundRect(50,y,c.getWidth()-70,c.getHeight()-2*y+5,45,45);

            // Delay the next iteration of the loop to make the box growth appear animated. 
            sleep(15);
        }

        // For loops work by performing the incrementation, then checking if the boolean expression is satisfied.
        // The last valid value of y was before the incrementation. To get the current y-coordinate of the box, one instance of this incrementation (or in this case, decrementation), needs to be undone.
        y+=15;


        // User instructions as to how to view the instructions
        // Set font and text colour
        c.setColor(BLUE);
        c.setFont(new Font("Calibri",Font.PLAIN,15));

        c.drawString("Press < or > to flip between the pages of the instructions,",65,540);
        c.drawString("or any other key to return to the main menu.",65,560);


        // Change font size for the actual instructions
        c.setFont(new Font("Calibri",Font.PLAIN,25));


        /*
        The instructions

        This do..while loop runs so long as the user does not choose to exit.
        It displays a single page of the instructions each time.        
        All instructions are left-aligned
        */
        do {
            // Erase the old page's contents
            c.setColor(WHITE);
            c.fillRoundRect(53,y+3,c.getWidth()-76,c.getHeight()-2*y-2,45,45);


            // Set the text colour.
            c.setColor(BLUE);

            /*
            This if structure executes a block of code depending on what page the user is viewing. 
            The boolean expression conditions check what the current page is. 
            */
            if (page == 1) {
                c.drawString("Guess the word before the hangman is finished!",75,150);
                c.drawString("This version of Hangman is cybersecurity themed.",75,190);
                c.drawString("There exist 2 difficulties of the game; Level 1 allows",75,230);
                c.drawString("for hints, Level 2 does not. In the Main Menu, select",75,270);
                c.drawString("the difficulty of Hangman that you would like to play:",75,310);
                // This is centre aligned
                c.drawString("'1' for Level 1",260,350);
                c.drawString("or",c.getWidth()/2-5,380);
                c.drawString("'2' for Level 2",260,410);

                c.drawString("In either level, a series of blanks will appear on the",75,450);
                c.drawString("screen. One blank is assigned to each letter of the",75,490);
            } else if (page == 2) {
                c.drawString("word you are to guess.",75,160);
                c.drawString("The letter bank below indicates which letters you",75,200);
                c.drawString("have already guessed. If you correctly guess one of",75,240);
                c.drawString("the letters of the word, then the blanks that",75,280);
                c.drawString("correspond to that letter in the word will be filled.",75,320);
                c.drawString("Otherwise, a part of the hangman is drawn. The",75,360);
                c.drawString("letters you guess must be those of the English",75,400);
                c.drawString("alphabet. On Level 1, you may obtain a hint by",75,440);
                c.drawString("entering '?'. On Level 2, you may cheat by entering",75,480);
            } else if (page == 3) {
                c.drawString("'#' - this will reveal the word. You can win the game,",75,160);
                c.drawString("but you'll receive a negative score.",75,200);
                c.drawString("If the hangman is fully drawn or if you choose to give",75,240);
                c.drawString("up, then you lose the game. Otherwise, you win; you",75,280);
                c.drawString("will see your score and you can enter your name,",75,320);
                c.drawString("which may appear on the scoreboard for your level",75,360);
                c.drawString("if you are among the top 10 scores. You will also be",75,400);
                c.drawString("presented with a little fact/feature about your game's",75,440);
                c.drawString("word after you finish the game.",75,480);
            } else if (page == 4) {
                c.drawString("The scoreboard consists of the top 10 scores for each",75,160);
                c.drawString("level. You can also clear either of the scoreboards.",75,200);
                c.drawString("Have fun!",75,330);
            }

            // No input error trapping is required here as user input defaults to a desire to exit the instructions screen.

            // Get the user's choice of character
            choice = c.getChar();

            // If the user entered the arrow keys, then flip to the corresponding page.
            if (choice == '<') {
                // Go to the previous page
                page--;

                // If the user was already on the first page, then go to the last page. 
                if (page <= 0) page = 4;
            } else if (choice == '>') {
                // Go to the next page
                page++;

                // If the user was already on the last page, then go to the first page. 
                if (page >= 5) page = 1;
            }
        } while (choice == '<' || choice == '>');

        // Erase the user instructions 
        c.setColor(WHITE);
        c.fillRect(55,520,400,45);

        /*
        Animate the instructions box "closing".
        This is done by animating the drawing of a box that decreases in size and moves towards the centre.
        The box moves down; this is controlled by the value of y. 
        The lowermost y-coordinate that the box will be at is less than c.getHeight()/2+10, hence the boolean expression.
        Each iteration, the box moves down 15 pixels, hence the increment.
        y is not local to the loop scope as it is needed elsewhere in this method, hence why the initialization of the loop is empty.
        */
        for (;y<c.getHeight()/2+10;y+=15) {
            // Draw the box
            c.setColor(BLUE);
            c.drawRoundRect(50,y,c.getWidth()-70,c.getHeight()-2*y+5,45,45);

            // Delay the next iteration of the loop to make the box shrink appear animated. 
            sleep(15);

            // Erase the current box. This is done after the drawing as after the last iteration, no box should be on the screen.
            c.setColor(WHITE);
            c.fillRoundRect(45,y-5,c.getWidth()-10,c.getHeight()-2*y+25,45,45);
        }

        // Reset the choice to 'I', which denotes the instructions in the main menu
        choice = 'I';
    }


    /*
    The scoreboard of the program. Displays the top 10 scores of each level.
    
    Local Variable Dictionary
    =======================================================
    NAME          DATATYPE                      DESCRIPTION
    lvl           int                           The level whose scoreboard the console is displaying. Levels are 0-indexed (0 for Level 1, 1 for Level 2).
    sbContents    String                        Holds the contents of the scoreboard that are not to be cleared. 
    y             int                           The y coordinate of each option of the scoreboard.
    i             int                           A loop variable used to print a line on the Console.
    rnk           int                           A loop variable; loops through each index of the scoreboard array. An index corresponds to a ranking entry.
    saveLvl       int                           The level that is not cleared upon selecting the clear command of the scoreboard.
    OPTIONS       String (final)                Contains the options for the scoreboard. Exists to make option drawing simpler.
    ierr          IOException                   The error from the BufferedReader, if caught. Exists for error trapping. 
    aerr          IllegalArgumentException      The error thrown when the user enters an invalid choice.
    */
    public void viewScoreboard() {
        // Local variables, as notated in the dictionary
        int lvl = 0; 
        final String [] OPTIONS = {"1 - view the scoreboard for Level 1","2 - view the scoreboard for Level 2","C - clear the current scoreboard","Q - return to the main menu"};

        while (true) {
            // Erase the main menu/current scoreboard text, but not the decorations
            c.setColor(WHITE);
            c.fillRect(0,85,c.getWidth(),475);

            // Set the text font and colour
            c.setColor(BLUE);
            c.setFont(new Font("Calibri",Font.PLAIN,30));

            // The subtitle
            c.drawString("The Scoreboard - Level " + (lvl+1),190,110);

            // Draw a line underneath the subtitle
            c.fillRect(200,125,300,3);

            // Set the ranks font
            c.setFont(new Font("Calibri",Font.PLAIN,25));

            // If the name of the first rank is a null string, then the scoreboard is empty. 
            if (scoreboardNames[lvl][0] == null) {
                c.drawString("Nothing to show here. . .",216,180);
            }
            else { 
                // Draws the available ranks
                c.setFont(new Font("Calibri",Font.PLAIN,25));

                /*
                This for loop iterates through all of the ranks of the scoreboard of the currently selected level and prints them to the console.
                In the scoreboard array, ranks are 0-indexed. 
                There are up to 10 entires in the scoreboard. After the 9th index, 10 entries have been read and the loop is broken by the boolean expression as it has fulfilled its purpose.
                */
                for (int rnk=0;rnk<10;rnk++) {
                    // If the name at the current rank is null, then it and the subsequent ranks are empty. There is nothing more to print out and the loop is terminated. 
                    if (scoreboardNames[lvl][rnk] == null) break;

                    // Draw the name and score on the same row. 
                    c.drawString(scoreboardNames[lvl][rnk],150,165+rnk*30);
                    c.drawString(scoreboardNos[lvl][rnk]+"",c.getWidth()-170,165+rnk*30);
                }
            }

            c.setFont(new Font("Calibri",Font.PLAIN,20));

            /*
            This for loop draws the scoreboard options to the console.
            The y-coordinate of the first option is 200, and the index of the first option is 0. The loop runs so long as there are options remaining to draw. 
            ind is incremented to move to the next index, y is incremented to space out the options. There are 50 pixels in between each option.
            */
            for (int y=468,ind=0;ind<OPTIONS.length;ind++,y+=28) {
                // Draw the option
                c.drawString(OPTIONS[ind],c.getWidth()/2-OPTIONS[ind].length()*3-10,y);
            }

            /*
            This while loop gets the user's choice of screen for the scoreboard commands.
            So long as the user enters invalid input, the loop will iterate. 
            */
            while (true) {
                // This try... catch structure checks whether the user has entered valid input. If not, an IllegalArgumentException is thrown.
                try {
                    // Get and store the user's choice
                    choice = c.getChar();

                    if (choice != '1' && choice != '2' && choice != 'Q' && choice != 'q' && choice != 'C' && choice != 'c' && choice != 10) throw new IllegalArgumentException();

                    // The program will reach this line of code if the option is correct; otherwise an exception would have been thrown.
                    // The loop is broken out of as valid input has been found. 
                    break;
                } catch (IllegalArgumentException aerr) { // The program will reach this block if an invalid option has been thrown.
                    // Display an error message to the user. 
                    JOptionPane.showMessageDialog(null, "You must enter a valid option:\n  1 - view the high scores of Level 1\n  2 - view the high scores of Level 2\n  C - clear the current scoreboard\n  Q - return to the main menu", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }   

            /*
            This if structure executes blocks of code depending on the user's choice of option.
            Different commands require different blocks of code. 
            */
            if (choice == '1' || choice == '2') {
                // As notated in the variable dictionary, the value of the lvl variable is either 0 or 1, for levels 1 and 2 respectively. 
                // The ASCII value of '1' holds a decimal value of 49. '2' is 50. 
                // By subtracting '1' from choice, the decimal number of the value is 0 or 1.
                lvl = choice-'1';
            }
            else if (choice == 'C' || choice == 'c') {
                // A local variable, as described in the dictionary. It must be declared here for scoping reasons.
                // sbContents is initialized with a String of the scoreboard heading. 
                String sbContents = "The Scoreboard\n==============\n\nLevel 1:\n";

                // If the scoreboard to be cleared is Level 1, then the scoreboard contents to be printed in the loop are for level 2 and this heading is printed. Nothing of the Level 1 scoreboard is in the sbContents string. 
                if (lvl==0) sbContents += "\nLevel 2:\n";

                /*
                This for loop iterates through all of the ranks of the scoreboard of the currently selected level. 
                In the scoreboard array, ranks are 0-indexed. 
                There are up to 10 entires in the scoreboard. After the 9th index, 10 entries have been read and the loop is broken by the boolean expression as it has fulfilled its purpose.
                Two variables are initialized in the initialization. One is the loop variable rnk, the other (saveLvl) is only needed in the scope of the loop.Both are notated in the variable dictionary.
                */
                for (int rnk=0,saveLvl=Math.abs(lvl-1);rnk<10;rnk++) {
                    // If the name at the current rank is null, then it and the subsequent ranks are empty. There is nothing more to save and the loop is terminated. 
                    if (scoreboardNames[saveLvl][rnk]==null) break;

                    // Print the name and score on the same line to the file, and "move" the cursor to the next line. 
                    // The name and score are comma-separated. 
                    sbContents += scoreboardNames[saveLvl][rnk]+','+scoreboardNos[saveLvl][rnk]+'\n';
                }

                // If the scoreboard to be cleared is Level 2, then the scoreboard contents that were printed in the loop are for level 1 and this heading is printed. Nothing of the Level 2 scoreboard is in the sbContents string. 
                if (lvl==1) sbContents += "\nLevel 2:\n";

                // Reset the scoreboard array for that level by initializing it to empty arrays. 
                scoreboardNames[lvl] = new String[10];
                scoreboardNos[lvl] = new int[10];

                // Clear the scoreboard file and rewrite the contents into it
                clearFile("hangman.scoreboard",sbContents);

                // Erase the original title
                c.setColor(WHITE);
                c.setFont(new Font("Calibri",Font.PLAIN,30));
                c.drawString("The Scoreboard - Level " + (lvl+1),190,110);
                
                // Draw a success message in the title area
                c.setColor(BLUE);
                c.setFont(new Font("Calibri",Font.PLAIN,25));
                c.drawString("Successfully cleared the scoreboard for Level "+(lvl+1),110,110);

                // Pause the screen briefly before returning to the scoreboard viewing screen. 
                sleep(1000);
            } 
            else {
                // Reset the choice to a different character
                // This is necessary as the loop in the main method breaks if the choice variable is equal to 'Q' or 'q'
                choice = 'S';

                // Break out of the method. The user's choice indicated that they wish to exit the scoreboard viewing scren.
                return;
            }
        }
    }

    /*
    Generates and returns a random word (a string) from the word bank that was not used in the past generation of words.
    If all of the words have been used, then a fresh cycle of words is started. 

    Local Variable Dictionary
    ======================================== 
    NAME        DATATYPE         DESCRIPTION
    allUsed     boolean          Whether all the words in the word bank have been used or not. This is found by looping through the boolean usedWord array.
    ORIG_STR    String (final)   The original vocabulary word stored in the file. 
    retStr      String           The word to be used solely in the display of the game. 
    w           int              A loop variable; loops through each index of the usedWord array.
    ind         int              A loop variable; loops through the character at each index of the ORIG_STR (the word)
    */
    private String generateWord() {
        // Local variables, as delcared above
        boolean allUsed = true;
        String retStr;

        /*
        This for loop checks whether all of the words available in the word bank have been used in the game. If so, then the array is reset. 
        This is accomplished by iterating through the entirety of the usedWord array, whose purpose is to store whether a word has been used.
        Initialization and increment: As the entire array is checked, w is initialized to 0 and incremented by 1 each time. 
            Unless the loop is broken beforehand, all of the indices of the array are checked in order from 0 to usedWord.length-1. 
        Boolean expression: The loop will break if allUsed is false or if w is greater than or equal to the length of the usedWord array. 
            Essentially, if an unused word has been found or if all of the indices have been checked, then the loop will break.
        Inside the for loop, the allUsed boolean is assigned to the value of usedWord[w]. If this value is false, then the loop will break as an unused word has been found. 
        Otherwise, it will move on to the next index; no unused words have been found yet. 
        */
        for (int w=0;w<usedWord.length && allUsed;w++) 
            allUsed = usedWord[w];

        /*
        In this if structure, the usedWord array is reset if all of the words have been found. 
        This will occur if in the previous for loop, no boolean in the usedWord array held the value of false; i.e. all words were used. 
        By reinitializing the array, all values in it are set to false, meaning that none of them have been used yet in the new cycle. 
        */
        if (allUsed) usedWord = new boolean[100];

        /*
        This do..while loop finds an unused word for the game.
        A do..while loop is used instead of a while loop as the code block is executed before evaluating the boolean expression as whether to run another iteration. 
        In the code block, wordIndex is initialized to a random integer value from 0 to 99 inclusive. 
            This means that the (wordIndex)th word of the wordBank will be used in the game so long as it has not been used yet, a condition that is checked in the boolean expression. 
        Boolean expression: The loop will run so long as the current word (at wordIndex) has not been used. 
        */
        do {
            wordIndex = (int)(Math.random()*100);
        } while (usedWord[wordIndex]);

        // An unused word has been found. It will be used in the next game; thus set this value to true. 
        usedWord[wordIndex]=true;

        // Ths local variable must be declared here as final variables must be initialized upon declaration.
        final String ORIG_STR = wordBank[wordIndex][0];
        retStr = ""; // Set this variable to an empty string. The next 

        /*
        When the word is displayed on the screen, there will be a maxmimum of 6 characters per row. Sometimes the first character is a space. 
        This for loop filters through the characters of the word/phrase and removes any spaces that would appear at the beginning of the string. 
            The length of the string being built, mod 6, indicates the column that the current character would appear in. If this value is 0, then it would appear in the first column. If this character is a space, then it is omitted from the stirng. 
        Initialization and increment: The character at each index of the ORIG_STR string is to be checked. Strings are 0-indexed, meaning that the first character of the string starts at index 1. 
            Thus, ind is initialized to 0 and incremented by one each time, checking one character after the other. 
        Boolean expression: Given that the entirety of the ORIG_STR is to be checked, after the ind variable hosts a value beyond the length of the string, then it is redundant to continue as it will cause StringIndexOutOfBound exceptions.
        */
        for (int ind=0;ind<ORIG_STR.length();ind++) {
            // This if structure skips the space if it is located at the beginning of a row, as described above. 
            if (ORIG_STR.charAt(ind) == ' ' && retStr.length() % 6 == 0) {
                // Ignoring the space by not adding it to the retStr string effectively omits it. 
                continue;
            }
            // Otherwise, the current character is to be kept; it is added to the retStr string. 
            retStr += ORIG_STR.charAt(ind);
        } 

        // Return the retStr string to the method it was called in (the game). 
        return retStr;
    }

    /*
    Introductory animation to the level. 
    
    Local Variable Dictionary
    ======================================== 
    NAME        DATATYPE                DESCRIPTION
    lvl         char                    The level to be played by the user. The sole parameter. 
    i           int                     A loop variable. Specifies the ending index of the substring to be drawn to the Console. 
    a           int                     A loop variable. Specifies the opacity of the lock being drawn in that iteration.
    randLines   ArrayList               Hosts a random permutation of y-coordinates of lines, used to animate the 
    HEADING     String (final)          A string consisting of level of the game. Used for animated display purposes. 
    */
    public void levelIntro(char lvl) {
        // Local variables, as described in the dictionary. 
        final String HEADING = "Level "+lvl;
        ArrayList randLines = new ArrayList();

        /*
        In the following code block, horizontal stripes spanning the width of the Console are drawn in, eventually colouring the background
        These stripes appear randomly to create a special effect. 
        I used an ArrayList, which is a data structure of the java.util library. It is like an array with a flexible length.
        This is so that I can shuffle the elements of the data structure using a built in method. 
        Before shuffling, the ArrayList will contain a sorted permutation of the integers from 0 to c.getHeight() inclusive. This will represent the lines' y-coordinates. 
        Afterwards, each of these lines are drawn onto the screen, drawing in a new background.

        References: 
        Java 1.4.2 Docs:
            "ArrayList" (http://www2.cs.duke.edu/csed/java/jdk1.4.2/docs/api/java/util/ArrayList.html)
            "Integer" (http://www2.cs.duke.edu/csed/java/jdk1.4.2/docs/api/java/lang/Integer.html)
            "Collection" (http://www2.cs.duke.edu/csed/java/jdk1.4.2/docs/api/java/util/Collections.html)
        Pankaj at JournalDev - "How to Shuffle an Array in Java" (https://www.journaldev.com/32661/shuffle-array-java).
        */
        c.setColor(BLUE);

        for (int i=0;i<=c.getHeight();i++) 
            randLines.add(new Integer(i));
        
        Collections.shuffle(randLines);

        for (Iterator itr = randLines.iterator();itr.hasNext();) {
            int y = ((Integer)(itr.next())).intValue();
            c.drawLine(0,y,c.getWidth(),y);
            sleep(1);
        }

        /*
        A graphic of a locked lock

        The following for loop draws the lock in a "swiping" manner. 
        a is a loop variable to determine the opacity of the colour. Once the lock is at full opacity (255), then the loop terminates. 
        While in the splash screen, the colours would overlap, in this case some of the background is erased each iteration of the lock drawing and the colours do not overlap.
        The initial opacity is the initialized value.
        */
        for (int a=85;a<=255;a+=85) {
            // shackle of the lock
            c.setColor(new Color(255,255,255,a)); // This colour is a translucent white. The opacity is based on the loop variable a.
            c.fillArc(10,120,150,180,0,180);

            c.setColor(new Color(25,50,175,a)); // This colour is a translucent blue shade. The opacity is based on the loop variable a.
            c.fillArc(35,140,100,150,0,180);

            // body of the lock 
            c.setColor(new Color(255,255,255,a)); // This colour is a translucent white. The opacity is based on the loop variable a.
            c.fillRoundRect(-10,210,190,190,45,45);

            // keyhole
            c.setColor(new Color(25,50,175,a)); // This colour is a translucent blue shade. The opacity is based on the loop variable a.
            c.fillOval(67,280,30,30);
            c.fillRoundRect(75,295,15,40,20,20);

            // The nature of Ready to Program makes this loop delayed (and thus the lock animated) without calling Thread.sleep. 
        }
        // Delay the appearance of the text
        sleep(500);

        // The title 
        c.setColor(WHITE);
        c.setFont(new Font("Courier New",Font.BOLD,70));

        /*
        This for loop draws each letter of the heading ("Level 1/2", one by one. 
        This is accomplished by increasing the length of the substring of the title that is drawn each time by one. 
        Note that substring parameters include the start index and exclude the end index. Thus, i is initialized to 1, initially drawing one character.
        As the characters are drawn one at a time, i is incremented by 1 each time.
        The substring from 0 to HEADING.length() is the entire string. After it is drawn, then continuing the loop would incur a StringIndexOutOfBounds exception. 
        Moreover, it is redundant to continue the loop as the entire String has already been drawn. The boolean expression makes the loop break after this point. 
        */
        for (int i=1;i<=HEADING.length();i++) {
            // Draw the substring of the heading
            c.drawString(HEADING.substring(0,i),280,350);

            // Delay the drawing of the next character of the String, to make the animation look "typed"
            sleep(80);
        }

        // Delay the change of the lock from locked to unlocked
        sleep(1000);

        // erase the old lock shackle
        c.setColor(BLUE);
        c.fillRect(10,120,150,100);

        // shackle of the unlocked lock
        c.setColor(WHITE);
        c.fillArc(130,105,150,220,0,180);

        c.setColor(BLUE);
        c.fillArc(160,135,90,180,0,180);

        // As part of the lock body was erased to redraw the shackle, the upper part of the body is redrawn as well
        c.setColor(WHITE);
        c.fillRoundRect(-10,210,190,50,45,45);

        // Pause the screen shortly so that the changed lock is seen. The program will then proceed to the actual game screen. 
        sleep(800);
    }

    /*
    The actual Hangman game. Returns whether the user had won or lost the game.
    
    Local Variable Dictionary
    ===========================================================
    NAME              DATATYPE                      DESCRIPTION
    allowHint         boolean                       Whether a hint is allowed in the level, and also indicates the game's level. Level 1 allows a hint; this variable will be true. Level 2 does not allow one; it will hold false. 
    WORD              String (final)                The word to be guessed in the game. 
    LEN               int (final)                   The length word to be used solely in the display of the game. 
    badGuessNo        int                           The number of incorrect guesses that the user made.
    foundCnt          int                           The number of letters that were found (by the user).
    gotHelp           boolean                       Whether the user has asked for the hint (Level 1) or the cheat (Level 2). 
    letters           boolean []                    Each letter of the alphabet is assigned to one index. The boolean value indicates whether the user has guessed that letter. 
    BLANKS_X          int [] (final)                The x-coordinates of each row of blanks.
    BLANKS_Y          int [] (final)                The y-coordinates of each column of blanks.
    refX              int (final)                   The x-coordinate of the top left corner of the box of blanks. Many of the other drawn components are positioned in relation to this coordinate.
    refY              int (final)                   The y-coordinate of the top left corner of the box of blanks. Many of the other drawn components are positioned in relation to this coordinate.
    standX            int (final)                   The x-coordinate of the top left corner of the stand. Many of the other drawn components are positioned in relation to this coordinate.
    standY            int (final)                   The y-coordinate of the top left corner of the stand. Many of the other drawn components are positioned in relation to this coordinate.
    ROWNO             int (final)                   The number of rows that the word/phrase takes up when drawn. 
    lastColNo         int                           The number of letters in the final row of the word/phrase when drawn. 
    stickman          Decorations                   An object of the Decorations class to draw the stand and the hangman stickman.
    row               int                           The current row of blanks being drawn and/or handled.
    col               int                           The current column of blanks being drawn and/or handled. 
    ind               int                           The current index of the word being handled.
    guess             char                          The user's input: a guess of a character in the word or a command, e.g. help.
    x                 int                           A loop variable. Represents an x-coordinate. 
    y                 int                           A loop variable. Represents a y-coordinate. 
    LTR               char (final)                  The current letter of the letters bank being handled.
    l                 int                           A loop variable. The current index of the letters array being handled. 
    e                 IllegalArgumentException      The error thrown when the user enters an invalid choice.
    */
    public boolean level(boolean allowHint) {
        // Local variables, as described in the variable dictionary
        // Word related variables
        final String WORD = generateWord().toUpperCase(); // It is capitalized as the game will display the letters in upper case. 
        final int LEN = WORD.length();
        int lastColNo = LEN%6;
        final int ROWNO = (LEN-lastColNo)/6 + (lastColNo == 0 ? 0 : 1);

        // Game related variables
        boolean [] letters = new boolean[26];
        int badGuessNo = 0;
        int foundCnt = 0;
        boolean gotHelp = false;

        // Drawing related variables
        final int [] BLANKS_X = {0, 55, 110, 165, 220, 275};
        final int [] BLANKS_Y = {45, 100, 155, 210, 265};
        final int refX = 300;
        final int refY = 110;
        final int standX=42;
        final int standY=-5;
        Decorations stickman = new Decorations(c); // as this object is not used for animations with Threads, a purpose does not need to be declared in the constructor.

        // Reset the user's score for this game
        userScore = 0;

        // The background and title
        title();

        // Draw the stand; it is not animated. 
        stickman.drawStand(standX,refY-14+standY,false);

        // Draw the boxes surrounding the stand and the blanks of the word/phrase
        c.setColor(BLUE);
        c.drawRoundRect(standX-20,refY-34+standY,250,370,45,45); // the stand box
        c.drawRoundRect(refX-20,refY-40,360,370,45,45); // the blanks box

        /*
        These braces are used to limit the scope of the ind variable.
        Even though the braces are not part of a structure of any sort, this is allowed in Java. 
        This is because braces are used for notating code blocks, and variables will be limited to the field of the braces. For example, in for loops, 
        
        The following block of code draws the blanks that represent each letter of the word/phrase to be guessed by the user.
        The blanks box consists of 5 rows and 6 columns; there is a maximum of 30 blanks. It is not neccessary for all of them to be used. These rows are labelled from indices 0 to 5 (o-indexed). 
        Note that rows are centralized in their box. Thus, the first row that the blanks start on is (rowNo/2) rows away from the centre. 
        */
        {
            // A local variable, as notated in the variable dictionary
            int ind=0;

            // This if statement executes if there is more than one row of blanks. If there is only 1 row, then the code following this if statement block suffices.
            // Note that all but the last row of blanks is drawn in this code block.
            if (ROWNO > 1) {
                /*
                The following nested for loop draws the blanks for all rows excluding the last.
                THe outer for loop is a counter for each row. 
                    The initialized value of row represents the first row that the letters will appear in. 
                    The boolean expression breaks the loop once the row is equal to the value of the last row they will appear in. 
                    No row is skipped; the row variable is incremented by 1 each time.
                The inner for loop is a counter for each column. All of the columns in these rows are used. Thus the counter iterates throough all columns, from 0 to 5 inclusive.
                */
                for (int row=2-ROWNO/2;row<ROWNO+1-ROWNO/2;row++) {
                    for (int col=0;col<6;col++,ind++) {
                        /*
                        No blank rectangle is indicated for a space. A gap is merely left. Otherwise, the rectangle blank is drawn.
                        If the current character is a space, then foundCnt is incremented by 1. 
                        The user is unable to guess symbols. However, whether the user won or not is determined based on if the number of characters found (foundCnt) is equal to the length of the word (LEN). 
                        Spaces are included in the length, and if they cannot be guessed by the user, then it would otherwise be impossible to win the game. Thus, spaces must be preadded to the foundCnt variable (as LEN is a constant and cannot be changed).
                        */
                        if (WORD.charAt(ind) != ' ') c.fillRect(refX+BLANKS_X[col],refY+BLANKS_Y[row],45,5);
                        else foundCnt++;
                    }
                }
            } 

            // lastColNo is equal to the LEN%6, 6 being the number of columns. 
            // However, if LEN is equal to 6, then lastColNo is 0, even though there is 1 row of 6 blanks. 
            // This if statement accounts for that; if not all of the blanks have been drawn yet, but lastColNo is 0, then it should be assigned the value of 6, meaning a full row of blanks.
            if (lastColNo == 0 && ind != LEN) lastColNo=6;

            /*
            This if structure affects the placement of blanks. For the last row, if there are 4 or more blanks, then they will be left aligned. Otherwise, the blanks are centre aligned.
            */
            if (lastColNo > 3) {
                /*
                Left aligned blanks

                This for loop initializes col to the first column and row to the last row that the blanks appear in. 
                The boolean expression prevents StringIndexOutOfBound errors. 
                After each blank is drawn, the loop moves on to the next index and column. Both need to be incremented.
                */
                for (int col=0,row=ROWNO+1-ROWNO/2;ind < LEN;col++,ind++) {
                    /*
                    No blank rectangle is indicated for a space. A gap is merely left. Otherwise, the rectangle blank is drawn.
                    If the current character is a space, then foundCnt is incremented by 1. 
                    The user is unable to guess symbols. However, whether the user won or not is determined based on if the number of characters found (foundCnt) is equal to the length of the word (LEN). 
                    Spaces are included in the length, and if they cannot be guessed by the user, then it would otherwise be impossible to win the game. Thus, spaces must be preadded to the foundCnt variable (as LEN is a constant and cannot be changed).
                    */
                    if (WORD.charAt(ind) != ' ') {
                        c.fillRect(refX+BLANKS_X[col],refY+BLANKS_Y[row],45,5);
                    }
                    else foundCnt++;
                }
            } else {
                /*
                Centre aligned blanks

                This for loop initializes col to the the middle of the row (3), subtracted by half of the number of columns in the last row (lastColNo/2) and row to the last row that the blanks appear in. 
                The boolean expression prevents StringIndexOutOfBound errors. 
                After each blank is drawn, the loop moves on to the next index and column. Both need to be incremented.
                */
                for (int col=3-lastColNo/2,row=ROWNO+1-ROWNO/2;ind < LEN;col++,ind++) {
                    /*
                    No blank rectangle is indicated for a space. A gap is merely left. Otherwise, the rectangle blank is drawn.
                    If the current character is a space, then foundCnt is incremented by 1. 
                    The user is unable to guess symbols. However, whether the user won or not is determined based on if the number of characters found (foundCnt) is equal to the length of the word (LEN). 
                    Spaces are included in the length, and if they cannot be guessed by the user, then it would otherwise be impossible to win the game. Thus, spaces must be preadded to the foundCnt variable (as LEN is a constant and cannot be changed).
                    */
                    if (WORD.charAt(ind) != ' ') {
                        c.fillRect(refX+BLANKS_X[col],refY+BLANKS_Y[row],45,5);
                    }
                    else foundCnt++;
                }
            }
        }

        // Draw the letter bank.
        c.setColor(BLUE);
        c.setFont(new Font("Courier New",Font.BOLD,25));

        /*
        This nested for loop structure draws the initial bank of letters to the Console. 
        The outer for loop: 
            Initialization: the y variable represents the y-coordinate (and row) of the letters being drawn in the current iteration. The intialized value is the y-coordinate of the first row. 
            Boolean expression: once the y-value goes beyond refY+500, rows should not be calculated or drawn anymore. The loop fulfiled its purpose.
            Increment: 25 pixels are in between each row. Thus, after the completion of each row of letters, 25 pixels is added to y to determine the y-coordinate of the next row.
        The inner for loop
            Initialization: the x variable represents the x-coordinate (and column) of the letters being drawn in the current iteration. The intialized value is the x-coordinate of the first column. 
            Boolean expression: once the y-value goes beyond refX+333, columns should not be calculated or drawn anymore. The loop fulfiled its purpose.
            Increment: 36 pixels are in between each column. Thus, after the completion of each letter, 36 pixels is added to x to determine the x-coordinate of the next row.
        Note that the letter bank is a 7 by 4 (columns by rows) grid of letters. 
        */
        for (int y=refY+375;y<refY+500;y+=25) {
            for (int x=refX+82;x<refX+333;x+=36) {
                // A local variable, as notated in the variable dictionary
                // The current letter is calculated based on the values of the loop variables as well as refX and refY. 
                // As the value is an integer, to safely use it, it must be casted to a char. 
                final char LTR = (char)(65+7*((y-refY-375)/25)+(x-refX-82)/36);

                // The last row of the letter box is not filled with letters. 
                // Thus, if the calculated letter is past the alphabet, then all of the letters have been drawn. Thus, it is redundant to continue and the loop is broken.
                // Note that the outer for loop does not need to be broken as the next value of y is guaranteed to render the loop's boolean expression false.
                if (LTR>'Z') break;

                // Draw the letter in the word bank. The x and y coordinates are based on the loop variables. 
                // As only String (as opposed to char) values can be drawn, an empty string is concatenated to the char to cast it to a String. 
                c.drawString(LTR+"",x,y);
            }
        }

        // This while loop takes in user input until the user quits, wins, or loses; these latter cases will terminate the loop or return statements. 
        while (true) {
            // A local variable, as notated in the variable dictionary
            char guess;

            // The code block for this if statement runs if all of the letters of the word have been found; its boolean expression evaluates for this condition. 
            if (foundCnt == LEN) {
                // If their score is -9999, then they have cheated and their score is already calculated.
                if (userScore != -9999) {
                    /*
                    The user's score is calculated by: 7*U-5*B+L+A+20
                    U - the number of unique letters in the word, 
                    B - number of bad guesses made (badGuessNo), 
                    L - the length of the word (LEN), 
                    A - additional points based on whether a hint was used and the game level
                    20 - 20 points are awarded for winning in the first place
                    */

                    /*
                    This for loop checks for unique characters in the string. For each unique character, the user's score is increased by 1.
                    There are 26 letters in the English alphabet. Each letter is assigned to one index in the boolean array letters, which stores whether a certain letter i has been guessed or not. 
                    */
                    for (int l=0;l<26;l++) {
                        if (letters[l] && WORD.indexOf((char)(l+'A')) != -1) userScore++;
                    }

                    // Calculate the remaining parts of the score
                    userScore *= 10;

                    // The nested ternary statements give the user 10 points if they did not get a hint in Level 1 and 15 in level 2. 
                    userScore += LEN - 5*badGuessNo + (!gotHelp ? (allowHint ? 10 : 15) : 0) + 20;
                }

                // Reset the user choice to that of the game level
                choice = allowHint ? '1' : '2';

                // As the user won the game, the method returns true. 
                return true;
            }

            // Draw the boxes outlining the instructions and the letters
            c.setColor(BLUE);
            c.drawRoundRect(standX-20,refY+345,330,120,45,45); // the instructions box
            c.drawRoundRect(refX+62,refY+345,275,120,45,45); // the box of letters
            
            // The instructions
            c.setColor(BLACK);
            c.setFont(new Font("Calibri",Font.PLAIN,15));
            c.drawString("Please enter one of the following:",refX-255,refY+382);
            c.drawString("[A-Z] An alphabetic character (your guess)",refX-235,refY+402);

            // Display this line if a hint was not obtained yet
            if (!gotHelp) {
                c.drawString(allowHint ? "? Show hint" : "# Cheat",refX-235,refY+422);
            }

            // The y-coordinate of this line depends on whether the previous line was displayed (i.e. whether a hint was obtained)
            c.drawString("! Give up",refX-235,refY+422+(gotHelp ? 0 : 20));

            /*
            This while loop gets the user's choice of screen on the main menu. 
            So long as the user enters invalid input, the loop will iterate. 
            */
            while (true) {
                // This try... catch structure checks whether the user has entered valid input. If not, an IllegalArgumentException is thrown.
                try {
                    // Get and store the user's choice
                    guess = c.getChar();

                    // This if structure checks if the user's choice was a valid option. If it is not, then an exception is thrown.
                    // For the first condition, the input is valid. guess-=32 changes the character from lower case to upper case; lowercase and uppercase characteras are 32 decimal values apart.
                    if (guess >= 'a' && guess <= 'z') guess -= 32;
                    else if (!(guess >= 'A' && guess <= 'Z') && guess != '!' && (allowHint ? !(guess == '?' && !gotHelp) : guess != '#'))
                        throw new IllegalArgumentException();

                    // This if statement is separate from the one above as the lowercaes letters are to be taken into context
                    // This ignores any input of previously guessed letters. guess >= 'A' && guess <= 'Z' is used as this condition only needs to be checked if the input is a letter.
                    if (guess >= 'A' && guess <= 'Z' && letters[guess-'A']) continue;

                    // The program will reach this line of code if the option is correct; otherwise an exception would have been thrown.
                    // Here, the execution of this loop will terminate. Its purpose has been fulfilled. 
                    break;
                } catch (IllegalArgumentException e) { // The program will reach this block if an invalid option has been thrown.
                    // Display an error message to the user. 
                    JOptionPane.showMessageDialog(null, "You must enter a valid option:\n   Your guess" + (!gotHelp ? "\n   " + (allowHint ? "? (show hint)" : "# (cheat)") : "") + "\n   ! (give up)", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }


            // This rectangle clears the instructions box
            c.setColor(WHITE);
            c.fillRoundRect(standX-15,refY+350,320,110,45,45);

            // This if structure executes different code blocks depending on the user's choice of character.
            // Different commands call for different lines of code. 
            // Note that '?' under the case of no hints being allowed has been troubleshooted for
            if (guess == '!') {
                // Give up; the user has quit the game. 
                // false is returned as they lost the game.
                return false;
            } else if (guess == '?') {
                // Set the font and text colour of the hint
                c.setFont(new Font("Courier New",Font.PLAIN,15));
                c.setColor(BLUE);

                // Draw the box surrounding the hint
                c.drawRoundRect(standX-20,refY+475,c.getWidth()-standX+5,30,45,45);

                // Draw the hint string. it is centre aligned to the console.
                c.drawString("Hint: "+wordBank[wordIndex][1].replaceAll(";;",","),c.getWidth()/2-5-5*wordBank[wordIndex][1].length(),refY+495);

                // As a hint has been obtained, set the gotHelp variable to true
                gotHelp = true;
            } else if (guess == '#') {
                // Show a cheat
                // This displays the answers in the hints box

                // Set the font and text colour of the box displaying the answer
                c.setFont(new Font("Courier New",Font.PLAIN,15));
                c.setColor(BLUE);

                // Draw the box surrounding the answer
                c.drawRoundRect(standX-20,refY+475,c.getWidth()-standX+5,30,45,45);

                // Draw the answer string
                c.drawString(wordBank[wordIndex][0],c.getWidth()/2-5-5*wordBank[wordIndex][0].length(),refY+495);

                // As a cheat has been obtained, set the gotHelp variable to true and the score to -9999
                userScore = -9999;
                gotHelp = true;
            } else {
                // Local variables, as notated in the variable dictionary
                int ind = 0;

                // Mark the character as having been guessed
                letters[guess-'A']=true;

                // For each unique character of a guess, the letter is "erased" from the letter bank - the letter is drawn on top in white colour (white is the background colour).
                c.setColor(WHITE);
                c.setFont(new Font("Courier New",Font.BOLD,25));

                // Set the font and text colour for erasing a letter in the letters box.
                c.drawString(""+guess,refX+82+36*((guess-'A')%7),refY+375+25*((guess-'A')/7));

                // The following code block checks whether the character is in the word and writes it on the blank, if present.

                // Set the text font and colour of the letters to be written on the blanks
                // This is not done inside the for loop as setting it here will have it set for all three loops, as opposed to writing the command for each loop.
                c.setColor(BLUE);
                c.setFont(new Font("Courier New",Font.BOLD,50));

                // This if structure checks if the guessed character is in the word/phrase. If it is, then find it among the blanks and draw it in its position. Otherwise, draw a part of the hangman.
                if (WORD.indexOf(guess) > -1) {
                    // The guessed character is in the word/phrase. The following block of code finds all occurences of said character and draws them in their position among the blanks.

                    // This if statement executes if there is more than one row of blanks. If there is only 1 row, then the code following this if statement block suffices.
                    // Note that all but the last row of blanks is drawn in this code block.
                    if (ROWNO > 1) {
                        /*
                        The following nested for loop draws the blanks for all rows excluding the last.
                        THe outer for loop is a counter for each row. 
                            The initialized value of row represents the first row that the letters will appear in. 
                            The boolean expression breaks the loop once the row is equal to the value of the last row they will appear in. 
                            No row is skipped; the row variable is incremented by 1 each time.
                        The inner for loop is a counter for each column. All of the columns in these rows are used. Thus the counter iterates throough all columns, from 0 to 5 inclusive.
                        */
                        for (int row=2-ROWNO/2;row<ROWNO+1-ROWNO/2;row++) {
                            for (int col=0;col<6;col++,ind++) {
                                // If the character at the current index is the guessed character, then draw it in.
                                if (WORD.charAt(ind) == guess) {
                                    // characters cannot be passed as arguments of drawString, hence the concatenation of an empty string; it casts the character to a String.
                                    c.drawString(WORD.charAt(ind)+"",refX+7+BLANKS_X[col],refY+BLANKS_Y[row]-5);
                                    foundCnt++; // a character has been found; thus this variable is to be incremented
                                }
                            }
                        }
                    }

                    /*
                    This if structure affects the placement of the letters. For the last row, if there are 4 or more letters, then they are to be left aligned. Otherwise, the letters are to be centre aligned. This follows the positioning of the already-drawn blanks.
                    */
                    if (lastColNo > 3) {
                        /*
                        Left aligned letters

                        This for loop initializes col to the first column and row to the last row that the letters will appear in. 
                        The boolean expression prevents StringIndexOutOfBound errors. 
                        After each blank is drawn, the loop moves on to the next index and column. Both need to be incremented.
                        */
                        for (int col=0,row=ROWNO+1-ROWNO/2;col<=lastColNo && ind < LEN;col++,ind++) {
                            // If the character at the current index is the guessed character, then draw it in.
                            if (WORD.charAt(ind) == guess) {
                                // characters cannot be passed as arguments of drawString, hence the concatenation of an empty string; it casts the character to a String.
                                c.drawString(WORD.charAt(ind)+"",refX+7+BLANKS_X[col],refY+BLANKS_Y[row]-5);
                                foundCnt++; // a character has been found; thus this variable is to be incremented
                            }
                        }   
                    } else {
                        /*
                        Centre aligned letters

                        This for loop initializes col to the the middle of the row (3), subtracted by half of the number of columns in the last row (lastColNo/2) and row to the last row that the letters will appear in. 
                        The boolean expression prevents StringIndexOutOfBound errors. 
                        After each blank is drawn, the loop moves on to the next index and column. Both need to be incremented.
                        */
                        for (int col=3-lastColNo/2,row=ROWNO+1-ROWNO/2;col<=3+lastColNo/2 && ind < LEN;col++,ind++) {
                            // If the character at the current index is the guessed character, then draw it in.
                            if (WORD.charAt(ind) == guess) {
                                // characters cannot be passed as arguments of drawString, hence the concatenation of an empty string; it casts the character to a String.
                                c.drawString(WORD.charAt(ind)+"",refX+7+BLANKS_X[col],refY+BLANKS_Y[row]-5);
                                foundCnt++; // a character has been found; thus this variable is to be incremented
                            }
                        }   
                    }
                } 
                else {
                    // The guessed character is not in the word/phrase.

                    /*
                    stickman.drawHangman will draw the next part of the stick man. 
                    A different part of the hangman is drawn depending on how many past mistakes have been made. 
                    badGuessNo is incremented as a bad guess has been made. 

                    The method returns true or false depending on if the stick man has been completed or not. If it has, then the user lost and this method returns false.
                    */
                    if (!stickman.drawHangman(++badGuessNo,refX,refY,standX,standY)) return false;
                }

                // Delay the Thread to show the changes that were made. 
                sleep(1000);
            }
        }
    } 

    /*
    The screen that appears when the user wins the game. Asks for the user's name to enter into the scoreboard. 
    
    Local Variable Dictionary
    ======================================== 
    NAME            DATATYPE          DESCRIPTION
    LVL             String            The level of the most recent game, 0-indexed (i.e. Level 1 is 0, Level 2 is 1)
    RANDOM_NAMES    String (final)    A list of sample names for the userName.
    userName        String            The user's chosen name to be listed on the scoreboard, if they are among the top 10 scores for their level
    */
    public void win() {
        // Local variables, as described in the dictionary
        final int LVL = choice-'1';
        final String [] RANDOM_NAMES = {"BitterBinder","FabulousFan","PointyPencil","PricklyPen","EagerEraser","WackyWater","RustyRuler","PolarPaper","CalmComputer","OddOffice","CreepyCalculator","MajesticMarker","StripedStapler","CrackedChair"};
        String userName = RANDOM_NAMES[(int)(Math.random()*RANDOM_NAMES.length)]; // initialize the userName to be a random name from the list of user names.

        // Fill in the background
        c.setColor(BLUE);
        c.fillRect(0,0,c.getWidth(),c.getHeight());

        // shackle of the lock
        c.setColor(WHITE);
        c.fillArc(10,120,150,180,0,180);

        c.setColor(BLUE);
        c.fillArc(35,140,100,150,0,180);

        // body of the lock 
        c.setColor(WHITE);
        c.fillRoundRect(-10,210,190,190,45,45);

        // keyhole
        c.setColor(BLUE);
        c.fillOval(67,280,30,30);
        c.fillRoundRect(75,295,15,40,20,20);

        // text heading 
        c.setColor(WHITE);
        c.setFont(new Font("Calibri",Font.BOLD,75));
        c.drawString("You win!",300,130);

        // display a "fact" about the word/phrase in the game
        factBox(wordBank[wordIndex][2]);

        // Draws some text
        c.setFont(new Font("Calibri",Font.PLAIN,30));
        c.drawString("Your score: "+userScore,c.getWidth()/2-260,460);
        c.drawString("Enter your name below:",c.getWidth()/2-260,500);

        c.setFont(new Font("Calibri",Font.PLAIN,20));
        c.drawString("Press the enter key to exit",c.getWidth()/2-100,600);

        // Font for the name
        c.setFont(new Font("Courier New",Font.BOLD,30));

        /*
        This while loop gets the user's name. 
        It runs so long as the user does not press the enter key, upon which the scoreborad will update with the name and score and the loop breaks.
        */
        while (true) {
            // Erase the previously drawn name
           c.setColor(WHITE);
           c.fillRoundRect(c.getWidth()/2-275,520,550,50,45,45);

            // Redraw the user's name
           c.setColor(BLUE);
           c.drawString(userName,c.getWidth()/2-260,555);

            /*
            This while loop gets a single character from the user and error traps for it. Error trap exists as names cannot exceed 28 characters in length nor contain commas.
            This single character is used to either exit the win screen or write the user's name.
            */
            while (true) {
                try {
                    // Get the user's character
                    choice = c.getChar();

                    // This if structure checks for invalid user input. 
                    // Names must be from 1 to 28 characters long and must not contain any commas.
                    // If the user attempts to delete a character from the name and the name is empty, then the command is ignored.
                    if (choice == 8 && userName.length() == 0) continue;
                    else if ((userName.length() > 28 && choice != 8) || (userName.length() == 0 && choice == 10) || choice == ',') throw new IllegalArgumentException();

                    // The program will reach this line of code if the option is correct; otherwise an exception would have been thrown.
                    // Here, the execution of this loop will terminate. Its purpose has been fulfilled. 
                    break;
                } catch (IllegalArgumentException e) {
                    // Display the error message.
                    JOptionPane.showMessageDialog(null, "Your name must follow four simple rules:\n  Consist of alphanumeric characters and/or symbols\n  Not contain commas\n  Not exceed 28 characters in length\n  Be at least 1 character in length.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Execute different commands depending on the character entered by the user. 
            // Namely, either exit the method or delete/add a character from/to the name.
            // This if structure controls that. 
            if (choice == 8) userName = userName.substring(0,userName.length()-1); // Deleting a character is equivalent to taking the substring of the string, excluding the last character.
            else if (choice == 10) {
                // If the user pressed the enter key, update the scoreboard with their name and score, and then exit the method.
                updateScoreboard(LVL,userName);
                return;
            }
            else userName += choice; // Append the character to the user's name.
        }
    }

    /*
    The screen that appears if a user loses the game. Displays the correct word/phrase to the user.
    
    Local Variable Dictionary
    ======================================== 
    NAME            DATATYPE        DESCRIPTION
    strLen          int             The length of the correct word/phrase.
    fontSize        int             The font size of the word/phrase; the initialized value varies depending on strLen. 
    */
    public void lose() {
        // The local variables, as described in the dictionary
        int fontSize;
        int strLen = wordBank[wordIndex][0].length();

        // the background
        c.setColor(BLUE);
        c.fillRect(0,0,c.getWidth(),c.getHeight());

        // a graphic of a lock that is unlocked
        // shackle of the lock
        c.setColor(WHITE);
        c.fillArc(115,105,150,220,0,180);

        c.setColor(BLUE);
        c.fillArc(145,135,90,180,0,180);

        // body of the lock 
        c.setColor(WHITE);
        c.fillRoundRect(-25,210,190,190,45,45);

        // keyhole
        c.setColor(BLUE);
        c.fillOval(57,280,30,30);
        c.fillRoundRect(60,295,15,40,20,20);

        // the text
        // A heading
        c.setColor(WHITE);
        c.setFont(new Font("Calibri",Font.BOLD,75));
        c.drawString("You lost",350,130);

        // A smaller heading
        c.setFont(new Font("Calibri",Font.PLAIN,30));
        c.drawString("The word was:",c.getWidth()/2-260,470);

        // This if structure determines the size of the to-be-displayed word/phrase string. 
        // The size is based on the string length; the smaller the string, the larger the text. 
        // If the string length is larger than 22 characters, then it defaults to a font size of 30. 
        if (strLen < 14) fontSize = 70;
        else if (strLen < 16) fontSize = 60;
        else if (strLen < 19) fontSize = 50;
        else if (strLen < 23) fontSize = 40;
        else fontSize = 30;

        // The actual word/phrase used in the game
        c.setFont(new Font("Courier New",Font.BOLD,fontSize));
        c.drawString(wordBank[wordIndex][0].toLowerCase(),c.getWidth()/2-260,540);

        // Display a "fact" about the word/phrase used in the game
        factBox(wordBank[wordIndex][2]);

        // Continue to the main menu upon key press by the user
        c.getChar();
    }

    /*
    Displays more information / a fact about a word.
    
    Local Variable Dictionary
    ===============================================
    NAME            DATATYPE            DESCRIPTION
    fact            String              The fact to be displayed. The sole parameter.
    rowNo           int                 The approximate number of rows used by the fact when drawn on the Console. 
    FACT_WORDS      String [] (final)   An array of the words of the fact. Used to help calculate the drawing of the fact onto the Console.
    y               int                 The y-coordinate of the fact on the Console. Varies based on the rowNo variable. 
    i               int                 A loop variable; used to draw the border of the box surrounding the fact. 
    ind             int                 A loop variable; used to indicate the index of the FACT_WORDS that will be displayed next
    */
    public void factBox(String fact) {
        // A local variable, as notated in the dictionary
        int rowNo;

        // In the wordbank file, the word, hint and fact are comma separated. As a result, any usage of commas in the fact are notated by ";;". 
        // Here, they are being replaced by ordinary commas. 
        fact = fact.replaceAll(";;",",");
        rowNo = (int)(Math.ceil(fact.length()/26.0)); // rowNo is initialized separately from its declaration as it relies on a modified version of the fact string. 

        // Set the text font and colour of the fact. 
        c.setColor(WHITE);
        c.setFont(new Font("Courier New",Font.PLAIN,20));

        // The fact is drawn to the Console slightly differently based on the approximate number of rows (rowNo) that it will take up.
        // This if structure controls that; essentially, code is separated based on if there is 1 or more than 1 rows.
        if (rowNo == 1) {
            // Draw the entire fact string to the console.
            c.drawString(fact,413-6*fact.length(),290);

            // The box border
            // The thickness of the box is three pixels; this loop will iterate three times, with i holding a value of either 0, 1, or 2. 
            // The box width spans the text. 
            for (int i=0;i<3;i++) {
                c.drawRect(403+i-6*fact.length(),264+i,12*fact.length()+20,40);
            }
        } else {
            // Local variables, as described in the dictionary.
            int y = 310-(rowNo/2)*25;
            final String [] FACT_WORDS = fact.split(" "); // a space delimiter is used as words are space separated. 

            /*
            This for loop draws the fact onto the Console.
            The ind variable is initialized to 0; the first word of the fact is in the 0th index of the FACT_WORDS array. 
            So long as there are words in the aforementioned array that have not yet been drawn to the Console, then the loop will continue. The boolean expression controls this condition.
            Each iteration of the outer for loop represents a row. Thus, y is incremented by 25 for each iteration, representing the y-coordinate of the current row.
            The incrementation of the ind variable happens in the inner for loop. 
            */
            for (int ind=0;ind<FACT_WORDS.length;y+=25) {
                // A local variable, as notated in the dictionary; for scoping reasons, it must be declared here. 
                String toDisplay = "";

                /*
                This for loop appends the words to be drawn in the current row to the toDisplay string so long as the number of characters in said string does not exceed the limit per row (26 characters). 
                So long as there are words in the FACT_WORDS array that have not yet been drawn to the Console, then the loop will continue. The boolean expression controls this condition. 
                    The loop will also break if the words to be printed in the row exceed the limit, as mentioned. The if statement inside the loop controls this, as it will break the loop if said condition is rendered false.
                No variables need to be initialized. 
                */
                for (;ind<FACT_WORDS.length;ind++) {
                    // The if struture, as mentioned above. 
                    if ((toDisplay+FACT_WORDS[ind]).length() < 26) toDisplay += FACT_WORDS[ind]+" ";
                    else break;
                }

                // Draw the string to the console. This is one row of the fact. 
                c.drawString(toDisplay,295,y);
            }

            // The box border
            // The thickness of the box is three pixels; this loop will iterate three times, with i holding a value of either 0, 1, or 2. 
            for (int i=0;i<3;i++) {
                c.drawRect(279+i,279+i-(rowNo/2)*25,325,y-280+(rowNo/2)*25);
            }
        }
    }

    /*
    Loads a given file int a given 2D String array.
    Note that the 2D arr parameter of this method must be already initialized, even if its empty. 
    This is because reinitializing the array parameter in this method changes the reference of the local array variable of this method, but not the reference of the array that was passed in as an argument.
    However, assigning values to indicies of the original array passed in as a reference (no reinitialization was performed) would affect the argument array as the reference refers to the same memory location.
    
    Local Variable Dictionary
    ==========================================
    NAME          DATATYPE          DESCRIPTION
    fileName      String            The name of the file to be read and loaded into the array. A parameter.
    arr           String [][]       The array to be filled. A parameter. 
    br            BufferedReader    Object to read the file.
    ind           int               A loop variable used to iterate through the indices of the array. 
    i             int               A loop variable used to skip the top heading of the file. 
    e             IOException       The error from the BufferedReader, if caught. Exists for error trapping. 
    */
    private void loadFile(String fileName, String [][] arr) {
        // This try.. catch block catches IOException errors that are thrown from the BufferedReader. 
        // Nothing is to occur if any errors are caught. 
        try {
            // A local variable, as notated in the dictionary. 
            // This BufferedReader reads the file indicated by the filename parameter. 
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            // The headers for all files used in this program are 4 lines long.
            // This for loop iterates 4 times, reading and ignoring the header of the file.
            for (int i=0;i<4;i++) 
                br.readLine();

            // This loop iterates through each index of the array and assigns the current line of the file, space delimited into an array, to that index.
            // This is performed for each index of the array. The boolean expression breaks the loop before it attempts to change an array index that is beyond its length (which would produce an ArrayIndexOutOfBounds exception).

            for (int ind=0;ind<arr.length;ind++) {
                arr[ind] = br.readLine().split(",");
            }
        } catch (IOException e) {};
    }

    /*
    Clears a given file and writes a header afterwards (the header may be empty). 
    
    Local Variable Dictionary
    ======================================== 
    NAME          DATATYPE          DESCRIPTION
    fileName      String            The name of the file to be cleared.
    header        String            The header/String that is written into the file after it is cleared.
    pw            PrintWriter       Writes to a given file, in this case the file name stored in the fileName parameter
    e             IOException       The error from the PrintWriter, if caught. Exists for error trapping. 
    */
    private void clearFile(String fileName, String header) {
        // This try.. catch block catches IOException errors that are thrown from the PrintWriter. 
        // Nothing is to occur if any errors are caught. 
        try {
            // A local variable, as notated in the above dictionary. 
            // This PrintWriter prints to the file whose name was passed as an argument to this method call.
            PrintWriter pw = new PrintWriter(new FileWriter(fileName));

            // Print the header, which may contain multiple lines (separated by newline characters)
            pw.println(header);

            // Closes the PrintWriter stream
            pw.close();
        } catch (IOException e) {};
    }

    /*
    Loads the scoreboard file into the scoreboard arrays.

    Local Variable Dictionary
    ======================================== 
    NAME          DATATYPE          DESCRIPTION
    br            BufferedReader    Object to read the file
    currStr       String            The string that the BufferedReader had just read
    lvl           int               The scoreboard level that the method is currently loading into the array
    rank          int               A loop variable; the 0-indexed rank of the scoreboard that the method is working with. 
    entry         String []         The current entry of the scoreboard file that the method is working with. It consists of the user's name in the 0th index and their score in the 1st index. 
    i             int               A loop variable used to skip the top heading of the file. 
    e             IOException       The error from the BufferedReader, if caught. Exists for error trapping. 
    */
    private void loadScoreboard() {
        // This try.. catch block catches IOException errors that are thrown from the BufferedReader. 
        // Nothing is to occur if any errors are caught. 
        try {
            // Local variables, as notated in the dictionary. 
            // This BufferedReader reads the "hangman.scoreboard" file.
            BufferedReader br = new BufferedReader(new FileReader("hangman.scoreboard"));
            String currStr;
            int lvl = 0; // The level whose scoreboard appears first is Level 1; as levels are 0-indexed, the initialized value is 0.

            // skips the header and "Level 1:" subheading, which is 4 lines long
            for (int i=0;i<4;i++) {
                br.readLine();
            }

            currStr = br.readLine(); // initialize currStr with the next line in the file.

            /*
            This for loop reads the remainder of the file and stores the available scores in the scoreboard arrays. 
            The first rank, 0-indexed, is 0. For each iteration of the loop, one line is read, which represents one rank entry. 
            The loop continues so long as the file has not been completely read yet. 
            */
            for (int rank=0;currStr != null;rank++) {
                // This if structure checks the contents of the just-read line of the file (currStr) and acts accordingly.
                // If the currStr consists of the Level 2 subheading, then the level is switched to the index of the scoreboard representing the level 2 scores and the rank is reset.
                if (currStr.startsWith("Level 2:")) {
                    lvl = 1; // ranks are 0-indexed. 1 represents the Level 2 scoreboard. 
                    rank = -1; // as after the completion of this iteration of the for loop, rank is incremented, for the next line in the file, rank will be 0, which is desired.
                }
                else if (currStr.indexOf(',') > -1) {
                    // This code block runs if the currStr is an entry of the scoreboard (i.e. if it contains commas).

                    // Split the current entry of the scoreboard into an array, based on a comma delimiter.
                    // The first element of this array is the name of the user; the second element is the score. 
                    String [] entry = currStr.split(",");
                    scoreboardNames[lvl][rank] = entry[0];
                    scoreboardNos[lvl][rank] = Integer.parseInt(entry[1]); // scores are guaranteed to be integers
                }

                // Read the next line of the file. 
                currStr = br.readLine();
            }
        } catch (IOException e) {};
    }

    /*
    Update the scoreboard array and file for a given level. The scoreboard is sorted by score, and to break ties, by name.

    Local Variable Dictionary
    ======================================= 
    NAME        DATATYPE        DESCRIPTION
    lvl         int             The level that the game was in; holds a value of 1 or 2. 
    name        String          The user's name.
    rank        int             A loop variable. Used to determine the user's rank in the scoreboard, if they place in the top 10 scores. 
    r           int             A loop variable. Used to push down rank entries by 1 rank in the case that the user's rank is in the top 9. 
    l           int             A loop variable. Represents the current level that the PrintWriter is printing.
    pw          PrintWriter     Writes to a given file, in this case "hangman.scoreboard".
    e           IOException     The error from the PrintWriter, if caught. Exists for error trapping.
    */
    private void updateScoreboard(int lvl,String name) {
        /*
        This for loop goes through the ranks of the current level's scoreboard and seeks to find the rank of the user's most recent game.
        For each rank, the score is compared with the score of that rank. 
        The scoreboard hosts only the top 10 ranks; if the user's score is below the top 10, then their name and score is not recorded. Accordingly, the loop only iterates for the 10 indicies that correspond to those ranks, and the boolean expression breaks the loop once all have been checked (i.e. when rank >= 10).
        */
        for (int rank=0;rank<10;rank++) {
            // This if statement checks if the user's rank is at the current rank.
            // This is true if the score recorded at this rank is less than the user's score, if the entry is empty, or if they are equal and the user's name alphabetically appears before the name of the current rank.
            if (scoreboardNames[lvl][rank] == null || scoreboardNos[lvl][rank] < userScore || (scoreboardNos[lvl][rank] == userScore && scoreboardNames[lvl][rank].compareTo(name) > 0)) {
                /*
                This for loop pushes the current ranks down by one rank. 
                Pushing down words by assigning the values of the current entry to the indices of the rank below.
                r is the current rank being pushed down.
                The loop iterates backwards to prevent entries from being overwritten before they are pushed.
                Note that if the user's rank is 10th, then it is redundant to "push down" the 10th rank to 11th as the 11th rank is not recorded. The loop will not run as a rank of tenth means that rank is equal to 9. 8 is not greater than or equal to 9; thus the loop will not execute at all.
                */
                for (int r=8;r>=rank;r--) {
                    scoreboardNames[lvl][r+1] = scoreboardNames[lvl][r];
                    scoreboardNos[lvl][r+1] = scoreboardNos[lvl][r];
                }

                // Update the current rank with the data of the most recent game. 
                scoreboardNames[lvl][rank] = name;
                scoreboardNos[lvl][rank] = userScore;

                // Rewrite the scoreboard file, updating it with the current scoreboard data.
                // This try.. catch block catches IOException errors that are thrown from the PrintWriter. 
                // Nothing is to occur if any errors are caught. 
                try {
                    // a local variable, as described in the dictionary. It is declared here for scoping purposes. 
                    PrintWriter pw = new PrintWriter(new FileWriter("hangman.scoreboard"));

                    // print the header of the scoreboard to the file
                    pw.println("The Scoreboard");
                    pw.println("==============");
                    pw.println();

                    /*
                    This nested for loop structure prints the scoreboard rankings of each level to the file. 
                    The outer for loop controls the index of level that the PrintWriter is printing. 
                    There are two levels, and in the array, they are 0-indexed. Thus, Level 1 is at index 0, and Level 2 is at index 1. After this, the loop breaks; there are no other levels.
                    The inner for loop writes each rank and its data to the file.
                    */
                    for (int l=0;l<2;l++) {
                        // Print the level header. 
                        pw.println("Level "+(l+1)+":");

                        /*
                        This for loop iterates through the ranks in each scoreboard and writes them to the file. 
                        There are 10 ranks in the file; ranks are 0-indexed in the scoreboard array, hence why rank is initialized to 0 and breaks once it is equal to 10.
                        */
                        for (rank=0;rank<10;rank++) {
                            // If the name is null, then this entry and subsequent entries are empty. Thus, the loop can be broken as no more ranks need to be written in.
                            if (scoreboardNames[l][rank] == null) break;

                            // The name and score associated with each rank of a level is written into the file. They are comma separated.
                            pw.println(scoreboardNames[l][rank]+","+scoreboardNos[l][rank]);
                        }

                        // Add an empty new line to the end of the file.
                        pw.println();
                    }

                    // Close the PrintWriter stream
                    pw.close(); 
                } catch (IOException e) {};

                // Only one rank is changed at a time. Thus, after a change has been made, the purpose of the method has been fulfilled and it can be terminated.  
                return;
            } 
        }
    }

    /*
    The goodbye method.
    This is where the goodbye message is drawn. 
    This method does not return any value nor takes any parameters. 


    Local Variable Dictionary
    =================================================
    NAME          DATATYPE                DESCRIPTION
    footerDecor   Decorations             The above-the-footer decoration to be drawn on the Console.
    click         Decorations             The locked lock/click "sound" animation to be drawn on the Console.
    */
    public void goodbye() {
        // Local variables, as defined above
        Decorations footerDecor = new Decorations(c,"above footer");
        Decorations click = new Decorations(c);

         // Erase main menu text, but not the decorations
        c.setColor(WHITE);
        c.fillRect(0,85,c.getWidth(),475);

        // draw the footer decoration
        footerDecor.start();

        // Set font and text colour
        c.setColor(BLUE);
        c.setFont(new Font("Calibri",Font.PLAIN,25));

        // Goodbye message
        c.drawString("Thank you for playing this Hangman game. It has terminated.",20,160);

        // Centre-align the programmer, class code, and date.
        c.drawString("Made by",c.getWidth()/2-40,200);
        c.drawString("Celeste Luo",c.getWidth()/2-60,240);
        c.drawString("ICS3U3",c.getWidth()/2-35,280);
        c.drawString("01.14.2020",c.getWidth()/2-55,320);

        c.drawString("Press any key to exit the program.",c.getWidth()/2-170,370);

        // User presses a key to close the program
        c.getChar();

        // Erase goodbye screen text, but not the decorations
        c.setColor(WHITE);
        c.fillRect(0,85,c.getWidth(),375);

        // shackle of the unlocked lock
        c.setColor(BLUE);
        c.fillArc(380,125,150,220,0,180);

        c.setColor(WHITE);
        c.fillArc(410,155,90,180,0,180);

        // body of the lock 
        c.setColor(BLUE);
        c.fillRoundRect(240,230,190,190,45,45);

        // keyhole
        c.setColor(WHITE);
        c.fillOval(317,300,30,30);
        c.fillRoundRect(325,315,15,40,20,20);

        // Delay the switching of the lock to show the original lock
        sleep(500);

        // Erase the old shackle
        c.setColor(WHITE);
        c.fillRect(350,120,200,120);

        // shackle of the closed lock
        c.setColor(BLUE);
        c.fillArc(260,140,150,180,0,180);

        c.setColor(WHITE);
        c.fillArc(285,160,100,150,0,180);

        // As part of the lock body was erased to redraw the shackle, the upper part of the body is redrawn as well
        c.setColor(BLUE);
        c.fillRoundRect(240,230,190,50,45,45);

        // Animate the "clicking" sound of the locked lock
        click.lockClick();

        // Delay the screen closure to show the newly closed lock
        sleep(800);

        c.close(); // closes the console window and terminates the program
    }

    /*
    Draws a stand for the hangman. This is done by calling a method from the Decorations class
    
    Local Variable Dictionary
    =======================================
    NAME        DATATYPE        DESCRIPTION
    x           int             The x-coordinate of the top left corner of the stand. 
    y           int             The y-coordinate of the top left corner of the stand. 
    animate     boolean         Whether or not to animate the drawing of the stand. If the value is false, then regular lines simply appear. 
    stand       Decorations     Object to draw the actual stand.
    */
    public void drawStand(int x, int y, boolean animate) {
        Decorations stand = new Decorations(c,"stand");
        stand.drawStand(x,y,animate);
    }

    /*
    The main method
    This is where the program is controlled through method calls
    This method does not return any value nor takes any parameters.     

    Local Variable Dictionary
    ====================================
    NAME        DATATYPE     DESCRIPTION
    hm          Hangman      The object where the window screen methods of the program are located. 
    */
    public static void main(String [] args) {
        // Create a new object from the Hangman class
        Hangman hm = new Hangman();
        hm.splashScreen(); // the splash screen
        hm.loadScoreboard(); // load the scoreboard into the array
        hm.loadFile("wordbank.csv",hm.wordBank); // load the word bank

        // This do..while loop runs the program so long as the user does not choose to quit. 
        do {
            hm.mainMenu(); // the main menu

            // This switch statement determines what screen to run based on the user's choice in the main menu. 
            switch (hm.choice) {
                case '?': // display the instructions
                    hm.instructions();
                    break;
                case 's':
                case 'S': // display the scoreboard
                    hm.viewScoreboard();
                    break;
                case '1':
                case '2': // play a game
                    hm.levelIntro(hm.choice); // display the introduction to a level
                    
                    // This if structure determines what to display based on if the user wins or loses. 
                    // Note that the boolean expression calls a method, which returns a boolean value. This method is the actual game
                    if (hm.level(hm.choice == '1')) hm.win(); // if the user wins, display the win screen
                    else hm.lose(); // the user lost; display the lose screen
                    break;
                // no default case; other entries other than 'q' and 'Q' (for quit) are error trapped by the main menu
            }
        } while (hm.choice != 'q' && hm.choice != 'Q'); 

        // Display the exit screen
        hm.goodbye();
    }
}
