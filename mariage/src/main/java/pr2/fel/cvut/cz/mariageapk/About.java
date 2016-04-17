package pr2.fel.cvut.cz.mariageapk;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Jiří on 15. 4. 2016.
 */
public class About extends Activity{

    TextView text1;
    //TextView text2;
    TextView text3;
    EditText text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setText();
    }

    private void setText(){
        text1 = (TextView)findViewById(R.id.about1);
        //text2 = (TextView)findViewById(R.id.about2);
        text3 = (TextView)findViewById(R.id.about3);
        text4 = (EditText)findViewById(R.id.about4);


        text1.setText("About");
        text4.setText("This application was created for PR2 like a semestral project. For more informations contact me on horynajirka@seznam.cz");
        text3.setText("Created by J. Horyna, 2016");

    }

}
