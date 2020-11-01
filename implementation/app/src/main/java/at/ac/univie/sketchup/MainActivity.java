package at.ac.univie.sketchup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    // UI elements
    private ListView listView;
    private FloatingActionButton fab;

    private ArrayAdapter<Sketch> arrayAdapter;
    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        fab = findViewById(R.id.floatingActionButton);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        mainActivityViewModel.init();

        mainActivityViewModel.getSketches().observe(this, new Observer<List<Sketch>>() {
            @Override
            public void onChanged(List<Sketch> sketches) {
                arrayAdapter.notifyDataSetChanged();
            }
        });


        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mainActivityViewModel.getSketches().getValue());
        listView.setAdapter(arrayAdapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
//                intent.putExtra("noteID", position);            //to tell us which row of listView was tapped
//                startActivity(intent);
//            }
//        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityViewModel.createNewSketch();

                // todo intent to new activity with sketch id
            }
        });



        // delete item
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
//            {
//                new AlertDialog.Builder(MainActivity.this)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setTitle("Delete?")
//                        .setMessage("Are you sure you want to delete this note?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which)
//                            {
//                                notes.remove(position);
//                                arrayAdapter.notifyDataSetChanged();
//                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.tanay.thunderbird.notes", Context.MODE_PRIVATE);
//                                HashSet<String> set = new HashSet<>(MainActivity.notes);
//                                sharedPreferences.edit().putStringSet("notes", set).apply();
//                            }
//                        })
//
//                        .setNegativeButton("No", null)
//                        .show();
//
//                return true;
//            }
//
//        });
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

}