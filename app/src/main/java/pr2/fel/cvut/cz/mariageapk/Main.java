package pr2.fel.cvut.cz.mariageapk;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Card[] card=new Card[32];
        int temp=0;
        for(int color=0; color<4; color++){
            for(int value=7; value<15; value++){
                card[temp]=new Card(value, color);
                temp++;
            }
        }
        Player player1 = new Player("Jiricek");
        Player player2 = new Player("Lukasek");
        Player player3 = new Player("Tomasek");
        Arena arena=new Arena(card);
        arena.addPlayer(player1);
        arena.addPlayer(player2);
        arena.addPlayer(player3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
