package pr2.fel.cvut.cz.mariageapk;

/**
 * Created by Jiří on 6. 3. 2016.
 */
public class Player {
    String name;
    private Card[] card=new Card[10];
    int cardCount=0;

    Player(String name){
        this.name=name;
    }

    public void addCard(Card card){
        this.card[cardCount]=card;
        cardCount++;
    }

}
