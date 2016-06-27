package com.example.takephototest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	//自定义变量
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	private Button takePhotoBn;
	private ImageView showImage;
	private Uri imageUri; //图片路径
	private String filename; //图片名称
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    takePhotoBn = (Button) findViewById(R.id.button1);
	    showImage = (ImageView) findViewById(R.id.imageView1);
	    //点击"Photo Button"按钮照相
	    takePhotoBn.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		//图片名称 时间命名
	    		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	            Date date = new Date(System.currentTimeMillis());
	            filename = format.format(date);
	    		//创建File对象用于存储拍照的图片 SD卡根目录           
	    		//File outputImage = new File(Environment.getExternalStorageDirectory(),"test.jpg");
	    		//存储至DCIM文件夹
	    		File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);  
	    		File outputImage = new File(path,filename+".jpg");
	    		try {
	    			if(outputImage.exists()) {
	     				outputImage.delete();
	    			}
	    			outputImage.createNewFile();
	    		} catch(IOException e) {
	    			e.printStackTrace();
	    		}
	    		//将File对象转换为Uri并启动照相程序
	    		imageUri = Uri.fromFile(outputImage);
	    		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE"); //照相
	    		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //指定图片输出地址
	    		startActivityForResult(intent,TAKE_PHOTO); //启动照相
	    		//拍完照startActivityForResult() 结果返回onActivityResult()函数
	    	}
	    });
	
	    if (savedInstanceState == null) {
	        getFragmentManager().beginTransaction()
	                .add(R.id.container, new PlaceholderFragment())
	                .commit();
	    }
	}

	@Override
	/** 
	 * 因为两种方式都用到了startActivityForResult方法 
	 * 这个方法执行完后都会执行onActivityResult方法, 所以为了区别到底选择了那个方式获取图片要进行判断
	 * 这里的requestCode跟startActivityForResult里面第二个参数对应 
	 */  
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) { 
			Toast.makeText(MainActivity.this, "ActivityResult resultCode error", Toast.LENGTH_SHORT).show();
			return; 
		}
		switch(requestCode) {
		case TAKE_PHOTO:
			Intent intent = new Intent("com.android.camera.action.CROP"); //剪裁
			intent.setDataAndType(imageUri, "image/*");
			intent.putExtra("scale", true);
			//设置宽高比例
			intent.putExtra("aspectX", 1);
	        intent.putExtra("aspectY", 1);
	        //设置裁剪图片宽高
	        intent.putExtra("outputX", 340);
		    intent.putExtra("outputY", 340);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			Toast.makeText(MainActivity.this, "剪裁图片", Toast.LENGTH_SHORT).show();
			//广播刷新相册 
Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
intentBc.setData(imageUri);     
this.sendBroadcast(intentBc);    
			startActivityForResult(intent, CROP_PHOTO); //设置裁剪参数显示图片至ImageView
			break;
		case CROP_PHOTO:
			try {	 
				//图片解析成Bitmap对象
				Bitmap bitmap = BitmapFactory.decodeStream(
						getContentResolver().openInputStream(imageUri));
				Toast.makeText(MainActivity.this, imageUri.toString(), Toast.LENGTH_SHORT).show();
				showImage.setImageBitmap(bitmap); //将剪裁后照片显示出来
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}


