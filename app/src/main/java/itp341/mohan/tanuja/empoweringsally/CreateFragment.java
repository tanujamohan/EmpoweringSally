package itp341.mohan.tanuja.empoweringsally;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import itp341.mohan.tanuja.empoweringsally.model.Internship;
import itp341.mohan.tanuja.empoweringsally.model.InternshipSingleton;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFragment extends android.support.v4.app.Fragment {

    private static final String TAG = CreateFragment.class.getSimpleName();
    public static final String ARGS_POSITION = "args_position";

    public EditText mDescription;
    public EditText mTitle;
    public Button mButton;
    public EditText mDate;
    public EditText mLocation;
    public FirebaseDatabase mDatabase;
    public DatabaseReference mReference;

    int mPosition;



    public static CreateFragment newInstance(int pos) {
        Bundle args = new Bundle();
        args.putInt(ARGS_POSITION, pos);
        CreateFragment f = new CreateFragment();
        f.setArguments(args);

        return f;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mPosition = args.getInt(ARGS_POSITION);
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
    }

    public CreateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create, container, false);

        mDescription = (EditText) v.findViewById(R.id.description_text);
        mTitle = (EditText) v.findViewById(R.id.editText);
        mButton = (Button) v.findViewById(R.id.save_button);
        mDate = (EditText) v.findViewById(R.id.date_text);
        mLocation = (EditText) v.findViewById(R.id.location_text);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndClose();
            }
        });

        return v;
    }

    public void saveAndClose() {
        Internship i = new Internship();
        i.setTitle(mTitle.getText().toString());
        i.setDescription(mDescription.getText().toString());
        i.setDate(mDate.getText().toString());
        i.setLocation(mLocation.getText().toString());
        mReference.child("Internships").child(i.getTitle()).setValue(i);


        if(mPosition != -1) {
            //store updated coffee
            InternshipSingleton.get(getActivity()).updateInternship(mPosition, i);
        } else { //we have a new record to add
            InternshipSingleton.get(getActivity()).addInternship(i);
        }
        getActivity().setResult(Activity.RESULT_OK); //our notification that the user hit saved (or delete)
        getActivity().finish();

    }

}
