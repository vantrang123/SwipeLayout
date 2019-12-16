package com.trangdv.swipelayoutme;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LeftActivity extends AppCompatActivity implements LeftAdapter.SwipeListener {
    private LeftAdapter leftAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lleft);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        leftAdapter = new LeftAdapter(getItems(), this);
        recyclerView.setAdapter(leftAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<String> getItems() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            items.add("item " + i);
        }
        return items;
    }

    @Override
    public void onCloseLastSwipe(int position) {
        leftAdapter.notifyItemChanged(position);
    }
}
