/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pr2.fel.cvut.cz.mariageapk;

import java.util.logging.Logger;

/**
 *
 * @author Jiří
 */
public class Player{
    private static final int GAME_PRICE = 1;
    private static final int BETL_PRICE = 2;
    private static final int DURCH_PRICE = 3;
    private int id;
    private String name;
    private Card[] deck = new Card[30];
    private Card[] playerCard = new Card[10];
    private int cardCount = 0;
    private int deckSize=0;
    boolean isBot;
    private int coins;
    private int trumpCount;
    private int localTrumpCount;
    private int trump;
    private int localTrump;
    private int yesOrNO=0;
    private Card[] talon = new Card[2];
    private static final Logger LOG = Logger.getLogger(Player.class.getName());

    /**
     * Constructor for initilizate new player
     * @param name is String which represents name of player
     * @param isBot is a boolean which means if player is real user or proggramed player
     * @param coins is an integer represents virtual cash
     * @param id is number of player, each player has his own original id
     */
    Player(String name, boolean isBot, int coins, int id) {
        LOG.info("New player was created");
        this.id=id;
        this.name = name;
        this.isBot = isBot;
        this.coins = coins;
    }

    /**
     * this method is called after each round and sets each important variable to default value
     * @param flek is an integer which means if the game was played. If yes, cords color of non bot player is decreased by int 4, because in Arena.java it was increased for better counting of trump and local trumps
     */
    public void restart(int flek){
        LOG.info("Players variable was set to default value");
        deckSize=0;
        cardCount=0;
        if(!isBot && flek!=0) {
            for (int i = 0; i < 10; i++) {
                playerCard[i].setColor(playerCard[i].getColor() - 4);
            }
        }
    }

    /**
     * this method sets count of coins if game is loaded
     * @param c is count of coins
     */
    public void setCoins(int c){
        this.coins=c;
    }

    /**
     *
     * @return deck, which player earned in round for line up a new playing deck (because each round except first is deck just removed, not shuffled
     */
    public Card[] getDeck(){
        return deck;
    }

    /**
     *
     * @return id of player
     */
    public int getId(){
        return id;
    }

    /**
     * this method counts number of trumps and local trumps, which is needed for finding out if a turn was right
     * @param localTrump is int which represent color of actual local trump
     */
    public void trumpsCount(int localTrump){
        trumpCount=0;
        localTrumpCount=0;
        this.localTrump=localTrump;
        for(int i=0; i<cardCount; i++){
            if(playerCard[i].getColor()==trump) {
                trumpCount++;
            }
            if(playerCard[i].getColor()==this.localTrump) {
                localTrumpCount++;
            }
        }
    }

    /**
     * Sets golbal trump (higher priority than local trump)
     */
    public void setTrumps(int trump){
        this.trump=trump;
    }

    /**
     * @return integer which represents count of cards whose color is tha same like trump color
     */
    public int getTrumpCount(){
        return trumpCount;
    }

    /**
     *
     * @return integer which represents count of cards whose color is tha same like local trump color
     */
    public int getLocalTrumpCount(){
        return localTrumpCount;
    }

    /**
     * sets an integer 0 or 1 (true or false)
     */
    public void setYesOrNo(int i){
        yesOrNO=i;
    }

    /**
     *
     * @return 0 or 1 (true or false) which represent players decision
     */
    public int getYesOrNo(){
        return yesOrNO;
    }

    /**
     *
     * @param num is an index of card which should be returned
     * @return card on index num from players cards
     */
    public Card getPlayerCard(int num){
        return playerCard[num];
    }

    /**
     * this method replace one of player cards another card
     * @param num is index of card for replace
     * @param card is card which will replace current player card
     */
    public void setPlayerCard(int num, Card card){
        playerCard[num]=card;
    }

    /**
     *
     * @return count of coins, which player have
     */
    public int getCoins() {
        return coins;
    }

    /**
     *
     * @return number which represents count of cards in deck
     */
    public int getDeckSize(){
        return deckSize;
    }

    /**
     *
     * @param card is Card which will be added to deck array. In deck are triples of cards which player wins in some turn
     */
    public void addToDeck(Card card) {
        deck[deckSize]=card;
        deckSize++;
    }

    /**
     * This method is called for bot player, who begins turn
     * @return card, which will be placed as first in little deck
     */
    public Card setCard() {
        int a = (int) (Math.random() * cardCount);
        Card temp=playerCard[a];
        playerCard[a]=playerCard[cardCount-1];
        cardCount--;
        return temp;
    }

