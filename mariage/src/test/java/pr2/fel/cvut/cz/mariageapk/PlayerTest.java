package pr2.fel.cvut.cz.mariageapk;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pr2.fel.cvut.cz.mariageapk.MakeDeck;
import pr2.fel.cvut.cz.mariageapk.Player;

import static org.junit.Assert.*;


public class PlayerTest {
    /**
     * test restart method
     */
    @Test
    public void testRestart() {
        Player player = new Player("Bot Bert", true, 50, 2);
        Card instance = new Card(7, 2);
        player.addCard(instance);
        player.restart();
        assertTrue(player.getDeckSize() == 0);
    }

    @Test
    public void testGetDeck() {
        MakeDeck deck = new MakeDeck();
        deck.setDeck();
        Card[] card = deck.getDeck();
        Player player = new Player("Bot Bert", true, 50, 2);
        for(int i=0; i<15; i++){
            player.addToDeck(card[i]);
        }
        Card[] pl=player.getDeck();
        for(int i=0; i< 15; i++){
            assertTrue(pl[i]==card[i]);
        }
    }

    @Test
    public void testYesOrNo() {
        Player player = new Player("Bot Bert", true, 50, 2);
        player.setYesOrNo(0);
        assertTrue(player.getYesOrNo() == 0);
        player.setYesOrNo(1);
        assertTrue(player.getYesOrNo()== 1);
    }

    @Test
    public void testLocalTrumpCound() {
        Player player = new Player("Bot Bert", true, 50, 2);
        Card[] card = new Card[6];
        for(int i=0; i<6; i++){
            card[i]=new Card(8, 3);
        }
        player.trumpsCount(3);
        assertTrue(player.getLocalTrumpCount()==6);
    }

    @Test
    public void test() {

    }

    @Test
    public void test() {

    }

    @Test
    public void test() {

    }

    @Test
    public void test() {

    }

}
