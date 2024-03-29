package au.com.liamgooch.tasker.Activities;

import android.content.Context;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import au.com.liamgooch.tasker.R;
import au.com.liamgooch.tasker.data.String_Values;

import static au.com.liamgooch.tasker.data.String_Values.ACCOUNTS;
import static au.com.liamgooch.tasker.data.String_Values.TYPE;

public class StartActivity extends AppCompatActivity {

    //firebase variables
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private DatabaseReference userReference;
    private String uid;
    private String accountType;

    private Context context;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //set splash theme
        setTheme(R.style.SplashStyle);
        super.onCreate(savedInstanceState);

        //get the context
        this.context = this;

        //get an authentication instance
        mAuth = FirebaseAuth.getInstance();
        //get user database
        database = FirebaseDatabase.getInstance();
        try{
            userReference = database.getReference(ACCOUNTS);
        }catch (NullPointerException e){

        }

        //get the user and check if logged in
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            uid = currentUser.getUid();

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

        dashBoard.putExtra(String_Values.ACCOUNT_UID,uid);

//        dashBoard.putExtra(ACCOUNT_TYPE,"admin");
//        dashBoard.putExtra(ACCOUNT_UID,"testadmin");
        startActivity(dashBoard);
        finish();

    }

    private void openLogin(){
        Intent loginIntent = new Intent(this,Login.class);
        startActivity(loginIntent);
    }
}
