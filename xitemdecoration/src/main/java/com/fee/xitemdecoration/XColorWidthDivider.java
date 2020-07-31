package com.fee.xitemdecoration;

import android.content.Context;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2018/9/13<br>
 * Time: 20:55<br>
 * <P>DESC:
 * 可定义不同的divider颜色和宽度的默认为底部的divider
 * 注：默认为0透明颜色
 * </p>
 * ******************(^_^)***********************
 */
public class XColorWidthDivider extends XSidesDividerItemDecoration {
    public XColorWidthDivider() {
    }

    public XColorWidthDivider(Context context) {
        super(context);
    }
    /**
     * Divider 的默认绘制颜色
     * def:透明
     */
    @ColorInt
    protected int dividerColor = 0;
    /**
     * 虽然这里是定义名为宽，但如果本Divider处在水平方向时，则看成是divider的高
     * 该值适合 item间 间距相等的情况,如果当前的RecyclerView为网格布局，并且item间的间距不相等的情况下，
     * 该值则看成除第一行上、第一列左、最后一列右、最后一行底外的 item间距(相等)
     * 单位：dp/px
     */
    protected float dividerWidthDpOrPxValue = 0.5f;

    /**
     * 单位：dp/px
     */
    protected float dividerPaddingStartDpOrPxValue;

    /**
     * 单位：dp/px
     */
    protected float dividerPaddingEndDpOrPxValue;


    public XColorWidthDivider withDividerColor(@ColorInt int dividerColor) {
        this.dividerColor = dividerColor;
        return this;
    }

    public XColorWidthDivider withDividerWidthValue(float dividerWidthDpOrPxValue) {
        if (dividerWidthDpOrPxValue > 0) {
            this.dividerWidthDpOrPxValue = dividerWidthDpOrPxValue;
        }
        return this;
    }
    public XColorWidthDivider withDividerPaddingStartValue(float dividerPaddingStartDpOrPxValue) {
        if (dividerPaddingStartDpOrPxValue > 0) {
            this.dividerPaddingStartDpOrPxValue = dividerPaddingStartDpOrPxValue;
        }
        return this;
    }
    public XColorWidthDivider withDividerPaddingEndValue(float dividerPaddingEndDpOrPxValue) {
        if (dividerPaddingEndDpOrPxValue > 0) {
            this.dividerPaddingEndDpOrPxValue = dividerPaddingEndDpOrPxValue;
        }
        return this;
    }

    /**
     * 在RecyclerView绘制时，根据当前绘制的itemPosition来获取当前是否有Divider
     *
     * @param itemPosition 当前RecyclerView所绘制的item位置
     * @return 当前itemview可能需要绘制的XSidesDivider分隔线(装饰)
     */
    @Nullable
    @Override
    public XSidesDivider getItemDivider(int itemPosition) {
        SideDivider bottomSideDivider = new SideDivider(true, dividerWidthDpOrPxValue, dividerPaddingStartDpOrPxValue, dividerPaddingEndDpOrPxValue, dividerColor);
        bottomSideDivider.withSideValuesUseDpValue(isSideValuesUseDpUnit);
        return provideDefXSideDividerBuilder()
                .withBottomSidesDivider(bottomSideDivider)
                .buildXSidesDivider();
    }
}
