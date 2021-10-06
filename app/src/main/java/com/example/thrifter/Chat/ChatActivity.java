package com.example.thrifter.Chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thrifter.MainActivity;
import com.example.thrifter.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ArrayList<String> chatItems = new ArrayList<String>();

    private ArrayAdapter<String> adapter;

    private Button returnMenu;

    private ListView lvList1;
    String[] chats = new String[]{
            "Example1",
            "Example2"
    };

    private List<String> chat_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chat_list = new ArrayList<String>(Arrays.asList(chats));

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, chat_list);

        lvList1 = (ListView) findViewById(R.id.lvList);

        lvList1.setAdapter(adapter);

        lvList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(ChatActivity.this, "Test", Toast.LENGTH_SHORT).show();

            }
        });

        lvList1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(ChatActivity.this, "Test1", Toast.LENGTH_SHORT).show();

                return false;

            }
        });

        returnMenu = (Button) findViewById(R.id.returnChatSwipe);
        returnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ChatActivity.this, MainActivity.class);
                startActivity(intent);
                return;
            }
        });

    }
}