package com.demo.demomutiprogress;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * 
* @ClassName: OverlayActivity 
* @Description: 实现水平图像重叠，前面压后面
* @author BMR
* @date 2015年9月9日 下午3:02:42
 */

public class OverlayActivity extends Activity {
    
    Context mContext = OverlayActivity.this;
    OverlayLayout overlayLayout;
    HTHOverlayLayout overlayLayoutHTH;
    int width;
    int widthHTH;
    
    private List<Drawable> mImgList = new ArrayList<Drawable>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay);
        overlayLayout = (OverlayLayout)findViewById(R.id.overlay);
        overlayLayoutHTH = (HTHOverlayLayout)findViewById(R.id.hth_overlay);
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // activity启动时得到焦点时才绘制，activity退出时失去焦点时不处理
        if (hasFocus) {
        width = overlayLayout.getMeasuredWidth();
        widthHTH = overlayLayoutHTH.getMeasuredWidth();
        Log.d("HTH", "width= " + width);
        Log.d("HTH", "widthHTH= " + widthHTH);
        initData();
        overlayLayout.initOverlay(mImgList, width);
        // 此处用屏幕宽度减去overlayLayout左右两端长度才最精确
        overlayLayoutHTH.initOverlay(mImgList, widthHTH);
        }
    }
    
    /*
     *  实现水平图像重叠
     */
    public void onClick(View v){
        if(mImgList.size()==12){
            mImgList.add(getResources().getDrawable(R.drawable.ic_img_05));
            overlayLayout.updateOverlay(mImgList);
            overlayLayoutHTH.updateOverlay(mImgList);
        }else if(mImgList.size()==13){
            mImgList.add(getResources().getDrawable(R.drawable.ic_img_06));
            overlayLayout.updateOverlay(mImgList);
            overlayLayoutHTH.updateOverlay(mImgList);
        }else if(mImgList.size()==14){
            mImgList.add(getResources().getDrawable(R.drawable.ic_img_07));
            overlayLayout.updateOverlay(mImgList);
            overlayLayoutHTH.updateOverlay(mImgList);
        }else{
            Toast.makeText(mContext, "No More Image", Toast.LENGTH_SHORT).show();
        }
       
    }
    
    private void initData(){
        mImgList.add(getResources().getDrawable(R.drawable.ic_img_01));
        mImgList.add(getResources().getDrawable(R.drawable.ic_img_02));
        mImgList.add(getResources().getDrawable(R.drawable.ic_img_03));
        mImgList.add(getResources().getDrawable(R.drawable.ic_img_04));
        mImgList.add(getResources().getDrawable(R.drawable.ic_img_01));
        mImgList.add(getResources().getDrawable(R.drawable.ic_img_02));
        mImgList.add(getResources().getDrawable(R.drawable.ic_img_03));
        mImgList.add(getResources().getDrawable(R.drawable.ic_img_04));
        mImgList.add(getResources().getDrawable(R.drawable.ic_img_01));
        mImgList.add(getResources().getDrawable(R.drawable.ic_img_02));
        mImgList.add(getResources().getDrawable(R.drawable.ic_img_03));
        mImgList.add(getResources().getDrawable(R.drawable.ic_img_04));

    }
}
