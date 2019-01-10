package au.com.liamgooch.tasker.Activities;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import au.com.liamgooch.tasker.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static au.com.liamgooch.tasker.Activities.StartActivity.SHARED_PREF;
import static au.com.liamgooch.tasker.Activities.StartActivity.TAG;

import java.util.ArrayList;

public class CreateAccount extends AppCompatActivity {

    private Spinner accountTypeSpinner;
    private Button createAccountButton;
    private TextView fullNameView;
    private TextView emailView;
    private TextView passwordView;

    private String fullName;
    private String email;
    private String password;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getSupportActionBar().hide();

        accountTypeSpinner = (Spinner) findViewById(R.id.chooseAccountTypeSpinner);
        ArrayList<String> accountTypesList = new ArrayList<>();
        accountTypesList.add("User");
        accountTypesList.add("Admin");


        final ArrayAdapter<String> accountSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, accountTypesList);
        accountSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        accountTypeSpinner.setAdapter(accountSpinnerAdapter);

        createAccountButton = (Button) findViewById(R.id.createAccountButton);
        fullNameView = (TextView) findViewById(R.id.chooseName);
        emailView = (TextView) findViewById(R.id.chooseEmail);
        passwordView = (TextView) findViewById(R.id.choosePassword);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userReference = database.getReference("accounts");

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fullName = fullNameView.getText().toString();
                email = emailView.getText().toString();
                password = passwordView.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    addAccountDetails(user);

                                    successfulCreation(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(CreateAccount.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }

    private void addAccountDetails(FirebaseUser user){
        DatabaseReference currentUserDB = userReference.child(user.getUid());

        String accountType = getAccountType();
        currentUserDB.child("type").setValue(accountType);
        currentUserDB.child("email").setValue(user.getEmail());
        currentUserDB.child("name").setValue(fullName);
        currentUserDB.child("verified").setValue(0);
        currentUserDB.child("uid").setValue(user.getUid());

        currentUserDB.child("tasks_db").child("version").setValue(0);

//        SharedPreferences sharedPreferences;
//        sharedPreferences.edit()
    }

    private String getAccountType() {
        String account = "";
        if (accountTypeSpinner.getSelectedItemPosition() == 0){
            account = "user";
        }else {
            account = "admin";
        }
        return account;
    }

    private void successfulCreation(FirebaseUser user){
        String email = user.getEmail();
        String password = passwordView.getText().toString();

        Intent toLogin = new Intent(this,Login.class);
        toLogin.putExtra("email",email);
        toLogin.putExtra("password",password);

        setResult(RESULT_OK,toLogin);
        finish();
    }
}
