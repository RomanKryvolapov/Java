package com.romankryvolapov.cameraascii;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import android.hardware.Camera;

public class MainActivity extends AppCompatActivity {

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    TextView textView;

    Bitmap bitmap;

    private Camera mCamera;
    private CameraPreview mPreview;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }


        textView = findViewById(R.id.textView);
        textView.setTextSize(0.5f);



//                while (true) {
//
//                    bitmap = getBitmap(imageProxy, rotationDegrees);
//
//                    textView.setText(TextConverter(bitmap));
//
//                }

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);





    }

//    private Bitmap getBitmap(Image image) {
//
//        final ByteBuffer yuvBytes = this.imageToByteBuffer(image);
//
//        // Convert YUV to RGB
//
//        final RenderScript rs = RenderScript.create(this);
//
//        final Bitmap bitmap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
//        final Allocation allocationRgb = Allocation.createFromBitmap(rs, bitmap);
//
//        final Allocation allocationYuv = Allocation.createSized(rs, Element.U8(rs), yuvBytes.array().length);
//        allocationYuv.copyFrom(yuvBytes.array());
//
//        ScriptIntrinsicYuvToRGB scriptYuvToRgb = ScriptIntrinsicYuvToRGB.create(rs, Element.U8_4(rs));
//        scriptYuvToRgb.setInput(allocationYuv);
//        scriptYuvToRgb.forEach(allocationRgb);
//
//        allocationRgb.copyTo(bitmap);
//
//        // Release
//
////        bitmap.recycle();
//
//
//        return bitmap;
//    }

