package at.ac.univie.sketchup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

import at.ac.univie.sketchup.model.Sketch;

public class NoteEditorActivity extends AppCompatActivity {

    int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

//        EditText editText = (EditText)findViewById(R.id.editText);
//        Intent intent = getIntent();
//        noteID = intent.getIntExtra("noteID", -1);
//
//        if(noteID != -1)
//        {
//            editText.setText(MainActivity.sketches.get(noteID).getText());
//        } else {
//            MainActivity.sketches.add(new Sketch());                // as initially, the note is empty
//            noteID = MainActivity.sketches.size() - 1;
//            MainActivity.arrayAdapter.notifyDataSetChanged();
//        }
//
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                MainActivity.sketches.get(noteID).setText(String.valueOf(s));
//                MainActivity.arrayAdapter.notifyDataSetChanged();
//
////                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.tanay.thunderbird.notes", Context.MODE_PRIVATE);
////                HashSet<Sketch> set = new HashSet<>(MainActivity.sketches);
////                sharedPreferences.edit().putStringSet("notes", set).apply();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


    }
}