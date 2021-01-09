package at.ac.univie.sketchup.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import at.ac.univie.sketchup.R;
import at.ac.univie.sketchup.view.service.ListItemAdapter;
import at.ac.univie.sketchup.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    // UI elements
    private ListView listView;
    private FloatingActionButton fab;

    private MainActivityViewModel mainViewModel;
    private ListItemAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpViewElements();
        setUpViewModel();
        setUpObserver();

        listItemAdapter = new ListItemAdapter(mainViewModel.getSketches().getValue(), this);
        listItemAdapter.setViewModel(mainViewModel);
        listView.setAdapter(listItemAdapter);

        fab.setOnClickListener(view -> {
            int sketchId = mainViewModel.createNewSketch();
            startEditActivity(sketchId);
        });
    }

    private void setUpViewElements() {
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        fab = findViewById(R.id.floatingActionButton);
    }

    private void setUpViewModel() {
        mainViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainViewModel.init(this.getApplicationContext());
    }

    private void setUpObserver() {
        mainViewModel.getSketches().observe(this, sketches -> listItemAdapter.addAll(mainViewModel.getSketches().getValue()));
    }

    private void startEditActivity(int sketchId) {
        Intent intent = new Intent(getApplicationContext(), SketchEditActivity.class);
        intent.putExtra("sketchId", sketchId);
        startActivity(intent);
    }


}