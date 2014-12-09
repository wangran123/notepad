package com.notepad.home.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wr on 2014/11/5.
 */
public class ImageDb extends SQLiteOpenHelper{
    private final static int DB_VERSION=1;
    private final static String DB_NAME="image_db";

    private String TABLE_NAME="image_table";
    public static String T_ID = "_id";//字段名
    public static String T_BLOB = "T_BLOB";//字段名
    private static int totalNum=0;
    private String TABLE_IMAGE_CREATE = "Create table " + TABLE_NAME + "(" + T_ID
            + " INTEGER DEFAULT '1' NOT NULL PRIMARY KEY AUTOINCREMENT ,"
            + T_BLOB + " BLOB );";

    private String[] col = { T_ID, T_BLOB };

    public static int getTotalNum() {
        return totalNum;
    }

    public static void setTotalNum(int totalNum) {
        ImageDb.totalNum = totalNum;
    }

    public ImageDb(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public ImageDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    /**创建数据**/
    public Long createData(long i,Bitmap bmp) {
        ContentValues initValues = new ContentValues();
        Long id = i;


        initValues.put(T_ID,id);
        initValues.put(T_BLOB, compressBitmap(bmp));//以字节形式保存

        SQLiteDatabase db = getDatabaseWrit();
        db.insert(TABLE_NAME, null, initValues);//保存数据
        db.close();

        Log.i("Image ", "create done.");
        return id;
    }

    public byte[] compressBitmap(Bitmap b){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
/**
 * Bitmap.CompressFormat.JPEG 和 Bitmap.CompressFormat.PNG
 * JPEG 与 PNG 的是区别在于 JPEG是有损数据图像，PNG使用从LZ77派生的无损数据压缩算法。
 * 这里建议使用PNG格式保存
 * 100 表示的是质量为100%。当然，也可以改变成你所需要的百分比质量。
 * os 是定义的字节输出流
 *
 * .compress() 方法是将Bitmap压缩成指定格式和质量的输出流
 */
       b.compress(Bitmap.CompressFormat.PNG, 100, os);
        return  os.toByteArray();
    }

    public List<Map<String, Object>> getData() {

        List<Map<String, Object>> list = null;

        SQLiteDatabase db = getDatabaseRead();
        Cursor cursor = db.query(TABLE_NAME, col, null, null, null, null, null);//数据的查询
        HashMap<String, Object> bindData = null;
        list = new ArrayList<Map<String, Object>>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            bindData = new HashMap<String, Object>();
            bindData.put(T_ID, cursor.getLong(0));
/**得到Bitmap字节数据**/
            byte[] in = cursor.getBlob(1);
/**
 * 根据Bitmap字节数据转换成 Bitmap对象
 * BitmapFactory.decodeByteArray() 方法对字节数据，从0到字节的长进行解码，生成Bitmap对像。
 **/
            Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
            bindData.put(T_BLOB, bmpout);

            list.add(bindData);
        }
        cursor.close();
        db.close();
        Log.i("Image ", "get a Bitmap.");
        return list;
    }
    public void delete() {
        SQLiteDatabase db = getDatabaseWrit();
        db.delete(TABLE_NAME, null, null);//数据的删除
        db.close();
        Log.i("Image ", "delete all data.");
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_IMAGE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        String sql = " drop table " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
    private SQLiteDatabase getDatabaseRead() {
        return this.getReadableDatabase();
    }

    private SQLiteDatabase getDatabaseWrit() {
        return this.getWritableDatabase();
    }

    public int updateData(int currentId, Bitmap bitmap) {
        SQLiteDatabase db = getDatabaseRead();
        String selection=T_ID+" = "+currentId;
        Cursor cursor = db.query(TABLE_NAME, col, selection, null, null, null, null);
        if(cursor.getCount()==0){
            Log.i("Image","no data");
            createData(currentId,bitmap);
        }
        else{
            ContentValues values=new ContentValues();
            values.put(T_BLOB,compressBitmap(bitmap));
            String[] args={String.valueOf(currentId)};
            db=getWritableDatabase();
            return db.update(TABLE_NAME,values,T_ID+" =?",args);
        }
        return currentId;
    }
}
