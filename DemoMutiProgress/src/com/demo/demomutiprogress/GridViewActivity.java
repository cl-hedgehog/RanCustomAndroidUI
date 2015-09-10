package com.demo.demomutiprogress;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * 
* @ClassName: GridViewActivity 
* @Description: 实现水平图像重叠，后面压前面
* @author BMR
* @date 2015年9月10日 上午10:49:39
 */
public class GridViewActivity extends Activity {
    
    Context mContext = GridViewActivity.this;
    private GridView grid;
    private ImageView mImageView;
    int[] imgList = { R.drawable.ic_img_01, R.drawable.ic_img_02,R.drawable.ic_img_03,
                      R.drawable.ic_img_04, R.drawable.ic_img_05, R.drawable.ic_img_06,
                      R.drawable.ic_img_07};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_overlay);
        
        grid = (GridView)findViewById(R.id.gridview);
        // 屏蔽GridView的点击事件
        grid.setEnabled(false);
        grid.setClickable(false);
        grid.setPressed(false);
        grid.setGravity(Gravity.CENTER);
        grid.setNumColumns(imgList.length);
        grid.setColumnWidth(Tools.dip2px(mContext, 30));
        MyAdapter adapter = new MyAdapter();
        grid.setAdapter(adapter);
    }
    
    class MyAdapter extends BaseAdapter {

        
        @Override
        public int getCount() {
            return imgList.length;
        }
        
        @Override
        public Object getItem(int position) {
            return imgList[position];
        }
        
        @Override
        public long getItemId(int position) {
            return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                int width = Tools.dip2px(mContext, 40);
                imageView.setLayoutParams(new GridView.LayoutParams(width, width));
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setImageResource(imgList[position]);
                //imageView.setBackgroundResource(imgList[position]);
                // imageView.setPadding(3, 3, 3, 3);
            } else {
                imageView = (ImageView)convertView;
            }
            
            return imageView;
        }
        
    }
    
}
