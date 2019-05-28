package sg.edu.rp.c347.p06_taskmanager_t2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    Button btnAddTask;
    ListView lvTask;

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
                startActivity(i);
            }
        });
    }
}
