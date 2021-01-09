package at.ac.univie.sketchup.view.service.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import at.ac.univie.sketchup.R;
import at.ac.univie.sketchup.model.drawable.CombinedShape;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;

public class DialogForSelectCombinedShape extends DialogTemplate {

    SketchEditActivityViewModel sketchEditActivityViewModel;
    Spinner shapes;

    public DialogForSelectCombinedShape(Context context, LayoutInflater inflater, SketchEditActivityViewModel vm) {
        super(context, inflater);
        sketchEditActivityViewModel = vm;
    }

    @Override
    void createView() {
        dialogView = inflater.inflate(R.layout.select_cobined_shape_diolog, null);
    }

    @Override
    void submitButtonListener() {
        buttonSubmit.setOnClickListener(view -> {
            sketchEditActivityViewModel.setTemplate((CombinedShape) shapes.getSelectedItem());
            dialogBuilder.dismiss();
        });
    }

    @Override
    void setInputElements() {
        shapes = dialogView.findViewById(R.id.sp_color);
        shapes.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, sketchEditActivityViewModel.getCombinedShapeTitles()));
    }
}
