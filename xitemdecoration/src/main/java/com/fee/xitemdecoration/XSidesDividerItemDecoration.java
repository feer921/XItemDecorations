package com.fee.xitemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.View;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2018/9/12<br>
 * Time: 17:20<br>
 * <P>DESC:
 * 可为RecyclerView itemView配置多边[左，上，右，底]的divider分隔的装饰
 * </p>
 * ******************(^_^)***********************
 */
public abstract class XSidesDividerItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private Context context;

    public XSidesDividerItemDecoration(Context context) {
        this.context = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //left, top, right, bottom
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int itemPosition = ((RecyclerView.LayoutParams) child.getLayoutParams()).getViewLayoutPosition();
            XSidesDivider divider = getItemDivider(itemPosition);
            int INDEX_SIDE_WIDTH = 0;
            int INDEX_PADDING_START = 1;
            int INDEX_PADDING_END = 2;
            int INDEX_SIDE_COLOR = 3;
            if (divider != null) {
                //左
                int[] sideDividerInfos = extractSideDividerInfos(divider.getLeftSideDivider());
                if (sideDividerInfos != null) {
                    drawChildLeftVertical(
                            child,
                            c,
                            parent,
                            sideDividerInfos[INDEX_SIDE_COLOR],
                            sideDividerInfos[INDEX_SIDE_WIDTH],
                            sideDividerInfos[INDEX_PADDING_START],
                            sideDividerInfos[INDEX_PADDING_END]
                            );
                }
                //上
                sideDividerInfos = extractSideDividerInfos(divider.getTopSideDivider());
                if (sideDividerInfos != null) {
                    drawChildTopHorizontal(
                            child,
                            c,
                            parent,
                            sideDividerInfos[INDEX_SIDE_COLOR],
                            sideDividerInfos[INDEX_SIDE_WIDTH],
                            sideDividerInfos[INDEX_PADDING_START],
                            sideDividerInfos[INDEX_PADDING_END]
                    );
                }
                //右
                sideDividerInfos = extractSideDividerInfos(divider.getRightSideDivider());
                if (sideDividerInfos != null) {
                    drawChildRightVertical(
                            child,
                            c,
                            parent,
                            sideDividerInfos[INDEX_SIDE_COLOR],
                            sideDividerInfos[INDEX_SIDE_WIDTH],
                            sideDividerInfos[INDEX_PADDING_START],
                            sideDividerInfos[INDEX_PADDING_END]
                    );
                }
                //底
                sideDividerInfos = extractSideDividerInfos(divider.getBottomSideDivider());
                if (sideDividerInfos != null) {
                    drawChildBottomHorizontal(
                            child,
                            c,
                            parent,
                            sideDividerInfos[INDEX_SIDE_COLOR],
                            sideDividerInfos[INDEX_SIDE_WIDTH],
                            sideDividerInfos[INDEX_PADDING_START],
                            sideDividerInfos[INDEX_PADDING_END]
                    );
                }
            }
        }
    }

    /**
     * int[0] = sideWidthPx
     * int[1] = sidePaddingStart
     * int[2] = sidePaddingEnd
     * int[3] = sideColor
     * @param theSideDivider 当前可能需要绘制的边divider
     * @return theSideDivider的需要绘制信息
     */
    private int[] extractSideDividerInfos(SideDivider theSideDivider) {
        if (theSideDivider == null || !theSideDivider.isNeedDraw()) {
            return null;
        }
        int sideWidthPx = PxUtil.dp2Px(context, theSideDivider.getSideWidthDp());
        int sidePaddingStart = PxUtil.dp2Px(context, theSideDivider.getSidePaddingStartDp());
        int sidePaddingEnd = PxUtil.dp2Px(context, theSideDivider.getSidePaddingEndDp());
        int sideColor = theSideDivider.getDividerColor();
        return new int[]{sideWidthPx, sidePaddingStart, sidePaddingEnd, sideColor};
    }
    private void drawChildBottomHorizontal(View child, Canvas c, RecyclerView parent, @ColorInt int color, int lineWidthPx, int startPaddingPx, int endPaddingPx) {

        int leftPadding = 0;
        int rightPadding = 0;

        if (startPaddingPx <= 0) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            leftPadding = -lineWidthPx;
        } else {
            leftPadding = startPaddingPx;
        }

        if (endPaddingPx <= 0) {
            rightPadding = lineWidthPx;
        } else {
            rightPadding = -endPaddingPx;
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int left = child.getLeft() - params.leftMargin + leftPadding;
        int right = child.getRight() + params.rightMargin + rightPadding;
        int top = child.getBottom() + params.bottomMargin;
        int bottom = top + lineWidthPx;
        mPaint.setColor(color);

        c.drawRect(left, top, right, bottom, mPaint);

    }

    private void drawChildTopHorizontal(View child, Canvas c, RecyclerView parent, @ColorInt int color, int lineWidthPx, int startPaddingPx, int endPaddingPx) {
        int leftPadding = 0;
        int rightPadding = 0;

        if (startPaddingPx <= 0) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            leftPadding = -lineWidthPx;
        } else {
            leftPadding = startPaddingPx;
        }
        if (endPaddingPx <= 0) {
            rightPadding = lineWidthPx;
        } else {
            rightPadding = -endPaddingPx;
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int left = child.getLeft() - params.leftMargin + leftPadding;
        int right = child.getRight() + params.rightMargin + rightPadding;
        int bottom = child.getTop() - params.topMargin;
        int top = bottom - lineWidthPx;
        mPaint.setColor(color);

        c.drawRect(left, top, right, bottom, mPaint);

    }

    private void drawChildLeftVertical(View child, Canvas c, RecyclerView parent, @ColorInt int color, int lineWidthPx, int startPaddingPx, int endPaddingPx) {
        int topPadding = 0;
        int bottomPadding = 0;

        if (startPaddingPx <= 0) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            topPadding = -lineWidthPx;
        } else {
            topPadding = startPaddingPx;
        }
        if (endPaddingPx <= 0) {
            bottomPadding = lineWidthPx;
        } else {
            bottomPadding = -endPaddingPx;
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int top = child.getTop() - params.topMargin + topPadding;
        int bottom = child.getBottom() + params.bottomMargin + bottomPadding;
        int right = child.getLeft() - params.leftMargin;
        int left = right - lineWidthPx;
        mPaint.setColor(color);

        c.drawRect(left, top, right, bottom, mPaint);

    }

    private void drawChildRightVertical(View child, Canvas c, RecyclerView parent, @ColorInt int color, int lineWidthPx, int startPaddingPx, int endPaddingPx) {

        int topPadding = 0;
        int bottomPadding = 0;

        if (startPaddingPx <= 0) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            topPadding = -lineWidthPx;
        } else {
            topPadding = startPaddingPx;
        }
        if (endPaddingPx <= 0) {
            bottomPadding = lineWidthPx;
        } else {
            bottomPadding = -endPaddingPx;
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int top = child.getTop() - params.topMargin + topPadding;
        int bottom = child.getBottom() + params.bottomMargin + bottomPadding;
        int left = child.getRight() + params.rightMargin;
        int right = left + lineWidthPx;
        mPaint.setColor(color);

        c.drawRect(left, top, right, bottom, mPaint);

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        //outRect 看源码可知这里只是把Rect类型的outRect作为一个封装了left,right,top,bottom的数据结构,
        //作为传递left,right,top,bottom的偏移值来用的

        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

        XSidesDivider divider = getItemDivider(itemPosition);

        if (divider == null) {
            divider = provideDefXSideDividerBuilder().buildXSidesDivider();
        }

        int left = 0, top = 0, right = 0, bottom = 0;

        left = gainTheSideDividerWithPx(divider.getLeftSideDivider());

        top = gainTheSideDividerWithPx(divider.getTopSideDivider());

        right = gainTheSideDividerWithPx(divider.getRightSideDivider());

        bottom = gainTheSideDividerWithPx(divider.getBottomSideDivider());

        outRect.set(left, top, right, bottom);
    }

    private int gainTheSideDividerWithPx(SideDivider curSideDivider) {
        if (curSideDivider != null && curSideDivider.isNeedDraw()) {
            return PxUtil.dp2Px(context, curSideDivider.getSideWidthDp());
        }
        return 0;
    }
    /**
     * 在RecyclerView绘制时，根据当前绘制的itemPosition来获取当前是否有Divider
     * @param itemPosition 当前RecyclerView所绘制的item位置
     * @return 当前itemview可能需要绘制的XSidesDivider分隔线(装饰)
     */
    public abstract @Nullable XSidesDivider getItemDivider(int itemPosition);

    /**
     * 提供一个默认的XSideDividerBuilder来创建XSideDivider,以免为空
     * @return
     */
    protected XSidesDividerBuilder provideDefXSideDividerBuilder() {
        return new XSidesDividerBuilder();
    }
}
