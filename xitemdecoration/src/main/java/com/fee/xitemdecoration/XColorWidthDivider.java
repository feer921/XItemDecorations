package com.fee.xitemdecoration;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2018/9/13<br>
 * Time: 20:55<br>
 * <P>DESC:
 * 可定义不同的divider颜色和宽度的默认为底部的divider
 * 注：默认为0xffdddddd颜色
 * </p>
 * ******************(^_^)***********************
 */
public class XColorWidthDivider extends XSidesDividerItemDecoration {
    @ColorInt
    protected int dividerColor = 0xffdddddd;
    /**
     * 虽然这里是定义名为宽，但因为本Divider为水平方向的，则看成是divider的高
     * 单位：dp
     */
    protected float dividerWidthDp = 0.5f;

    /**
     * 单位：dp
     */
    protected float dividerPaddingStartDp;

    /**
     * 单位：dp
     */
    protected float dividerPaddingEndDp;

    public XColorWidthDivider(Context context) {
        super(context);
    }

    public XColorWidthDivider withDividerColor(@ColorInt int dividerColor) {
        if (dividerColor > 0) {
            this.dividerColor = dividerColor;
        }
        return this;
    }

    public XColorWidthDivider withDividerWidthDp(float dividerWidthDp) {
        if (dividerWidthDp > 0) {
            this.dividerWidthDp = dividerWidthDp;
        }
        return this;
    }
    public XColorWidthDivider withDividerPaddingStartDp(float dividerPaddingStartDp) {
        if (dividerPaddingStartDp > 0) {
            this.dividerPaddingStartDp = dividerPaddingStartDp;
        }
        return this;
    }
    public XColorWidthDivider withDividerPaddingEndDp(float dividerPaddingEndDp) {
        if (dividerPaddingEndDp > 0) {
            this.dividerPaddingEndDp = dividerPaddingEndDp;
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
        SideDivider bottomSideDi = new SideDivider(true, dividerWidthDp, dividerPaddingStartDp, dividerPaddingEndDp, dividerColor);
        return provideDefXSideDividerBuilder().withBottomSidesDivider(bottomSideDi)
                .buildXSidesDivider();
    }
}
