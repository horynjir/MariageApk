package pr2.fel.cvut.cz.mariageapk;

/**
 * Created by Jiří on 6. 3. 2016.
 */
public class Arena {
    Card[] card;
    int playerCount=0;
    Player[] player=new Player[3];

    Arena(Card[] card){
        this.card=card;
    }

    public void addPlayer(Player player){
        this.player[playerCount]=player;
        playerCount++;
    }

    public void shuffleDeck(Card[] card){
        int max=card.length-1;
        int num = (int)((Math.random()*50)+51);
        for(int i=0; i<num; i++) {
            int a = (int) (Math.random() * 32);
            int b = (int) (Math.random() * 32);
            Card temp=card[a];
            card[a]=card[b];
            card[b]=temp;
        }
    }

    public void removeDeck(Card[] card){
        int max=card.length-1;
        int half = (int) (Math.random() * 22+5);
        Card[] firstHalf = new Card[half];
    }
}
