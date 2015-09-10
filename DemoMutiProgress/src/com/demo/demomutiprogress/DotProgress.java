/*
 * @Project: GZJK
 * @Author: BMR
 * @Date: 2015年9月7日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.demo.demomutiprogress;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/** 
* @ClassName: DotProgress 
* @Description: TODO
* @author BMR
* @date 2015年9月7日 下午5:11:31 
*/
public class DotProgress extends ViewGroup {
    
    private int dotsNum;
    private int currentDotNo;
    private int lineColorUnprogress;
    private int lineColorProgressed;
    private int textColorUnprogress;
    private int textColorProgressed;
    private Drawable drawableUnprogress;
    private Drawable drawableInprogress;
    private Drawable drawableProgressed;
    
    private Context mContext;
    private int mWidth;
    private int mHeight;
    // every child view width and height.
    int childWidth = 0;
    int childHeight = 0;
    int dotRadius = 0;
    int childNum = 0;
    
    private List<DotView> childList = new ArrayList<DotView>();
    
    public DotProgress(Context context) {
        this(context, null);
        
    }
    
    public DotProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public DotProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        // TODO Auto-generated constructor stub
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.DotProgress);
        dotsNum = mTypedArray.getInteger(R.styleable.DotProgress_dotsNum, 1); // 默认一个节点
        dotRadius = mTypedArray.getDimensionPixelSize(R.styleable.DotProgress_dotRadius, 10); //节点半径
        currentDotNo = mTypedArray.getInteger(R.styleable.DotProgress_currentDotNo, 1); // 默认一个节点
        lineColorUnprogress = mTypedArray.getColor(R.styleable.DotProgress_lineColorUnprogress,
            Color.WHITE);
        lineColorProgressed = mTypedArray.getColor(R.styleable.DotProgress_lineColorProgressed,
            Color.WHITE);
        textColorUnprogress = mTypedArray.getColor(R.styleable.DotProgress_textColorUnprogress,
            Color.WHITE);
        textColorProgressed = mTypedArray.getColor(R.styleable.DotProgress_textColorProgressed,
            Color.WHITE);
        drawableUnprogress = mTypedArray.getDrawable(R.styleable.DotProgress_drawableUnprogress);
        drawableInprogress = mTypedArray.getDrawable(R.styleable.DotProgress_drawableInprogress);
        drawableProgressed = mTypedArray.getDrawable(R.styleable.DotProgress_drawableProgressed);
        mTypedArray.recycle();
        initChildView();
        setCurrentDotNo(currentDotNo);
    }
    
    void initChildView() {
        
        for (int i = 0; i < dotsNum; i++) {
            DotView child = new DotView(mContext, i);
            if (i == dotsNum - 1) {
                child.setLastView(true);
            }
            childList.add(child);
            addView(child, i);
            
        }
        
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        childWidth = dotsNum > 1 ? (mWidth - 2 * dotRadius) / (dotsNum - 1) : 0;
        //childHeight = Tools.dip2px(mContext, 100);
        childHeight = mHeight;
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
            LayoutParams lParams = (LayoutParams)child.getLayoutParams();
            lParams.left = i * childWidth;
            lParams.top = 0;
        }
        int vw = mWidth;
        int vh = mHeight;
        setMeasuredDimension(vw, vh);
        
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        
        int childCount = this.getChildCount();
        int hp = dotsNum > 1 ? (mWidth - 2 * dotRadius) / (dotsNum - 1) : 0;
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            LayoutParams lParams = (LayoutParams)child.getLayoutParams();
            child.layout(lParams.left, lParams.top, lParams.left + hp, lParams.top
                                                                               + childHeight);
            
        }
    }
    
    @Override
    public int getChildCount() {
        return childList.size();
    }
    
    @Override
    public DotView getChildAt(int index) {
        return childList.get(index);
    }
    
    public static class LayoutParams extends ViewGroup.LayoutParams {
        
        public int left = 0;
        public int top = 0;
        
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            // TODO Auto-generated constructor stub
        }
        
        public LayoutParams(int width, int height) {
            super(width, height);
            // TODO Auto-generated constructor stub
        }
        
        public LayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
            // TODO Auto-generated constructor stub
        }
        
    }
    
    // 自定义的ViewGroup使用自定义的LayoutParams类来添加子View
    @Override
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof DotProgress.LayoutParams;
    }
    
    @Override
    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new DotProgress.LayoutParams(mContext, attrs);
    }
    
    @Override
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }
    
    @Override
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }
    
    public void setCurrentDotNo(int curr){
        if(curr < 0){
            curr = 0;
        }
        if(curr > dotsNum){
            curr = dotsNum;
        }
        // curr = dotsNum时，所有dot是已进行状态
        currentDotNo = curr;
        
        for(int i=0; i< dotsNum; i++){
            DotView dot = getChildAt(i);
            dot.setProperty(curr);
        }
    
    }
    
    public int getCurrentDotNo() {
        return currentDotNo;
    }


    public int getDotsNum() {
        return dotsNum;
    }

    class DotView extends RelativeLayout {
        
        // =============================================================================
        // Child views
        // =============================================================================
        private TextView textView;
        private ImageView imageView;
        private View line;
        private int index;
        private boolean isLast = false;
        
        // =============================================================================
        // Constructor
        // =============================================================================
        
        public DotView(Context context,int index) {
            this(context, null);
            setIndex(index);
        }
        
        public DotView(Context context) {
            this(context, null);
        }
        
        public DotView(Context context, AttributeSet attrs) {
            this(context, attrs,0);
        }

        public DotView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            inflate(getContext(), R.layout.layout_dot_view, this);
            this.textView = (TextView)findViewById(R.id.tv_dot);
            this.imageView = (ImageView)findViewById(R.id.iv_dot);
            this.line = (View)findViewById(R.id.vi_line);
            setLastView(isLast);
            
        }
        
        public void setLastView(boolean isLast) {
            this.isLast = isLast;
            if (isLast) {
                this.line.setVisibility(View.GONE);
            }
        }
        public void setProperty(int currentDotNo) {
            if(index < currentDotNo){
                textView.setTextColor(textColorProgressed);
                textView.setText("√");
                Tools.setDrawableToBkg(textView, drawableProgressed);
                line.setBackgroundColor(lineColorProgressed);
            }else if(index == currentDotNo){
                textView.setTextColor(Color.RED);
                textView.setText("" + (index+1));
                Tools.setDrawableToBkg(textView, drawableInprogress);
                line.setBackgroundColor(lineColorUnprogress);
            }
            else {
                textView.setTextColor(textColorUnprogress);
                textView.setText("" + (index+1));
                Tools.setDrawableToBkg(textView, drawableUnprogress);
                line.setBackgroundColor(lineColorUnprogress);
            }
        }
        
        
        // =============================================================================
        // Getters
        // =============================================================================
        public TextView getTextView() {
            return textView;
        }
        
        public ImageView getImageView() {
            return imageView;
        }
        
        public View getLine() {
            return line;
        }
        
        public boolean isLast() {
            return isLast;
        }
        
        public void setLast(boolean isLast) {
            this.isLast = isLast;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
            
        }
        
    }
}
