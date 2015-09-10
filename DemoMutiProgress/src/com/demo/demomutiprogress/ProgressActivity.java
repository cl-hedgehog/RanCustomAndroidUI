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
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * 
* @ClassName: ProgressActivity 
* @Description: 显示各种节点进度条
* @author BMR
* @date 2015年9月9日 下午3:02:42
 */

public class ProgressActivity extends Activity {

    int current = 0;
    int total = 0;
    DotProgress dp;
    Context mContext = ProgressActivity.this;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        dp = (DotProgress)findViewById(R.id.mp_5);
        current = dp.getCurrentDotNo();
        total = dp.getDotsNum();
    }
    
    // 上一个节点
    public void onPre(View v){
        current = current > 0? current - 1:0;
        dp.setCurrentDotNo(current);
    }

    // 下一个节点
    public void onNext(View v){
        current = current < total? current + 1:total;
        dp.setCurrentDotNo(current);
    }
   
}
