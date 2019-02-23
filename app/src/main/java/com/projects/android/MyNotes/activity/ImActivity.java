package com.projects.android.MyNotes.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.database.DbHelper;
import com.projects.android.MyNotes.helper.Shared_Preferences;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ImActivity extends AppCompatActivity {
ImageView imageView;
DbHelper helper;
SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im);
        Shared_Preferences shared_preferences = new Shared_Preferences(getApplicationContext());
        Toolbar toolbar =  findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackground(new ColorDrawable(shared_preferences.get_primaryColor()));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        imageView = findViewById(R.id.imageView);
        try {
            if (getIntent().hasExtra("ImageURI")) {
                Uri imageUri = Uri.parse(getIntent().getStringExtra("ImageURI"));
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(bitmap);
            }
            if(getIntent().hasExtra("byteArray"))
            {
                Bitmap bmp = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
                imageView.setImageBitmap(bmp);
            }
        }
        catch( Exception e)
        {
            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
        if(getIntent().hasExtra(Intent.EXTRA_TEXT))
        {
            setImage();
        }
    }

    @Override
    public void onBackPressed() {
        if(!getIntent().hasExtra(Intent.EXTRA_TEXT))
        {
            addImg();
        }
        else
        {
            Intent i = new Intent ( getApplicationContext(), Main.class);
            startActivity (i);
        }
    }
    private void setImage()
    {
        Bitmap bmp = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArrayPreview"), 0, getIntent().getByteArrayExtra("byteArrayPreview").length);
        imageView.setImageBitmap(bmp);
    }
    private void addImg()
    {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bmp = imageView.getDrawingCache();
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bs);
        byte[] Image = bs.toByteArray();
        helper = new DbHelper(getApplicationContext());
        db = helper.getWritableDatabase();
        helper.addInfo("Title", Image, "Content", " ", db);
        Intent i = new Intent(getApplicationContext(), Main.class);
        startActivity(i);
    }
}
