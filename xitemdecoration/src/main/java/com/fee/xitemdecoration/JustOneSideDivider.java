package com.fee.xitemdecoration;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/8/4<br>
 * Time: 20:13<br>
 * <P>DESC:
 * 仅设置 一边的 Divider/分隔
 * 并且不需要绘制出来(相当于只保留间距)
 * 较适合非网络列表
 * </p>
 * ******************(^_^)***********************
 */
public class JustOneSideDivider extends RecyclerView.ItemDecoration {
    /**
     * Divider(分隔线)的空间/间距 的像素值
     */
    protected @Px int spaceOfDivider;

    /**
     * 本次 Divider的位置
     * 左
     * 上
     * 右
     * 底
     * def:{@link SideDivider#SIDE_POSITION_BOTTOM}
     */
    @IntRange(from = SideDivider.SIDE_POSITION_LEFT, to = SideDivider.SIDE_POSITION_BOTTOM)
    protected int positionOfDivider = SideDivider.SIDE_POSITION_BOTTOM;

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (spaceOfDivider > 0) {
            int left = 0;
            int top = 0;
            int right = 0;
            int bottom = 0;
            switch (positionOfDivider) {
                case SideDivider.SIDE_POSITION_LEFT:
                    left = spaceOfDivider;
                    break;
                case SideDivider.SIDE_POSITION_TOP:
                    top = spaceOfDivider;
                    break;
                case SideDivider.SIDE_POSITION_RIGHT:
                    right = spaceOfDivider;
                    break;
                case SideDivider.SIDE_POSITION_BOTTOM:
                    bottom = spaceOfDivider;
                    break;
            }
            outRect.set(left,top,right,bottom);
        }
    }

    public JustOneSideDivider withDividerPosition(@IntRange(from = SideDivider.SIDE_POSITION_LEFT, to = SideDivider.SIDE_POSITION_BOTTOM) int theDividerPostition) {
        this.positionOfDivider = theDividerPostition;
        return this;
    }

    public JustOneSideDivider withDividerSpaceValue(@Px int spaceOfDivider) {
        this.spaceOfDivider = spaceOfDivider;
        return this;
    }



}
