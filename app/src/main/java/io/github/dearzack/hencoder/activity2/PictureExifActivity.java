package io.github.dearzack.hencoder.activity2;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import io.github.dearzack.hencoder.R;

public class PictureExifActivity extends AppCompatActivity {

    public static final int PIC_REQUEST_CODE = 100;

    private Button select;
    private ImageView pic;
    private TextView info;
    private ExifInterface exifInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_exif);
        init();
    }

    private void init() {
        select = (Button) findViewById(R.id.select_pic);
        pic = (ImageView) findViewById(R.id.pic);
        info = (TextView) findViewById(R.id.info);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PIC_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PIC_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    String imagePath = c.getString(columnIndex);
                    Toast.makeText(this, R.string.analysising, Toast.LENGTH_SHORT).show();
                    Glide.with(PictureExifActivity.this).load(imagePath).into(pic);
                    getExif(imagePath);
                    c.close();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getExif(String imagePath) {
        try {
            exifInterface = new ExifInterface(imagePath);
            info.setText(exifInterface.getLatLong()[0] + "  " + exifInterface.getLatLong()[1]);
            if (exifInterface.getLatLong() == null) {
                Toast.makeText(this, R.string.no_gps_info, Toast.LENGTH_SHORT).show();
                return;
            }
            goMap(exifInterface.getLatLong());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goMap(double[] gps) {
        Uri mUri = Uri.parse("geo:" + gps[0] + "," + gps[1] + "?q=" + getString(R.string.your_hasband_position));
        Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
        startActivity(mIntent);
    }
}
