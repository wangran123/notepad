package com.notepad.home.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;

import com.notepad.home.DB.ImageDb;
import com.notepad.home.notepad.R;
import com.notepad.home.paint.EasyPaint;
import com.notepad.home.view.EditView;
import com.notepad.home.view.HandWritingView;

import java.util.ArrayList;

/**
 * Created by wr on 2014/11/4.
 */
public class EditViewManager extends ArrayList<EditView>{


    private HandWritingView hwv=null;
    private Context context=null;
    private int nowEdit=0;
    private ImageDb db=null;

    public EditViewManager(ImageDb db) {
        this.db=db;
    }

    public int getNowEdit() {
        return nowEdit;
    }
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    public void setNowEdit(int nowEdit) {
        this.nowEdit = nowEdit;
    }
    public void createEditView(int width,int height,Bitmap b){
        if(context!=null) {
            EditView e=new EditView(context);
            e.setHdv(hwv);
            e.initCanvas(width,height);
            if(b!=null){
                e.load(b);
            }
            this.add(e);
        }
    }
    public void addEditView(EditView e){
        this.add(e);
    }
    public EditView getCurrentView(){
        return this.get(nowEdit);
    }
    public EditView getEditView(int i) throws NullPointerException{
        return this.get(i);
    }

    public HandWritingView getHwv() {
        return hwv;
    }

    public void setHwv(HandWritingView hwv) {
        this.hwv = hwv;
    }

    public void editNow(Bitmap b){
        EditView e=getCurrentView();
        e.input(b);
        e.invalidate();
    }

    public void deleteNow(){
        EditView e=getCurrentView();
        e.delete();
        e.invalidate();
    }
    public Bitmap getCurrentBitmap(){
        EditView e=getCurrentView();
        return e.getmBitmap();
    }

    public void setCurrentBitmap(Bitmap bitmap){
        EditView e=getCurrentView();
        e.setmBitmap(bitmap);
        e.invalidate();
    }
    public long createData(Bitmap bitmap,int backgroundcolor){
        Bitmap newBitmap=Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c=new Canvas(newBitmap);
        c.drawColor(backgroundcolor);
        c.drawBitmap(bitmap,0,0,new Paint());
        return db.createData(ImageDb.getTotalNum(),newBitmap);
    }
    public int updateData(int id,Bitmap bitmap,int backgroundcolor) {
        Bitmap newBitmap=Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c=new Canvas(newBitmap);
        c.drawColor(backgroundcolor);
        c.drawBitmap(bitmap,0,0,new Paint());
        return db.updateData(id,newBitmap);
    }
}
