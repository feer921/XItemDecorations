package com.fee.xitemdecoration;

import android.content.Context;
import android.support.annotation.Nullable;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2018/9/23<br>
 * Time: 15:15<br>
 * <P>DESC:
 * 适合非网格RecyclerView列表的设置items 间距
 * 可以指定本Divider位置 items的左、上、右、底
 * 默认为给items设置 底部的Divider
 * </p>
 * ******************(^_^)***********************
 */
public class OneSideDivider extends XColorWidthDivider {

    private boolean notDrawFirstDivider;
    /**
     * 是否需要绘制最后一个divider,
     * def:false
     */
    private boolean needDrawLastDivider;
    public OneSideDivider(Context context) {
        super(context);
    }

    private XSidesDivider xSidesDivider;
    private int theOneSideDividerPos = SideDivider.SIDE_POSITION_BOTTOM;

    public void withDividerPosition(int dividerPosition) {
        theOneSideDividerPos = dividerPosition;
    }

    public void withNotDrawFirstDivide(boolean notDrawFirstDivider) {
        this.notDrawFirstDivider = notDrawFirstDivider;
    }

    public void withNeedDrawLastDivider(boolean needDrawLastDivider) {
        this.needDrawLastDivider = needDrawLastDivider;
    }
    @Nullable
    @Override
    public XSidesDivider getItemDivider(int itemPosition) {
        if (notDrawFirstDivider && itemPosition == 0) {
            return new XSidesDivider();
        }
        if (xSidesDivider == null) {
            xSidesDivider = new XSidesDivider();
            SideDivider theOneSideDivider = new SideDivider(true, dividerWidthDpOrPxValue, dividerPaddingStartDpOrPxValue, dividerPaddingEndDpOrPxValue, dividerColor);
            switch (theOneSideDividerPos) {
                case SideDivider.SIDE_POSITION_BOTTOM:
                    theOneSideDivider.withNeedDrawAtLastPos(needDrawLastDivider);
                    xSidesDivider.withBottomSideDivider(theOneSideDivider);
                    break;
                case SideDivider.SIDE_POSITION_LEFT:
                    xSidesDivider.withLeftSideDivider(theOneSideDivider);
                    break;
                case SideDivider.SIDE_POSITION_RIGHT:
                    theOneSideDivider.withNeedDrawAtLastPos(needDrawLastDivider);
                    xSidesDivider.withRightSideDivider(theOneSideDivider);
                    break;
                case SideDivider.SIDE_POSITION_TOP:
                    xSidesDivider.withTopSideDivider(theOneSideDivider);
                    break;
            }
        }
        return xSidesDivider;
    }
}
