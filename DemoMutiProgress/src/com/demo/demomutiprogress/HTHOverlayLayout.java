/*
 * @Project: GZJK
 * @Author: BMR
 * @Date: 2015年9月8日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.demo.demomutiprogress;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/** 
* @ClassName: HTHOverlayLayout 
* @Description: 图片水平重叠控件for HTH
* @author BMR
* @date 2015年9月8日 下午4:04:56 
*/

class HTHOverlayLayout extends RelativeLayout {
    
    // =============================================================================
    // Child views
    // =============================================================================
    // 初始化必须
    private List<Drawable> mImgList = new ArrayList<Drawable>();//栈，最大下标显示在最开始，超出不显示
    private int mWidth;
    //属性读取必须
    private int imageSize;
    private int overlayWidth;
    
    private int maxCount; // 计算得出最大的图片显示个数
    private int imgNum; // 实际显示的图片个数
    private boolean isDisplayLast = false;// 是否显示更多Image
    
    private Context mContext;
    private Drawable drawableMore;
    
    // =============================================================================
    // Constructor
    // =============================================================================
    public HTHOverlayLayout(Context context) {
        this(context, null);
    }
    
    public HTHOverlayLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public HTHOverlayLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.overlay);
        imageSize = mTypedArray.getDimensionPixelSize(R.styleable.overlay_imageSize, 46);
        overlayWidth = mTypedArray.getDimensionPixelSize(R.styleable.overlay_overlayWidth, 8);
        this.mWidth = Tools.dip2px(mContext, Tools.getScreenWidth((Activity)mContext));
    }
    
    
    /**
     * 必须要调用的绘制函数，传入要显示的图片List和控件宽度
     * List<Drawable> imgList
     * int width
     */
    public void initOverlay(List<Drawable> imgList, int width) {
        this.mWidth = width;
        this.mImgList = imgList;
        drawableMore = mContext.getResources().getDrawable(R.drawable.ic_more_memeber);
        drawOverlay();
    }
    
    /**
     * 更新绘制函数，传入要显示的图片List
     * List<Drawable> imgList
     * int width
     */
    public void updateOverlay(List<Drawable> imgList) {
        this.mImgList = imgList;
        drawOverlay();
    }
    
    public void drawOverlay() {
        // 计算最多显示的图片数目
        maxCount = (mWidth-overlayWidth) / (imageSize - overlayWidth);
        // 计算实际要绘制的图片数目,最后一个显示默认图片-更多
        imgNum = Math.min(maxCount-1, mImgList.size());
        if(mImgList.size() >= maxCount){
            // 显示默认图片-更多
            isDisplayLast = true;
        }
        removeAllViews();
        if(isDisplayLast){
            addDrawable(drawableMore, maxCount * (imageSize - overlayWidth));
        }
        for (int i = 0; i < imgNum; i++) {
            int index = mImgList.size() - imgNum + i;
            int left = (imgNum - i - 1) * (imageSize - overlayWidth);
            addDrawable(mImgList.get(index), left);
        }

    }
    
    
    private void addDrawable(Drawable drawable, int left){
        ImageView imageView = new ImageView(mContext);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(imageSize,
            imageSize);
        int top = 0;
        int right = 0;
        int bottom = 0;
        layoutParams.setMargins(left, top, right, bottom);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageDrawable(drawable);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        addView(imageView);
    }

    
}
