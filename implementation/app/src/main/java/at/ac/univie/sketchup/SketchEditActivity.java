package at.ac.univie.sketchup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import at.ac.univie.sketchup.model.DrawableObject;
import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.model.sketchObjects.TextBox;
import at.ac.univie.sketchup.view.PaintView;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;

public class SketchEditActivity extends AppCompatActivity {

    private int sketchId;

    private FloatingActionButton fabText;
    private PaintView paintView;

    private SketchEditActivityViewModel sketchViewModel;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpViewElements();

        intent = getIntent();
        setUpViewModel();

        paintView.init(sketchViewModel);

        sketchViewModel.getSketch().observe(this, new Observer<Sketch>() {
            @Override
            public void onChanged(Sketch sketch) {
                paintView.postInvalidate();
            }
        });

        fabText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpSelected(new TextBox());
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
        Button buttonSubmit = (Button) dialogView.findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintView.setTextForSelected(editText.getText().toString());
                dialogBuilder.dismiss();
            }
        });

        //todo button Cancel

        dialogBuilder.show();
    }

    private void setUpViewElements(){
        setContentView(R.layout.activity_sketch_editor);
        paintView = findViewById(R.id.paintView);
        fabText = findViewById(R.id.floatingActionButton);
    }

    private void setUpViewModel() {
        sketchId = intent.getIntExtra("sketchId", -1);
        sketchViewModel = new ViewModelProvider(this).get(SketchEditActivityViewModel.class);
        sketchViewModel.init(sketchId);
    }

    private void setUpSelected(DrawableObject selected) {
        paintView.setSelected(selected);
    }



}