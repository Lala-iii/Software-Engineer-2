package at.ac.univie.sketchup.view.service.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import at.ac.univie.sketchup.R;

public abstract class DialogTemplate {

    final AlertDialog dialogBuilder;
    LayoutInflater inflater;
    Context context;
    Button buttonSubmit;
    Button buttonCancel;
    View dialogView;

    public DialogTemplate(Context context, LayoutInflater inflater) {
        this.context = context;
        this.inflater = inflater;
        dialogBuilder = new AlertDialog.Builder(context).create();
    }

    public void create() {
        createView();
        setViewToDialog();

        setCancelButton();
        setInputElements();
        setSubmitButton();

        submitButtonListener();
        cancelButtonListener();

        showDialog();
    }

    abstract void createView();
    abstract void submitButtonListener();
    abstract void setInputElements();

    public void setViewToDialog() {
        dialogBuilder.setView(dialogView);
    }

    public void setSubmitButton() {
        buttonSubmit = dialogView.findViewById(R.id.buttonSubmit);
    }

    public void setCancelButton() {
        buttonCancel = dialogView.findViewById(R.id.buttonCancel);
    }

    public void cancelButtonListener(){
        buttonCancel.setOnClickListener(view -> {
            dialogBuilder.dismiss();
        });
    }

    public void showDialog() {
        dialogBuilder.show();
    }


 
}