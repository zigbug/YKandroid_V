package ru.def.ykandroidv00;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class TakePhoto extends AppCompatActivity {


    static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;
    private ImageView imageView;
    private Uri photoURI;
    String imageFileName;
    File image;
    Intent intent2;
    String what,h_much,comment;;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        intent2=getIntent();
        what = intent2.getStringExtra("what1");
        h_much = intent2.getStringExtra("h-much1");
        comment =intent2.getStringExtra("comment1");

        System.out.println("vsya figna"+what+h_much+comment);


        imageView = findViewById(R.id.receiptPhoto);
    }

    public void onClick(View view) {
        dispatchTakePictureIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            imageView.setImageURI(photoURI);
        }
    }

    public void saveBtn(View view){
        File savePhotoDir= new File(getFilesDir() + "/receipts_photos/");
        System.out.println("gfd"+getFilesDir());
        try {
            File photo2=File.createTempFile(imageFileName,".jpg", savePhotoDir);
            Toast.makeText(this,"Успешно сохранён", Toast.LENGTH_LONG);
            image.delete();
            Intent intent = new Intent(this,VvodZatrat.class);
            intent.putExtra("picName", (imageFileName+".jpg"));
            intent.putExtra("what", what);
            intent.putExtra("h-much",h_much);
            intent.putExtra("comment",comment);
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"Обломб, не сохранилось", Toast.LENGTH_LONG);
        }
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp;
        //String toPhoto2="receipts_photos/"+imageFileName;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        System.out.println(mCurrentPhotoPath+"mcpp");
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

}

//
//    final int CAMERA_REQUEST = 0;
//    final int PIC_CROP = 2;
//    private Uri picUri;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_take_photo);
//        setTitle("Съёмка и Кадрирование");
//    }
//
//    public void onClick(View v) {
//        try {
//            // Намерение для запуска камеры
//            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(captureIntent, CAMERA_REQUEST);
//        } catch (ActivityNotFoundException e) {
//            // Выводим сообщение об ошибке
//            String errorMessage = "Ваше устройство не поддерживает съемку";
//            Toast toast = Toast
//                    .makeText(this, errorMessage, Toast.LENGTH_SHORT);
//            toast.show();
//        }
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        System.out.println("rc,rc,dt"+requestCode+ resultCode + data);
//        if (resultCode == RESULT_OK) {
//            // Вернулись от приложения Камера
//            if (requestCode == CAMERA_REQUEST) {
//                // Получим Uri снимка
//                picUri = data.getData();
//                System.out.println("picURi-" + picUri);
//                // кадрируем его
//                performCrop();
//            }
//            // Вернулись из операции кадрирования
//            else if(requestCode == PIC_CROP){
//                Bundle extras = data.getExtras();
//                // Получим кадрированное изображение
//                Bitmap thePic = extras.getParcelable("data");
//                // передаём его в ImageView
//                ImageView picView = findViewById(R.id.picture);
//                picView.setImageBitmap(thePic);
//            }
//        }
//    }
//
//    private void performCrop(){
//        try {
//            // Намерение для кадрирования. Не все устройства поддерживают его
//            Intent cropIntent = new Intent("com.android.camera.action.CROP");
//            cropIntent.setDataAndType(picUri, "image/*");
//            cropIntent.putExtra("crop", "true");
//            cropIntent.putExtra("aspectX", 1);
//            cropIntent.putExtra("aspectY", 1);
//            cropIntent.putExtra("outputX", 256);
//            cropIntent.putExtra("outputY", 256);
//            cropIntent.putExtra("return-data", true);
//            startActivityForResult(cropIntent, PIC_CROP);
//        }
//        catch(ActivityNotFoundException anfe){
//            String errorMessage = "Извините, но ваше устройство не поддерживает кадрирование";
//            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
//            toast.show();
//        }
//    }