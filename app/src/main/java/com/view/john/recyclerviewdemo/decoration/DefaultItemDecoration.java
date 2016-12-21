package com.view.john.recyclerviewdemo.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.view.john.recyclerviewdemo.R;

/**
 * Created by John on 2016/12/11.
 */

public class DefaultItemDecoration  extends RecyclerView.ItemDecoration{
    public static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    public static final int LIST_HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int LIST_VERTICAL = LinearLayoutManager.VERTICAL;
    private Drawable dividerDrawable;
    private int mOrientation;


    public DefaultItemDecoration(Context context, int mOrientation) {
//       final TypedArray mTypedArray = context.obtainStyledAttributes(ATTRS);
//        dividerDrawable = mTypedArray.getDrawable(0);
//        final TypedArray mTypedArray = context.obtainStyledAttributes(R.attr.divider);
//        dividerDrawable = mTypedArray.getDrawable(0);
        dividerDrawable = context.getResources().getDrawable(R.drawable.shape_list_divider2,null);
//        回收TypedArray，以便后面重用。在调用这个函数后，你就不能再使用这个TypedArray。
//        在TypedArray后调用recycle主要是为了缓存。当recycle被调用后，这就说明这个对象从现在可以被重用了。TypedArray 内部持有部分数组，它们缓存在Resources类中的静态字段中，这样就不用每次使用前都需要分配内存。
//        mTypedArray.recycle();
        setOrientation(mOrientation);
    }

    /**
     * 设置分割线的方向
     * @param mOrientation
     */
    private void setOrientation(int mOrientation) {
        if (mOrientation!=LinearLayoutManager.HORIZONTAL && mOrientation != LinearLayoutManager.VERTICAL){
            throw new  IllegalArgumentException("the mOrientation is invalidate");
        }
        this.mOrientation = mOrientation;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LIST_VERTICAL)
            drawVerticalItemDecoration(c, parent);
        else
            drawHorizationItemDecoration(c, parent);
        super.onDrawOver(c, parent, state);
    }

    private void drawVerticalItemDecoration(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth()-parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom()+params.bottomMargin;
            int bottom = top + dividerDrawable.getIntrinsicHeight();
            dividerDrawable.setBounds(left,top,right,bottom);
            dividerDrawable.draw(c);

        }


    }

    private void drawHorizationItemDecoration(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight()-parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight()+params.rightMargin;
            int right = left + dividerDrawable.getIntrinsicWidth();
            dividerDrawable.setBounds(left,top,right,bottom);
            dividerDrawable.draw(c);

        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation==LIST_VERTICAL)
            outRect.set(0,0,0,dividerDrawable.getIntrinsicHeight());
         else
            outRect.set(0,0,dividerDrawable.getIntrinsicWidth(),0);

        super.getItemOffsets(outRect, view, parent, state);
    }
}
