package com.system.caseandroid.two;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.system.caseandroid.BaseActivity;
import com.system.caseandroid.MyApplication;
import com.system.caseandroid.R;
import com.system.caseandroid.utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 相机，裁剪
 */
public class TwoMainActivity extends BaseActivity
{

    @BindView(R.id.btn_pz)
    Button btnPz;
    @BindView(R.id.img_pz)
    ImageView imgPz;
    @BindView(R.id.btn_xc)
    Button btnXc;
    @BindView(R.id.img_xc)
    ImageView imgXc;
    @BindView(R.id.img_pz1)
    ImageView imgPz1;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_main);
    }

    @OnClick(R.id.btn_pz)
    public void paizhao()
    {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
                Toast.makeText(this, "您拒绝了调用相机权限", Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
            }

        }

    }

    public void camera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        uri = Uri.fromFile(new File(FileUtils.getInstance().getImageDir(), getName()));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);// false回调是data为null，true回调不为null
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("crop", true);  //发送裁剪信号
//        intent.putExtra("aspectX", 1);  //X方向上的比例
//        intent.putExtra("aspectY", 1);  //Y方向上的比例
//        intent.putExtra("outputX", 1000); //裁剪区的宽
//        intent.putExtra("outputY", 1000);   //裁剪区的高
//        intent.putExtra("scale", true);  //是否保留比例
//        intent.putExtra("noFaceDetection",true); //是否取消人脸识别功能
        //file:///storage/emulated/0/CaseAndroid/camera/51a6035c-a6ee-425f-88f6-a201faa7ee65.jpg
        startActivityForResult(intent, 1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    AlertDialog alertDialog = new AlertDialog.Builder(TwoMainActivity.this).setTitle("系统获取相机权限")
                            .setMessage("app申请用户受理权限 yes/no ?").setPositiveButton("yes", new DialogInterface
                                    .OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    camera();
                                }
                            }).setNegativeButton("no",new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    Toast.makeText(TwoMainActivity.this, "拒绝了", Toast.LENGTH_SHORT).show();
                                }
                            }).create();
                    alertDialog.show();
                }else{
                    Toast.makeText(this, "拒绝了", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    /**
     * 调用裁剪
     * @param uri
     */
    public void cropIma(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("return-data", true);// false回调是data为null，true回调不为null
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("crop", "circle");  //发送裁剪信号   类型：circle(圆形)
        intent.putExtra("aspectX", 1);  //X方向上的比例
        intent.putExtra("aspectY", 1);  //Y方向上的比例
        intent.putExtra("outputX", 300); //裁剪区的宽
        intent.putExtra("outputY", 300);   //裁剪区的高
        intent.putExtra("scale", true);  //是否保留比例
        intent.putExtra("noFaceDetection",true); //是否取消人脸识别功能
        //file:///storage/emulated/0/CaseAndroid/camera/51a6035c-a6ee-425f-88f6-a201faa7ee65.jpg
        startActivityForResult(intent, 2);
    }

    public String getName()
    {
        return UUID.randomUUID().toString() + ".jpg";
    }

    @OnClick(R.id.btn_xc)
    public void xiangche()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");  //选择内容为图片
        intent.putExtra("return-data",false);
        startActivityForResult(intent, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1)
        {
            cropIma(uri);

        }
        else if(requestCode == 2){
            try
            {//file:///storage/emulated/0/CaseAndroid/camera/3af3c196-4b41-4a72-8432-da6ccaa3729b.jpg
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                imgPz1.setImageBitmap(bm);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            //            imgPz.setImageURI(uri);
        }
        else if(requestCode == 3){
            imgXc.setImageURI(data.getData());
        }
        if (resultCode == RESULT_CANCELED)
        {
            return;
        }
    }
}
