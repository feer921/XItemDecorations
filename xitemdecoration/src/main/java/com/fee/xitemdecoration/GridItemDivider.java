package com.fee.xitemdecoration;

import android.support.annotation.Nullable;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2019/10/31<br>
 * Time: 21:28<br>
 * <P>DESC:
 * 给RecyclerView使用 GridLayoutManager 来布局ItemView的 ItemDecoration
 * 本类为标准实现，即给RecyclerView设置GridLayoutManager后，并且每个ItemView占据一个SPAN，
 * 如果存在ItemView占据多个SPAN的情况，则不适用(主要是本类根据Posistion来判断所处在的行序、列序不正确)
 * </p>
 * ******************(^_^)***********************
 */
public class GridItemDivider extends XColorWidthDivider {
    private int gridSpanCount = 1;

    /**
     * 是否需要考虑绘制顶部的Divider
     * def:false
     */
    private boolean isNeedDrawTopDivider = false;

    public GridItemDivider(int gridSpanCount) {
        this.gridSpanCount = gridSpanCount;
    }

    @Nullable
    @Override
    public XSidesDivider getItemDivider(int itemPosition) {
        if (gridSpanCount <= 0) {
            return null;
        }
        //总行数
        int totalRowCount = totalItemViewCount / gridSpanCount;
        if (totalItemViewCount % gridSpanCount != 0) {//如果当前的ItemView总数整除不了分列数
            totalRowCount += 1;
        }
        XSidesDivider xSidesDivider = new XSidesDivider();
        SideDivider justBottomSideDivider = null;
        if (!isNeedDrawTopDivider) {
            justBottomSideDivider = new SideDivider(true, dividerWidthDpOrPxValue,
                    0, 0, dividerColor);
            justBottomSideDivider.withNeedDrawAtLastPos(true);
        }
        SideDivider halfWidthSideDivider = new SideDivider(true, dividerWidthDpOrPxValue / 2, 0, 0,
                dividerColor);

        xSidesDivider.withBottomSideDivider(isNeedDrawTopDivider ? halfWidthSideDivider : justBottomSideDivider);//每个item有底部Divider
        xSidesDivider.withTopSideDivider(isNeedDrawTopDivider ? halfWidthSideDivider : null);
        xSidesDivider.withRightSideDivider(halfWidthSideDivider)
                .withLeftSideDivider(halfWidthSideDivider);
        int curRowIndex = itemPosition / gridSpanCount;//==0时为第一行
        debugInfo("-->getItemDivider() curRowIndex = " + curRowIndex + " totalRowCount = " + totalRowCount + " itemPosition = " + itemPosition);
        if (curRowIndex == 0) {//第一行时不需要绘制顶部Divider
            xSidesDivider.withTopSideDivider(null);
        }
        else {
            if (curRowIndex == totalRowCount -1) {//最后一行不需要绘制底部？也可以绘制,不影响显示效果
                xSidesDivider.withBottomSideDivider(null);
            }
        }
//        int curRow = 0;
//        if (itemPosition % gridSpanCount == 0) {//如果当前的Posistion能整除列数,则行直接等于
//            curRow = itemPosition / gridSpanCount;
//        }
//        else {
//            curRow = 1 + (itemPosition % gridSpanCount);
//        }
//        if (curRow == 1) {
//
//        }
        int posModResult = (itemPosition + 1) % gridSpanCount;//当前的position位置+1 与 当前列数取余
        if (posModResult == 0) {//表示当前Posistion在最后一列
//            justBottomSideDivider.withNeedDrawAtLastPos(true);
            xSidesDivider.withRightSideDivider(null);
        }
        else {
            if (posModResult == 1) {//第一列不需要左Divider
                xSidesDivider.withLeftSideDivider(null);
            }
        }
        debugInfo("-->getItemDivider() xSidesDivider = " + xSidesDivider);
        return xSidesDivider;
    }

    public void setNeedDrawTopDivider(boolean needDrawTopDivider) {
        this.isNeedDrawTopDivider = needDrawTopDivider;
    }

}
