package at.ac.univie.sketchup.view.service.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;

import at.ac.univie.sketchup.R;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;

public class DialogForText extends DialogTemplate {

    EditText editText;
    SketchEditActivityViewModel sketchEditActivityViewModel;

    public DialogForText(Context context, LayoutInflater inflater, SketchEditActivityViewModel vm) {
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
            sketchEditActivityViewModel.setTextForSelected(editText.getText().toString());
            dialogBuilder.dismiss();
        });
    }

    @Override
    void setInputElements() {
        editText = dialogView.findViewById(R.id.edt_comment);
    }
}
