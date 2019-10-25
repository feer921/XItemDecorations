package com.fee.xitemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
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
    protected String TAG = getClass().getSimpleName();
    private Paint mPaint;
    private Context context;
    private SparseArray<XSidesDivider> xSidesDividerMapItemPosition = new SparseArray<>();

    private boolean isDebugLog = false;


    /**
     * 是否可重用XSideDivider
     * def:true
     */
    protected boolean isCanUseCacheXSidesDivider = true;

    public XSidesDividerItemDecoration(Context context) {
        this.context = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }
    private static final int INDEX_SIDE_WIDTH = 0x10;
    private static final int INDEX_PADDING_START = INDEX_SIDE_WIDTH + 1;
    private static final int INDEX_PADDING_END = INDEX_PADDING_START + 1;
    private static final int INDEX_SIDE_COLOR = INDEX_PADDING_END + 1;


    /**
     * Draw any appropriate decorations into the Canvas supplied to the RecyclerView.
     * Any content drawn by this method will be drawn before the item views are drawn,
     * and will thus appear underneath the views.
     * 该方法的回调表示：该方法所绘制的内容会在 itemView的绘制之前绘制的，所以所绘制的内容会在 itemView的下面
     * @param c Canvas to draw into
     * @param parent RecyclerView this ItemDecoration is drawing into
     * @param state The current state of RecyclerView
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //left, top, right, bottom
        int childCount = parent.getChildCount();//注意：这里表示 当前屏幕上(可见区域)的child view
        int totalItemsCount = 0;
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter != null) {
            totalItemsCount = adapter.getItemCount();
        }
        if (isDebugLog) {
            L.i(TAG, "-->onDraw()  childCount = " + childCount + " totalItemsCount = " + totalItemsCount
//                + " RecyclerView state = " + state
            );
        }
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int itemViewPos = parent.getChildLayoutPosition(child);

//            int itemPosition = ((RecyclerView.LayoutParams) child.getLayoutParams()).getViewLayoutPosition();
            XSidesDivider divider = providerItemDivider(itemViewPos);
            boolean is1stItem = itemViewPos == 0;
            boolean isLastItem = (itemViewPos == totalItemsCount - 1);
            if (divider != null) {
                //左
                SparseIntArray sideDividerInfos = extractSidesDividerInfos(is1stItem, isLastItem, divider.getLeftSideDivider());
//                int[] sideDividerInfos = extractSideDividerInfos(divider.getLeftSideDivider());
                if (sideDividerInfos != null) {
                    drawChildLeftVertical(
                            child,
                            c,
                            parent,
                            sideDividerInfos.get(INDEX_SIDE_COLOR),
                            sideDividerInfos.get(INDEX_SIDE_WIDTH),
                            sideDividerInfos.get(INDEX_PADDING_START),
                            sideDividerInfos.get(INDEX_PADDING_END)
                    );
                }
                //上
                sideDividerInfos = extractSidesDividerInfos(is1stItem, isLastItem, divider.getTopSideDivider());
//                sideDividerInfos = extractSideDividerInfos(divider.getTopSideDivider());
                if (sideDividerInfos != null) {
                    drawChildTopHorizontal(
                            child,
                            c,
                            parent,
                            sideDividerInfos.get(INDEX_SIDE_COLOR),
                            sideDividerInfos.get(INDEX_SIDE_WIDTH),
                            sideDividerInfos.get(INDEX_PADDING_START),
                            sideDividerInfos.get(INDEX_PADDING_END)
                    );
                }
                //右
                sideDividerInfos = extractSidesDividerInfos(is1stItem, isLastItem, divider.getRightSideDivider());
//                sideDividerInfos = extractSideDividerInfos(divider.getRightSideDivider());
                if (sideDividerInfos != null) {
                    drawChildRightVertical(
                            child,
                            c,
                            parent,
                            sideDividerInfos.get(INDEX_SIDE_COLOR),
                            sideDividerInfos.get(INDEX_SIDE_WIDTH),
                            sideDividerInfos.get(INDEX_PADDING_START),
                            sideDividerInfos.get(INDEX_PADDING_END)
                    );
                }
                //底
                sideDividerInfos = extractSidesDividerInfos(is1stItem, isLastItem, divider.getBottomSideDivider());
//                sideDividerInfos = extractSideDividerInfos(divider.getBottomSideDivider());
                if (sideDividerInfos != null) {
                    drawChildBottomHorizontal(
                            child,
                            c,
                            parent,
                            sideDividerInfos.get(INDEX_SIDE_COLOR),
                            sideDividerInfos.get(INDEX_SIDE_WIDTH),
                            sideDividerInfos.get(INDEX_PADDING_START),
                            sideDividerInfos.get(INDEX_PADDING_END)
                    );
                }
            }
        }
    }
    private SparseIntArray extractSidesDividerInfos(boolean itemViewAt1st, boolean itemViewAtLast, SideDivider theSideDivider) {
        SparseIntArray dividerInfos = null;
        if (theSideDivider == null || !theSideDivider.isNeedDraw()) {
            return null;
        }
        if (itemViewAt1st) {
            if (!theSideDivider.isNeedDrawAt1stPos()) {
                return null;
            }
        }
        if (itemViewAtLast) {
            if (!theSideDivider.isNeedDrawAtLastPos()) {
                return null;
            }
        }
        int sideWidthPx = PxUtil.dp2Px(context, theSideDivider.getSideWidthDp());
        int sidePaddingStart = PxUtil.dp2Px(context, theSideDivider.getSidePaddingStartDp());
        int sidePaddingEnd = PxUtil.dp2Px(context, theSideDivider.getSidePaddingEndDp());
        int sideColor = theSideDivider.getDividerColor();
        dividerInfos = new SparseIntArray(4);
        dividerInfos.put(INDEX_SIDE_WIDTH, sideWidthPx);
        dividerInfos.put(INDEX_PADDING_START, sidePaddingStart);
        dividerInfos.put(INDEX_PADDING_END,sidePaddingEnd);
        dividerInfos.put(INDEX_SIDE_COLOR, sideColor);
        return dividerInfos;
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

        XSidesDivider divider = providerItemDivider(itemPosition);

        if (divider == null) {
            divider = provideDefXSideDividerBuilder().buildXSidesDivider();
        }

        int left = 0, top = 0, right = 0, bottom = 0;

        left = gainTheSideDividerWithPx(divider.getLeftSideDivider());

        top = gainTheSideDividerWithPx(divider.getTopSideDivider());

        right = gainTheSideDividerWithPx(divider.getRightSideDivider());

        bottom = gainTheSideDividerWithPx(divider.getBottomSideDivider());

        Rect srcRect = new Rect(outRect);
        outRect.set(left, top, right, bottom);
        if (isDebugLog) {
            L.d(TAG, "-->getItemOffsets() srcRect = " + srcRect + " cur rect = " + outRect);
        }
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
     * 提供当前itemView 的 各边Divider
     * 此方法子类也可以重写掉
     * 注：先去缓存里找有没有对应的XSidesDivider
     * @param curItemPosition 当前RecyclerView中的itemView
     * @return XSidesDivider
     */
    protected XSidesDivider providerItemDivider(int curItemPosition) {
        XSidesDivider xSidesDivider = null;
        if (xSidesDividerMapItemPosition != null && isCanUseCacheXSidesDivider) {
            xSidesDivider = xSidesDividerMapItemPosition.get(curItemPosition);
        }
        if (xSidesDivider == null) {
            xSidesDivider = getItemDivider(curItemPosition);
            if (isCanUseCacheXSidesDivider) {
                xSidesDividerMapItemPosition.put(curItemPosition, xSidesDivider);
            }
        }
        return xSidesDivider;
    }
    /**
     * 提供一个默认的XSideDividerBuilder来创建XSideDivider,以免为空
     * @return
     */
    protected XSidesDividerBuilder provideDefXSideDividerBuilder() {
        return new XSidesDividerBuilder();
    }



    //    @Override
