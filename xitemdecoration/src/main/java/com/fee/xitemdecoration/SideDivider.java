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
    /**
     * divider边的位置：于左部
     */
    public static final int SIDE_POSITION_LEFT = 1;
    /**
     * divider边的位置：于上部
     */
    public static final int SIDE_POSITION_TOP = 2;
    /**
     * divider边的位置：于右部
     */
    public static final int SIDE_POSITION_RIGHT = 3;
    /**
     * divider边的位置：于底部
     */
    public static final int SIDE_POSITION_BOTTOM = 4;
    public SideDivider() {

    }

    public SideDivider(boolean isNeedDraw, float sideWidthValue, float sidePaddingStartValue, float sidePaddingEndValue,@ColorInt int dividerColor) {
        this.dividerColor = dividerColor;
        this.isNeedDraw = isNeedDraw;
        this.sideWidthValue = sideWidthValue;
        this.sidePaddingStartValue = sidePaddingStartValue;
        this.sidePaddingEndValue = sidePaddingEndValue;
    }

    /**
     * 当前divider当前边的位置
     */
    private int sidePostion;
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
     * 当itemview在列表第一个位置时，本边divider是否需要绘制
     * def: true; 如果本边是itemview divider的top边，则不应该绘制
     * 垂直列表时：第一个itemview,如果为【TOP】边，则不应该绘制
     * 水平列表时：第一个itemview,如果为【LEFT】边，则不应该绘制
     */
    private boolean isNeedDrawAt1stPos = true;

    /**
     * 当itemview在列表的最后位置时，本边divider是否需要绘制
     * def: false
     */
    private boolean isNeedDrawAtLastPos;

    /**
     * 本SideDivider所指定的值是否以DP为单位
     * def: DP 为单位
     * false:使用px像素为单位
     */
    private boolean isSideValuesUseDpUnit = true;

    /**
     * 是否主动设置了值所使用的单位
     */
    private boolean isActiveSetValueUnit;
    /**
     * divider本边的宽，如果是横向的为高，如果是竖向的为宽
     * 单位：dp/或者px (值的单位决定于{{@link #isSideValuesUseDpUnit}})
     * 横向：
     * ------------------
     * |-->sideWidthValue|
     * ------------------
     * 竖向：
     * |---- -->sideWidthValue
     * |   |
     * |   |
     * |   |
     * |---|
     */
    private float sideWidthValue;

    /**
     * 本边绘制时的起始内边距(缩进)
     * 单位：dp
     * 水平方向左为start，垂直方向上为start
     */
    private float sidePaddingStartValue;

    /**
     * 本边绘制结束时的内边距(缩进)
     * 单位：dp
     * 水平方向右为end，垂直方向下为end
     */
    private float sidePaddingEndValue;

    public boolean isNeedDraw() {
        return isNeedDraw;
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public float getSideWidthValue() {
        return sideWidthValue;
    }

    public float getSidePaddingStartValue() {
        return sidePaddingStartValue;
    }

    public float getSidePaddingEndValue() {
        return sidePaddingEndValue;
    }

    public SideDivider withDividerColor(@ColorInt int sideDividerColor) {
        this.dividerColor = sideDividerColor;
        return this;
    }

    public SideDivider withNeedDraw(boolean needDraw) {
        this.isNeedDraw = needDraw;
        return this;
    }

    public SideDivider withSideWidthValue(float sideWidthDpOrPxValue) {
        this.sideWidthValue = sideWidthDpOrPxValue;
        return this;
    }

    public SideDivider withSidePaddingStartValue(float sidePaddingStartDpOrPxValue) {
        this.sidePaddingStartValue = sidePaddingStartDpOrPxValue;
        return this;
    }

    public SideDivider withSidePaddingEndValue(float sidePaddingEndDpOrPxValue) {
        this.sidePaddingEndValue = sidePaddingEndDpOrPxValue;
        return this;
    }

    public SideDivider withNeedDrawAt1stPos(boolean needDrawAt1stPos) {
        this.isNeedDrawAt1stPos = needDrawAt1stPos;
        return this;
    }

    public SideDivider withNeedDrawAtLastPos(boolean needDrawAtLastPos) {
        this.isNeedDrawAtLastPos = needDrawAtLastPos;
        return this;
    }

    public SideDivider withSidePostion(int sidePosition) {
        this.sidePostion = sidePosition;
        return this;
    }

    public SideDivider withSideValuesUseDpValue(boolean isSideValuesUseDpUnit) {
        this.isSideValuesUseDpUnit = isSideValuesUseDpUnit;
        isActiveSetValueUnit = true;
        return this;
    }

    public boolean isSideValuesUseDpUnit() {
        return this.isSideValuesUseDpUnit;
    }
    public boolean isActiveSetValueUnit() {
        return this.isActiveSetValueUnit;
    }

    public boolean isNeedDrawAt1stPos() {
        return judgeDefNeedDrawAt1stOrLast(true, false);
//        return isNeedDrawAt1stPos;
    }

    public boolean isNeedDrawAtLastPos() {
//        return isNeedDrawAtLastPos;
        return judgeDefNeedDrawAt1stOrLast(false, true);
    }

    private boolean judgeDefNeedDrawAt1stOrLast(boolean whenItemAt1st, boolean whenItemAtLast) {
        if (sidePostion == 0) {//未赋值位置,则返回原来的配置
            if (whenItemAt1st) {
                return isNeedDrawAt1stPos;
            }
            if (whenItemAtLast) {
                return isNeedDrawAtLastPos;
            }
        }
        switch (sidePostion) {
            case SIDE_POSITION_LEFT://divider边在左边，则默认不管是item在列表头还是最后一个都显示/绘制,
                 break;
            case SIDE_POSITION_TOP:
                if (whenItemAt1st) {//在第一个时，不绘制
                    return false;
                }
            case SIDE_POSITION_RIGHT://在右侧时，则，都需要绘制,但横向时(在最后一个位置时)又不需要绘制

                break;
            case SIDE_POSITION_BOTTOM:
                if (whenItemAtLast) {
                    return false;
                }
                break;
        }
        return true;
    }
}