    /**
     * This method is called for bot player, for adding card to little deck
     * If choosen card is first card which is not in contrast with rules
     * @param localTrump is integer which represent color of local trump (it is color of first card in little deck)
     * @return card which will be added to little deck
     */
    public Card setCard(int localTrump) {
        LOG.info("Player should get some card to little deck");
        this.localTrump=localTrump;
        trumpsCount(localTrump);
        int a = (int) (Math.random() * cardCount);

        if(trumpCount>0){
            for(int i=0; i<cardCount; i++){
                if(playerCard[i].getColor()==trump) {
                    a=i;
                    break;
                }
            }
        }
        if(localTrumpCount>0){
            for(int i=0; i<cardCount; i++){
                if(playerCard[i].getColor()==localTrump) {
                    a=i;
                    break;
                }
            }
        }
        Card temp = playerCard[a];
        playerCard[a]=playerCard[cardCount-1];
        cardCount--;
        return temp;
    }

    /**
     * this method add card to an array where are cards which player plays with
     */
    public void addCard(Card card) {
        playerCard[cardCount]=card;
        cardCount++;
    }

    /**
     *
     * @return String represents player name
     */
    public String getName(){
        return name;
    }

    /**
     *
     * @param num is index of card in array talon
     * @return card from talon from choosen index
     */
    public Card getTalonCard(int num){
        return talon[num];
    }

    /**
     * @param num is index in array where card will be placed
     * @param card is Card which will be added to players talon if he won licitation
     */
    public void setTalonCard(int num, Card card){
        talon[num] = card;
    }

    /**
     * If was played game 0 (Game), it has to be compared score of each player which depends on cards with walue 10 a 14 (ace)
     * @return integer which represents score
     */
    public int scoreOfGame() {
        int score = 0;
        for (int i = 0; i < deckSize; i++) {
            if (deck[i].getValue() == 10) {
                score += 10;
            } else if (deck[i].getValue() == 14) {
                score += 10;
            }
        }
        return score;
    }

    /**
     *
     * @return boolean which says if player is user or bot
     */
    public boolean isBot() {
        return isBot;
    }

    /**
     * method which is called after each round on player(s) who lose this round and he will than play to player(s) who win
     * Value of int depends on flek and type of game
     * @param player is opponent player who current player should pay
     * @param flek is value of game
     * @param game is type of game
     * @return integer which has same value like that one which current player payed to winner
     */
    public int pay(Player player, int flek, int game) {
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
            default:
                main = GAME_PRICE;
        }
        int num = (int) (main * (Math.pow(2, flek)));
        if (num > coins) {
            num = coins;
        }
        decreaseCoins(num);
        player.increaseCoins(num);
        return num;
    }

    /**
     *
     * @param num is integer which represents count of coins which should be added to current coins
     */
    public void increaseCoins(int num) {
        this.coins = coins + num;
    }

    /**
     *
     * @param num is integer which represents count of coins which should be pay to another player
     */
    private void decreaseCoins(int num) {
        this.coins = coins - num;
    }

    /**
     * If player wins licitation, this method will be called and get him a talon (2 cards not used cards yet)
     * @param talon is array of 2 Cards
     * @return if player is bot it returns cards which he swap with talon
     */
    public Card[] setTalon(Card[] talon) {
        LOG.info("Player gets talon");
        this.talon = talon;
        if (isBot) {
            for (int i = 0; i < 2; i++) {
                int num = (int) (Math.random() * 10);
                Card temp = playerCard[num];
                playerCard[num]= talon[i];
                talon[i] = temp;
            }
        }
        return talon;
    }

    /**
     * @param cardCount is a number which shoud be setted as count of cards
     */
    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    /**
     * This method is called if player is bot
     * @return number which represents color of trump cards
     */
    public int chooseTrumfs() {
        int trumfs = 0;
        trumfs = (int) (Math.random() * 4);
        return trumfs;
    }

    /**
     * This method is called if player is bot. fl<2 means 66 percent probability that player want to play a fleck
     * @return 0 or 1. It means if player wanted fleck or no
     */
    public int flek() {
        int flek = 0;
            int fl = (int) (Math.random() * 3);
            if (fl < 2) {
                flek = 1;
            } else {
                flek = 0;
            }

        return flek;
    }

    /**
     *
     * @param level represents level of current licitation (higher games are more dificult, so they have less probability
     * @return 0 or 1 which means if player want plays higher game
     */
    public int licitate(int level) {
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
                default:
                    maxProbability = 50;
            }
            double probability = Math.random() * maxProbability;
            if (probability > 50) {
                return 1;
            } else {
                return 0;
            }
    }

    /**
     * This method removes card deck. It separates deck in two smaller decks and then it fix back in different order
     * @param card is deck of playing cards
     * @param h is number of cards which should be removed
     * @return removed card as an array
     */
    public Card[] removeDeck(Card[] card, int h) {
        LOG.info("Player is removing deck");
        int half = h;
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
        half = 0;
        return card;
    }
}
