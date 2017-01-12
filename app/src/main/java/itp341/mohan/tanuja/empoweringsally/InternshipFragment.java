package itp341.mohan.tanuja.empoweringsally;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import itp341.mohan.tanuja.empoweringsally.model.Internship;
import itp341.mohan.tanuja.empoweringsally.model.InternshipSingleton;


/**
 * A simple {@link Fragment} subclass.
 */
public class InternshipFragment extends Fragment {
    private static final String TAG = "xyz";

    FloatingActionButton mButtonAdd;
    ListView mListView;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    private ArrayList<Internship> mInternships;
    private InternshipAdapter mInternshipAdapter;

    public InternshipFragment() {}

    public static InternshipFragment newInstance() {
        Bundle args = new Bundle();
        InternshipFragment f = new InternshipFragment();
        f.setArguments(args);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_internship, container, false);

        mButtonAdd = (FloatingActionButton) v.findViewById(R.id.fab);
        mButtonAdd.setSize(FloatingActionButton.SIZE_AUTO);
        mListView = (ListView) v.findViewById(R.id.listView);

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();


        mInternships = InternshipSingleton.get(getActivity()).getInternships();
        mInternshipAdapter = new InternshipAdapter(mInternships);
        mListView.setAdapter(mInternshipAdapter);

        mReference.child("Internships").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "right before get children");
                if(dataSnapshot.getChildrenCount() != mInternships.size()) {
                    for (DataSnapshot c : dataSnapshot.getChildren()) {
                        Log.d(TAG, "entered the listener");
                        Internship i = c.getValue(Internship.class);
                        Log.d(TAG, "Event title: " + i.getTitle());
                        InternshipSingleton.get(getActivity()).addInternship(i);
                        refresh();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CreateActivity.class);
                startActivityForResult(i, 0);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d(TAG, "mListView: onListItemClick");
                Log.d(TAG, "Clicking " + position);

                //create the intent that will launch the detail view
                //need to pass in the position (index) that was clicked
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra(DetailActivity.EXTRA_POSITION, position);
                startActivityForResult(i, 1);

            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode: " + requestCode);

        //returned from DetailFragment and user hit save or delete
        if((requestCode == 0 || requestCode == 1) && resultCode == Activity.RESULT_OK) {
            refresh();
        }
    }

    public void refresh() {
        mInternshipAdapter.notifyDataSetChanged();
    }

    public class InternshipAdapter extends ArrayAdapter<Internship> {
        ArrayList<Internship> mInternships;

        public InternshipAdapter(ArrayList<Internship> internships) {
            super(getActivity(), 0, internships);
            mInternships = internships;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.layout_list, null);
            }
            //Load data from the model (AKA the arrayList) into a single row
            //access current row of model data
            Internship i = mInternships.get(position);

            TextView textTitle = (TextView) convertView.findViewById(R.id.event_title);

            //loading the data from model to row
            textTitle.setText(i.getTitle());

            return convertView;
        }
    }

}
