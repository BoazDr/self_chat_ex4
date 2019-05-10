package com.boaz.dragonski.mychat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recycler_view;
    private Messages messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = findViewById(R.id.editText);
        messages = ((SelfChat) getApplicationContext()).getMessages();

        final RecycleViewUtils.RowAdapter rowAdapter = new RecycleViewUtils.RowAdapter(messages, this);
        rowAdapter.submitList(messages.getMessageList());
        recycler_view = findViewById(R.id.recyclerView);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(rowAdapter);

        findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                if (message.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Realy?! Empty message? Try again...", Toast.LENGTH_SHORT).show();
                    return;
                }
                messages.addMessage(new OneMessage(message));
                editText.getText().clear();
                rowAdapter.notifyDataSetChanged();
                recycler_view.scrollToPosition(messages.getMessageList().size() - 1);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
