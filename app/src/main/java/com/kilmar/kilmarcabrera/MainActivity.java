package com.kilmar.kilmarcabrera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private TextView editText_mail;
    private TextView editText_git;
    private TextView editText_twitter;
    private TextView editText_phone;
    private TextView editText_name;
    private TextView editText_career;

    private ImageView image_profile;

    private Button share_info;
    private Button share_img;

    private String email_var;
    private String git_var;
    private String twitter_var;
    private String phone_var;
    private String name_var;
    private String career_var;

    private String msg_final;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAllViews();

        share_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email_var = editText_mail.getText().toString();
                git_var = editText_git.getText().toString();
                twitter_var = editText_twitter.getText().toString();
                phone_var = editText_phone.getText().toString();
                name_var = editText_name.getText().toString();
                career_var = editText_career.getText().toString();

                sendInfo(contentToString(email_var,git_var,twitter_var,phone_var, name_var, career_var));

            }
        });

        share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDrawable(R.drawable.kilian, "kilian");
            }
        });
    }

    private void getAllViews(){

        editText_mail = findViewById(R.id.name_mail);
        editText_twitter = findViewById(R.id.name_twitter);
        editText_git = findViewById(R.id.name_git);
        editText_phone = findViewById(R.id.name_phone);
        editText_career = findViewById(R.id.career);
        editText_name = findViewById(R.id.name);

        image_profile = findViewById(R.id.image_profile);

        share_info = findViewById(R.id.share_info);
        share_img = findViewById(R.id.share_image);

    }

    private String contentToString(String mail, String git, String twitter, String phone, String name, String career){
        String msg = "About me...\nNombre: "+name+"\nCarrera: "+career+"\nCorreo electronico: "+
                mail+"\nGithub: "+git+"\nTwitter: "+twitter+"\nPhone: "+phone;

        return msg;
    }

    private void sendInfo(String msg){
        Intent sendIntent = new Intent();

        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.setType("text/plain");

        startActivity(Intent.createChooser(sendIntent,"About me share..."));
    }

    public void shareDrawable(int resourceId, String fileName) {
        try {
            //Convertir el recurso en bitmap
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);

            //Guardar el bitmap en cache
            File outputFile = new File(getCacheDir(), fileName + ".png");
            FileOutputStream outPutStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outPutStream);
            outPutStream.flush();
            outPutStream.close();
            outputFile.setReadable(true, false);

            //share file
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(outputFile));
            shareIntent.setType("image/png");
            startActivity(shareIntent);
        }
        catch (Exception e) { Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }
    }
}
