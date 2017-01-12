package itp341.mohan.tanuja.empoweringsally.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by tanujamohan on 12/10/16.
 */
public class InternshipSingleton {

    private ArrayList<Internship> mInternships;
    public Context mContext;
    public static InternshipSingleton internshipSingleton;

    public InternshipSingleton(Context c) {
        mContext = c;
        mInternships = new ArrayList<>();
    }

    public static InternshipSingleton get(Context c) {
        if(internshipSingleton == null) {
            internshipSingleton = new InternshipSingleton(c);
        }
        return internshipSingleton;
    }

    public Internship getInternship(int index) { return mInternships.get(index); }
    public void addInternship(Internship i) { mInternships.add(i); }
    public ArrayList<Internship> getInternships() { return mInternships; }

    public void updateInternship(int index, Internship m) {
        if(index >= 0 && index < mInternships.size()) {
            mInternships.set(index, m);
        }
    }

}
