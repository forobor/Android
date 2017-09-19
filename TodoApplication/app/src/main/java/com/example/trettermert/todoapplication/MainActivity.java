package com.example.trettermert.todoapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.scalified.fab.ActionButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private ActionButton actionButton;
    private ListView listView;
    private CustomAdapter mAdapter;
    private ArrayList<Project> projects;
    private static Context context;

    static private int projectId;
    static private String todoName;

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:postgresql://localhost:3000/todo";
    private static final String user = "super_user";
    private static final String password = "1";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        context = MainActivity.this;
        projects = new ArrayList<Project>();

        super.onCreate(savedInstanceState);



        //бд
        String query = "select titles(*) from projects";

        Connection con = null;
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        Ion.with(this)
                .load(getString(R.string.kIndexRequest))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (result != null) {
                            List<Project> projects = new ArrayList<Project>();
                            for (final JsonElement projectJsonElement : result) {
                                projects.add(new Gson().fromJson(projectJsonElement, Project.class));
                            }
                        }
                    }
                });

        setContentView(R.layout.activity_main);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        actionButton = (ActionButton) findViewById(R.id.action_button);
        listView = (ListView) findViewById(R.id.list_view);

        mAdapter = new CustomAdapter(this);
        projects.add(new Project("Семья"));
        projects.get(0).addTodo("Купить молоко",false);
        projects.get(0).addTodo("Заменить масло в двигателе до 23 апреля", true);
        projects.get(0).addTodo("Отправить письмо бабушке",false);
        projects.get(0).addTodo("Заплатить за квартиру",false);
        projects.get(0).addTodo("Забрать обувь из ремонта",false);
        projects.get(0).addTodo("Помыть посуду",false);
        projects.get(0).addTodo("Открыть отчет",false);

        projects.add(new Project("Работа"));
        projects.get(1).addTodo("Позвонить заказчику");
        projects.get(1).addTodo("Отправить документы",false);
        projects.get(1).addTodo("Заполнить отчет",false);

        projects.add(new Project("Прочее"));
        projects.get(2).addTodo("Позвонить другу",true);
        projects.get(2).addTodo("Подготовиться к поездке",true);
        projects.get(2).addTodo("Купить носки",false);
        projects.get(2).addTodo("Забрать вещи",false);

        for(int i=0; i< projects.size(); i++){
            mAdapter.addSectionHeaderItem(projects.get(i).getTitle());
            for(int j=0; j<projects.get(i).getTodosCount(); j++){
                mAdapter.addItem(projects.get(i).getTodoAtIndex(j).getText(),
                        projects.get(i).getTodoAtIndex(j).isCompleted());
            }
        }
        listView.setAdapter(mAdapter);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putStringArray("List", Project.getTitles(projects));
                Intent addTodo = new Intent(MainActivity.this, AddTodo.class);
                addTodo.putExtras(b);
                startActivityForResult(addTodo, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        projectId = data.getIntExtra("id", projectId);
        todoName = data.getStringExtra("name");
        mAdapter.addItem(todoName,projectId);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
