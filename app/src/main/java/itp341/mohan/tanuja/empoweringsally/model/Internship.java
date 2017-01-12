package itp341.mohan.tanuja.empoweringsally.model;

/**
 * Created by tanujamohan on 12/10/16.
 */
public class Internship {
    private String mTitle;
    private String mDescription;
    private String mLocation;
    private String mDate;

    public Internship() { super(); }

    public Internship(String title, String date, String location, String description) {
        super();
        mTitle = title;
        mDate = date;
        mLocation = location;
        mDescription = description;
    }

    public String getTitle() { return mTitle; }
    public String getDescription() { return mDescription; }
    public String getLocation() { return mLocation; }
    public String getDate() { return mDate; }
    public void setTitle(String title) { mTitle = title; }
    public void setDate(String date) { mDate = date; }
    public void setLocation(String location) { mLocation = location; }
    public void setDescription(String description) { mDescription = description; }
}
