package com.fee.xitemdecoration;

import android.support.annotation.ColorInt;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2018/9/12<br>
 * Time: 17:27<br>
 * <P>DESC:
 * 一个Divider所具有的边信息的表示对象，在本框架也看成是一个divider便于理解
 * 表示divider的属性信息：颜色、粗度(宽)、
 * </p>
 * ******************(^_^)***********************
 */
public class SideDivider {

    public SideDivider() {

    }

    public SideDivider(boolean isNeedDraw, float sideWidthDp, float sidePaddingStartDp, float sidePaddingEndDp,@ColorInt int dividerColor) {
        this.dividerColor = dividerColor;
        this.isNeedDraw = isNeedDraw;
        this.sideWidthDp = sideWidthDp;
        this.sidePaddingStartDp = sidePaddingStartDp;
        this.sidePaddingEndDp = sidePaddingEndDp;
    }

    /**
     * divider本边的填充颜色
     * eg.: 0xff00ff00
     */
    @ColorInt
    private int dividerColor;

    /**
     * 本边是否需要绘制
     */
    private boolean isNeedDraw;

    /**
     * divider本边的宽，如果是横向的为高，如果是竖向的为宽
     * 单位：dp
     * 横向：
     * ------------------
     * |-->sideWidthDp  |
     * ------------------
     * 竖向：
     * |---- -->sideWidthDp
     * |   |
     * |   |
     * |   |
     * |---|
     */
    private float sideWidthDp;

    /**
     * 本边绘制时的起始内边距(缩进)
     * 单位：dp
     * 水平方向左为start，垂直方向上为start
     */
    private float sidePaddingStartDp;

    /**
     * 本边绘制结束时的内边距(缩进)
     * 单位：dp
     * 水平方向右为end，垂直方向下为end
     */
    private float sidePaddingEndDp;

    public boolean isNeedDraw() {
        return isNeedDraw;
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public float getSideWidthDp() {
        return sideWidthDp;
    }

    public float getSidePaddingStartDp() {
        return sidePaddingStartDp;
    }

    public float getSidePaddingEndDp() {
        return sidePaddingEndDp;
    }

    public SideDivider withDividerColor(@ColorInt int sideDividerColor) {
        this.dividerColor = sideDividerColor;
        return this;
    }

    public SideDivider withNeedDraw(boolean needDraw) {
        this.isNeedDraw = needDraw;
        return this;
    }

    public SideDivider withSideWidthDp(float sideWidthDp) {
        this.sideWidthDp = sideWidthDp;
        return this;
    }

    public SideDivider withSidePaddingStartDp(float sidePaddingStartDp) {
        this.sidePaddingStartDp = sidePaddingStartDp;
        return this;
    }

    public SideDivider withSidePaddingEndDp(float sidePaddingEndDp) {
        this.sidePaddingEndDp = sidePaddingEndDp;
        return this;
    }
}
