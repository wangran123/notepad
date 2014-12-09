package com.notepad.home.adpater;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.notepad.home.DB.ImageDb;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by wr on 2014/12/9.
 */
public class NoteAdapter extends BaseAdapter{
    private Context mContext;
    ImageDb db=null;
    List<Map<String, Object>> noteList=null;
    public NoteAdapter(Context c){
        mContext=c;
        db=new ImageDb(mContext);
        noteList=db.getData();
        ImageDb.setTotalNum(noteList.size());
    }
    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return (Long)noteList.get(i).get(ImageDb.T_ID);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f,0.5f);
        ImageView imageView=new ImageView(mContext);
        Map<String,Object> noteMap=noteList.get(i);
        Bitmap b = (Bitmap) noteMap.get(ImageDb.T_BLOB);
        Bitmap image=Bitmap.createBitmap(b,0,0,b.getWidth(),b.getHeight(),matrix,true);
        imageView.setImageBitmap(image);
        return imageView;
    }
}
