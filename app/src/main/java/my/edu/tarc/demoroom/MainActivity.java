package my.edu.tarc.demoroom;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String DATABASE_NAME = "user_db";
    private static final int INSERT_USER_REQUEST = 1;
    private AppDatabase userDatabase;

    private List<User> mUserList;
    private RecyclerView recyclerView;
    private UserListAdapter userListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
                startActivityForResult(intent, INSERT_USER_REQUEST);
            }
        });

        createDB();
        loadUsers();
    }

    private void loadUsers() {
        //Get handler to the RecyclerView
        recyclerView = findViewById(R.id.recyclerViewUser);

        mUserList = userDatabase.userDao().getAllUsers();

        //Create an adapter and supply data
        userListAdapter = new UserListAdapter(this, mUserList);

        recyclerView.setAdapter(userListAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createDB(){
        userDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == INSERT_USER_REQUEST && resultCode == RESULT_OK && data != null){
            User user = new User(data.getStringExtra(InsertActivity.INSERT_PHONE),
                    data.getStringExtra(InsertActivity.INSERT_FIRST),
                    data.getStringExtra(InsertActivity.INSERT_LAST));
            addUser(user);
        }else{
            Toast.makeText(getApplicationContext(), R.string.empty_input, Toast.LENGTH_LONG).show();
        }
    }

    private void addUser(final User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                userDatabase.userDao().insertUser(user);
            }
        }).start();
    }
}
