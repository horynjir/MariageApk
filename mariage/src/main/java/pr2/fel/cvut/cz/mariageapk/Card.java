/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pr2.fel.cvut.cz.mariageapk;

/**
 *
 * @author Jiří
 */
class Card {

    private final int value;
    private int color;
    private int id;

    Card(int value, int color) {
        this.color = color;
        this.value = value;

    }

    /**
     * set color of card
     * @param c is color (0-3 standart)
     */
    public void setColor(int c){
        this.color=c;
    }

    /**
     *
     * @return value of card (7-14)
     */
    public int getValue() {
        return this.value;
    }

    /**
     *
     * @return color of card (0-3]
     */
    public int getColor() {
        return this.color;
    }

    /**
     * This method sets id of card, which means id of png in mipmap
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     *
     * @return id of card. It is needed for GUI
     */
    public int getId(){
        return id;
    }

    /**
     * This method is important for comparison in game, it depends on trumps and type of game
     * @param localTrump is color of first card in little deck
     * @param trump is global trump for game, it can be localTrump
     * @param game is "value" of each round
     * @return integer which means "value" of card
     */
    public int cardPoints(int localTrump, int trump, int game) {
        int bonus = 0;
        if (color == trump) {
            bonus = 3;
        } else if (color == localTrump) {
            bonus = 1;
        } else {
            bonus = 0;
        }
        int temp = value;
        if (game == 0) {
            if (value == 10) {
                temp = 13;
            } else if (value == 13) {
                temp = 12;
            } else if (value == 12) {
                temp = 11;
            } else if (value == 11) {
                temp = 10;
            }
        }
        return bonus*temp;
    }
}
