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
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.ac.univie.sketchup.model.drawable.DrawableObjectFactory;
import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.model.drawable.shape.Circle;
import at.ac.univie.sketchup.model.drawable.shape.Line;
import at.ac.univie.sketchup.model.drawable.shape.Quadrangle;
import at.ac.univie.sketchup.model.drawable.shape.Triangle;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;
import at.ac.univie.sketchup.view.PaintView;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;

public class SketchEditActivity extends AppCompatActivity {

    private FloatingActionButton fabParam, fabText, fabCircle, fabTriangle, fabQuad, fabLine, fabPlus;
    private PaintView paintView;
    private boolean isButtonsHide = true;

    private SketchEditActivityViewModel sketchViewModel;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setViewElements();

        intent = getIntent();
        setViewModel();

        paintView.init(sketchViewModel);

        setObserver();

        fabPlus.setOnClickListener(view -> {
            showHideAction();
            buttonsLister();
        });

    }

    private void createDialogForText() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.type_text_alert_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        Button buttonSubmit = (Button) dialogView.findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sketchViewModel.setTextForSelected(editText.getText().toString());
                dialogBuilder.dismiss();
            }
        });

        //todo button Cancel

        dialogBuilder.show();
    }

    private void createDialogForParam() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.input_dialog, null);
        dialogBuilder.setView(dialogView);

        Spinner sp_color = (Spinner) dialogView.findViewById(R.id.sp_color);
        sp_color.setAdapter(new ArrayAdapter<Color>(this, android.R.layout.simple_spinner_item, Color.values()));

        final EditText et_strokeWidth = (EditText) dialogView.findViewById(R.id.et_strokeWidth);

        Button btn_confirm = (Button) dialogView.findViewById(R.id.btn_confirm);

        dialogBuilder.show();

        btn_confirm.setOnClickListener(view -> {

            try {
                if (et_strokeWidth.getText().toString().matches("^[0-9]+$"))
                    sketchViewModel.setSizeForSelected(Integer.valueOf(et_strokeWidth.getText().toString()));
                sketchViewModel.setColorForSelected(((Color) sp_color.getSelectedItem()));
            } catch (IncorrectAttributesException e) {
                final AlertDialog dialogBuilder2 = new AlertDialog.Builder(this).create();
                LayoutInflater inflater2 = this.getLayoutInflater();
                View dialogView2 = inflater2.inflate(R.layout.error_alert_dialog, null);
                dialogBuilder2.setView(dialogView2);

                TextView error_txt = (TextView) dialogView2.findViewById(R.id.error_message);

                error_txt.setText(e.getMessage());

                dialogBuilder2.show();

                Button btn_close = (Button) dialogView2.findViewById(R.id.btn_close);

                btn_close.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialogBuilder2.dismiss();
                    }
                });

                e.printStackTrace();
            }
            dialogBuilder.dismiss();
        });

    }


    private void setViewElements() {
        setContentView(R.layout.activity_sketch_editor);
        paintView = findViewById(R.id.paintView);

        fabText = findViewById(R.id.floatingActionButton);
        fabPlus = findViewById(R.id.fab1);
        fabCircle = findViewById(R.id.fab2);
        fabTriangle = findViewById(R.id.fab3);
        fabQuad = findViewById(R.id.fab4);
        fabLine = findViewById(R.id.fab5);
        fabParam = findViewById(R.id.fabParam);
    }

    private void setViewModel() {
        int sketchId = intent.getIntExtra("sketchId", -1);
        sketchViewModel = new ViewModelProvider(this).get(SketchEditActivityViewModel.class);
        sketchViewModel.init(sketchId);
    }

    private void setSelected(DrawableObject selected) {
        sketchViewModel.setSelected(selected);
    }

    // Observer through an event to redraw all object if sketch was changed.
    private void setObserver() {
        sketchViewModel.getSketch().observe(this, new Observer<Sketch>() {
            @Override
            public void onChanged(Sketch sketch) {
                paintView.postInvalidate();
            }
        });
    }

    private void hideAction() {
        List<FloatingActionButton> fabButtons = new ArrayList<>(Arrays.asList(fabParam, fabText, fabCircle, fabTriangle, fabQuad, fabLine));
        fabButtons.forEach(fab -> fab.hide());
        fabButtons.forEach(fab -> fab.animate().translationY(0));

        fabPlus.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);

        isButtonsHide = true;
    }

    private void showAction() {
        List<FloatingActionButton> fabButtons = new ArrayList<>(Arrays.asList(fabParam, fabText, fabCircle, fabTriangle, fabQuad, fabLine));
        fabButtons.forEach(fab -> fab.show());

        fabParam.animate().translationY(-(fabParam.getCustomSize() + 5 + fabText.getCustomSize() + 5 + fabCircle.getCustomSize() + 5 + fabTriangle.getCustomSize() + 5 + fabQuad.getCustomSize() + 5 + fabLine.getCustomSize() + 50));
        fabText.animate().translationY(-(fabText.getCustomSize() + 5 + fabCircle.getCustomSize() + 5 + fabTriangle.getCustomSize() + 5 + fabQuad.getCustomSize() + 5 + fabLine.getCustomSize() + 50));
        fabCircle.animate().translationY(-(fabCircle.getCustomSize() + 5 + fabTriangle.getCustomSize() + 5 + fabQuad.getCustomSize() + 5 + fabLine.getCustomSize() + 50));
        fabTriangle.animate().translationY(-(fabTriangle.getCustomSize() + 5 + fabQuad.getCustomSize() + 5 + fabLine.getCustomSize() + 50));
        fabQuad.animate().translationY(-(fabQuad.getCustomSize() + 5 + fabLine.getCustomSize() + 50));
        fabLine.animate().translationY(-(fabLine.getCustomSize() + 50));

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
        DrawableObjectFactory drawableObjectFactory = new DrawableObjectFactory();

        fabParam.setOnClickListener(view -> createDialogForParam());
        fabText.setOnClickListener(view -> {
            setSelected(drawableObjectFactory.getDrawableObject(TextBox.class));
            createDialogForText();
        });
        fabCircle.setOnClickListener(view -> setSelected(drawableObjectFactory.getDrawableObject(Circle.class)));
        fabTriangle.setOnClickListener(view -> setSelected(drawableObjectFactory.getDrawableObject(Triangle.class)));
        fabQuad.setOnClickListener(view -> setSelected(drawableObjectFactory.getDrawableObject(Quadrangle.class)));
        fabLine.setOnClickListener(view -> setSelected(drawableObjectFactory.getDrawableObject(Line.class)));
    }


}