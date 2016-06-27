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

	//�Զ������
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	private Button takePhotoBn;
	private ImageView showImage;
	private Uri imageUri; //ͼƬ·��
	private String filename; //ͼƬ����
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    takePhotoBn = (Button) findViewById(R.id.button1);
	    showImage = (ImageView) findViewById(R.id.imageView1);
	    //���"Photo Button"��ť����
	    takePhotoBn.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		//ͼƬ���� ʱ������
	    		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	            Date date = new Date(System.currentTimeMillis());
	            filename = format.format(date);
	    		//����File�������ڴ洢���յ�ͼƬ SD����Ŀ¼           
	    		//File outputImage = new File(Environment.getExternalStorageDirectory(),"test.jpg");
	    		//�洢��DCIM�ļ���
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
	    		//��File����ת��ΪUri�������������
	    		imageUri = Uri.fromFile(outputImage);
	    		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE"); //����
	    		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //ָ��ͼƬ�����ַ
	    		startActivityForResult(intent,TAKE_PHOTO); //��������
	    		//������startActivityForResult() �������onActivityResult()����
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
	 * ��Ϊ���ַ�ʽ���õ���startActivityForResult���� 
	 * �������ִ����󶼻�ִ��onActivityResult����, ����Ϊ�����𵽵�ѡ�����Ǹ���ʽ��ȡͼƬҪ�����ж�
	 * �����requestCode��startActivityForResult����ڶ���������Ӧ 
	 */  
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) { 
			Toast.makeText(MainActivity.this, "ActivityResult resultCode error", Toast.LENGTH_SHORT).show();
			return; 
		}
		switch(requestCode) {
		case TAKE_PHOTO:
			Intent intent = new Intent("com.android.camera.action.CROP"); //����
			intent.setDataAndType(imageUri, "image/*");
			intent.putExtra("scale", true);
			//���ÿ�߱���
			intent.putExtra("aspectX", 1);
	        intent.putExtra("aspectY", 1);
	        //���òü�ͼƬ���
	        intent.putExtra("outputX", 340);
		    intent.putExtra("outputY", 340);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			Toast.makeText(MainActivity.this, "����ͼƬ", Toast.LENGTH_SHORT).show();
			//�㲥ˢ����� 
Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
intentBc.setData(imageUri);     
this.sendBroadcast(intentBc);    
			startActivityForResult(intent, CROP_PHOTO); //���òü�������ʾͼƬ��ImageView
			break;
		case CROP_PHOTO:
			try {	 
				//ͼƬ������Bitmap����
				Bitmap bitmap = BitmapFactory.decodeStream(
						getContentResolver().openInputStream(imageUri));
				Toast.makeText(MainActivity.this, imageUri.toString(), Toast.LENGTH_SHORT).show();
				showImage.setImageBitmap(bitmap); //�����ú���Ƭ��ʾ����
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


