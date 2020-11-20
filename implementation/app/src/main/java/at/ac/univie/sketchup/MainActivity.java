package at.ac.univie.sketchup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    // UI elements
    private ListView listView;
    private FloatingActionButton fab;

    private ArrayAdapter<Sketch> arrayAdapter;
    private MainActivityViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpViewElements();
        setUpViewModel();
        setUpObserver();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mainViewModel.getSketches().getValue());
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                startEditActivity(position+1);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sketchId = mainViewModel.createNewSketch();
                startEditActivity(sketchId);
            }
        });


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.main_menu, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item)
//    {
//        super.onOptionsItemSelected(item);
//
//        if(item.getItemId() == R.id.add_note)
//        {
//            Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
//            startActivity(intent);
//            return true;
//        }
//
//        return false;
//    }

    private void setUpViewElements() {
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        fab = findViewById(R.id.floatingActionButton);
    }

    private void setUpViewModel() {
        mainViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainViewModel.init();
    }

    private void setUpObserver() {
        mainViewModel.getSketches().observe(this, new Observer<List<Sketch>>() {
            @Override
            public void onChanged(List<Sketch> sketches) {
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    private void startEditActivity(int sketchId) {
        Intent intent = new Intent(getApplicationContext(), SketchEditActivity.class);
        intent.putExtra("sketchId", sketchId);
        startActivity(intent);
    }


}