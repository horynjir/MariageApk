package pr2.fel.cvut.cz.mariageapk;

/**
 * Created by Jiří on 6. 3. 2016.
 */
public class Card {
    private int value;
    private int color;

    Card(int value, int color){
        this.color=color;
        this.value=value;

    }

    public int getValue(){
        return this.value;
    }

    public int getColor(){
        return this.color;
    }

}
