package pr2.fel.cvut.cz.mariageapk;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Jiří on 16. 4. 2016.
 */
public class A extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Arena arena = new Arena();

    }

}
