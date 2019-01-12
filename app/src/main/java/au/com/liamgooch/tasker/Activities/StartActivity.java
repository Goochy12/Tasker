package au.com.liamgooch.tasker.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import au.com.liamgooch.tasker.R;

public class StartActivity extends AppCompatActivity {

    public static final String TAG = "TaskerTAG";
    public static final String ACCOUNT_TYPE = "account_type";
    public static final String ACCOUNT_UID = "account_uid";
    public static final String SHARED_PREF = "account_shared_pref";
    public static final String DATABASE_VERSION = "database_shared_pref_version";

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private DatabaseReference userReference;
    private String uid;
    private String accountType;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //set splash theme
        setTheme(R.style.SplashStyle);
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        //get user database
        database = FirebaseDatabase.getInstance();
        try{
            userReference = database.getReference("accounts");
        }catch (NullPointerException e){

        }

        //get the user and check if logged in
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            uid = currentUser.getUid();
            accountType = getAccountType();

            //open the dashboard
            openDashboard();
        }else{
            openLogin();
        }

        finish();
    }

    private void openDashboard() {
        Intent dashBoard = new Intent(this,Dashboard.class);
//        sharedPreferences.edit().putBoolean("logged_in",true).apply();
//        sharedPreferences.edit().putString("account_type",accountType).apply();
//        sharedPreferences.edit().putString("uid",uid).apply();

        dashBoard.putExtra(ACCOUNT_TYPE,accountType);
        Log.i(TAG, "openDashboard: " + accountType);
        dashBoard.putExtra(ACCOUNT_UID,uid);
        startActivity(dashBoard);
        finish();

    }

    private void openLogin(){
        Intent loginIntent = new Intent(this,Login.class);
        startActivity(loginIntent);
    }


    private String getAccountType(){
        Log.i(TAG, "getAccountType: " + uid);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("accounts").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "onDataChange: ");
                accountType = dataSnapshot.child("type").getValue().toString();
                Log.i(TAG, "onDataChange: " + accountType);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return accountType;
    }

}