//    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        //left, top, right, bottom
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = parent.getChildAt(i);
//            int itemPosition = ((RecyclerView.LayoutParams) child.getLayoutParams()).getViewLayoutPosition();
//            XSidesDivider divider = getItemDivider(itemPosition);
//            int INDEX_SIDE_WIDTH = 0;
//            int INDEX_PADDING_START = 1;
//            int INDEX_PADDING_END = 2;
//            int INDEX_SIDE_COLOR = 3;
//            if (divider != null) {
//                //左
//                int[] sideDividerInfos = extractSideDividerInfos(divider.getLeftSideDivider());
//                if (sideDividerInfos != null) {
//                    drawChildLeftVertical(
//                            child,
//                            c,
//                            parent,
//                            sideDividerInfos[INDEX_SIDE_COLOR],
//                            sideDividerInfos[INDEX_SIDE_WIDTH],
//                            sideDividerInfos[INDEX_PADDING_START],
//                            sideDividerInfos[INDEX_PADDING_END]
//                            );
//                }
//                //上
//                sideDividerInfos = extractSideDividerInfos(divider.getTopSideDivider());
//                if (sideDividerInfos != null) {
//                    drawChildTopHorizontal(
//                            child,
//                            c,
//                            parent,
//                            sideDividerInfos[INDEX_SIDE_COLOR],
//                            sideDividerInfos[INDEX_SIDE_WIDTH],
//                            sideDividerInfos[INDEX_PADDING_START],
//                            sideDividerInfos[INDEX_PADDING_END]
//                    );
//                }
//                //右
//                sideDividerInfos = extractSideDividerInfos(divider.getRightSideDivider());
//                if (sideDividerInfos != null) {
//                    drawChildRightVertical(
//                            child,
//                            c,
//                            parent,
//                            sideDividerInfos[INDEX_SIDE_COLOR],
//                            sideDividerInfos[INDEX_SIDE_WIDTH],
//                            sideDividerInfos[INDEX_PADDING_START],
//                            sideDividerInfos[INDEX_PADDING_END]
//                    );
//                }
//                //底
//                sideDividerInfos = extractSideDividerInfos(divider.getBottomSideDivider());
//                if (sideDividerInfos != null) {
//                    drawChildBottomHorizontal(
//                            child,
//                            c,
//                            parent,
//                            sideDividerInfos[INDEX_SIDE_COLOR],
//                            sideDividerInfos[INDEX_SIDE_WIDTH],
//                            sideDividerInfos[INDEX_PADDING_START],
//                            sideDividerInfos[INDEX_PADDING_END]
//                    );
//                }
//            }
//        }
//    }

