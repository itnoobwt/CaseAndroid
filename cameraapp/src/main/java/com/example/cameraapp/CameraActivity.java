package com.example.cameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.cameraapp.fileUtils.FileUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.UUID;

public class CameraActivity extends AppCompatActivity
{

    @BindView(R.id.simp_img)
    SimpleDraweeView simpImg;
    @BindView(R.id.button)
    Button button;

    public static int CAMERA = 1; //相机

    public static int PHOTO = 2; //相册
    public String filePath;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.ima)
    ImageView ima;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void came(View view)
    {

        filePath = FileUtils.createFile("Camerapp/camer");

        String name = UUID.randomUUID().toString() + ".jpg";
        filePath = filePath.toString() + "/" + name;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(filePath)));
        startActivityForResult(intent, CAMERA);
    }

    @OnClick(R.id.button2)
    public void xiangce(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED)
        {
            return;
        }
        if (requestCode == CAMERA)
        {
            simpImg.setImageURI(Uri.parse("file://" + filePath));
            yasuo(filePath);
        }
        else if (requestCode == PHOTO)
        {
            Uri paht = data.getData();
            simpImg.setImageURI(paht);
            yasuo(paht.toString());
        }

    }


    public void yasuo(String paht)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(paht, options);
        if (options.outHeight == -1 || options.outWidth == -1) {
            try {
                ExifInterface exifInterface = new ExifInterface(paht);
                int height = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH,
                        ExifInterface.ORIENTATION_NORMAL);//获取图片的高度
                int width = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH,
                        ExifInterface.ORIENTATION_NORMAL);//获取图片的宽度
                options.outWidth = width;
                options.outHeight = height;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int w = options.outWidth;
        int h = options.outHeight;
        int widht = 400;
        int height = 400;
        if (w > widht || h > height)
        {
            int a = Math.round((float) w / widht);
            options.inSampleSize = a;
        }
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(paht, options);
        ima.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
