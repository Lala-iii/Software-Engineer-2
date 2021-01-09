package at.ac.univie.sketchup.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.ac.univie.sketchup.R;
import at.ac.univie.sketchup.exception.IncorrectAttributesException;
import at.ac.univie.sketchup.model.drawable.CombinedShape;
import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.shape.Circle;
import at.ac.univie.sketchup.model.drawable.shape.Line;
import at.ac.univie.sketchup.model.drawable.shape.Polygon;
import at.ac.univie.sketchup.model.drawable.shape.Quadrangle;
import at.ac.univie.sketchup.model.drawable.shape.Triangle;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;
import at.ac.univie.sketchup.view.service.DrawableObjectAbstractFactory;
import at.ac.univie.sketchup.view.service.DrawableObjectFactory;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;

public class SketchEditActivity extends AppCompatActivity {


    private FloatingActionButton fabParam, fabText, fabCircle, fabTriangle, fabQuadrangle, fabLine, fabPlus, fabPolygon,
            fabNewComShape, fabSelectComShape, fabSelector, fabConfirm, fabCancel, fabDelete;

    private PaintView paintView;
    private boolean isButtonsHide = true;
    private SketchEditActivityViewModel  sketchViewModel;
    private Intent intent;
    private DrawableObjectAbstractFactory drawableObjectFactory;

    private boolean isChecked = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setViewElements();

        intent = getIntent();
        drawableObjectFactory = new DrawableObjectFactory();

        setViewModel();

        paintView.init(sketchViewModel);
        this.setTitle(sketchViewModel.getSketch().getValue().getTitle());

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

        final EditText editText = dialogView.findViewById(R.id.edt_comment);
        Button buttonSubmit = dialogView.findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(view -> {
            sketchViewModel.setTextForSelected(editText.getText().toString());
            dialogBuilder.dismiss();
        });

        //todo button Cancel

