package at.ac.univie.sketchup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.view.PaintView;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;

public class SketchEditActivity extends AppCompatActivity {

    private int sketchId;
    private FloatingActionButton fab;

    private SketchEditActivityViewModel sketchViewModel;
    private PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch_editor);

        paintView = findViewById(R.id.paintView);
        fab = findViewById(R.id.floatingActionButton);

        Intent intent = getIntent();
        sketchId = intent.getIntExtra("sketchId", -1);

        sketchViewModel = new ViewModelProvider(this).get(SketchEditActivityViewModel.class);

        sketchViewModel.init(sketchId);

        sketchViewModel.getSketch().observe(this, new Observer<Sketch>() {
            @Override
            public void onChanged(Sketch sketch) {
//                editText.setText(sketch.getText());
            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getDisplay().getRealMetrics(metrics);
        paintView.init(metrics, sketchViewModel.getSketch().getValue());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogForText();
            }
        });
    }

    private void createDialogForText(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.type_text_alert_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintView.setText(editText.getText().toString());
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.show();
    }



}