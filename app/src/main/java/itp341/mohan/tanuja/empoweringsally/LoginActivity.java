package itp341.mohan.tanuja.empoweringsally;

import android.app.ActionBar;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    public FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    public Button loginButton;
    public Button accountButton;
    public EditText usernameEdit;
    public EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();

        loginButton = (Button) findViewById(R.id.button);
        accountButton = (Button) findViewById(R.id.button2);

        usernameEdit = (EditText) findViewById(R.id.usernameText);
        passwordEdit = (EditText) findViewById(R.id.passwordText);

        addListeners();
    }

    public void addListeners() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameEdit.getText().toString();
                final String password = passwordEdit.getText().toString();

                mReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean exists = false;
                        for (DataSnapshot c : dataSnapshot.getChildren()) {
                            if (c.getKey().equals(username)) {
                                if (c.child("password").getValue().equals(password)) {
                                    exists = true;
                                    //Toast.makeText(LoginActivity.this, "Found user in database", Toast.LENGTH_SHORT).show();
                                    nextActivity(username);
                                }
                            }
                        }
                        if (!exists) {
                            Toast.makeText(getApplicationContext(), "This account does not exist", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameEdit.getText().toString();
                final String password = passwordEdit.getText().toString();

                mReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean exists = false;
                        for(DataSnapshot c : dataSnapshot.getChildren()) {
                            if(c.getKey().equals(username)) {
                                exists = true;
                                Toast.makeText(getApplicationContext(), "This username already exists", Toast.LENGTH_SHORT).show();
                            }
                        }

                        if(username.equals("") || password.equals("")) {
                            exists = true;
                            Toast.makeText(getApplicationContext(), "Invalid account information", Toast.LENGTH_SHORT).show();
                        }
                        if(!exists) {
                            Toast.makeText(getApplicationContext(), "Creating new account in database", Toast.LENGTH_SHORT).show();
                            User u = new User(username, password);
                            mReference.child("users").child(username).setValue(u);
                            nextActivity(username);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void nextActivity(String username) {
//        Toast.makeText(getApplicationContext(), "Next activity", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("username", username);
        startActivity(i);
    }


}
