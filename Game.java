import java.util.*;
/**
 * Write a description of class Game here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Game
{

    private Player p1;
    private Player p2;
    private Player currentPlayer;

    private Stadium currentStadium;

    private int turnNumber; // if p1 starts, then p1 starts on turn number 1, p2 starts on turn number 2, therefore p1 is on turnNumber%2==1, p2 on turnNumber%2==0;

    /**
     * Constructor for objects of class Game
     */
    public Game(Player p1, Player p2)
    {
        this.p1 = p1;
        this.p2 = p2;
    }

    public boolean isSetup()
    {
        return checkDeckSize() && check4Cards();
    }

    public void setupGame() throws BadDeckException
    {
        if (!isSetup())
        {
            throw new BadDeckException();
        }

        int p1Mulligans = p1.drawOpeningHand();
        int p2Mulligans = p2.drawOpeningHand();

        p1.chooseActivePokemonSetup();
        p2.chooseActivePokemonSetup();

        //NOT A MISTAKE! p1 draws mulligans based off of p2's # of mulligans, vice versa. 
        p1.drawNCards(p2Mulligans);
        p2.drawNCards(p1Mulligans);

        p1.chooseBenchedPokemonSetup();
        p2.chooseBenchedPokemonSetup();

        //change this value if you want to adjust prizes
        int numPrizes = 6;

        for (int i = 0; i < numPrizes; i++)
        {
            p1.addPrizeFromDeck();
            p2.addPrizeFromDeck();
        }

        turnNumber = 0;

    }
    //Is this really the best way to do it? Sure, I guess...
    public void doTurn(Player p)
    {
        p.doTurn();
        resolveBetweenTurns();
    }

    
    
    private boolean checkDeckSize()
    {
        //allows for easily adjustable formats
        int properDeckSize = 60;

        //I know I could just do return condition1 && condition2, but this seems more obvious to me
        if (p1.getDeck().size() == properDeckSize && p2.getDeck().size() == properDeckSize) 
        {
            return true;
        }

        else 
        {
            return false;
        }
    }

    /**
     * Purpose: Make sure each deck has strictly less than 5 of the same card (b/c (n<5) <==> !(n>4) if n is an element of set of integers)  
     * Future todos: Possibly change return type from boolean to int to allow for different errors (0 if everything's hunky-dory, 1 if p1 failed, 2 if p2 failed)? Maybe split
     * into 2 method calls, where check4Cards takes an ArrayList<Cards>?
     */

    private boolean check4Cards()
    {

        for (Card c1 : p1.getDeck())
        {
            int counter = 0;
            for (Card c2 : p1.getDeck())
            {
                if (c1.equals(c2))
                {
                    counter += 1;
                }
            }
            if (counter > 4)
            {
                return false;
            }
        }

        for (Card c1 : p2.getDeck())
        {
            int counter = 0;
            for (Card c2 : p2.getDeck())
            {
                if (c1.equals(c2))
                {
                    counter += 1;
                }
            }
            if (counter > 4)
            {
                return false;
            }
        }

        return true;

    }

    /**
     * IMPLEMENT THIS LATER!!!
     */
    private void resolveBetweenTurns()
    {

    }

    /**
     * PERSONALLY IMPLEMENT THIS METHOD!!!
     */
    public boolean isWon()
    {
        return true;
    }

    public Player getNonCurrentPlayer()
    {
        if (currentPlayer.equals(p1))
        {
            return p2;
        }

        return p1;
    }
    
    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }
    
    public Player[] getPlayers()
    {
        return new Player[]{p1, p2};
    }
    
    public ArrayList<Pokemon> getAllPokemon()
    {
        ArrayList<Pokemon> pList = new ArrayList<Pokemon>();
        pList.add(currentPlayer.getActive());
        pList.add(getNonCurrentPlayer().getActive());
        pList.addAll(currentPlayer.getBench());
        pList.addAll(getNonCurrentPlayer().getBench());
        return pList;
    }
    
}