//    /**
//     * int[0] = sideWidthPx
//     * int[1] = sidePaddingStart
//     * int[2] = sidePaddingEnd
//     * int[3] = sideColor
//     * @param theSideDivider 当前可能需要绘制的边divider
//     * @return theSideDivider的需要绘制信息
//     */
//    private int[] extractSideDividerInfos(SideDivider theSideDivider) {
//        if (theSideDivider == null || !theSideDivider.isNeedDraw()) {
//            return null;
//        }
//        int sideWidthPx = PxUtil.dp2Px(context, theSideDivider.getSideWidthDp());
//        int sidePaddingStart = PxUtil.dp2Px(context, theSideDivider.getSidePaddingStartDp());
//        int sidePaddingEnd = PxUtil.dp2Px(context, theSideDivider.getSidePaddingEndDp());
//        int sideColor = theSideDivider.getDividerColor();
//        return new int[]{sideWidthPx, sidePaddingStart, sidePaddingEnd, sideColor};
//    }

    public void setCanUseCacheXSidesDivider(boolean canUseCacheXSidesDivider) {
        this.isCanUseCacheXSidesDivider = canUseCacheXSidesDivider;
    }

    public void setDebugLog(boolean isDebugLog) {
        this.isDebugLog = isDebugLog;
    }
}
