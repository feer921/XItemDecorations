package com.fee.xitemdecoration;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2018/9/12<br>
 * Time: 18:13<br>
 * <P>DESC:
 * </p>
 * ******************(^_^)***********************
 */
public class XSidesDividerBuilder {
    private SideDivider leftSideDivider;
    private SideDivider topSideDivider;
    private SideDivider rightSideDivider;
    private SideDivider bottomSideDivider;

    public XSidesDividerBuilder withLeftSidesDivider(SideDivider leftSideDivider) {
        this.leftSideDivider = leftSideDivider;
        return this;
    }
    public XSidesDividerBuilder withTopSidesDivider(SideDivider topSideDivider) {
        this.topSideDivider = topSideDivider;
        return this;
    }
    public XSidesDividerBuilder withRightSidesDivider(SideDivider rightSideDivider) {
        this.rightSideDivider = rightSideDivider;
        return this;
    }
    public XSidesDividerBuilder withBottomSidesDivider(SideDivider bottomSideDivider) {
        this.bottomSideDivider = bottomSideDivider;
        return this;
    }


    public XSidesDivider buildXSidesDivider() {
        XSidesDivider theSidesDivider = new XSidesDivider();
        SideDivider defSideDivider = new SideDivider(false, 0, 0, 0, 0xff666666);
        theSidesDivider.withLeftSideDivider(leftSideDivider)
                        .withTopSideDivider(topSideDivider)
                        .withRightSideDivider(rightSideDivider)
                        .withBottomSideDivider(bottomSideDivider == null ? defSideDivider : bottomSideDivider);
        return theSidesDivider;
    }
}
