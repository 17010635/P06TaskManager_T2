package sg.edu.rp.c347.p06_taskmanager_t2;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    EditText etName, etDesc, etReminder;
    Button btnAdd, btnCancel;
    int reqCode = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        etReminder = findViewById(R.id.etReminder);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etName.getText().toString().equalsIgnoreCase("") || etDesc.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(AddTaskActivity.this,"Please fill in the fields above", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent i = new Intent();

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, 5);

                Intent intent = new Intent(AddTaskActivity.this,
                        ScheduledNotificationReceiver.class);
                intent.putExtra("name",etName.getText().toString());
                intent.putExtra("desc", etDesc.getText().toString());

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        AddTaskActivity.this, reqCode,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);


                DBHelper dbHelper = new DBHelper(AddTaskActivity.this);
                dbHelper.insertTask(etName.getText().toString(),etDesc.getText().toString());
                dbHelper.close();

                setResult(RESULT_OK, i);

                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });



    }
}
