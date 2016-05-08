package pr2.fel.cvut.cz.mariageapk;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main extends ActionBarActivity {
    private String name;
    private int game;
    private EditText newName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.mipmap.icon);

    }

    /**
     * This method start when button New game is clicked. It sets name of user and starts new activity by intent
     */
    public void setNewGame(View view){
        game=0;
        newName=(EditText)findViewById(R.id.name);
        name = newName.getText().toString();
        Intent newGame= new Intent(this, Arena.class);
        newGame.putExtra("SLOVO", name);
        newGame.putExtra("GAME", game);
        startActivity(newGame);
    }

    public void continueGame(View view){
        game=1;
        newName=(EditText)findViewById(R.id.name);
        name = newName.getText().toString();
        Intent continueGame= new Intent(this, Arena.class);
        continueGame.putExtra("SLOVO", "default");
        continueGame.putExtra("GAME", game);
        startActivity(continueGame);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * If "About" in optionsItem is selected, new activity is started, where are informations abou this project
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            Intent about= new Intent(this, About.class);
            startActivity(about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
