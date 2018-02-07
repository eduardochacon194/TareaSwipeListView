package com.example.laptop.tarea2swipelistview;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class App extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<listItem> items = null;
    private ListView lista            = null;
    private adapterListItem adapter   = null;
    private int cuantos = 19;
    private SwipeRefreshLayout swipe  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe);


        swipe = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_purple,
                android.R.color.holo_red_dark);

        lista    = (ListView)findViewById(R.id.list);
        items = new ArrayList<>();
        for (int i=0;i<cuantos;i++){
            items.add(new listItem("Titulo"+i,"Subtitulo"+1));
        }
        adapter = new adapterListItem(this,items);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    @Override
    public void onRefresh() {
        swipe.setRefreshing(true);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(lista.getChildCount()>0)
                    items.remove(--cuantos);
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);

            }
        },100);

    }


    class adapterListItem extends ArrayAdapter<listItem>{
       Context context;
        public adapterListItem(Context context, ArrayList<listItem> users) {
            super(context, 0, users);
            this.context = context;
        }
        public adapterListItem(@NonNull Context context, int resource) {
            super(context, resource);
        }
        public adapterListItem(Activity context){
            super(context, R.layout.item);
            this.context = context;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
          //  return super.getView(position, convertView, parent);
            View item = convertView;
            listItem user = getItem(position);

            if (item == null ){
                item= LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
            }
            TextView tvName = (TextView) item.findViewById(R.id.titulo);
            TextView tvHome = (TextView) item.findViewById(R.id.subtitulo);
            // Populate the data into the template view using the data object
            tvName.setText(user.getTitle());
            tvHome.setText(user.getSubtitle());
            // Return the completed view to render on screen
            return item;
        }

    }

}