//    private ByteBuffer byteToByteBuffer(final Image image) {
//        final Rect crop = image.getCropRect();
//        final int width = crop.width();
//        final int height = crop.height();
//
//        final Image.Plane[] planes = image.getPlanes();
//        final byte[] rowData = new byte[planes[0].getRowStride()];
//        final int bufferSize = width * height * ImageFormat.getBitsPerPixel(ImageFormat.YUV_420_888) / 8;
//        final ByteBuffer output = ByteBuffer.allocateDirect(bufferSize);
//
//        int channelOffset = 0;
//        int outputStride = 0;
//
//        for (int planeIndex = 0; planeIndex < 3; planeIndex++) {
//            if (planeIndex == 0) {
//                channelOffset = 0;
//                outputStride = 1;
//            } else if (planeIndex == 1) {
//                channelOffset = width * height + 1;
//                outputStride = 2;
//            } else if (planeIndex == 2) {
//                channelOffset = width * height;
//                outputStride = 2;
//            }
//
//            final ByteBuffer buffer = planes[planeIndex].getBuffer();
//            final int rowStride = planes[planeIndex].getRowStride();
//            final int pixelStride = planes[planeIndex].getPixelStride();
//
//            final int shift = (planeIndex == 0) ? 0 : 1;
//            final int widthShifted = width >> shift;
//            final int heightShifted = height >> shift;
//
//            buffer.position(rowStride * (crop.top >> shift) + pixelStride * (crop.left >> shift));
//
//            for (int row = 0; row < heightShifted; row++) {
//                final int length;
//
//                if (pixelStride == 1 && outputStride == 1) {
//                    length = widthShifted;
//                    buffer.get(output.array(), channelOffset, length);
//                    channelOffset += length;
//                } else {
//                    length = (widthShifted - 1) * pixelStride + 1;
//                    buffer.get(rowData, 0, length);
//
//                    for (int col = 0; col < widthShifted; col++) {
//                        output.array()[channelOffset] = rowData[col * pixelStride];
//                        channelOffset += outputStride;
//                    }
//                }
//
//                if (row < heightShifted - 1) {
//                    buffer.position(buffer.position() + rowStride - length);
//                }
//            }
//        }
//
//        return output;
//    }

    private String TextConverter(Bitmap bitmap) {

        StringBuilder temp = new StringBuilder();

        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {

                int grey = (int) (Color.red(bitmap.getPixel(x, y)) * 0.299 + Color.green(bitmap.getPixel(x, y)) * 0.587 + Color.blue(bitmap.getPixel(x, y)) * 0.114);

//                Log.i("status", "grey - " + grey);

                if (0 <= grey && grey <= 21) {
                    temp.append("-");
                }
                if (22 <= grey && grey <= 42) {
                    temp.append(":");
                }
                if (43 <= grey && grey <= 63) {
                    temp.append("!");
                }
                if (64 <= grey && grey <= 84) {
                    temp.append("=");
                }
                if (85 <= grey && grey <= 105) {
                    temp.append("+");
                }
                if (106 <= grey && grey <= 126) {
                    temp.append(">");
                }
                if (127 <= grey && grey <= 147) {
                    temp.append("?");
                }
                if (148 <= grey && grey <= 168) {
                    temp.append("#");
                }
                if (169 <= grey && grey <= 189) {
                    temp.append("%");
                }
                if (190 <= grey && grey <= 210) {
                    temp.append("&");
                }
                if (211 <= grey && grey <= 231) {
                    temp.append("$");
                }
                if (232 <= grey && grey <= 255) {
                    temp.append("@");
                }

            }
            temp.append("\n");
        }


        return temp.toString();
    }




    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback  {
        private SurfaceHolder mHolder;
        private Camera mCamera;

        public CameraPreview(Context context, Camera camera) {
            super(context);
            Log.d("status", "CameraPreview");
            mCamera = camera;

            if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                Log.d("status", "setDisplayOrientation(90)");
                mCamera.setDisplayOrientation(90);
            } else {
                Log.d("status", "setDisplayOrientation(0)");
                mCamera.setDisplayOrientation(0);
            }

            // Install a SurfaceHolder.Callback so we get notified when the
            // underlying surface is created and destroyed.
            mHolder = getHolder();
            mHolder.addCallback(this);
            // deprecated setting, but required on Android versions prior to 3.0
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        }

        public void surfaceCreated(SurfaceHolder holder) {
            // The Surface has been created, now tell the camera where to draw the preview.

            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
                Log.d("status", "surfaceCreated");
            } catch (IOException e) {
                Log.d("status", "Error setting camera preview: " + e.getMessage());
            }

            try {
                mCamera.setPreviewCallback(this);
                mCamera.startPreview();
            } catch (Exception e){
                Log.d("status", "Error starting camera preview: " + e.getMessage());
            }

        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // empty. Take care of releasing the Camera preview in your activity.
            Log.d("status", "surfaceDestroyed");
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            // If your preview can change or rotate, take care of those events here.
            // Make sure to stop the preview before resizing or reformatting it.

            Log.d("status", "surfaceChanged");

            if (mHolder.getSurface() == null){
                // preview surface does not exist
                return;
            }

            // stop preview before making changes
            try {
                mCamera.stopPreview();
                Log.d("status", "mCamera.stopPreview()");
            } catch (Exception e){
                // ignore: tried to stop a non-existent preview
            }



            // set preview size and make any resize, rotate or
            // reformatting changes here

            // start preview with new settings
            try {
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();
                Log.d("status", "mCamera.startPreview()");
            } catch (Exception e){
                Log.d("status", "Error starting camera preview: " + e.getMessage());
            }




        }


        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {



//            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//            Log.d("status", "onPreviewFrame");
//            mHolder.
//            mCamera.get
            Camera.Parameters parameters = camera.getParameters();
            int width = parameters.getPreviewSize().width;
            int height = parameters.getPreviewSize().height;
            ByteArrayOutputStream outstr = new ByteArrayOutputStream();
            Rect rect = new Rect(0, 0, width, height);
            YuvImage yuvimage=new YuvImage(data,ImageFormat.NV21,width,height,null);
            yuvimage.compressToJpeg(rect, 100, outstr);
            Bitmap bmp = BitmapFactory.decodeByteArray(outstr.toByteArray(), 0, outstr.size());
            Matrix matrix = new Matrix();
            if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                matrix.postRotate(90);
            }
            Bitmap rotatedBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
//            imgView1.setImageBitmap(bmp);
            int q = 0;
//            Log.d("status", "rotatedBitmap Height - " + rotatedBitmap.getHeight() + " Width - " + rotatedBitmap.getWidth());

            String temp = TextConverter(rotatedBitmap);

            textView.setText(temp);



        }
    }








}


