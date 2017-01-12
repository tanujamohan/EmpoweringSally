package itp341.mohan.tanuja.empoweringsally;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CreateActivity extends FragmentActivity {

    public static final String TAG = CreateActivity.class.getSimpleName();

    //TODO how will we pass data from MainListFragment?
    public static final String EXTRA_POSITION = "extra_position";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        Intent i = getIntent();
        int position = i.getIntExtra(EXTRA_POSITION, -1);

        //Create fragment
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.content_frame);

        //TODO modify fragment creation
        if (f == null ) {
            f = CreateFragment.newInstance(position);
            // f = new DetailFragment();
        }
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, f);
        fragmentTransaction.commit();
    }

}
