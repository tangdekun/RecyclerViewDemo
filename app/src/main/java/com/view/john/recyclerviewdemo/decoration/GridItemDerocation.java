package com.view.john.recyclerviewdemo.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.view.john.recyclerviewdemo.R;

/**
 * Created by John on 2016/12/17.
 */

public class GridItemDerocation extends RecyclerView.ItemDecoration {
    private Drawable dividerDrawable;//分割线图片
    private final int GRIDLAYOUT_VERTICAL = GridLayoutManager.VERTICAL;//GridView纵向布局
    private final int GRIDLAYOUT_HORIZONTAL = GridLayoutManager.HORIZONTAL;//GridView横向布局
    private final int STAGGERED_GRIDLAYOUT_VERTICAL = StaggeredGridLayoutManager.VERTICAL;//StaggeredGridLayout纵向布局
    private final int STAGGERED_GRIDLAYOUT_HORIZONTAL = StaggeredGridLayoutManager.HORIZONTAL;//StaggeredGridLayout横向布局

    public GridItemDerocation(Context context) {
        dividerDrawable = context.getResources().getDrawable(R.drawable.shape_list_divider2);


    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVertialItemDecoration(c, parent);
        drawHorizontalItemDecoration(c, parent);
    }

    /**
     * 水平分割线
     *
     * @param c
     * @param parent
     */
    private void drawHorizontalItemDecoration(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - params.leftMargin;
            int right = child.getRight() + params.rightMargin;
            int top = child.getBottom() + params.bottomMargin + child.getPaddingBottom();
            int bottom = top + dividerDrawable.getIntrinsicHeight();
            dividerDrawable.setBounds(left, top, right, bottom);
            dividerDrawable.draw(c);
        }


    }

    /**
     * 竖直分割线
     *
     * @param c
     * @param parent
     */
    private void drawVertialItemDecoration(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int right = left + dividerDrawable.getIntrinsicWidth();
            int top = child.getTop() - params.topMargin - child.getPaddingTop();
            int bottom = child.getBottom() + params.bottomMargin + child.getPaddingBottom() + dividerDrawable.getIntrinsicHeight();
            dividerDrawable.setBounds(left, top, right, bottom);
            dividerDrawable.draw(c);
        }

    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        if (isLastColum(parent, position, spanCount, childCount)) {
//            outRect.bottom = dividerDrawable.getIntrinsicHeight();
            outRect.set(0, 0, 0,dividerDrawable.getIntrinsicHeight());
        } else if (isLastRow(parent, position, spanCount, childCount)) {
//            outRect.right = dividerDrawable.getIntrinsicWidth();
            outRect.set(0, 0, dividerDrawable.getIntrinsicWidth(), 0);

        } else {
            outRect.bottom = dividerDrawable.getIntrinsicHeight();
            outRect.right = dividerDrawable.getIntrinsicWidth();
        }
    }

    /**
     * 判断是否是最后一行
     * 1.条件分支：布局管理器是GridLayoutManager 或 StaggeredGridLayoutManager
     * 2.布局的方向：Vertical 或 Horizontal
     * 3.如果是Vertical竖直布局，最后一行的判断标准
     * （1）一种情况是最后一行未满，那么用总item对默认每行item数取余得到A，然后用总item-A得到排满了的最后一行最后一个元素，这个元素之后就是最后一行
     * （2）一种情况是最后一行已满，用总item-默认每行的item数，得到倒数第二行最后一个元素的位置，以后的就是最后一行
     * 4.如果是Horizontal横向布局，最后一行的判断标准
     * （1）用当前view的位置（position+1）对默认每列的item数取余，余数为0即为最后一行
     * （2）如果是最后一列，那么最后一个元素，也视为最后一行
     *
     * @param parent     父布局
     * @param position   位置-1
     * @param spanCount  默认每行或每列的item数
     * @param childCount 总共子元素个数
     * @return
     */
    private boolean isLastRow(RecyclerView parent, int position, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if (((GridLayoutManager) layoutManager).getOrientation() == GRIDLAYOUT_VERTICAL) {
                int LastRowFristPosition = -1;
                if (childCount % spanCount == 0) {
                    LastRowFristPosition = childCount - spanCount;

                } else {
                    LastRowFristPosition = childCount - childCount % spanCount;
                }
                if (position >= LastRowFristPosition) {
                    return true;

                }
            } else {

                if ((position + 1) % spanCount == 0){

                   return  true;

                }
                else if(position+1 == childCount){

                    return true;

                }else{
                    return false;
                }
            }

        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            if (((StaggeredGridLayoutManager) layoutManager).getOrientation() == STAGGERED_GRIDLAYOUT_VERTICAL) {
                int LastRowFristPosition = -1;
                if (childCount % spanCount == 0) {

                    LastRowFristPosition = childCount - spanCount;

                } else {
                    LastRowFristPosition = childCount - childCount % spanCount;
                }
                if (position >= LastRowFristPosition) {
                    return true;
                }
            } else {
                if ((position + 1) % spanCount == 0 || position == childCount - 1) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断是否是最后一列
     * 1.条件分支：布局管理器是GridLayoutManager 或 StaggeredGridLayoutManager
     * 2.布局的方向：Vertical 或 Horizontal
     * 3.如果是Vertical竖直布局，最后一列的判断标准
     * （1）用当前view的位置（position+1）对默认每行的item数取余，余数为0即为最后一列
     * （2）如果是最后一行，那么最后一个元素，也视为最后一列
     * <p>
     * 4.如果是Horizontal横向布局，最后一列的判断标准
     * （1）一种情况是最后一列未满，那么用总item对默认每列item数取余得到A，然后用总item-A得到排满了的最后一列最后一个元素，这个元素之后就是最后一列
     * （2）一种情况是最后一列已满，用总item-默认每列的item数，得到倒数第二列最后一个元素的位置，以后的就是最后一列
     *
     * @param parent
     * @param position
     * @param spanCount
     * @param childCount
     * @return
     */
    private boolean isLastColum(RecyclerView parent, int position, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if (((GridLayoutManager) layoutManager).getOrientation() == GRIDLAYOUT_HORIZONTAL) {
                int LastRowFristPosition = -1;
                if (childCount % spanCount == 0) {

                    LastRowFristPosition = childCount - spanCount;

                } else {
                    LastRowFristPosition = childCount - childCount % spanCount;
                }
                if (position >= LastRowFristPosition) {

                    return true;
                }
            } else {
                if ((position + 1) % spanCount == 0 || position == childCount - 1) {
                    return true;
                }
            }

        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            if (((StaggeredGridLayoutManager) layoutManager).getOrientation() == STAGGERED_GRIDLAYOUT_HORIZONTAL) {
                int LastRowFristPosition = -1;
                if (childCount % spanCount == 0) {
                    LastRowFristPosition = childCount - spanCount;

                } else {
                    LastRowFristPosition = childCount - childCount % spanCount;
                }
                if (position >= LastRowFristPosition) {
                    return  true;
                }
            } else {
                if ((position + 1) % spanCount == 0 || position == childCount - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取设置的每行或每列元素个数
     *
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();

        }
        return spanCount;
    }
}
