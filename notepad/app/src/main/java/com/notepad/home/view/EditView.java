package com.notepad.home.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.notepad.home.notepad.R;

/**
 * Created by wr on 2014/11/3.
 */
public class EditView extends View{
    private HandWritingView hdv=null;
    private Bitmap mBitmap=null;
    private Canvas canvas=null;
    private int x=0,y=0;
    private Paint bitmapPaint=null;
    private int xSpace=5,ySpace=5;
    private int width;
    private int height;
    private int perLineWords=10;
    private int lines=10;
    private Matrix m=new Matrix();
    private int wordWidth=0, wordHeight=0;
    private Paint cleanPaint=new Paint();
    private boolean isDrawTime=true;
    private long refreshTime=700;
    private long lastRefreshTime= SystemClock.uptimeMillis();
    private int cursorWidth=5;
    private int maxY=0;


    private int sql_id=0;


    private int bgcolor= getResources().getColor(R.color.yellow);
    public EditView(Context context) {
        super(context);
    }

    public EditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap,0,0,new Paint());
        drawCursor(canvas);
        super.onDraw(canvas);
    }

    private void drawCursor(Canvas canvas) {
          if(isDrawTime){
              canvas.drawRect(x,y,x+cursorWidth,y+wordHeight,new Paint());
          }
          else{

          }
          long time=SystemClock.uptimeMillis();
        if(time-lastRefreshTime>refreshTime){
            isDrawTime=!isDrawTime;
            lastRefreshTime=time;
            postInvalidateDelayed(refreshTime,x,y,x+xSpace,y+wordHeight);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                moveCursor(x,y);
                invalidate();
                break;
        }
        return false;
    }
    public int getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(int bgcolor) {
        this.bgcolor = bgcolor;
    }
    public int getSql_id() {
        return sql_id;
    }

    public void setSql_id(int sql_id) {
        this.sql_id = sql_id;
    }
    public HandWritingView getHdv() {
        return hdv;
    }

    public void setHdv(HandWritingView hdv) {
        this.hdv = hdv;
    }

    public void initCanvas(int width,int height){
        this.width=width;
        this.height=height;
        mBitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        canvas=new Canvas(mBitmap);
        bitmapPaint=new Paint(Paint.DITHER_FLAG);

        this.setBackgroundColor(bgcolor);
        wordWidth=width/perLineWords;
        wordHeight=height/lines;
        cleanPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public void input(Bitmap b){
        if(x>width){
            x=0;
            y+=wordHeight+ySpace;
        }
       canvas.drawBitmap(Bitmap.createScaledBitmap(b,width/perLineWords,height/lines,true),x,y,bitmapPaint);
       maxY=y>maxY?y:maxY;
       x+=wordWidth+xSpace;
    }

    public void delete(){
        Rect erase=new Rect();
        if(x-wordWidth<0) {
            if(y-wordHeight>0) {
                x = width - wordWidth;
                y = y - wordHeight - ySpace;
                erase.set(x, y, width, y + wordHeight);
            }
            else{
                x=0;
                y=0;
            }
        }
        else{
            x=x-wordWidth-xSpace;
            erase.set(x, y, x + wordWidth, y + wordHeight);
        }
        canvas.drawRect(erase,cleanPaint);
    }
    public void load(Bitmap b){
        canvas.drawBitmap(b,0,0,bitmapPaint);
    }
    public void moveCursor(float x,float y){
        int i=(int)(x/(wordWidth+xSpace));
        int j=(int)(y/(wordHeight+ySpace));

        this.x=i*(wordWidth+xSpace);
        this.y=j*(wordHeight+ySpace);
    }

    public Bitmap getmBitmap() {
        Bitmap m=Bitmap.createBitmap(mBitmap,0,0,mBitmap.getWidth(),maxY+wordHeight);
        return m;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
