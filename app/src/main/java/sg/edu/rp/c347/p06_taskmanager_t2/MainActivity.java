package sg.edu.rp.c347.p06_taskmanager_t2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAddTask;
    ListView lvTask;
    ArrayList<String> al = new ArrayList<String>();
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddTask = findViewById(R.id.btnAddTask);



        
        lvTask = findViewById(R.id.lvTasks);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddTaskActivity.class);

                startActivityForResult(i,9);
            }
        });

        DBHelper dbh = new DBHelper(MainActivity.this);
        ArrayList<String> query = dbh.getAllTask();
        al.addAll(query);
        dbh.close();
        aa = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, al);

        lvTask.setAdapter(aa);


    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9) {
            al.clear();
            DBHelper dbh = new DBHelper(MainActivity.this);
            ArrayList<String> query = dbh.getAllTask();
            al.addAll(query);
            dbh.close();
            aa.notifyDataSetChanged();

        }

    }
}
