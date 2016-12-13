package com.system.caseandroid.seven;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.system.caseandroid.BaseActivity;
import com.system.caseandroid.R;
import com.system.caseandroid.seven.utils.MyBitmapCache;

/**
 * 深入了解Bitmap
 */
public class BitmapCaseActivity extends BaseActivity
{

    @BindView(R.id.btn_img_size)
    Button btnImgSize;
    @BindView(R.id.txt_size)
    TextView txtSize;
    @BindView(R.id.ima_photo)
    ImageView imaPhoto;
    @BindView(R.id.btn_img_compress)
    Button btnImgCompress;
    @BindView(R.id.ima_compress)
    ImageView imaCompress;
    @BindView(R.id.et_url)
    EditText etUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_case);
    }


    /**
     * 获取到图片的大小
     */
    @OnClick(R.id.btn_img_size)
    public void getCount()
    {
        int count = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher).getByteCount();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.xiongmao);

        BitmapFactory.decodeResource(getResources(), R.mipmap.xiongmao).getHeight();
        BitmapFactory.decodeResource(getResources(), R.mipmap.xiongmao).recycle();
        txtSize.setText("图片大小：长*宽=" + count + "像素");
    }

    /**
     * 获取头像，并且进行缓存
     */
    @OnClick(R.id.btn_img_person)
    public void getPhoto()
    {
        String url = etUrl.getText().toString().trim();
        MyBitmapCache.getInstance().disPlay(url, imaPhoto);
    }

    @OnClick(R.id.btn_img_compress)
    public void getPhotoCompress()
    {

    }

}
