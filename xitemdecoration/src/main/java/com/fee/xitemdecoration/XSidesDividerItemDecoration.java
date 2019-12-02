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

    protected boolean isDebugLog = false;

    protected String extraTag4Debug;
    /**
     * 是否可重用XSideDivider
     * def:true
     */
    protected boolean isCanUseCacheXSidesDivider = true;

    /**
     * 在绘制各边的divider时是否要考虑当前[ItemView]的布局参数的 [margin] 边距
     * 即效果为 |  [ItemView] 则divider示意为
     *         ~~~~~~~~~~~~~
     * def: true,如果为false时，则divider为紧贴ItemView各边
     */
    protected boolean isNeedConsiderItemViewMargin = true;

    /**
     * 在绘制ItemView 的左、上、右、底 边的Divider时，如果左和上、底相交
     * 或者如果右边和上、底相交，是否需要填充掉
     */
    protected boolean isNeedFillDividersCrossGap = true;

    /**
     * 所绘制的边的宽/高、缩进的值的单位是否使用DP为单位
     * def:使用DP为单位
     * false:使用px像素为单位
     */
    protected boolean isSideValuesUseDpUnit = true;

    /**
     * 是否需要绘制透明的矩形
     * def:true
     */
    private boolean isNeedDrawTransparentRect = true;

    /**
     * 本次被装饰的RecyclerView总共有多少个ItemView
     */
    protected int totalItemViewCount;
    public XSidesDividerItemDecoration(Context context) {
        this.context = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public XSidesDividerItemDecoration() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }
    private static final int INDEX_SIDE_WIDTH = 0x10;
    private static final int INDEX_PADDING_START = INDEX_SIDE_WIDTH + 1;
    private static final int INDEX_PADDING_END = INDEX_PADDING_START + 1;
    private static final int INDEX_SIDE_COLOR = INDEX_PADDING_END + 1;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect,view,parent,state);
        if (context == null) {
            context = view.getContext();
            if (context != null) {
                context = context.getApplicationContext();
            }
        }
        //outRect 看源码可知这里只是把Rect类型的outRect作为一个封装了left,right,top,bottom的数据结构,
        //作为传递left,right,top,bottom的偏移值来用的
        int itemInAdapterPosition = parent.getChildAdapterPosition(view);
        int childLayoutPosition = parent.getChildLayoutPosition(view);
        debugInfo("-->getItemOffsets() itemInAdapterPosition = " + itemInAdapterPosition + " childLayoutPosition = " + childLayoutPosition);

        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        totalItemViewCount = theRealItemsCount();
        if (totalItemViewCount == -1) {
            RecyclerView.Adapter adapter = parent.getAdapter();
            if (adapter != null) {
                totalItemViewCount = adapter.getItemCount();
            }
            else {
                totalItemViewCount = -1;
            }
        }
        if (totalItemViewCount <= 0) {
            return;
        }
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
        super.onDraw(c,parent,state);
        if (context == null) {
            context = parent.getContext();
            if (context != null) {
                context.getApplicationContext();
            }
        }
        //left, top, right, bottom
        int visibleChildCount = parent.getChildCount();//注意：这里表示 当前屏幕上(可见区域)的child view
        totalItemViewCount = theRealItemsCount();
        if (totalItemViewCount == -1) {
            RecyclerView.Adapter adapter = parent.getAdapter();
            if (adapter != null) {
                totalItemViewCount = adapter.getItemCount();
            }
        }
        debugInfo("-->onDraw()  childCount = " + visibleChildCount + " totalItemsCount = " + totalItemViewCount
        );
        if (totalItemViewCount <= 0) {//to do 要判断这个？？
            return;
        }
        for (int i = 0; i < visibleChildCount; i++) {
            View child = parent.getChildAt(i);
            int itemViewPos = parent.getChildAdapterPosition(child);
//            int itemViewPos = parent.getChildLayoutPosition(child);

//            int itemPosition = ((RecyclerView.LayoutParams) child.getLayoutParams()).getViewLayoutPosition();
            XSidesDivider divider = providerItemDivider(itemViewPos);
            boolean is1stItem = itemViewPos == 0;
            boolean isLastItem = (itemViewPos == totalItemViewCount - 1);
            if (divider != null) {
                //左 todo ??这个也可以缓存??
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
                else {
                    debugInfo(" child index = " + i + " not need draw left side divider ");
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
                else {
                    debugInfo(" child index = " + i + " not need draw Top side divider ");
                }
                //右
                sideDividerInfos = extractSidesDividerInfos(is1stItem, isLastItem, divider.getRightSideDivider());
//                sideDividerInfos = extractSideDividerInfos(divider.getRightSideDivider());
                if (sideDividerInfos != null) {
                    drawChildRightVertical(i,
                            child,
                            c,
                            parent,
                            sideDividerInfos.get(INDEX_SIDE_COLOR),
                            sideDividerInfos.get(INDEX_SIDE_WIDTH),
                            sideDividerInfos.get(INDEX_PADDING_START),
                            sideDividerInfos.get(INDEX_PADDING_END)
                    );
                }
                else {
                    debugInfo("child index = " + i + " not need draw right side divider ");
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
                else {
                    debugInfo(" child index = " + i + " not need draw bottom side divider ");
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
        boolean isSideValueUseDpUnit = theSideDivider.isActiveSetValueUnit() ? theSideDivider.isSideValuesUseDpUnit() : isSideValuesUseDpUnit;
        float sideWithValue = theSideDivider.getSideWidthValue();
        int sideWidthPx = isSideValueUseDpUnit ? PxUtil.dp2Px(context, sideWithValue) : (int) sideWithValue;
        float sidePaddingStartValue = theSideDivider.getSidePaddingStartValue();
        int sidePaddingStart = isSideValueUseDpUnit ? PxUtil.dp2Px(context, sidePaddingStartValue) : (int) sidePaddingStartValue;
        float sidePaddingEndValue = theSideDivider.getSidePaddingEndValue();
        int sidePaddingEnd = isSideValueUseDpUnit ? PxUtil.dp2Px(context, sidePaddingEndValue) : (int) sidePaddingEndValue;
        int sideColor = theSideDivider.getDividerColor();
        dividerInfos = new SparseIntArray(4);
        dividerInfos.put(INDEX_SIDE_WIDTH, sideWidthPx);
        dividerInfos.put(INDEX_PADDING_START, sidePaddingStart);
        dividerInfos.put(INDEX_PADDING_END,sidePaddingEnd);
        dividerInfos.put(INDEX_SIDE_COLOR, sideColor);
        return dividerInfos;
    }

    protected static final int DIVIDER_POS_LEFT = 1;
    protected static final int DIVIDER_POS_TOP = 2;
    protected static final int DIVIDER_POS_RIGHT = 3;
    protected static final int DIVIDER_POS_BOTTTOM = 4;

    /**
     * 绘制 ItemView 左侧的divider 矩形
     * @param child 当前 ItemView
     * @param c 画布
     * @param parent RecyclerView
     * @param color 画笔颜色
     * @param lineWidthPx 要绘制的空间
     * @param startPaddingPx 矩形Divider 左边距
     * @param endPaddingPx 矩形Divider 右边距
     */
    private void drawChildLeftVertical(View child, Canvas c, RecyclerView parent, @ColorInt int color, int lineWidthPx,
                                       int startPaddingPx, int endPaddingPx) {
        int topPadding = 0;
        int bottomPadding = 0;

        if (startPaddingPx <= 0 && isNeedFillDividersCrossGap) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            topPadding = -lineWidthPx;
        } else {
            topPadding = startPaddingPx;
        }
        if (endPaddingPx <= 0 && isNeedFillDividersCrossGap) {
            bottomPadding = lineWidthPx;
        } else {
            bottomPadding = -endPaddingPx;
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int top = child.getTop() + topPadding;
        if (isNeedConsiderItemViewMargin) {
            top -= params.topMargin;
        }
        int bottom = child.getBottom() + bottomPadding;
        if (isNeedConsiderItemViewMargin) {
            bottom += params.bottomMargin;
        }
        int right = child.getLeft();
        if (isNeedConsiderItemViewMargin) {
            right -= params.leftMargin;
        }

        int left = right - lineWidthPx;
        mPaint.setColor(color);
        Rect drawRect = new Rect(left, top, right, bottom);
        boolean isInterceptedDraw = interceptDrawItemViewDividers(DIVIDER_POS_LEFT, child, c, parent, drawRect);
        if (isInterceptedDraw) {
            return;
        }
        if (color != 0 || isNeedDrawTransparentRect) {
            c.drawRect(left, top, right, bottom, mPaint);
        }
        extraDrawItemViewDividers(DIVIDER_POS_LEFT, child, c, parent, drawRect);
    }



    /**
     * 绘制 ItemView 顶部的divider 矩形
     * @param child 当前 ItemView
     * @param c 画布
     * @param parent RecyclerView
     * @param color 画笔颜色
     * @param lineWidthPx 要绘制的空间
     * @param startPaddingPx 矩形Divider 左边距
     * @param endPaddingPx 矩形Divider 右边距
     */
    private void drawChildTopHorizontal(View child, Canvas c, RecyclerView parent, @ColorInt int color, int lineWidthPx,
                                        int startPaddingPx, int endPaddingPx) {
        int leftPadding = 0;
        int rightPadding = 0;

        if (startPaddingPx <= 0 && isNeedFillDividersCrossGap) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            leftPadding = -lineWidthPx;
        } else {
            leftPadding = startPaddingPx;
        }
        if (endPaddingPx <= 0 && isNeedFillDividersCrossGap) {
            rightPadding = lineWidthPx;
        } else {
            rightPadding = -endPaddingPx;
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int left = child.getLeft() + leftPadding;
        if (isNeedConsiderItemViewMargin) {
            left -= params.leftMargin;
        }

        int top = child.getTop() - lineWidthPx;//要在ItemView 的top还要往Y轴负方向偏移 矩形高度
        if (isNeedConsiderItemViewMargin) {
            top -= params.topMargin;
        }
        int right = child.getRight() + rightPadding;
        if (isNeedConsiderItemViewMargin) {
            right += params.rightMargin;
        }
        int bottom = top + lineWidthPx;
        mPaint.setColor(color);
        Rect drawRect = new Rect(left, top, right, bottom);
        boolean isIntercepted = interceptDrawItemViewDividers(DIVIDER_POS_TOP, child, c, parent, drawRect);
        if (isIntercepted) {
            return;
        }
        if (color != 0 || isNeedDrawTransparentRect) {
            c.drawRect(left, top, right, bottom, mPaint);
        }
//        c.drawRect(left, top, right, bottom, mPaint);
        extraDrawItemViewDividers(DIVIDER_POS_TOP, child, c, parent, drawRect);
    }

    /**
     * 绘制 ItemView 右侧的divider 矩形
     * @param child 当前 ItemView
     * @param c 画布
     * @param parent RecyclerView
     * @param color 画笔颜色
     * @param lineWidthPx 要绘制的空间
     * @param startPaddingPx 矩形Divider 左边距
     * @param endPaddingPx 矩形Divider 右边距
     */
    private void drawChildRightVertical(int childIndex,View child, Canvas c, RecyclerView parent, @ColorInt int color, int lineWidthPx,
                                        int startPaddingPx, int endPaddingPx) {

        int topPadding = 0;
        int bottomPadding = 0;

        if (startPaddingPx <= 0 && isNeedFillDividersCrossGap) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            topPadding = -lineWidthPx;
        } else {
            topPadding = startPaddingPx;
        }
        if (endPaddingPx <= 0 && isNeedFillDividersCrossGap) {
            bottomPadding = lineWidthPx;
        } else {
            bottomPadding = -endPaddingPx;
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int left = child.getRight();
        if (isNeedConsiderItemViewMargin) {
            left += params.rightMargin;
        }
        int top = child.getTop() + topPadding;
        if (isNeedConsiderItemViewMargin) {
            top -= params.topMargin;
        }
        int right = left + lineWidthPx;
        int bottom = child.getBottom() + bottomPadding;
        if (isNeedConsiderItemViewMargin) {
            bottom += params.bottomMargin;
        }
        mPaint.setColor(color);
        Rect drawRect = new Rect(left, top, right, bottom);
        boolean isIntercepted = interceptDrawItemViewDividers(DIVIDER_POS_RIGHT, child, c, parent, drawRect);
        if (isIntercepted) {
            return;
        }
        if (color != 0 || isNeedDrawTransparentRect) {
            c.drawRect(left, top, right, bottom, mPaint);
        }
//        c.drawRect(left, top, right, bottom, mPaint);
        debugInfo("--> drawChildRightVertical() childIndex = " + childIndex + " drawRect = " + drawRect + "  lineWidthPx = " + lineWidthPx);
        extraDrawItemViewDividers(DIVIDER_POS_RIGHT, child, c, parent, drawRect);
    }

    /**
     * 绘制itemView底部的divider 矩形
     * @param child 当前 ItemView
     * @param c 画布
     * @param parent RecyclerView
     * @param color 画笔颜色
     * @param lineWidthPx 要绘制的空间
     * @param startPaddingPx 矩形Divider 左边距
     * @param endPaddingPx 矩形Divider 右边距
     */
    private void drawChildBottomHorizontal(View child, Canvas c, RecyclerView parent, @ColorInt int color, int lineWidthPx,
                                           int startPaddingPx, int endPaddingPx) {
        int leftPadding = 0;
        int rightPadding = 0;

        if (startPaddingPx <= 0 && isNeedFillDividersCrossGap) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            leftPadding = -lineWidthPx;
        } else {
            leftPadding = startPaddingPx;
        }

        if (endPaddingPx <= 0 && isNeedFillDividersCrossGap) {
            rightPadding = lineWidthPx;
        } else {
            rightPadding = -endPaddingPx;
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int childLeft = child.getLeft();
        int left;
        if (isNeedConsiderItemViewMargin) {
            left = childLeft - params.leftMargin + leftPadding;//表示如果ItemView有左外边距，则Divider的left边要向左偏移 边距的距离,其实呢？child.getLeft()就是margin的值
            //等效于
//            left = leftPadding;
        }
        else {
            left = childLeft + leftPadding;
        }

        int top = child.getBottom();
        if (isNeedConsiderItemViewMargin) {
            top += params.bottomMargin;
        }
        int right = child.getRight() + rightPadding;
        if (isNeedConsiderItemViewMargin) {
            right += params.rightMargin;
        }

        int bottom = top + lineWidthPx;
        mPaint.setColor(color);
        Rect drawRect = new Rect(left, top, right, bottom);
        //是否拦截绘制ItemView的底部 divider
        boolean interceptedDraw = interceptDrawItemViewDividers(DIVIDER_POS_BOTTTOM,child, c, parent, drawRect);
        if (interceptedDraw) {
            return;
        }
        //不拦截的话，默认绘制一个矩形
        if (color != 0 || isNeedDrawTransparentRect) {
            c.drawRect(left, top, right, bottom, mPaint);
        }
//        c.drawRect(left, top, right, bottom, mPaint);
        extraDrawItemViewDividers(DIVIDER_POS_BOTTTOM, child, c, parent, drawRect);
    }

    /**
     * 拦截绘制当前ItemView的某边Divider信息
     * 如果拦截了,则本类不对某边进行默认的矩形绘制
     * @param dividerPosition Divider的方位 参见:
     *                        <ul><li>{@link #DIVIDER_POS_LEFT}</li></ul>
     *                        <ul><li>{@link #DIVIDER_POS_TOP}</li></ul>
     *                        <ul><li>{@link #DIVIDER_POS_RIGHT}</li></ul>
     *                        <ul><li>{@link #DIVIDER_POS_BOTTTOM}</li></ul>
     * @param child 当前的ItemView
     * @param c 画布
     * @param parent 当前的RecyclerView
     * @param curDividerSpaceRect 当前边可绘制的矩形区域
     * @return true:拦截了，则本父类不进行默认的矩形绘制；false:不拦截
     */
    protected boolean interceptDrawItemViewDividers(int dividerPosition, View child, Canvas c, RecyclerView parent, Rect curDividerSpaceRect) {
        return false;
    }

    /**
     * 额外绘制当前ItemView的某边Divider信息
     * 即在本父类对某边进行默认的矩形绘制后，子类还想绘制相关的内容
     * @param dividerPosition Divider的方位 参见:
     *                        <ul><li>{@link #DIVIDER_POS_LEFT}</li></ul>
     *                        <ul><li>{@link #DIVIDER_POS_TOP}</li></ul>
     *                        <ul><li>{@link #DIVIDER_POS_RIGHT}</li></ul>
     *                        <ul><li>{@link #DIVIDER_POS_BOTTTOM}</li></ul>
     * @param child 当前的ItemView
     * @param c 画布
     * @param parent 当前的RecyclerView
     * @param curDividerSpaceRect 当前边可绘制的矩形区域
     */
    protected void extraDrawItemViewDividers(int dividerPosition, View child, Canvas c, RecyclerView parent, Rect curDividerSpaceRect){
        //here do nothing...
    }
    private int gainTheSideDividerWithPx(SideDivider curSideDivider) {
        if (curSideDivider != null && curSideDivider.isNeedDraw()) {
            float sideWidthValue = curSideDivider.getSideWidthValue();
            boolean isSideValueUseDpUnit = curSideDivider.isActiveSetValueUnit() ? curSideDivider.isSideValuesUseDpUnit() : isSideValuesUseDpUnit;
            if (isSideValueUseDpUnit) {
                return PxUtil.dp2Px(context, sideWidthValue);
            }
            return (int) sideWidthValue;
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




    public XSidesDividerItemDecoration setCanUseCacheXSidesDivider(boolean canUseCacheXSidesDivider) {
        this.isCanUseCacheXSidesDivider = canUseCacheXSidesDivider;
        return this;
    }

    public XSidesDividerItemDecoration setDebugLog(boolean isDebugLog) {
        this.isDebugLog = isDebugLog;
        return this;
    }

    public XSidesDividerItemDecoration setNeedConsiderItemViewMargin(boolean needConsiderItemViewMargin) {
        this.isNeedConsiderItemViewMargin = needConsiderItemViewMargin;
        return this;
    }

    public XSidesDividerItemDecoration setNeedFillDividerCrossGap(boolean needFillDividerCrossGap) {
        this.isNeedFillDividersCrossGap = needFillDividerCrossGap;
        return this;
    }

    public XSidesDividerItemDecoration letSideValueUseDpValue(boolean isSideValuesUseDpUnit) {
        this.isSideValuesUseDpUnit = isSideValuesUseDpUnit;
        return this;
    }

    public XSidesDividerItemDecoration setExtraDebugTag(String extraDebugTag) {
        this.extraTag4Debug = extraDebugTag;
        return this;
    }
    public XSidesDividerItemDecoration setNeedDrawTransparentRect(boolean needDrawTranslateRect) {
        isNeedDrawTransparentRect = needDrawTranslateRect;
        return this;
    }
    protected void debugInfo(String logContent) {
        if (isDebugLog) {
            L.d(extraTag4Debug != null ? extraTag4Debug : TAG, logContent);
        }
    }
    private IItemsCounter iItemsCounter;

    public XSidesDividerItemDecoration setItemsCounter(IItemsCounter itemsCounter) {
        this.iItemsCounter = itemsCounter;
        return this;
    }

    protected int theRealItemsCount() {
        if (iItemsCounter != null) {
            return iItemsCounter.countItemsCount();
        }
        return -1;
    }
    public interface IItemsCounter{
        int countItemsCount();
    }
}
