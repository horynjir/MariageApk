/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pr2.fel.cvut.cz.mariageapk;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Jiří
 */
class Player {

    private final int GAME_PRICE = 2;
    private final int BETL_PRICE = 4;
    private final int DURCH_PRICE = 6;
    String name;
    private ArrayList<Card> deck = new ArrayList<>();
    private ArrayList<Card> playerCard = new ArrayList<>();
    int cardCount = 0;
    boolean isBot;
    int coins;
    private Card[] talon = new Card[2];

    Player(String name, boolean isBot, int coins) {
        this.name = name;
        this.isBot = isBot;
        this.coins = coins;
    }

    public ArrayList<Card> getPlayerCard() {
        return playerCard;
    }

    public int getCoins() {
        return coins;
    }

    public int deckSize() {
        return deck.size();
    }

    public void addToDeck(Card card) {
        deck.add(card);
    }

    public Card setCard() {
        return playerCard.get(0);
    }

    public Card setCard(Card mainCard, int trump) {
        Card temp=playerCard.get(0);
        playerCard.remove(0);
        return temp;
    }

    public void addCard(Card card) {
        playerCard.add(card);
        /*this.playerCard[cardCount] = card;*/
        cardCount++;
    }
    public String getName(){
        return name;
    }

    public int scoreOfGame() {
        int score = 0;
        for (int i = 0; i < deckSize(); i++) {
            if (deck.get(i).getValue() == 10) {
                score += 10;
            } else if (deck.get(i).getValue() == 14) {
                score += 10;
            }
        }
        return score;
    }

    public boolean isBot() {
        return isBot;
    }

    public void pay(Player player, int flek, int game) {
        int main = 0;
        switch (game) {
            case 0:
                main = GAME_PRICE;
                break;
            case 1:
                main = BETL_PRICE;
                break;
            case 2:
                main = DURCH_PRICE;
                break;
        }
        int num = (int) (main * (Math.pow(2, flek)));
        if (num > coins) {
            num = coins;
        }
        decreaseCoins(num);
        player.increaseCoins(num);
    }

    public void increaseCoins(int num) {
        this.coins = coins + num;
    }

    public void decreaseCoins(int num) {
        this.coins = coins - num;
    }

    public Card[] setTalon(Card[] talon) {
        this.talon = talon;
        if (!isBot) {
            System.out.println("Swap cards ");
            printCards();
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXX");
            for (int i = 0; i < 2; i++) {
                System.out.println(talon[i].toString());
                System.out.println("0-10 what you want swap (10 nothing) for first card from talon");
                Scanner scan = new Scanner(System.in);
                int num = scan.nextInt();
                if (num < 10) {
                    /*Card temp = playerCard[num];
                     playerCard[num] = talon[i];*/
                    Card temp = playerCard.get(num);
                    playerCard.set(num, talon[i]);
                    talon[i] = temp;
                }
            }
            printCards();

        } else {
            for (int i = 0; i < 2; i++) {
                int num = (int) (Math.random() * 10);
                /*Card temp=playerCard[num];
                 playerCard[num]=talon[i];*/
                Card temp = playerCard.get(num);
                playerCard.set(num, talon[i]);
                talon[i] = temp;
            }
        }
        return talon;
    }

    public void printCards() {
        String str = "";
        for (int i = 0; i < cardCount; i++) {
            str += playerCard.get(i).toString();
        }
        System.out.println(str);
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public int chooseTrumfs() {
        int trumfs = 0;
        if (!isBot) {
            System.out.println("Choose trumfs (0-3): 0=srdce, 1=kule, 2=zeleny, 3=zaludy");
            Scanner scan = new Scanner(System.in);
            trumfs = scan.nextInt();
        } else {
            trumfs = (int) (Math.random() * 4);
        }
        return trumfs;
    }

    public boolean flek() {
        boolean flek = false;
        if (!isBot) {
            System.out.println("Do you want flek? 0 no 1 yes");
            Scanner scan = new Scanner(System.in);
            int fl = scan.nextInt();
            if (fl == 0) {
                flek = false;
            } else {
                flek = true;
            }
        } else {
            int fl = (int) (Math.random() * 3);
            if (fl < 2) {
                flek = true;
            } else {
                flek = false;
            }
        }
        return flek;
    }

    public int chooseGame(int level) {
        int game = 0;
        if (level == 2) {
            game = 2;
        } else {
            if (!isBot) {
                System.out.println("Choose game " + level + " - 2. 0 for Game, 1 for Betl, 2 for Durch");
                Scanner scan = new Scanner(System.in);
                game = scan.nextInt();
            } else {
                game = (int) ((Math.random() * (3 - level)) + level);
            }
        }
        return game;
    }

    public boolean licitate(int level) {
        if (!isBot) {

            String str = "";
            if (level == 0) {
                str += "Hra";
            } else if (level == 1) {
                str += "Betl";
            } else {
                str += "Durch";
            }
            System.out.println("Wanna play " + str);
            System.out.println("0 for pass, 1 for play");
            Scanner scan = new Scanner(System.in);
            int play = scan.nextInt();
            if (play == 0) {
                return false;
            } else {
                return true;
            }
        } else {
            int maxProbability = 0;
            switch (level) {
                case 0:
                    maxProbability = 100;
                    break;
                case 1:
                    maxProbability = 85;
                    break;
                case 2:
                    maxProbability = 70;
                    break;
            }
            double probability = Math.random() * maxProbability;
            if (probability > 50) {
                return true;
            } else {
                return false;
            }
        }
    }

    public void removeDeck(Card[] card) {
        int max = card.length - 1;
        int half = 0;
        if (isBot == false) {
            while (half < 5 || half > 27) {
                Scanner scan = new Scanner(System.in);
                half = scan.nextInt();
            }
        } else {
            half = (int) (Math.random() * 22 + 5);
        }
        Card[] firstHalf = new Card[half];
        Card[] secondHalf = new Card[32 - half];
        for (int i = 0; i < half; i++) {
            firstHalf[i] = card[i];
        }
        int temp = 0;
        for (int i = half; i < card.length; i++) {
            secondHalf[temp] = card[i];
            temp++;
        }
        for (int i = 0; i < secondHalf.length; i++) {
            card[i] = secondHalf[i];
        }
        temp = 0;
        for (int i = secondHalf.length; i < card.length; i++) {
            card[i] = firstHalf[temp];
            temp++;
        }

        System.out.println("Karty byly sejmuty");
        half = 0;
    }

    public String toString() {
        String str = "";
        str += name + " \n";
        /*for (int i = 0; i < cardCount; i++) {
         str += playerCard[i].toString();
         }*/
        return str;
    }

    public void reset() {
        cardCount = 0;
        playerCard.clear();
        deck.clear();
        //talon??
    }
}
