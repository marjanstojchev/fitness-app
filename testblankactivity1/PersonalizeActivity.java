package com.example.marjan.testblankactivity1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by marjan on 6/17/2015.
 */
public class PersonalizeActivity extends ActionBarActivity implements TextWatcher, AdapterView.OnItemSelectedListener,View.OnClickListener {

    Toolbar toolbar;

    EditText insertAgeEditText;
    EditText insertWeigthEditText;
    EditText insertHeigthEditText;
    Spinner genderSpinner;
    Button confirmButton;

    int weigthValue;
    int heigthValue;
    int ageValue;
    int genderValue;

    boolean isPersonalized;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.personalize_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        genderSpinner = (Spinner) findViewById(R.id.gender_spinner);
        ArrayAdapter genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        genderSpinner.setAdapter(genderAdapter);

        insertAgeEditText = (EditText) findViewById(R.id.insert_age_edit_text);
        insertAgeEditText.addTextChangedListener(this);

        insertWeigthEditText = (EditText) findViewById(R.id.insert_weigth_edit_text);
        ModifiedTextWatcher  weigthTextWatcher = new ModifiedTextWatcher();
        insertWeigthEditText.addTextChangedListener(weigthTextWatcher);

        insertHeigthEditText = (EditText) findViewById(R.id.insert_heigth_edit_text);
        ModifiedTextWatcher  heigthTextWatcher = new ModifiedTextWatcher();
        insertHeigthEditText.addTextChangedListener(heigthTextWatcher);

        confirmButton = (Button) findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(this);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_personalize, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.personalize) {

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

            return true;
        }
        if(id==android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            int num = Integer.parseInt(s.toString());
            if (num > 100) {
                s.replace(0, s.length(), "100");
            }
        } catch (NumberFormatException e){}
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirm_button){
            isPersonalized = true;

            weigthValue = editTextToInt(insertWeigthEditText);
            heigthValue = editTextToInt(insertHeigthEditText);
            ageValue = editTextToInt(insertAgeEditText);
            genderValue = genderSpinner.getSelectedItemPosition();

            SharedPreferences sharedPreferences = getSharedPreferences("Personalization", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("personalized", isPersonalized);
            editor.commit();
            editor.putInt("gender", genderValue);
            editor.commit();
            editor.putInt("weigth", weigthValue);
            editor.commit();
            editor.putInt("heigth", heigthValue);
            editor.commit();
            editor.putInt("age", ageValue);
            editor.commit();


            NavUtils.navigateUpFromSameTask(this);
        }
    }

    public class ModifiedTextWatcher implements TextWatcher {



        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                int num = Integer.parseInt(s.toString());
                if (num > 300) {
                    s.replace(0, s.length(), "300");
                }
            } catch (NumberFormatException e){}
        }
    }

    int editTextToInt(EditText editText) {

        int editTextDouble;

        String editTextString = new String();

        editTextString = editText.getText().toString();

        if (editText == null || editTextString.isEmpty()) {

            editTextDouble = 0;

        } else {

            editTextDouble = Integer.parseInt(editText.getText().toString());

        }

        return editTextDouble;
    }
}
