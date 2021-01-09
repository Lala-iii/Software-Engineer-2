package at.ac.univie.sketchup.view.service.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import at.ac.univie.sketchup.R;
import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;

public class DialogForParam extends DialogTemplate {

    SketchEditActivityViewModel sketchEditActivityViewModel;
    Spinner colorSpinner;
    EditText strokeWidth;

    public DialogForParam(Context context, LayoutInflater inflater, SketchEditActivityViewModel vm) {
        super(context, inflater);
        sketchEditActivityViewModel = vm;
    }

    @Override
    void createView() {
        dialogView = inflater.inflate(R.layout.input_dialog, null);
    }

    @Override
    void submitButtonListener() {
        buttonSubmit.setOnClickListener(view -> {
            try {
                sketchEditActivityViewModel.setSizeForSelected(Integer.parseInt(strokeWidth.getText().toString()));
                sketchEditActivityViewModel.setColorForSelected(((Color) colorSpinner.getSelectedItem()));
            } catch (Exception e) {
                DialogForError errorDialog = new DialogForError(context, inflater, "Wrong input", e.getMessage());
                errorDialog.create();
            }
            dialogBuilder.dismiss();
        });

    }

    @Override
    void setInputElements() {
        colorSpinner = dialogView.findViewById(R.id.sp_color);
        colorSpinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Color.values()));

        strokeWidth = dialogView.findViewById(R.id.edt_comment);
    }
}
