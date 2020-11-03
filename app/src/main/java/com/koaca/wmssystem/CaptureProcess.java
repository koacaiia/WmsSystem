package com.koaca.wmssystem;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewDebug;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureProcess implements SurfaceHolder.Callback{
    private final MainActivity mainActivity;
    private final SurfaceHolder surfaceHolder;
    private Camera camera;

    public CaptureProcess(MainActivity mainActivity, SurfaceHolder surfaceHolder) {
        this.mainActivity = mainActivity;
        this.surfaceHolder = surfaceHolder;
    }

    public void capture() {
        CaptureCallback callback=new CaptureCallback();
        camera.takePicture(null,null,callback);

    }
    public class CaptureCallback implements Camera.PictureCallback{

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            OutputStream fos=null;
            Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
            WindowManager windowManager=mainActivity.getWindowManager();
            Display display=windowManager.getDefaultDisplay();
            int rotation=display.getRotation();
            int degree=0;
            switch(rotation){
                case Surface.ROTATION_0: degree = 90; break;
                case Surface.ROTATION_90: degree = 0; break;
                case Surface.ROTATION_180: degree = 270; break;
                case Surface.ROTATION_270: degree = 180; break;
            }

            bitmap=rotate(bitmap,degree);
            Bitmap dest=Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            String timeStamp1 = new SimpleDateFormat("yyyy년MM월dd일E요일").format(new Date());
            String timeStamp2 = new SimpleDateFormat("a_HH시mm분ss초").format(new Date());
            String name=mainActivity.textView.getText().toString();
            ContentResolver contentResolver=mainActivity.getContentResolver();
            Canvas cs=new Canvas(dest);
            Paint tPaint=new Paint();
            tPaint.setTextSize(100);
            tPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            tPaint.setColor(Color.WHITE);
            tPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            cs.drawBitmap(bitmap,0f,0f,null);
            ContentValues contentValues=new ContentValues();
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME,System.currentTimeMillis()+".jpg");
            contentValues.put(MediaStore.Images.Media.MIME_TYPE,"image/*");
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES+"/Fine");

            Uri imageUri=contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
            try {
                fos=contentResolver.openOutputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            camera.startPreview();
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Bitmap rotate(Bitmap bitmap, int degree) {
              Matrix matrix=new Matrix();
              matrix.postRotate(degree);
              return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        }
    }

    public void preView() {
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(surfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        camera=Camera.open();
        WindowManager windowManager=mainActivity.getWindowManager();
        Display display=windowManager.getDefaultDisplay();
        int rotation=display.getRotation();
        int degree=0;
        switch(rotation){
            case Surface.ROTATION_0: degree = 90; break;
            case Surface.ROTATION_90: degree = 0; break;
            case Surface.ROTATION_180: degree = 270; break;
            case Surface.ROTATION_270: degree = 180; break;
        }

        camera.setDisplayOrientation(degree);

        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}
