package at.ac.univie.sketchup.view.service.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import at.ac.univie.sketchup.R;
import at.ac.univie.sketchup.exception.IncorrectAttributesException;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;

public class DialogForCombinedShapeTitle extends DialogTemplate {

    EditText editText;
    SketchEditActivityViewModel sketchEditActivityViewModel;

    public DialogForCombinedShapeTitle(Context context, LayoutInflater inflater, SketchEditActivityViewModel vm) {
        super(context, inflater);
        sketchEditActivityViewModel = vm;
    }

    @Override
    void createView() {
        dialogView = inflater.inflate(R.layout.type_text_alert_dialog, null);
    }

    @Override
    void submitButtonListener() {
        buttonSubmit.setOnClickListener(view -> {
            try {
                sketchEditActivityViewModel.storeNewCombinedShape(editText.getText().toString());
            } catch (IncorrectAttributesException e) {
                DialogForError errorDialog = new DialogForError(context, inflater, "Wrong input", e.getMessage());
                errorDialog.create();
            }
            dialogBuilder.dismiss();
        });
    }

    @Override
    void setInputElements() {
        editText = dialogView.findViewById(R.id.edt_comment);
    }
}
