package pr2.fel.cvut.cz.mariageapk;
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

    /**
     * test get deck method
     */
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

    /**
     * test if returns value which was setted
     */
    @Test
    public void testYesOrNo() {
        Player player = new Player("Bot Bert", true, 50, 2);
        player.setYesOrNo(0);
        assertTrue(player.getYesOrNo() == 0);
        player.setYesOrNo(1);
        assertTrue(player.getYesOrNo()== 1);
    }

    /**
     * Test roght count of local trumps
     */
    @Test
    public void testLocalTrumpCount() {
        Player player = new Player("Bot Bert", true, 50, 2);
        Card[] card = new Card[6];
        for(int i=0; i<6; i++){
            card[i]=new Card(8, 3);
            player.addCard(card[i]);
        }
        player.trumpsCount(3);
        assertTrue(player.getLocalTrumpCount()==6);
    }

    /**
     * Test if cards are added in right order
     */
    @Test
    public void testGetPlayerCard() {
        Player player = new Player("Bot Bert", true, 50, 2);
        Card c1 = new Card(8, 3);
        Card c2 = new Card(10, 2);
        Card c3 = new Card(12, 1);
        player.addCard(c1);
        player.addCard(c2);
        player.addCard(c3);
        assertTrue(player.getPlayerCard(1)==c2);
    }

    /**
     * Tests if we gets cards which we set
     */
    @Test
    public void testSetPlayerCard() {
        Player player = new Player("Bot Bert", true, 50, 2);
        Card c1 = new Card(8, 3);
        Card c2 = new Card(10, 2);
        Card c3 = new Card(12, 1);
        Card c4 = new Card(7, 0);
        player.addCard(c1);
        player.addCard(c2);
        player.addCard(c3);
        player.setPlayerCard(1, c4);
        assertTrue(player.getPlayerCard(1) == c4);
    }

    /**
     * Tests if method right counts score of cards
     */
    @Test
    public void testScore() {
        Player player = new Player("Bot Bert", true, 50, 2);
        Card c1 = new Card(8, 3);
        Card c2 = new Card(10, 2);
        Card c3 = new Card(12, 1);
        Card c4 = new Card(14, 3);
        Card c5 = new Card(10, 2);
        Card c6 = new Card(8, 1);
        player.addCard(c1);
        player.addCard(c2);
        player.addCard(c3);
        player.addCard(c4);
        player.addCard(c5);
        player.addCard(c6);
        int sc=player.scoreOfGame();
        assertTrue(sc == 30);
    }

    /**
     * tests if player give another player right count of coins
     */
    @Test
    public void testPay() {
        Player player = new Player("Bot Bert", true, 50, 2);
        int p = player.pay(player, 2, 1);
        assertTrue(p == 8);
    }

    /**
     * Tests if player adds coins which someone give him
     */
    @Test
    public void testIncreaseCoins() {
        Player player = new Player("Bot Bert", true, 50, 2);
        player.increaseCoins(20);
        assertTrue(player.getCoins() == 70);
    }

    /**
     * Tests if returns 0 or 1
     */
    @Test
    public void testFlek() {
        Player player = new Player("Bot Bert", true, 50, 2);
        assertTrue(player.flek()<2);
        assertTrue(player.flek()>-1);
    }

    /**
     * Tests if returns 0 or 1
     */
    @Test
    public void testLicitate() {
        Player player = new Player("Bot Bert", true, 50, 2);
        assertTrue(player.licitate(1)<2);
        assertTrue(player.licitate(1)>-1);
    }
}
