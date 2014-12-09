package com.notepad.home.notepad;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.notepad.home.DB.ImageDb;
import com.notepad.home.adpater.NoteAdapter;
import com.notepad.home.util.EditViewManager;
import com.origamilabs.library.views.StaggeredGridView;


public class HomeActivity extends Activity {
    ActionBar actionBar=null;
    private ImageDb db=new ImageDb(this);
    private EditViewManager evm=new EditViewManager(db);
    private StaggeredGridView staggeredGridView=null;
    private final String editAction="com.notepad.home.edit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        actionBar=getActionBar();
        staggeredGridView=(StaggeredGridView)findViewById(R.id.sta_noteList);
        staggeredGridView.setAdapter(new NoteAdapter(this));
        staggeredGridView.setOnItemClickListener(new StaggeredGridView.OnItemClickListener() {
            @Override
            public void onItemClick(StaggeredGridView parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setClass(HomeActivity.this,AddActivity.class);
                intent.putExtra("id", id);
                Log.v("id", id+"");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        staggeredGridView.setAdapter(new NoteAdapter(this));
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            if (id == R.id.action_add) {
                Intent intent=new Intent();
                intent.setClass(this,AddActivity.class);
                startActivity(intent);
            }
            return super.onOptionsItemSelected(item);
    }
}
