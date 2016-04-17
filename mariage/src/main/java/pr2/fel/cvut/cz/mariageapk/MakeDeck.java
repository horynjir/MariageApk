package pr2.fel.cvut.cz.mariageapk;

/**
 * Created by Jiří on 16. 4. 2016.
 */
public class MakeDeck {

    Card[] card = new Card[32];
    //ArrayList<Card> card=new ArrayList<Card>();
    public void setDeck() {
        int temp = 0;
        for (int color = 0; color < 4; color++) {
            for (int value = 7; value < 15; value++) {
                card[temp] = new Card(value, color);
                temp++;
            }
        }
        card[0].setId(R.mipmap.c01);
        card[1].setId(R.mipmap.c02);
        card[2].setId(R.mipmap.c03);
        card[3].setId(R.mipmap.c04);
        card[4].setId(R.mipmap.c05);
        card[5].setId(R.mipmap.c06);
        card[6].setId(R.mipmap.c07);
        card[7].setId(R.mipmap.c08);
        card[8].setId(R.mipmap.c09);
        card[9].setId(R.mipmap.c10);
        card[10].setId(R.mipmap.c11);
        card[11].setId(R.mipmap.c12);
        card[12].setId(R.mipmap.c13);
        card[13].setId(R.mipmap.c14);
        card[14].setId(R.mipmap.c15);
        card[15].setId(R.mipmap.c16);
        card[16].setId(R.mipmap.c17);
        card[17].setId(R.mipmap.c18);
        card[18].setId(R.mipmap.c19);
        card[19].setId(R.mipmap.c20);
        card[20].setId(R.mipmap.c21);
        card[21].setId(R.mipmap.c22);
        card[22].setId(R.mipmap.c23);
        card[23].setId(R.mipmap.c24);
        card[24].setId(R.mipmap.c25);
        card[25].setId(R.mipmap.c26);
        card[26].setId(R.mipmap.c27);
        card[27].setId(R.mipmap.c28);
        card[28].setId(R.mipmap.c29);
        card[29].setId(R.mipmap.c30);
        card[30].setId(R.mipmap.c31);
        card[31].setId(R.mipmap.c32);

    }

    public Card[] getDeck(){
        return card;
    }

}
