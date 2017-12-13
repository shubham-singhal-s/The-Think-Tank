package com.thethinktankmit.thethinktank;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;



public class SnapOn extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView buckysImageView;
    Bitmap bm2, loda, myBitmap=null;
    File imgFile;
    int flag=0;

    Uri mURI;

    int id,n=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snapon);

        if (Build.VERSION.SDK_INT >= 23) {
            if ((checkSelfPermission(android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED)&&
                    (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED)){
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 102);
            }

            else if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            }

            else if (checkSelfPermission(android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            }

            }


        // BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inScaled=false;
        TextView buckyButton = (TextView) findViewById(R.id.buckysButton);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rad);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                //ImageView test=(ImageView)findViewById(R.id.buckysImageView);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                setIMG();
                if(n==0){return;}
                else
                {
                    if (myBitmap!=null) {

                        ImageView myImage = (ImageView) findViewById(R.id.buckysImageView);
                        if (myBitmap.getHeight() > myBitmap.getWidth())
                            loda = overlayPot(bm2, myBitmap);
                        else
                            loda = overlayLand(bm2, myBitmap);
                        myImage.setImageBitmap(loda);
                    }
                }
            }
        });



//        boolean ch = true;

        bm2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.tran);


        buckysImageView = (ImageView) findViewById(R.id.buckysImageView);

        //Disable the button if the user has no camera
        if (!hasCamera())
            buckyButton.setEnabled(false);

        ImageView bb = (ImageView) findViewById(R.id.utton);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shhare();

            }
        });
    }

    //Check if the user has a camera
    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }


    public void shhare() {
        String pathofBmp = MediaStore.Images.Media.insertImage(getContentResolver(), loda, "title", null);
        Uri bmpUri = Uri.parse(pathofBmp);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, bmpUri);
        startActivity(Intent.createChooser(share, "Share Image"));
        /**
         final Intent emailIntent1 = new Intent(android.content.Intent.ACTION_SEND);
         emailIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         emailIntent1.putExtra(Intent.EXTRA_STREAM, bmpUri);
         emailIntent1.setType("image/png");
         startActivity(emailIntent1);*/

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if ((requestCode == 100) || (requestCode == 101) || (requestCode == 102)) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {

                Intent intent = new Intent(this, SnapOn.class);
                startActivity(intent);

            }

        }
    }

    public void setIMG(){
        RadioGroup rdg = (RadioGroup)findViewById(R.id.rad);
        flag=0;
        id=rdg.getCheckedRadioButtonId();
        switch (id){
            case R.id.r1: bm2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ted2); break;
            case R.id.r2: bm2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ted3); break;

            case R.id.r3: bm2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ted4); break;

            case R.id.r4: bm2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ted5); break;
            case R.id.r5: bm2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ted6); break;
            case R.id.r6: bm2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.tran); break;

        }
    }
    //Launching the camera
    private String pictureImagePath = "";

    public void launchCamera(View view) {
        //findViewById(R.id.diag).setVisibility(View.VISIBLE);
        setIMG();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(pictureImagePath);
        Uri outputFileUri = FileProvider.getUriForFile(
                this,
                getApplicationContext()
                        .getPackageName() + ".provider", file);
        mURI=outputFileUri;
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(cameraIntent,1);
    }

    public void launchGallery(View view){

        setIMG();
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
        startActivityForResult(galleryIntent, 2);
    }

    public void launchSave(View view) {

        if(flag==1){
            Toast.makeText(this, "The image has already been saved.", Toast.LENGTH_SHORT).show();
            return;}


        String date = new SimpleDateFormat("MMddHHmm").format(new Date());
        final String filename = "TTT_"+date+".jpeg";
        String path = Environment.getExternalStorageDirectory().getPath() + "/TTT";
        File sd = new File(path);
        if(!sd.exists()){
            sd.mkdir();
        }
        Toast.makeText(this.getBaseContext(), "Saving...", Toast.LENGTH_SHORT).show();
        File dest = new File(sd, filename);

        // Bitmap bitmap = loda;
        try {
            FileOutputStream out = new FileOutputStream(dest);
            loda.compress(Bitmap.CompressFormat.JPEG, 100, out);
            MediaScannerConnection.scanFile(this, new String[]{dest.getPath()},null,new MediaScannerConnection.OnScanCompletedListener(){
                public void  onScanCompleted(String path, Uri uri){

                }
            });
            File storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            //Toast.makeText(this, "Error in broadcast"+storageDir.getAbsolutePath(), Toast.LENGTH_SHORT).show();

            out.flush();
            out.close();
            try{
                flag = 1;
                addNotification("/"+filename);}
            catch (Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void addNotification(String filew) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logob)
                        .setContentTitle("Image Saved.")
                        .setContentText("Tap to view.");

        builder.setAutoCancel(true);

        Intent notificationIntent = new Intent();
        notificationIntent.setAction(Intent.ACTION_VIEW);
        notificationIntent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/TTT"+filew),"image/jpeg");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);


        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }







    //If you want to return the image taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        if(resultCode!=RESULT_OK){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        else if (requestCode == 1) {
            imgFile = new File(pictureImagePath);
            findViewById(R.id.diag).setVisibility(View.VISIBLE);
            if (imgFile.exists()) {
                myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(),options);
                ImageView myImage = (ImageView) findViewById(R.id.buckysImageView);
                if (myBitmap.getHeight() > myBitmap.getWidth())
                    loda = overlayPot(bm2, myBitmap);
                else
                    loda = overlayLand(bm2, myBitmap);
                myImage.setImageBitmap(loda);

                n=1;

            }
        }

        else if (requestCode == 2&&data.getData()!=null) {
            //imgFile = new File(pictureImagePath);
            // Get the Image from data
            findViewById(R.id.diag).setVisibility(View.VISIBLE);
            try{
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                myBitmap = BitmapFactory.decodeFile(imgDecodableString,options);
                ImageView myImage = (ImageView) findViewById(R.id.buckysImageView);
                if (myBitmap.getHeight() > myBitmap.getWidth())
                    loda = overlayPot(bm2, myBitmap);
                else
                    loda = overlayLand(bm2, myBitmap);
                myImage.setImageBitmap(loda);

                n=1;}
            catch (Exception e){}


        }

        else if(requestCode== Activity.RESULT_CANCELED){
            Toast.makeText(this, "Please select an image...", Toast.LENGTH_SHORT).show();
        }

    }




    public Bitmap overlayPot(Bitmap bmp2, Bitmap bmp1) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        float j= (float)bmp2.getHeight()/bmp2.getWidth();
        int xx =  (int)(bmp1.getHeight() / j);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bmp2, xx, bmp1.getHeight(), false);
        int yy = (bmp1.getWidth() - xx) / 2;
        canvas.drawBitmap(bmp1, new Matrix(), null);
        if(id==R.id.r5){
            canvas.drawBitmap(scaledBitmap, bmp1.getWidth()-xx, 0, null);
        }
        else{
            canvas.drawBitmap(scaledBitmap, yy, 0, null);}
        return bmOverlay;
    }

    public Bitmap overlayLand(Bitmap bmp2, Bitmap bmp1) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        float j= (float)bmp2.getHeight()/bmp2.getWidth();
        int hei = (int) (bmp1.getHeight() * 1.2);
        int xx = (int) (hei /j);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bmp2, xx, hei, false);
        int yy = bmp1.getWidth() - xx;
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(scaledBitmap, yy, -(int)(hei / 6.2), null);
        return bmOverlay;
    }
}
