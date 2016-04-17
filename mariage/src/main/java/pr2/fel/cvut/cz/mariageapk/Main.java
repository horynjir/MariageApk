package pr2.fel.cvut.cz.mariageapk;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Main extends ActionBarActivity {
    private String name;
    private EditText newName;
    private Button buttonNew;
    private Button buttonContinue;
    private ImageView cardImage;
    private int baf=R.mipmap.c20;


    public static final String MY_TAG = "the_custom_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.mipmap.icon);

    }


    public void setNewGame(View view){
        newName=(EditText)findViewById(R.id.name);
        name = newName.getText().toString();
        Intent newGame= new Intent(this, Arena.class);
        newGame.putExtra("SLOVO", name);
        startActivity(newGame);
    }


    /*public void newGame(){
        newName=(EditText)findViewById(R.id.name);
        name=newName.getText().toString();
        cardImage=(ImageView)findViewById(R.id.imageView);
        buttonNew=(Button)findViewById(R.id.newGame);

        buttonNew.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cardImage.setImageResource(baf);*/
                        /*Toast.makeText(
                                Main.this, "abcd---"+baf+"-"+baf2+"-"+baf3+"-"+baf4+"-"+baf5+"-"+baf6+"-"+baf7+"-"+baf8+"-"+baf9+"-"+baf10+"-"+baf11+"-"+baf12+"-"+baf13+"-"+baf14+"-"+baf15+"-"+baf16+"-"+baf17+"-"+baf18+"-"+baf19+"-"+baf20+"-"+baf21+"-"+baf22+"-"+baf23+"-"+baf24+"-"+baf25+"-"+baf26+"-"+baf27+"-"+baf28+"-"+baf29+"-"+baf30+"-"+baf31+"-"+baf32, Toast.LENGTH_SHORT
                        ).show();*/
                        /*if(name==null || name=="Name"){
                            Toast.makeText(
                              Main.this, "Set please your name", Toast.LENGTH_LONG
                            ).show();
                        }
                        else{
                            setContentView(R.layout.hokus);
                        }*/

                /*    }
                }
        );
    }*/


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

        if (id == R.id.action_about) {
            Intent about= new Intent(this, About.class);
            startActivity(about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
