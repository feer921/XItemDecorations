package com.fee.xitemdecoration;

import android.support.annotation.Nullable;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2019/10/31<br>
 * Time: 21:28<br>
 * <P>DESC:
 * 给RecyclerView使用 GridLayoutManager 来布局ItemView的 ItemDecoration
 * 一般情况为只需要设置ItemView的底部和右侧(ItemView处于网格的最后一列时不绘制)的Divider即可
 * </p>
 * ******************(^_^)***********************
 */
public class GridItemDivider extends XColorWidthDivider {
    private int gridSpanCount = 1;

    public GridItemDivider(int gridSpanCount) {
        this.gridSpanCount = gridSpanCount;
    }

    @Nullable
    @Override
    public XSidesDivider getItemDivider(int itemPosition) {
        SideDivider bottomSideDivider = new SideDivider(true, dividerWidthDpOrPxValue, dividerPaddingStartDpOrPxValue, dividerPaddingEndDpOrPxValue, dividerColor);
        boolean isTheLastColumn = (itemPosition + 1) % gridSpanCount == 0;
        SideDivider rightSideDivider = null;
        if (!isTheLastColumn) {
            rightSideDivider = new SideDivider(true, dividerWidthDpOrPxValue, dividerPaddingStartDpOrPxValue, dividerPaddingEndDpOrPxValue, dividerColor);
        }
        XSidesDivider xSidesDivider = provideDefXSideDividerBuilder()
                .withBottomSidesDivider(bottomSideDivider)
                .withRightSidesDivider(rightSideDivider)
                .buildXSidesDivider();
        return xSidesDivider;
    }
}
