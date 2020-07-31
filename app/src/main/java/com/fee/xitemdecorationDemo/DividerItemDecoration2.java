package com.fee.xitemdecorationDemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2019/10/29<br>
 * Time: 14:16<br>
 * <P>DESC:
 * 注：测试用的，毫无参考意义
 * </p>
 * ******************(^_^)***********************
 */
public class DividerItemDecoration2 extends RecyclerView.ItemDecoration {
    private Paint mPaint;

    // 在构造函数里进行绘制的初始化，如画笔属性设置等
    public DividerItemDecoration2() {

        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        // 画笔颜色设置为红色
    }

    // 重写getItemOffsets（）方法
    // 作用：设置矩形OutRect 与 Item 的间隔区域
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);


        int itemPosition = parent.getChildAdapterPosition(view);
        // 获得每个Item的位置

        outRect.set(0, 0, 0, 10);
//        // 第1个Item不绘制分割线
//        if (itemPosition != 0) {
//            outRect.set(0, 0, 0, 10);
//            // 设置间隔区域为10px,即onDraw()可绘制的区域为10px
//        }
    }

    // 重写onDraw（）
    // 作用:在间隔区域里绘制一个矩形，即分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        // 获取RecyclerView的Child view的个数
        int childCount = parent.getChildCount();

        // 遍历每个Item，分别获取它们的位置信息，然后再绘制对应的分割线
        for ( int i = 0; i < childCount; i++ ) {
            // 获取每个Item的位置
            final View child = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(child);
//            // 第1个Item不需要绘制
//            if ( index == 0 ) {
//                continue;
//            }

            // 获取布局参数
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            // 设置矩形(分割线)的宽度为10px
            final int mDivider = 10;

            // 根据子视图的位置 & 间隔区域，设置矩形（分割线）的2个顶点坐标(左上 & 右下)

            // 矩形左上顶点 = (ItemView的左边界,ItemView的下边界)
            // ItemView的左边界 = RecyclerView 的左边界 + paddingLeft距离 后的位置
//            final int left = parent.getPaddingLeft();
            Log.i("info", "--> onDraw() left margin = " + params.leftMargin);
            final int left = child.getLeft() - params.leftMargin;
//            final int left = 0;
            // ItemView的下边界：ItemView 的 bottom坐标 + 距离RecyclerView底部距离 +translationY
            final int top = child.getBottom() + params.bottomMargin +
                    Math.round(ViewCompat.getTranslationY(child));

            // 矩形右下顶点 = (ItemView的右边界,矩形的下边界)
            // ItemView的右边界 = RecyclerView 的右边界减去 paddingRight 后的坐标位置
//            final int right = parent.getWidth() - parent.getPaddingRight();
            final int right = child.getRight();
            // 绘制分割线的下边界 = ItemView的下边界+分割线的高度
            final int bottom = top + mDivider;

            Rect rect = new Rect(left, top, right, bottom);
            Log.i("info", "-->  onDraw() rect = " + rect);

            if (index == 1) {
                mPaint.setColor(Color.BLACK);
            }
            else {
                mPaint.setColor(Color.GREEN);
            }

            // 通过Canvas绘制矩形（分割线）
            c.drawRect(left,top,right,bottom,mPaint);
        }
    }
}
