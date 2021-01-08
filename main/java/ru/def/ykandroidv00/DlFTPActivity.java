package ru.def.ykandroidv00;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;

import static android.widget.Toast.LENGTH_LONG;

public class DlFTPActivity extends AppCompatActivity {

    private static final String TAG = null;
    final String server = "185.68.92.11";
    public String filename;
    private ProgressBar progressBar;
    private short sinch_need;
    Button button;
    TextView tv;
    int progress;
    private String[] fileArr={"zp.txt","base_zak.txt","base_dbtr.txt","base_opl.def","base_res.def","ostatki.txt",
            "base.txt","zatrati.def","techinfo.def","articles.def","articlesgoods.def","cagents.def"};
    final int port = 21;
    final String user = "user82204";
    final String pass = "cfIa4zxV";
    boolean file_download_complete=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dl_f_t_p);


        progressBar = findViewById(R.id.progressBar);
        button= findViewById(R.id.button);
        tv=findViewById(R.id.textView2);

        tv.setVisibility(TextView.INVISIBLE);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        button.setVisibility(Button.INVISIBLE);

        sinch_need = sinch_need();


    }

    @Override
    protected void onStart()
    {   super.onStart();
        if (sinch_need==0){
            isStoragePermissionGranted();
            for (int i = 0; i <fileArr.length ; i++) {
                if (i!=fileArr.length-1){
                    progress = (int) Math.round((i+1)*100/7);
                    postProgress(progress);
                } else {
                    progress=100;
                    postProgress(progress); }

                filename = fileArr[i];
                DLftp(filename);

                System.out.println(filename +" "+ progress+"%");
            }

            tv.setVisibility(TextView.VISIBLE);
            button.setVisibility(Button.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("sinch",true);
                    startActivity(intent);

                }
            });

        } else if (sinch_need==1){
            tv.setVisibility(View.VISIBLE);
            tv.setText("Надо залить файло на сервак");
            button.setText("Понеслось");
            button.setVisibility(Button.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date date = new Date();
                    long sec = date.getTime()/1000;

                    String text = Long.toString(sec);
                    FileOutputStream fos = null;

                    try {
                        fos = openFileOutput("techinfo.def", MODE_PRIVATE);
                        fos.write(text.getBytes());

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    for (int i = 0; i <fileArr.length ; i++) {
                        if (i!=fileArr.length-1){
                            progress = (int) Math.round((i+1)*100/fileArr.length);
                            postProgress(progress);
                        } else {
                            progress=100;
                            postProgress(progress); }

                        filename = fileArr[i];
                        ULftp(filename);
                        System.out.println("Upload "+ filename +" "+ progress+"%");
                    }
                    tv.setText("Синхронизировано");
                    button.setText("в меню");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("sinch",true);
                            startActivity(intent);
                        }
                    });
                }
            });


        }
        else{ tv.setText("Синхронизация не нужна");
            tv.setVisibility(TextView.VISIBLE);
            button.setVisibility(Button.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("sinch",true);
                    startActivity(intent);

                }
            });

        }

        Toast.makeText(this, "Вызван onResume()", Toast.LENGTH_SHORT).show();

    }

