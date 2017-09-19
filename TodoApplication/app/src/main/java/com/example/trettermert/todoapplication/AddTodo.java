package com.example.trettermert.todoapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddTodo extends AppCompatActivity {

    ListView listView;
    public static  CustomAdapter2 adapter;
    EditText textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        setTitle("Добавить задачу");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_USE_LOGO);
        textView = (EditText) findViewById(R.id.taskField);
        listView = (ListView) findViewById(R.id.listCategory);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                CustomAdapter2.setSelectedPosition(position, adapter);
            }
        });
        Bundle b=this.getIntent().getExtras();
        String[] list = b.getStringArray("List");
        if (list != null){
           adapter =  new CustomAdapter2(AddTodo.this, list);

            //adapter.addAll(list);
            listView.setAdapter(adapter);
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ok:
                Intent intent = new Intent();
                Bundle args = new Bundle();
                args.putString("name",textView.getText().toString());
                args.putInt("id", CustomAdapter2.getSelectedId());
                intent.putExtras(args);
                //intent.putExtras("name", textView.getText().toString(), "id", CustomAdapter2.getSelectedId());
                //intent.putExtra("id", CustomAdapter2.getSelectedId());
                setResult(RESULT_OK, intent);
                finish();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
