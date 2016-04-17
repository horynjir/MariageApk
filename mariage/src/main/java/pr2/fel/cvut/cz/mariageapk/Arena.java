/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pr2.fel.cvut.cz.mariageapk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 *
 * @author Jiří
 */
public class Arena extends Activity {

    private final int GAME_PRICE = 2;
    private final int BETL_PRICE = 4;
    private final int DURCH_PRICE = 6;
    Card[] card;
    Card[] talon = new Card[2];
    int playerCount = 0;
    Player[] player = new Player[3];
    int flek = 0;
    int game = 0;

    /*ImageButton c1;
    ImageButton c2;
    ImageButton c3;
    ImageButton c4;
    ImageButton c5;
    ImageButton c6;
    ImageButton c7;
    ImageButton c8;
    ImageButton c9;
    ImageButton c10;*/

    ImageButton[] playerCard=new ImageButton[10];
    SeekBar removeDeck;
    TextView count;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginning);

        playerCard[0]=(ImageButton)findViewById(R.id.c1);
        playerCard[1]=(ImageButton)findViewById(R.id.c2);
        playerCard[2]=(ImageButton)findViewById(R.id.c3);
        playerCard[3]=(ImageButton)findViewById(R.id.c4);
        playerCard[4]=(ImageButton)findViewById(R.id.c5);
        playerCard[5]=(ImageButton)findViewById(R.id.c6);
        playerCard[6]=(ImageButton)findViewById(R.id.c7);
        playerCard[7]=(ImageButton)findViewById(R.id.c8);
        playerCard[8]=(ImageButton)findViewById(R.id.c9);
        playerCard[9]=(ImageButton)findViewById(R.id.c10);
        removeDeck = (SeekBar)findViewById(R.id.remove);
        count=(TextView)findViewById(R.id.count);

        String name = getIntent().getStringExtra("SLOVO").toUpperCase();

        Player player1 = new Player(name, false, 50);
        Player player2 = new Player("Bot Bert", true, 50);
        Player player3 = new Player("Bot Rudolf", true, 50);
        addPlayer(player1);
        addPlayer(player2);
        addPlayer(player3);
        MakeDeck deck=new MakeDeck();
        deck.setDeck();
        this.card=deck.getDeck();
        shuffleDeck(card);

        //click();
        game();
        setOnClick();
    }

    /*



     */

    public void setOnClick(){
        playerCard[5].setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        playerCard[6].setImageResource(card[10].getId());

                    }
                }
        );
    }

    /*public void click(){
        button=(ImageButton)findViewById(R.id.imageButton);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(Arena.this, player[0].getName(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }*/


    /*Arena(Card[] card) {
        this.card = card;
        shuffleDeck(card);
    }*/

    public void addCards(Card[] card){
        this.card=card;
        shuffleDeck(this.card);
    }

    public void addPlayer(Player player) {
        this.player[playerCount] = player;
        playerCount++;
    }

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
        if(player[0].isBot()==false) {
            removeDeck.setVisibility(View.VISIBLE);
            count.setVisibility(View.VISIBLE);

            removeDeck.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            count.setText(i + " / " + seekBar.getMax());
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            removeDeck.setVisibility(View.GONE);
                            count.setVisibility(View.GONE);
                            deal();
                        }
                    }
            );
        }
        else {
            player[2].removeDeck(card);
            deal();
        }





        //System.out.println(player[0].toString());

        //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        //firstLicitation(0, player[0], player[2], player[1]);
    }

    private void deal() {
        int temp = 0;
        int pc=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                player[i].addCard(card[temp]);
                if(player[i].isBot()==false){
                    playerCard[pc].setImageResource(card[temp].getId());
                            pc++;

                }
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
                if(player[i].isBot()==false){
                    playerCard[pc].setImageResource(card[temp].getId());
                    pc++;
                }
                temp++;
            }
        }
    }

    private void firstLicitation(int level, Player deffender, Player attacker, Player expectant) {
        Player winner = deffender;
        Player defeated = attacker;
        if (deffender.licitate(level) == true) {
            if (level == 2) {
                winner = deffender;
                defeated = attacker;
                secondLicitation(level, winner, expectant, defeated);
            } else if (attacker.licitate(level + 1) == true) {
                firstLicitation(level + 1, deffender, attacker, expectant);
            } else {
                winner = deffender;
                defeated = attacker;
                secondLicitation(level, winner, expectant, defeated);
            }
        } else {
            winner = attacker;
            defeated = deffender;
            secondLicitation(level, winner, expectant, defeated);
        }
    }

    private void secondLicitation(int level, Player deffender, Player attacker, Player expectant) {
        Player winner = deffender;
        Player defeated = attacker;
        if (level == 2) {
            winner = deffender;
            defeated = attacker;
            choosingGame(level, winner, defeated, expectant);
        } else {
            if (attacker.licitate(level + 1) == true) {
                if (deffender.licitate(level + 1) == true) {
                    secondLicitation(level + 1, deffender, attacker, expectant);

                } else {
                    level++;
                    winner = attacker;
                    defeated = deffender;
                    choosingGame(level, winner, defeated, expectant);
                }
            } else {
                winner = deffender;
                defeated = attacker;
                choosingGame(level, winner, defeated, expectant);
            }
        }
    }

    private void choosingGame(int level, Player player, Player opponent1, Player opponent2) {
        talon = player.setTalon(talon);
        if (level == 2) {

        }
        int trump = player.chooseTrumfs();
        game = player.chooseGame(level);
        flek(player, opponent1, opponent2);
        System.out.println(flek);
        if (flek != 0) {
            switch (game) {
                case 0:
                    Game game = new Game(player, opponent1, opponent2, flek, trump);
                    break;
                case 1:
                    Betl betl = new Betl(player, opponent1, opponent2, flek, trump);
                    break;
                case 2:
                    Durch durch = new Durch(player, opponent1, opponent2, flek, trump);
                    break;
            }
        } else {
            opponent1.pay(player, flek, game);
            opponent2.pay(player, flek, game);
        }
        reset();
    }

    private void reset() {
        game = 0;
        flek = 0;
        ArrayList<Card> temp = player[0].getPlayerCard();
        int count = 0;
        for (int i = 0; i < temp.size(); i++) {
            card[count] = temp.get(i);
            count++;
        }
        temp.clear();
        temp = player[1].getPlayerCard();
        for (int i = 0; i < temp.size(); i++) {
            card[count] = temp.get(i);
            count++;
        }
        temp.clear();
        temp = player[2].getPlayerCard();
        for (int i = 0; i < temp.size(); i++) {
            card[count] = temp.get(i);
            count++;
        }
        temp.clear();
        for (int i = 0; i < 3; i++) {
            player[i].reset();
        }
    }

    private void flek(Player player, Player opponent1, Player opponent2) {
        if (flek < 3) {
            if (opponent1.flek() == true) {
                flek++;
                if (player.flek() == true) {
                    flek++;
                    flek(player, opponent1, opponent2);
                }
            } else if (opponent2.flek() == true) {
                flek++;
                if (player.flek() == true) {
                    flek++;
                    flek(player, opponent1, opponent2);
                }
            }
        }
    }

    public void swapPlayers() {
        Player temp = player[2];
        player[2] = player[1];
        player[1] = player[0];
        player[0] = temp;
        for (int i = 0; i < 3; i++) {
            player[i].setCardCount(0);
        }
        System.out.println("Hraci bzli protoceni");
    }

}
