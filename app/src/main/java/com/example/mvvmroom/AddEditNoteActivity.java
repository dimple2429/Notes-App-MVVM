package com.example.mvvmroom;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.mvvmroom.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.mvvmroom.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.mvvmroom.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.example.mvvmroom.EXTRA_PRIORITY";


    private EditText editTextTitle, editTextDescription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle= findViewById(R.id.edit_text_title);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        editTextDescription = findViewById(R.id.edit_text_description);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.cancel);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));

        }else{
            setTitle("Add Note");
        }
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int numberPriority = numberPickerPriority.getValue();

        //validate if any of ttile and description is empty
        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this,"Please Enter the Details",Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, numberPriority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != 1){
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
