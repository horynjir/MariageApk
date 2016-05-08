package pr2.fel.cvut.cz.mariageapk;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class CardTest {
    /**
     * Test if setted color is changed
     */
    @Test
    public void testSetColor() {
        Card instance = new Card(8, 3);
        int color=2;
        instance.setColor(color);
        assertTrue(instance.getColor()== 4);
    }

    /**
     * Tests if getValue returns right value. In Cards, value is from 7 to 14, but it should be able work with another values
     */
    @Test
    public void testGetValue() {
        for(int i=0; i<10; i++) {
            int num = (int) (Math.random() * 20);
            Card instance = new Card(num, 2);
            assertTrue(instance.getValue()== num);
        }
    }

    /**
     * Test similar thing like test method before
     */
    @Test
    public void testGetColor() {
        for(int i=0; i<10; i++) {
            int num = (int) (Math.random() * 20);
            int color = (int) (Math.random() * 4);
            Card instance = new Card(num, color);
            assertTrue(instance.getColor()== color);
        }
    }

    /**
     * tests if this method right gets setted ID of card
     */
    @Test
    public void testSetAndGetId() {
        int num = (int) (Math.random() * 20);
        int color = (int) (Math.random() * 4);
        Card instance = new Card(num, color);
        for(int i=0; i<10; i++) {
            int id = (int) (Math.random() * 1000);
            assertTrue(instance.getId()== id);
        }
    }

    /**
     * Tests if card with higher value returns higher points
     */
    @Test
    public void testCardPoints() {
        int num = (int) ((Math.random() * 4)+7);
        int num2 = (int) ((Math.random() * 4)+11);
        int color = (int) (Math.random() * 4);
        Card instance = new Card(num, color);
        Card instance2 = new Card(num2, color);
        for(int i=0; i<20; i++) {
            int trump = (int) (Math.random() * 4);
            int localTrump = (int) (Math.random() * 4);
            int game = (int) (Math.random() * 3);
            assertTrue(instance.cardPoints(localTrump, trump, game)< instance2.cardPoints(localTrump, trump, game));
        }
    }


}
