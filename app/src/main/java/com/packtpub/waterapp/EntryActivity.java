package com.packtpub.waterapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.Date;

public class EntryActivity extends Activity {

    // add private member that will contain URI for the photo to take
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        findViewById(R.id.entry_image_button_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        findViewById(R.id.entry_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitEntry();
            }
        });

    }

    // create a takePicture method and add implemention for it. Create a file with
    // a unique image name up front by using a time stamp and tell image capture
    // intent to use URI for that file.
    private int REQUEST_IMAGE_CAPTURE = 1;
    private void takePicture() {
        File filePhoto = new File(Environment.getExternalStorageDirectory(),
                String.valueOf(new Date().getTime()) + "selfie.jpg");
        mUri = Uri.fromFile(filePhoto);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    //override the onActivityResult method that will be triggered once a
    // photo has been taken. If true, then create bitmap of the file
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = getBitmapFromUri();
            ImageView preview = (ImageView) findViewById(R.id.entry_image_view_preview);
            preview.setImageBitmap(bitmap);
        }
    }

    //implement getBitmapFromUri() method
    public Bitmap getBitmapFromUri() {
        getContentResolver().notifyChange(mUri, null);
        ContentResolver resolver = getContentResolver();
        Bitmap bitmap;
        try {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(resolver, mUri);
            return bitmap;
        }
        catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    // create submitEntry() method. return comment and uri of photo and end the activity
    private void submitEntry() {
        EditText editComment = (EditText) findViewById(R.id.entry_edit_text_comment);
        Intent intent = new Intent();
        intent.putExtra("comments", editComment.getText().toString());
        if (mUri != null) {
            intent.putExtra("uri", "file://" + mUri.getPath().toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

}
