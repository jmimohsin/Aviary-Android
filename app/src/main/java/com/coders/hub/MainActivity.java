package com.coders.hub;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;

import com.aviary.android.feather.sdk.AviaryIntent;


public class MainActivity extends Activity {

    private int selected_image_option;
    private Uri captured_image_uri;
    private final int IMAGE_EDITOR_REQUEST_CODE=111;
    private final int IMAGE_EDITOR_RESULT_CODE=222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openImageSelectorDialog(IMAGE_EDITOR_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(resultCode!=RESULT_OK)
            return;

        if(requestCode==IMAGE_EDITOR_REQUEST_CODE){
            Uri uri;

            if(selected_image_option==1)
                uri=captured_image_uri;
            else if(selected_image_option==2)
                uri=intent.getData();
            else
                return;

            intent = new AviaryIntent.Builder(MainActivity.this).setData(uri).build();
            startActivityForResult(intent, IMAGE_EDITOR_RESULT_CODE);
        }
        else if(requestCode==IMAGE_EDITOR_RESULT_CODE){
            System.out.println("IMAGE SAVED.");
        }
    }

    public void openImageSelectorDialog(final int request_code){
        selected_image_option=0;
        final CharSequence[] items = {"Take Photo", "Select from Gallery", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    String fileName = "temp.jpg";
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    captured_image_uri = getContentResolver()
                            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, captured_image_uri);
                    selected_image_option = 1;
                    startActivityForResult(intent, request_code);
                } else if (items[item].equals("Select from Gallery")) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, request_code);
                    selected_image_option = 2;
                } else if (items[item].equals("Cancel")) {
                    selected_image_option = 0;
                    dialog.cancel();
                }
            }
        });

        builder.show();
    }

}
