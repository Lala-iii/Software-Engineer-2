package at.ac.univie.sketchup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import at.ac.univie.sketchup.model.Color;
import at.ac.univie.sketchup.model.DrawableObject;
import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.model.sketchObjects.Circle;
import at.ac.univie.sketchup.model.sketchObjects.TextBox;
import at.ac.univie.sketchup.view.PaintView;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;

public class SketchEditActivity extends AppCompatActivity {

    private FloatingActionButton fabParam, fabText, fabPlus, fabCircle, fab3, fab4, fab5;
    private PaintView paintView;
    private boolean isButtonsHide = true;

    private SketchEditActivityViewModel sketchViewModel;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpViewElements();

        intent = getIntent();
        setUpViewModel();

        paintView.init(sketchViewModel);

        setUpObserver();

        fabPlus.setOnClickListener(view -> {
            showHideAction();
            buttonsLister();
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

    private void createDialogForParam(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.input_dialog, null);
        dialogBuilder.setView(dialogView);

        Spinner sp_color = (Spinner) dialogView.findViewById(R.id.sp_color);
        sp_color.setAdapter(new ArrayAdapter<Color>(this, android.R.layout.simple_spinner_item, Color.values()));

        final EditText et_strokeWidth = (EditText) dialogView.findViewById(R.id.et_strokeWidth);

        Button btn_confirm = (Button) dialogView.findViewById(R.id.btn_confirm);

        btn_confirm.setOnClickListener(view -> {
            if (et_strokeWidth.getText().toString().matches("^[0-9]+$"))
                paintView.setSize(Integer.valueOf(et_strokeWidth.getText().toString()));
            paintView.setColor(((Color)sp_color.getSelectedItem()));
            dialogBuilder.dismiss();
        });

        dialogBuilder.show();
    }

    private void setUpViewElements(){
        setContentView(R.layout.activity_sketch_editor);
        paintView = findViewById(R.id.paintView);

        fabText = findViewById(R.id.floatingActionButton);
        fabPlus = findViewById(R.id.fab1);
        fabCircle = findViewById(R.id.fab2);
        fab3 = findViewById(R.id.fab3);
        fab4 = findViewById(R.id.fab4);
        fab5 = findViewById(R.id.fab5);
        fabParam = findViewById(R.id.fabParam);
    }

    private void setUpViewModel() {
        int sketchId = intent.getIntExtra("sketchId", -1);
        sketchViewModel = new ViewModelProvider(this).get(SketchEditActivityViewModel.class);
        sketchViewModel.init(sketchId);
    }

    private void setUpSelected(DrawableObject selected) {
        paintView.setSelected(selected);
    }

    // Observer through an event to redraw all object if sketch was changed.
    private void setUpObserver() {
        sketchViewModel.getSketch().observe(this, new Observer<Sketch>() {
            @Override
            public void onChanged(Sketch sketch) {
                paintView.postInvalidate();
            }
        });
    }

    private void hideAction() {
        fabText.hide();
        fabCircle.hide();
        fab3.hide();
        fab4.hide();
        fab5.hide();
        fabParam.hide();

        fabText.animate().translationY(0);
        fabCircle.animate().translationY(0);
        fab3.animate().translationY(0);
        fab4.animate().translationY(0);
        fab5.animate().translationY(0);
        fabParam.animate().translationY(0);

        fabPlus.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
        //mode = ShapeType.NONE;
        isButtonsHide = true;
    }

    private void showAction() {
        fabText.show();
        fabCircle.show();
        fab3.show();
        fab4.show();
        fab5.show();
        fabParam.show();

        fabText.animate().translationY(-(fabText.getCustomSize() + 5 + fabCircle.getCustomSize()+ 5 +fab3.getCustomSize()+ 5 +fab4.getCustomSize()+ 5 + fab5.getCustomSize() + 50));
        fabCircle.animate().translationY(-(fabCircle.getCustomSize()+ 5 +fab3.getCustomSize()+ 5 +fab4.getCustomSize()+ 5 + fab5.getCustomSize() + 50));
        fab3.animate().translationY(-(fab3.getCustomSize()+ 5 +fab4.getCustomSize()+ 5 +fab5.getCustomSize() + 50));
        fab4.animate().translationY(-(fab4.getCustomSize()+ 5 +fab5.getCustomSize() + 50));
        fab5.animate().translationY(-(fab5.getCustomSize() + 50));
        fabParam.animate().translationY(-(fabParam.getCustomSize() + 5 + fabText.getCustomSize() + 5 + fabCircle.getCustomSize()+ 5 +fab3.getCustomSize()+ 5 +fab4.getCustomSize()+ 5 + fab5.getCustomSize() + 50));

        fabPlus.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24);
        isButtonsHide = false;
    }

    private void showHideAction() {
        if (isButtonsHide) {
            showAction();
        } else {
            hideAction();
        }
    }

    private void buttonsLister() {
        fabCircle.setOnClickListener(view -> setUpSelected(new Circle()));
//       fab3.setOnClickListener(view -> setAction(ElementType.TRIANGLE));
//       fab4.setOnClickListener(view -> setAction(ElementType.QUADRANGLE));
//       fab5.setOnClickListener(view -> setAction(ElementType.LINE));
        fabParam.setOnClickListener(view -> createDialogForParam());
        fabText.setOnClickListener(view -> {
            setUpSelected(new TextBox());
            createDialogForText();
        });
    }






}