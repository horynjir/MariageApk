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
    private final int color;
    private int id;

    Card(int value, int color) {
        this.color = color;
        this.value = value;

    }

    public int getValue() {
        return this.value;
    }

    public int getColor() {
        return this.color;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

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
        return bonus * temp;
    }

    public String toString() {
        String str = "";
        String clr = "";
        switch (this.color) {
            case 0:
                clr = "srdce";
                break;
            case 1:
                clr = "kule";
                break;
            case 2:
                clr = "zeleny";
                break;
            case 3:
                clr = "zlaudy";
                break;
        }
        String num = "";
        if (this.value < 11) {
            num += value;
        } else {
            switch (value) {
                case 11:
                    num += "spodek";
                    break;
                case 12:
                    num += "svrsek";
                    break;
                case 13:
                    num += "kral";
                    break;
                case 14:
                    num += "eso";
                    break;
            }
        }
        str += "Barva: " + clr + " hodnota: " + num + "\n";
        return str;
    }
}