//    @Override protected void onPause() {
//        Toast.makeText(this, "Вызван onPause()", Toast.LENGTH_SHORT).show();
//        super.onPause();
//    }
//
//    @Override protected void onResume() {
//        super.onResume();
//
//    }
//
//    @Override protected void onStop() {
//        Toast.makeText(this, "Вызван onStop()", Toast.LENGTH_SHORT).show();
//        super.onStop();
//    }
//
//    @Override protected void onDestroy() {
//        Toast.makeText(this, "Вызван onDestroy()", Toast.LENGTH_SHORT).show();
//        super.onDestroy();
//    }

    private void postProgress(int progress) {
        progressBar.setProgress(progress);
     }

    public void DLftp (final String filenameD) {

        Runnable runnable= new Runnable() {
            @Override
            public void run() {
                
                FTPClient ftpClient = new FTPClient();
                try {

                    ftpClient.connect(server, port);
                    ftpClient.login(user, pass);
                    ftpClient.enterLocalPassiveMode();
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                    String fileputh= getApplicationContext().getFilesDir()+"/"+filenameD;

                    // APPROACH #2: using InputStream retrieveFileStream(String)
                    String remoteFile2 = "/www/905911.ru/base/"+filenameD;
                    File downloadFile2 = new File(fileputh);
                    OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(downloadFile2));
                    InputStream inputStream = ftpClient.retrieveFileStream(remoteFile2);

                    byte[] bytesArray = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(bytesArray)) != -1) {
                        outputStream2.write(bytesArray, 0, bytesRead);
                    }

                    boolean success = ftpClient.completePendingCommand();
                    if (success) {
                        System.out.println("File "+ filenameD +" has been downloaded successfully.");
                    }
                    outputStream2.close();
                    inputStream.close();


                } catch (IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                    ex.printStackTrace();
                } finally {
                    try {
                        if (ftpClient.isConnected()) {
                            ftpClient.logout();
                            ftpClient.disconnect();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };

        Thread th= new Thread(runnable);
        th.start();
    }

    public void ULftp (final String filenameU) {

        Runnable runnable= new Runnable() {
            @Override
            public void run() {
                FTPClient ftpClient = new FTPClient();
                String fileputh= getApplicationContext().getFilesDir()+"/"+filenameU;
                FileInputStream fInput = null;
                try {
                    fInput = new FileInputStream(fileputh);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                String fs = "/www/905911.ru/base/"+filenameU;
                try {
                    ftpClient.connect(server, port);
                    ftpClient.login(user, pass);
                    ftpClient.enterLocalPassiveMode();
                    ftpClient.storeFile(fs, fInput);
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        };
        Thread th= new Thread(runnable);
        th.start();

    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    public short sinch_need(){
        short tf = 3;
        long phoneDate = 0;
        long FTPDate = 0;
        final String FILE_SINCH="techinfo.def";
        final String FILE_SINCH_tmp="techinfo_tmp.def";
        

        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_SINCH);
            if (fis != null) {
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;
                while ((text = br.readLine()) != null) {
                    sb.append(text);
                }
                System.out.println(sb+"-sb");
                phoneDate=Long.parseLong(sb.toString());
                System.out.println("phoneDateLong "+ phoneDate);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "файл не найден", LENGTH_LONG);
            toast.show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Runnable runnable= new Runnable() {
            @Override
            public void run() {
                file_download_complete=false;
                FTPClient ftpClient = new FTPClient();
                try {

                    ftpClient.connect(server, port);
                    ftpClient.login(user, pass);
                    ftpClient.enterLocalPassiveMode();
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                    String fileputh= getApplicationContext().getFilesDir()+"/"+FILE_SINCH_tmp;

                    // APPROACH #2: using InputStream retrieveFileStream(String)
                    String remoteFile2 = "/www/905911.ru/base/"+FILE_SINCH;
                    File downloadFile2 = new File(fileputh);
                    OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(downloadFile2));
                    InputStream inputStream = ftpClient.retrieveFileStream(remoteFile2);

                    byte[] bytesArray = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(bytesArray)) != -1) {
                        outputStream2.write(bytesArray, 0, bytesRead);
                    }

                    boolean success = ftpClient.completePendingCommand();
                    if (success) {
                        System.out.println("File "+ FILE_SINCH_tmp +" has been downloaded successfully.");
                    }
                    outputStream2.close();
                    inputStream.close();
                    file_download_complete=true;


                } catch (IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                    ex.printStackTrace();
                } finally {
                    try {
                        if (ftpClient.isConnected()) {
                            ftpClient.logout();
                            ftpClient.disconnect();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };

        Thread th= new Thread(runnable);
        th.start();

while (file_download_complete!=true){
    int r = 0;
}
FileInputStream fis2 = null;
        try {

            fis2 = openFileInput(FILE_SINCH_tmp);
            if (fis2 != null) {
                InputStreamReader isr1 = new InputStreamReader(fis2);
                BufferedReader br1 = new BufferedReader(isr1);
                StringBuilder sb1 = new StringBuilder();
                String text1;
                while ((text1 = br1.readLine()) != null) {
                    sb1.append(text1);
                }
                System.out.println(sb1+"-sb1");
                FTPDate=Long.parseLong(sb1.toString());
                System.out.println(FTPDate + " FTPDateLong");

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "файл не найден", LENGTH_LONG);
            toast.show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis2 != null) {
                try {
                    fis2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (FTPDate-phoneDate>0)
            tf=0;
        else if (FTPDate-phoneDate<0) tf=1; else tf=3;

        System.out.println(tf + "-tf");
        return  tf;
    }

}