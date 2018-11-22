package com.i4vine.ryufragment;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.i4vine.remap.RemapTest;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.Policy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class PopupPreview extends Activity {
    PreviewThread previewThread;
    public final PreviewHandler mHandler = new PreviewHandler(this);
    FrameLayout previewFrame;
    CameraPreview cameraView;
    ImageView popup_preview_close;
    TextView previewCnt;
    Button capture;
    Button torch;
    private Camera mCamera = null;
    private SurfaceHolder mHolder;
    private static final String TAG = "CameraPreview";
    //Camera camera;
    int sIdx = 0;
    //  private SurfaceHolder mHolder;

    TimerTask t_preview;
    boolean test =true;
    int preview_cnt = 3;
    Timer preview_timer;

    /* for camera calibration */
    private static final int CAMERA_MATRIX_ROWS = 3;
    private static final int CAMERA_MATRIX_COLS = 3;
    private static final int DISTORTION_COEFFICIENTS_SIZE = 4;
  //  Mat mK,mD, mx1, my1;

    boolean screen_mode = false;
    boolean torch_mode = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        //   layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        //   layoutParams.dimAmount = 1.0f;
        //   layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        getWindow().setAttributes(layoutParams);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        // getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        // Hide navigation bar
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOption = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        newUiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOption);
        // Hide Status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.popup_preview);

        checkDangerousPermissions();
        previewFrame = (FrameLayout) findViewById(R.id.previewFrame);
        cameraView = new CameraPreview(getApplicationContext());
        previewFrame.addView(cameraView);

        popup_preview_close = (ImageView) findViewById(R.id.popup_preview_close);

        capture = (Button)findViewById(R.id.capture);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCapture(view);
            }
        });
        previewCnt = (TextView)findViewById(R.id.previewCnt);
        torch = (Button)findViewById(R.id.torch);


        torch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                    if(mCamera != null){
                        Camera.Parameters parameters = mCamera.getParameters();
                        if(torch_mode == false) {
                            parameters.setFlashMode(parameters.FLASH_MODE_TORCH);
                            mCamera.setParameters(parameters);
                            torch.setText("Torch OFF");
                            torch_mode = true;
                        }else {
                            parameters.setFlashMode(parameters.FLASH_MODE_OFF);
                            mCamera.setParameters(parameters);
                            torch.setText("Torch ON");
                            torch_mode = false;
                        }
                    }


                }
                return true;//false;
            }
        });




        popup_preview_close.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked() == motionEvent.ACTION_DOWN) {
                    finish();
                }
                return true;//false;
            }
        });
     /*   String folder = "Capture";
        String path = Environment.getExternalStorageDirectory().getPath();
        String subpath = path +"/"+folder+"/";
        String text = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder;
        File directory = new File(text);

        File dirs = new File(Environment.getExternalStorageDirectory(), folder);
       if(dirs.exists()){
            dirs.mkdir();
            Log.d("Camera test", "Directory created");
        }*/

  //     cameraCapture(true);



       this.capture_start();


     //   t_preview = timerTaskMaker();

     //   preview_timer = new Timer();
   //     preview_timer.schedule(t_preview, 0, 1000);



    }

    private void capture_start(){
        capture.performClick();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
     /*   if(t_preview != null) {
            t_preview.cancel();
            t_preview = null;
        }*/

    }



    private void handlerMessage(Message msg){
        if(preview_cnt == 0) {
            preview_cnt = -1;
            previewCnt.setText("");
            previewCnt.setVisibility(previewCnt.INVISIBLE);

            mCamera.takePicture(null,null, mPicture);
           // cameraCapture(true);

        }else if(preview_cnt < 0){

        }else {
            if(preview_cnt == 3){
              //  cameraCapture(false);

            }
          //  previewThread.setRunning(true);
            previewCnt.setVisibility(previewCnt.VISIBLE);
            previewCnt.setText(Integer.toString(preview_cnt));
            preview_cnt--;
        }



    }

    @Override
    protected void onStart() {
        super.onStart();
        previewThread = new PreviewThread();
        previewThread.setRunning(true);
        previewThread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        boolean retry = true;
        previewThread.setRunning(false);
        while(retry){
            try {
                previewThread.join();
                retry = false;
            }catch(InterruptedException e){
                e.printStackTrace();
            }

        }
    }