        dialogBuilder.show();
    }

    private void createDialogForCombinedShapeTitle() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.type_text_alert_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editText = dialogView.findViewById(R.id.edt_comment);
        Button buttonSubmit = dialogView.findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(view -> {
            sketchViewModel.storeNewCombinedShape(editText.getText().toString());
            dialogBuilder.dismiss();
        });

        //todo button Cancel

        dialogBuilder.show();
    }

    private void createDialogForParam() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.input_dialog, null);
        dialogBuilder.setView(dialogView);

        Spinner sp_color = dialogView.findViewById(R.id.sp_color);
        sp_color.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Color.values()));

        final EditText et_strokeWidth = dialogView.findViewById(R.id.et_strokeWidth);

        Button btn_confirm = dialogView.findViewById(R.id.btn_confirm);

        dialogBuilder.show();

        btn_confirm.setOnClickListener(view -> {

            try {
                if (et_strokeWidth.getText().toString().matches("^[0-9]+$"))
                    sketchViewModel.setSizeForSelected(Integer.parseInt(et_strokeWidth.getText().toString()));
                sketchViewModel.setColorForSelected(((Color) sp_color.getSelectedItem()));
            } catch (IncorrectAttributesException e) {
                final AlertDialog dialogBuilder2 = new AlertDialog.Builder(this).create();
                LayoutInflater inflater2 = this.getLayoutInflater();
                View dialogView2 = inflater2.inflate(R.layout.error_alert_dialog, null);
                dialogBuilder2.setView(dialogView2);

                TextView error_txt = dialogView2.findViewById(R.id.error_message);

                error_txt.setText(e.getMessage());

                dialogBuilder2.show();

                Button btn_close = dialogView2.findViewById(R.id.btn_close);

                btn_close.setOnClickListener(v -> dialogBuilder2.dismiss());

                e.printStackTrace();
            }
            dialogBuilder.dismiss();
        });

    }

    private void createDialogForCombinedShapes() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.select_cobined_shape_diolog, null);
        dialogBuilder.setView(dialogView);

        Spinner sp_shape = dialogView.findViewById(R.id.sp_color);
        sp_shape.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sketchViewModel.getCombinedShapeTitles()));

        Button btn_confirm = dialogView.findViewById(R.id.btn_confirm);

        dialogBuilder.show();

        btn_confirm.setOnClickListener(view -> {
            setSelected((CombinedShape) sp_shape.getSelectedItem());

            dialogBuilder.dismiss();
        });
    }


    private void setViewElements() {
        setContentView(R.layout.activity_sketch_editor);
        paintView = findViewById(R.id.paintView);

        fabText = findViewById(R.id.fabText);
        fabPlus = findViewById(R.id.fabMenu);
        fabCircle = findViewById(R.id.fabCircle);
        fabTriangle = findViewById(R.id.fabTriangle);
        fabQuadrangle = findViewById(R.id.fabQuadrangle);
        fabLine = findViewById(R.id.fabLine);
        fabPolygon = findViewById(R.id.fabPolygon);
        fabParam = findViewById(R.id.fabParam);

        fabSelector = findViewById(R.id.fabSelector);
        fabConfirm = findViewById(R.id.fabConfirm);
        fabCancel = findViewById(R.id.fabCancel);
        fabDelete = findViewById(R.id.fabDelete);

        fabSelectComShape = findViewById(R.id.fabCombinedShape);
        fabNewComShape = findViewById(R.id.fabAddCombinedShape);

        if(Build.VERSION.SDK_INT > 25) {
            fabText.setTooltipText("Add text");
            fabPlus.setTooltipText("Start painting");
            fabCircle.setTooltipText("Add circle");
            fabTriangle.setTooltipText("Add triangle");
            fabQuadrangle.setTooltipText("Add quadrangle");
            fabLine.setTooltipText("Add line");
            fabPolygon.setTooltipText("Add handwriting");
            fabParam.setTooltipText("Change color or width");

            fabSelector.setTooltipText("Select a shape by snipping it");
            fabConfirm.setTooltipText("Confirm the changes");;
            fabCancel.setTooltipText("Cancel the changes");
            fabDelete.setTooltipText("Delete the shape");

            fabSelectComShape.setTooltipText("Select the custom shape");
            fabNewComShape.setTooltipText("Add the custom shape");;

        }
    }

    private void setViewModel() {
        int sketchId = intent.getIntExtra("sketchId", -1);
        sketchViewModel = new ViewModelProvider(this).get(SketchEditActivityViewModel.class);
        sketchViewModel.init(sketchId);

    }

    private void setSelected(DrawableObject selected) {
        sketchViewModel.setTemplate(selected);
    }

    // Observer through an event to redraw all object if sketch was changed.
    @SuppressLint("ResourceAsColor")
    private void setObserver() {
        sketchViewModel.getSketch().observe(this, sketch -> paintView.postInvalidate());
        sketchViewModel.getMode().observe(this, mode -> {
            if (mode.equals(SketchEditActivityViewModel.EDIT)) {
                fabConfirm.show();
                fabCancel.show();
                fabDelete.show();

                fabDelete.animate().translationX(-(fabDelete.getCustomSize() + 5 + fabSelector.getCustomSize() + 5 + fabSelectComShape.getCustomSize() + 5 + fabNewComShape.getCustomSize() + 5 + fabParam.getCustomSize() + 50));
                fabDelete.animate().translationY(-50);

                fabCancel.animate().translationX(-(fabCancel.getCustomSize() + 5 + fabDelete.getCustomSize() + 5 + fabSelector.getCustomSize() + 5 + fabSelectComShape.getCustomSize() + 5 + fabNewComShape.getCustomSize() + 5 + fabParam.getCustomSize() + 50));
                fabCancel.animate().translationY(-50);

                fabConfirm.animate().translationX(-(fabConfirm.getCustomSize() + 5 + fabCancel.getCustomSize() + 5 + fabDelete.getCustomSize() + 5 + fabSelector.getCustomSize() + 5 + fabSelectComShape.getCustomSize() + 5 + fabNewComShape.getCustomSize() + 5 + fabParam.getCustomSize() + 50));
                fabConfirm.animate().translationY(-50);
            } else {
                fabConfirm.hide();
                fabCancel.hide();
                fabDelete.hide();

                fabConfirm.animate().translationY(0);
                fabCancel.animate().translationY(0);
                fabDelete.animate().translationY(0);

                fabConfirm.animate().translationX(0);
                fabCancel.animate().translationX(0);
                fabDelete.animate().translationX(0);
                if (mode.equals(SketchEditActivityViewModel.SELECTION))
                    fabSelector.animate().translationY(-50);
            }
            paintView.postInvalidate();
        });
    }

    private void hideAction() {
        new ArrayList<>(Arrays.asList(fabText, fabCircle, fabTriangle, fabQuadrangle, fabLine, fabPolygon)).forEach(fab -> {
            fab.hide();
            fab.animate().translationY(0);
        });

        new ArrayList<>(Arrays.asList(fabSelector, fabParam, fabNewComShape, fabSelectComShape)).forEach(fab -> {
            fab.hide();
            fab.animate().translationX(0);
        });


        fabPlus.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);

        isButtonsHide = true;
    }

    private void showAction() {
        List<FloatingActionButton> fabButtons = new ArrayList<>(Arrays.asList(fabParam, fabText, fabCircle, fabTriangle, fabQuadrangle, fabLine, fabPolygon, fabSelector, fabNewComShape, fabSelectComShape));
        fabButtons.forEach(FloatingActionButton::show);

        // vertical
        fabSelector.animate().translationX(-(fabSelector.getCustomSize() + 5 + fabSelectComShape.getCustomSize() + 5 + fabNewComShape.getCustomSize() + 5 + fabParam.getCustomSize() + 50));
        fabSelectComShape.animate().translationX(-(fabSelectComShape.getCustomSize() + 5 + fabNewComShape.getCustomSize() + 5 + fabParam.getCustomSize() + 50));
        fabNewComShape.animate().translationX(-(fabNewComShape.getCustomSize() + 5 + fabParam.getCustomSize() + 50));
        fabParam.animate().translationX(-(fabParam.getCustomSize() + 50));

        // horizontal
        fabText.animate().translationY(-(fabPolygon.getCustomSize() + 5 + fabText.getCustomSize() + 5 + fabCircle.getCustomSize() + 5 + fabTriangle.getCustomSize() + 5 + fabQuadrangle.getCustomSize() + 5 + fabLine.getCustomSize() + 50));
        fabCircle.animate().translationY(-(fabPolygon.getCustomSize() + 5 + fabCircle.getCustomSize() + 5 + fabTriangle.getCustomSize() + 5 + fabQuadrangle.getCustomSize() + 5 + fabLine.getCustomSize() + 50));
        fabTriangle.animate().translationY(-(fabPolygon.getCustomSize() + 5 + fabTriangle.getCustomSize() + 5 + fabQuadrangle.getCustomSize() + 5 + fabLine.getCustomSize() + 50));
        fabQuadrangle.animate().translationY(-(fabPolygon.getCustomSize() + 5 + fabQuadrangle.getCustomSize() + 5 + fabLine.getCustomSize() + 50));
        fabLine.animate().translationY(-(fabPolygon.getCustomSize() + 5 + fabLine.getCustomSize() + 50));
        fabPolygon.animate().translationY(-(fabPolygon.getCustomSize() + 50));

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
        fabText.setOnClickListener(view -> {
            setSelected(drawableObjectFactory.getDrawableObject(TextBox.class));
            animateButton((FloatingActionButton) view);
            createDialogForText();
        });

        fabCircle.setOnClickListener(view -> {
            setSelected(drawableObjectFactory.getDrawableObject(Circle.class));
            animateButton((FloatingActionButton) view);
        });

        fabTriangle.setOnClickListener(view -> {
            setSelected(drawableObjectFactory.getDrawableObject(Triangle.class));
            animateButton((FloatingActionButton) view);
        });

        fabQuadrangle.setOnClickListener(view -> {
            setSelected(drawableObjectFactory.getDrawableObject(Quadrangle.class));
            animateButton((FloatingActionButton) view);
        });

        fabLine.setOnClickListener(view -> {
            setSelected(drawableObjectFactory.getDrawableObject(Line.class));
            animateButton((FloatingActionButton) view);
        });

        fabPolygon.setOnClickListener(view -> {
            setSelected(drawableObjectFactory.getDrawableObject(Polygon.class));
            animateButton((FloatingActionButton) view);
        });

        fabSelector.setOnClickListener(view -> {
            sketchViewModel.setMode(SketchEditActivityViewModel.SELECTION);
            sketchViewModel.restoreDrawableObjectCoordinates();
            animateButton((FloatingActionButton) view);
        });

        fabParam.setOnClickListener(view -> createDialogForParam());

        fabConfirm.setOnClickListener(view -> {
            sketchViewModel.storeDrawableObjectCoordinates();

        });
        fabCancel.setOnClickListener(view -> {
            sketchViewModel.restoreDrawableObjectCoordinates();

        });

        fabDelete.setOnClickListener(view -> {
            sketchViewModel.removeDrawableObject();
        });

        fabNewComShape.setOnClickListener(view -> createDialogForCombinedShapeTitle());
        fabSelectComShape.setOnClickListener(view -> createDialogForCombinedShapes());
    }

    private void animateButton(FloatingActionButton fab) {
        List<FloatingActionButton> fabButtons = new ArrayList<>(Arrays.asList(fabText, fabCircle, fabTriangle,
                fabQuadrangle, fabLine, fabPolygon));
        for (FloatingActionButton fabButton : fabButtons) {
            fabButton.animate().translationX(0);
        }
        fabSelector.animate().translationY(0);

        if (sketchViewModel.getMode().getValue() == SketchEditActivityViewModel.SELECTION) {
            if (fab == fabSelector) fab.animate().translationY(-50);
        } else fab.animate().translationX(-50);

        // TODO animation to focus on last selected shape or selector:
        //  after we select a shape and cancel it, it should focus back the selector and not creation
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        MenuItem checkable1 = menu.findItem(R.id.layer1);
        checkable1.setChecked(sketchViewModel.getByLayerId(0).getVisibility());

        MenuItem checkable2 = menu.findItem(R.id.layer2);
        checkable2.setChecked(sketchViewModel.getByLayerId(1).getVisibility());

        MenuItem checkable3 = menu.findItem(R.id.layer3);
        checkable3.setChecked(sketchViewModel.getByLayerId(2).getVisibility());

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.layer1:
                isChecked = !item.isChecked();
                item.setChecked(isChecked);
                sketchViewModel.getByLayerId(0).setVisibility(isChecked);
                paintView.postInvalidate();
                return true;
            case R.id.layer2:
                isChecked = !item.isChecked();
                item.setChecked(isChecked);
                sketchViewModel.getByLayerId(1).setVisibility(isChecked);
                paintView.postInvalidate();
                return true;
            case R.id.layer3:
                isChecked = !item.isChecked();
                item.setChecked(isChecked);
                sketchViewModel.getByLayerId(2).setVisibility(isChecked);
                paintView.postInvalidate();
                return true;
            case R.id.delete:
                sketchViewModel.deleteAllDrawObj(); //clear Sketch
            case R.id.save:
                sketchViewModel.storeSketchChanges(); //save all new changes on Sketch
            default:
                return false;
        }
    }

}