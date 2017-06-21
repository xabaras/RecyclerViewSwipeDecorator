package it.xabaras.android.recyclerview.swipedecorator.sample;

import android.graphics.Canvas;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {

    private SampleRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // Set a layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // Create and set an adapter
        mAdapter = new SampleRecyclerViewAdapter(this);
        recyclerView.setAdapter(mAdapter);
        // Create and add a callback
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                try {
                    final int position = viewHolder.getAdapterPosition();
                    final String item = mAdapter.removeItem(position);
                    Snackbar snackbar = Snackbar.make(viewHolder.itemView, "Item " + (direction == ItemTouchHelper.RIGHT ? "deleted" : "archived") + ".", Snackbar.LENGTH_LONG);
                    snackbar.setAction(android.R.string.cancel, new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            try {
                                mAdapter.addItem(item, position);
                            } catch(Exception e) {
                                Log.e("MainActivity", e.getMessage());
                            }
                        }
                    });
                    snackbar.show();
                } catch(Exception e) {
                    Log.e("MainActivity", e.getMessage());
                }
            }

            // You must use @RecyclerViewSwipeDecorator inside the onChildDraw method
            @Override
            public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,float dX, float dY,int actionState, boolean isCurrentlyActive){
                new RecyclerViewSwipeDecorator.Builder(MainActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.recycler_view_item_swipe_left_background))
                        .addSwipeLeftActionIcon(R.drawable.ic_archive_white_24dp)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.recycler_view_item_swipe_right_background))
                        .addSwipeRightActionIcon(R.drawable.ic_delete_white_24dp)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            if ( item.getItemId() == R.id.actionRefresh ) {
                mAdapter.reloadItems();
                mAdapter.notifyDataSetChanged();
            }
        } catch(Exception e) {
            Log.e("MainActivity", e.getMessage());
        }
        return super.onOptionsItemSelected(item);
    }
}
