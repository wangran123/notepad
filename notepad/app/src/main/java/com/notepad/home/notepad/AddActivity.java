package com.notepad.home.notepad;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.notepad.home.DB.ImageDb;
import com.notepad.home.util.EditViewManager;
import com.notepad.home.view.EditView;
import com.notepad.home.view.HandWritingView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class AddActivity extends Activity {

    private HandWritingView mhandWritingView;

    private View delete=null,confirm=null;
    private ImageDb db=new ImageDb(this);
    private EditViewManager evm=new EditViewManager(db);
    private int currentId=0;
    private MenuItem save=null;
    private EditView editView=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        evm.setContext(this);
        editView=(EditView)findViewById(R.id.editview);
        editView.initCanvas(width,height);
        mhandWritingView=(HandWritingView)findViewById(R.id.handwrintview);

        delete=findViewById(R.id.deletebutton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editView.delete();
                editView.invalidate();
            }
        });
        evm.setHwv(mhandWritingView);
        mhandWritingView.setEvm(evm);
        List<Map<String, Object>>  noteHistory=db.getData();

        long id=getIntent().getLongExtra("id",-1);
        if(id!=-1){
            Map<String,Object> noteMap=noteHistory.get((int)id);
            Bitmap bitmap=(Bitmap)noteMap.get(ImageDb.T_BLOB);
            editView.load(bitmap);
            editView.setSql_id((int)id);
        }
        else
           editView.setSql_id(noteHistory.size());
        mhandWritingView.setEditView(editView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_list, menu);

        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mhandWritingView.init();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_save) {
            Bitmap b=editView.getmBitmap();

            long sqlId=evm.updateData(editView.getSql_id(),b,editView.getBgcolor());
            Log.v("sqlid",sqlId+"");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
