package at.ac.univie.sketchup.view;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import java.util.List;

import at.ac.univie.sketchup.R;
import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.viewmodel.MainActivityViewModel;

public class ListItemAdapter extends BaseAdapter {
    MainActivityViewModel viewModel;
    private List<Sketch> sketches;
    private Context context;
    private View row;

  public ListItemAdapter(List<Sketch> sketches, Context context){
      this.sketches=sketches;
      this.context=context;
  }
    @Override
    public int getCount() {
        return sketches.size();
    }

    @Override
    public Object getItem(int i) {
        return sketches.get(i);
    }

    @Override
    public long getItemId(int i) {
        return sketches.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        row = LayoutInflater.from(context).inflate(R.layout.custom_layout,parent,false);
        TextView txtSketchTitle = (TextView) row.findViewById(R.id.sketch);
        txtSketchTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), SketchEditActivity.class);
                intent.putExtra("sketchId", sketches.get(position).getId());
                context.startActivity(intent);
            }
        });
        txtSketchTitle.setText(sketches.get(position).getTitle());

        Button btnAction = (Button) row.findViewById(R.id.delete_button);
        // Click listener of button
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewModel.deleteSketchById(sketches.get(position).getId());

                notifyDataSetChanged();

            }
        });

        return row;

    }

    public void setViewModel(ViewModel vm){
      viewModel = (MainActivityViewModel) vm;
    }

    public void addAll(List<Sketch> all) {
        sketches = all;
        notifyDataSetChanged();
    }

}