/*
    public TimerTask timerTaskMaker() {
        TimerTask tempTask = new TimerTask() {
            @Override
            public void run() {
                int ret;
                Log.d("preview timer","============="+Integer.toString(preview_cnt));
                if(preview_cnt > 0) {

                    previewCnt.setText(preview_cnt);

                    cameraCapture(true);
                }
                preview_cnt--;
                if(preview_cnt == 0){
                    cameraCapture(true);
                    test = false;

                    t_preview.cancel();
                    t_preview=null;
                }




            }
        };
        return tempTask;
    }

*/
    public void onCapture(View view) {
     //   t_preview = timerTaskMaker();

    //    preview_timer.schedule(t_preview, 0, 1000);
   //     test = false;
   //     previewThread.setRunning(true);
   //     previewThread.start();

        preview_cnt = 3;
       // cameraCapture(true);

    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        String folder = "Capture";
        //       String resize_folder = "saveImg";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date currentTime_1 = new Date();
        String dateString = formatter.format(currentTime_1);
        File dirs = new File(Environment.getExternalStorageDirectory(), folder);
        if (!dirs.exists()) {
            dirs.mkdir();
            Log.d("Camera test", "Directory created");
        }


        String path = Environment.getExternalStorageDirectory().getPath();


        path = path + "/" + folder + "/";
        File file = new File(path, dateString + ".jpg");

        return file;
    }

    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            mCamera.startPreview();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            File pictureFile = getOutputMediaFile(1);
            if (pictureFile == null) {
                Log.d(TAG, "Error creating media file, check storage permissions");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

         //       fos.write(bytes);
                fos.close();
         //       MediaStore.Images.Media.insertImage(getContentResolver(), pictureFile.getAbsolutePath(), pictureFile.getName(), pictureFile.getName());

            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }

    //        Uri outUri = getOutputMediaFileUri(1);
    //        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, outUri));

      //      mCamera.startPreview();
            Intent pre = new Intent(getApplicationContext(), PopupPhoto.class);
            pre.putExtra("data", 0);
            startActivity(pre);
        }
    };



    public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
     //   private Camera mCamera = null;
      //  private SurfaceHolder mHolder;

        public CameraPreview(Context context) {
            super(context);

            mHolder = getHolder();
            mHolder.addCallback(this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        public void surfaceCreated(SurfaceHolder holder) {
            mCamera = Camera.open();
            try {
                mCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                Log.e(TAG, "Error setting camera preview" + e);
            }
        }

        public void surfaceDestroyed(SurfaceHolder holer) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            if (mHolder.getSurface() == null) {
                return;
            }
            Camera.Parameters parameters = mCamera.getParameters();
            if(screen_mode == true) {
                parameters.setPreviewSize(720, 400);

             //   mCamera.setParameters(parameters);
            }
            Log.d("===============","torch on");

            parameters.setFlashMode(parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(parameters);
            torch.setText("Torch OFF");
            torch_mode = true;

            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();
        }
    }



    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            //    Toast.makeText(getApplicationContext(), "PERMISSION_GRANTED", Toast.LENGTH_SHORT).show();
        } else {
    //        Toast.makeText(getApplicationContext(), "!PERMISSION_GRANTED", Toast.LENGTH_SHORT).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
    //            Toast.makeText(getApplicationContext(), "should show request permission rationale", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //           Toast.makeText(getApplicationContext(), permissions[i] + "Permission_granted", Toast.LENGTH_SHORT).show();
                } else {
           //         Toast.makeText(getApplicationContext(), permissions[i] + "!Permission_granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

/*
    private class SaveImageTask extends AsyncTask<byte[], Void, Void>{
        @Override
        protected Void doInBackground(byte[]... bytes) {
            FileOutputStream outStream = null;
            try{
                String folder = "Capture";
                String path = Environment.getExternalStorageDirectory().getPath();
                path = path + "/" + folder + "/";

                String fileName = String.format("%d.jpg",System.currentTimeMillis());
                File outputFile = new File(path, fileName);

                outStream = new FileOutputStream(outputFile);
                outStream.write(bytes[0]);
                outStream.flush();
                outStream.close();

                mCamera.startPreview();

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(Uri.fromFile(outputFile));
                sendBroadcast(mediaScanIntent);

                try{
                    mCamera.setPreviewDisplay(mHolder);
                    mCamera.startPreview();

                }catch(Exception e){
                    Log.e("PopupPreview", "Error Starting camera preview");
                }


            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }


    Camera.PictureCallback jpegCallback = (new Camera.PictureCallback(){
        public void onPictureTaken(byte[] data, Camera camera){
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] currentData = stream.toByteArray();

            new PopupPreview.SaveImageTask().execute(currentData);
        }
    });
    */



    public class PreviewThread extends Thread{
        boolean running = false;
        void setRunning(boolean b){
            running = b;
        }

        @Override
        public void run() {
            //super.run();
            while(running){
                try{
                    sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }



                if(preview_cnt <= 3) {
                    mHandler.sendMessage(mHandler.obtainMessage());
                }else  {
                  //  setRunning(false);
                    test = false;
                   // cameraCapture(true);
                }

            }
        }
    }


    public static class PreviewHandler extends Handler{
        public final WeakReference<PopupPreview> mActivity;
        public PreviewHandler(PopupPreview activity){
            mActivity = new WeakReference<PopupPreview>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            PopupPreview activity = mActivity.get();
            if(activity != null){
                activity.handlerMessage(msg);
            }
        }
    }



}
