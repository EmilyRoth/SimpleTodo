package com.codepath.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //obtain a reference to the Listview created in the layout
        lvItems = (ListView) findViewById(R.id.lvItems);
        //Initialize the items list
        //items = new ArrayList<>();
        readItems();
        //Init the adapter using the items list
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        //wire the adapter to the view
        lvItems.setAdapter(itemsAdapter);

        //add items to list
        //items.add("First todo item");
        //items.add("Second todo item");

        //set up listener
        setupListViewListener();

    }
    private void setupListViewListener(){
        //set the list veiw
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                //remove the item in the list
                items.remove(position);
                //notify the adapter that underlying dataset changed
                itemsAdapter.notifyDataSetChanged();
                //return to tell framework the long click was consumed
                //Log.i("MainActivity", "Removed item " + position);
                writeItems();
                return true;
            }
        });
    }// setup listener
        private File getDataFile(){
            return new File(getFilesDir(), "todo.txt");
        }
    public void onAddItem(View v){
        //Obtain Reference to edit text
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        //grab the editexts content as a string
        String itemText = etNewItem.getText().toString();
        //add the items to the list via the adapter
        itemsAdapter.add(itemText);
        //clear the edit text
        etNewItem.setText("");
        // display notification
        Toast.makeText(getApplicationContext(), "Item added to list", Toast.LENGTH_SHORT).show();
    }//onAddItem

    private void readItems(){
        try{
            items = new ArrayList<String>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e){
           e.printStackTrace();
           items = new ArrayList<>();
        }

    }// read item

    private void writeItems(){
        try{
            FileUtils.writeLines(getDataFile(), items);
        } catch(IOException e){
          e.printStackTrace();
        }

    }//write items
}