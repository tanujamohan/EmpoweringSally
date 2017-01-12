package itp341.mohan.tanuja.empoweringsally;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import itp341.mohan.tanuja.empoweringsally.model.Internship;
import itp341.mohan.tanuja.empoweringsally.model.InternshipSingleton;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    private static final String TAG = DetailFragment.class.getSimpleName();

    //Bundle key
    public static final String ARGS_POSITION = "args_position";

    int mPosition;
    TextView mTitle;
    TextView mDate;
    TextView mLocation;
    TextView mDescription;
    Internship i;

    public DetailFragment() {
        // Required empty public constructor
    }

    //TODO store newInstance input into fragment argument
    public static DetailFragment newInstance(int pos) {
        Bundle args = new Bundle();
        args.putInt(ARGS_POSITION, pos);

        DetailFragment f = new DetailFragment();
        f.setArguments(args);

        return f;
    }

    //TODO read bundle argument
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mPosition = args.getInt(ARGS_POSITION);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        mTitle = (TextView) v.findViewById(R.id.movieTitle);
        mDescription = (TextView) v.findViewById(R.id.descriptionText);
        mDate = (TextView) v.findViewById(R.id.dateText);
        mLocation = (TextView) v.findViewById(R.id.locationText);





        //check for valid position -- meaning existing position
        if(mPosition != -1) {
            i = InternshipSingleton.get(getActivity()).getInternship(mPosition);
            loadData(i);
        }

        return v;
    }

    //TODO load data from existing coffee shop object
    private void loadData(Internship i) {
        mTitle.setText(i.getTitle());
        mDescription.setText(i.getDescription());
        mDate.setText(i.getDate());
        mLocation.setText(i.getLocation());

    }

}
