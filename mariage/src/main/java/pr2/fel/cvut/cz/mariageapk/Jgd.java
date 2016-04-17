package pr2.fel.cvut.cz.mariageapk;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Jiří on 15. 4. 2016.
 */
public class Jgd extends Activity{
    private final int GAME_PRICE = 2;
    private final int BETL_PRICE = 4;
    Card[] card;
    Card[] talon = new Card[2];
    int playerCount = 0;
    Player[] player = new Player[3];
    int flek = 0;
    int game = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginning);
    }



    /*Jgd(Card[] card) {
        this.card = card;
        //shuffleDeck(card);
    }*/

    private void addPlayer(Player player) {
        this.player[playerCount] = player;
        playerCount++;
    }
/*
    public void shuffleDeck(Card[] card) {
        int max = card.length - 1;
        int num = (int) ((Math.random() * 50) + 51);
        for (int i = 0; i < num; i++) {
            int a = (int) (Math.random() * 32);
            int b = (int) (Math.random() * 32);
            Card temp = card[a];
            card[a] = card[b];
            card[b] = temp;
        }
    }

    public void game() {
        player[2].removeDeck(card);
        deal();
        //System.out.println(player[0].toString());
        //firstLicitation(0, player[0], player[2], player[1]);
    }

    public void deal() {
        int temp = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                player[i].addCard(card[temp]);
                temp++;
            }
        }
        for (int i = 0; i < 2; i++) {
            talon[i] = card[temp];
            temp++;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                player[i].addCard(card[temp]);
                temp++;
            }
        }
    }*/
}
