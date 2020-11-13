package at.ac.univie.sketchup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.sax.Element;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import at.ac.univie.sketchup.model.ElementType;
import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.view.PaintView;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;

public class SketchEditActivity extends AppCompatActivity {

    private int sketchId;
    private FloatingActionButton fab, fab1, fab2, fab3, fab4, fab5;

    private SketchEditActivityViewModel sketchViewModel;
    private PaintView paintView;
    private boolean collapsedActions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch_editor);
        collapsedActions = true;

        paintView = findViewById(R.id.paintView);
        fab = findViewById(R.id.floatingActionButton);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        fab3 = findViewById(R.id.fab3);
        fab4 = findViewById(R.id.fab4);
        fab5 = findViewById(R.id.fab5);

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

//        DisplayMetrics metrics = new DisplayMetrics();
//        getDisplay().getRealMetrics(metrics);
//        paintView.init(metrics, sketchViewModel.getSketch().getValue());

        paintView.setSketch(sketchViewModel.getSketch().getValue());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogForText();
            }
        });

        fab1.setOnClickListener(view -> {
            if (collapsedActions) {
                fab.show();
                fab2.show();
                fab3.show();
                fab4.show();
                fab5.show();

                fab.animate().translationY(-(fab.getCustomSize() + 5 + fab2.getCustomSize()+ 5 +fab3.getCustomSize()+ 5 +fab4.getCustomSize()+ 5 + fab5.getCustomSize() + 50));
                fab2.animate().translationY(-(fab2.getCustomSize()+ 5 +fab3.getCustomSize()+ 5 +fab4.getCustomSize()+ 5 + fab5.getCustomSize() + 50));
                fab3.animate().translationY(-(fab3.getCustomSize()+ 5 +fab4.getCustomSize()+ 5 +fab5.getCustomSize() + 50));
                fab4.animate().translationY(-(fab4.getCustomSize()+ 5 +fab5.getCustomSize() + 50));
                fab5.animate().translationY(-(fab5.getCustomSize() + 50));

                fab1.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24);
                collapsedActions = false;

            } else collapseActions();
        });

        fab2.setOnClickListener(view -> setAction(ElementType.CIRCLE));
        fab3.setOnClickListener(view -> setAction(ElementType.TRIANGLE));
        fab4.setOnClickListener(view -> setAction(ElementType.QUADRANGLE));
        fab5.setOnClickListener(view -> setAction(ElementType.LINE));
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

    private void setAction(ElementType type) {
        paintView.setMode(type);
        collapseActions();
    }

    private void collapseActions() {
        fab.hide();
        fab2.hide();
        fab3.hide();
        fab4.hide();
        fab5.hide();
        fab.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
        fab4.animate().translationY(0);
        fab5.animate().translationY(0);

        fab1.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
        //mode = ShapeType.NONE;
        collapsedActions = true;
    }



}