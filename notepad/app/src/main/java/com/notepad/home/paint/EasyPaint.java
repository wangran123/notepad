package com.notepad.home.paint;

        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.Path;

/**
 * Created by wr on 2014/11/3.
 */
public class EasyPaint{
    private Paint p=new Paint();
    private Path path=new Path();
    private int color=Color.BLACK;
    private float width=10f;

    public  void initPaint(){
        p.setAntiAlias(true);
        p.setColor(color);
        p.setDither(true);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(width);
    }

    public void onActionDown(float x,float y){
        path.reset();
        path.moveTo(x,y);
    }

    public void onActionMove(float x,float y){
        path.lineTo(x,y);
    }

    public void onActionUp(float x,float y){
        path.lineTo(x,y);
    }

    public void onDraw(Canvas canvas){
        canvas.drawPath(path,p);
    }
}
