package com.notepad.home.view;

import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;

import com.notepad.home.paint.EasyPaint;
import com.notepad.home.util.EditViewManager;

/**
 * Created by wr on 2014/11/3.
 */
public class HandWritingView extends View{
    private Bitmap mBitmap=null;
    private Canvas mCanvas=null;
    private EasyPaint ep=new EasyPaint();
    private Paint cleanPaint=new Paint();
    private boolean isTouchUp=true;
    private HandWritingView hdv=null;

    public void setEditView(EditView editView) {
        this.editView = editView;
    }

    private EditView editView=null;
    private CountDownTimer cdt=new CountDownTimer(1000,1000) {//两个参数，前一个指倒计时的总时间，后一个指多长时间倒数一下。

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            editView.input(mBitmap);
            editView.invalidate();
            mCanvas.drawPaint(cleanPaint);
            invalidate();
            this.cancel();
        }
    };

    public void clean(){
        mCanvas.drawPaint(cleanPaint);
        invalidate();
    }
    private EditViewManager evm=null;

    public EditViewManager getEvm() {
        return evm;
    }

    public void setEvm(EditViewManager evm) {
        this.evm = evm;
    }
    public HandWritingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public HandWritingView(Context context) {
        super(context);

    }

    public HandWritingView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public HandWritingView getHdv() {
        return hdv;
    }

    public void setHdv(HandWritingView hdv) {
        this.hdv = hdv;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public void init(){
        int width=this.getWidth();
        int height=this.getHeight();
        cleanPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mBitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        mCanvas=new Canvas(mBitmap);
        ep.initPaint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mBitmap!=null) {
            canvas.drawBitmap(mBitmap, 0, 0, new Paint());
        }
        if(!isTouchUp)
        ep.onDraw(canvas);
        super.onDraw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();
        isTouchUp=false;
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                    ep.onActionDown(x,y);
                    invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                    ep.onActionMove(x,y);
                    invalidate();

                break;
            case MotionEvent.ACTION_UP:
                    ep.onActionUp(x,y);
                    ep.onDraw(mCanvas);
                    invalidate();
                    isTouchUp=true;
                    cdt.start();
                break;

        }

        return true;
    }
}
