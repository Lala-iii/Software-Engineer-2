package at.ac.univie.sketchup.view.service.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import at.ac.univie.sketchup.R;

public class DialogForError extends DialogTemplate {
    String errorTitle;
    String errorMessage;

    public DialogForError(Context context, LayoutInflater inflater, String errorTitle, String errorMessage) {
        super(context, inflater);
        this.errorTitle = errorTitle;
        this.errorMessage = errorMessage;
    }

    @Override
    void createView() {
        dialogView = inflater.inflate(R.layout.error_alert_dialog, null);
    }

    @Override
    void submitButtonListener() {}

    @Override
    public void setSubmitButton() {}

    @Override
    void setInputElements() {
        TextView errorTitleView = dialogView.findViewById(R.id.textViewTitle);
        errorTitleView.setText(errorTitle);

        TextView errorTitleMessage = dialogView.findViewById(R.id.textViewMessage);
        errorTitleMessage.setText(errorMessage);
    }
}
