/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pr2.fel.cvut.cz.mariageapk;

import java.util.ArrayList;

/**
 *
 * @author Jiří
 */
class Durch {

    int game = 2;
    int flek;
    int trump;
    Player player, opponent1, opponent2;
    private ArrayList<Card> littleDeck = new ArrayList<>();

    Durch(Player player, Player opponent1, Player opponent2, int flek, int trump) {
        this.player = player;
        this.opponent1 = opponent1;
        this.opponent2 = opponent2;
        this.flek = flek;
        this.trump = trump;
    }

    public void play() {
        for (int i = 0; i < 10; i++) {
            turn();
            if (opponent1.deckSize() != 0) {
                break;
            } else if (opponent2.deckSize() != 0) {
                break;
            }
        }
        pay();
    }

    public void turn() {
        littleDeck.add(player.setCard());
        int localTrump = littleDeck.get(0).getColor();
        littleDeck.add(opponent1.setCard(littleDeck.get(0), trump));
        littleDeck.add(opponent2.setCard(littleDeck.get(0), trump));
        comparison(localTrump);
        littleDeck.clear();
    }

    public void comparison(int localTrump) {
        int playerPoints = littleDeck.get(0).cardPoints(localTrump, trump, game);
        int opponent1Points = littleDeck.get(1).cardPoints(localTrump, trump, game);
        int opponent2Points = littleDeck.get(2).cardPoints(localTrump, trump, game);
        Player turnWinner;
        if (playerPoints > opponent1Points && playerPoints > opponent2Points) {
            turnWinner = player;
        } else if (opponent1Points > playerPoints && opponent1Points > opponent2Points) {
            turnWinner = opponent1;
        } else {
            turnWinner = opponent2;
        }
        for (int i = 0; i < 3; i++) {
            turnWinner.addToDeck(littleDeck.get(i));
        }
    }

    public void pay() {
        if (opponent1.deckSize() != 0 || opponent2.deckSize() != 0) {
            player.pay(opponent1, flek, game);
            player.pay(opponent2, flek, game);
        } else {
            opponent1.pay(player, flek, game);
            opponent2.pay(player, flek, game);
        }
    }
}
