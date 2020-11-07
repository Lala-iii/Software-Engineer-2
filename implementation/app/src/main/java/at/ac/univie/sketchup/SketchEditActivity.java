package at.ac.univie.sketchup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashSet;
import java.util.List;

import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.repository.SketchRepository;
import at.ac.univie.sketchup.viewmodel.MainActivityViewModel;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;

public class SketchEditActivity extends AppCompatActivity {

    private int sketchId;
    private EditText editText;

    private SketchEditActivityViewModel sketchEditActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        editText = (EditText)findViewById(R.id.editText);

        Intent intent = getIntent();
        sketchId = intent.getIntExtra("sketchId", -1);

        sketchEditActivityViewModel = new ViewModelProvider(this).get(SketchEditActivityViewModel.class);

        sketchEditActivityViewModel.init(sketchId);

        sketchEditActivityViewModel.getSketch().observe(this, new Observer<Sketch>() {
            @Override
            public void onChanged(Sketch sketch) {
//                editText.setText(sketch.getText());
            }
        });

        editText.setText(sketchEditActivityViewModel.getSketch().getValue().getText());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                sketchEditActivityViewModel.updateSketch(String.valueOf(s));
            }
        });


    }
}