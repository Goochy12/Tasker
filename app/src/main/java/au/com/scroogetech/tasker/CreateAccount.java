package au.com.scroogetech.tasker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class CreateAccount extends AppCompatActivity {

    private Spinner accountTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getSupportActionBar().hide();

        accountTypeSpinner = (Spinner) findViewById(R.id.chooseAccountTypeSpinner);
        ArrayList<String> accountTypesList = new ArrayList<>();
        accountTypesList.add("Admin");
        accountTypesList.add("User");


        final ArrayAdapter<String> accountSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, accountTypesList);
        accountSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        accountTypeSpinner.setAdapter(accountSpinnerAdapter);
    }
}
