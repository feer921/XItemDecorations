package com.fee.xitemdecoration;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2018/9/12<br>
 * Time: 17:36<br>
 * <P>DESC:
 * 具有多个边的Divider，其本身是作为RecyclerView中itemview的一个divider，只不过该divider可以给itemview以多个边的装饰
 * </p>
 * ******************(^_^)***********************
 */
public class XSidesDivider {

    public XSidesDivider() {

    }

    public XSidesDivider(SideDivider leftSideDivider, SideDivider topSideDivider, SideDivider rightSideDivider, SideDivider bottomSideDivider) {
        this.leftSideDivider = leftSideDivider;
        this.topSideDivider = topSideDivider;
        this.rightSideDivider = rightSideDivider;
        this.bottomSideDivider = bottomSideDivider;
    }

    /**
     * 本Divider可能有的左边(divider，也看成divider)
     */
    private SideDivider leftSideDivider;

    /**
     * 本Divider可能有的上边(divider，也看成divider)
     */
    private SideDivider topSideDivider;

    /**
     * 本Divider可能有的右边(divider，也看成divider)
     */
    private SideDivider rightSideDivider;

    /**
     * 本Divider可能有的底边(divider，也看成divider)
     * 注：大多数RecyclerView中(如果是垂直列表)有这一个底边的divider
     */
    private SideDivider bottomSideDivider;

    public SideDivider getLeftSideDivider() {
        return leftSideDivider;
    }

    public SideDivider getTopSideDivider() {
        return topSideDivider;
    }

    public SideDivider getRightSideDivider() {
        return rightSideDivider;
    }

    public SideDivider getBottomSideDivider() {
        return bottomSideDivider;
    }

    /**
     * 给当前Divider配置[左边]的(divider)装饰
     * @param leftSideDivider [左边]带所需绘制信息的divider
     * @return selft
     */
    public XSidesDivider withLeftSideDivider(SideDivider leftSideDivider) {
        this.leftSideDivider = leftSideDivider;
        return this;
    }
    /**
     * 给当前Divider配置[上边]的(divider)装饰
     * @param topSideDivider [上边]带所需绘制信息的divider
     * @return selft
     */
    public XSidesDivider withTopSideDivider(SideDivider topSideDivider) {
        this.topSideDivider = topSideDivider;
        return this;
    }
    /**
     * 给当前Divider配置[右边]的(divider)装饰
     * @param rightSideDivider [右边]带所需绘制信息的divider
     * @return selft
     */
    public XSidesDivider withRightSideDivider(SideDivider rightSideDivider) {
        this.rightSideDivider = rightSideDivider;
        return this;
    }
    /**
     * 给当前Divider配置[底边]的(divider)装饰
     * @param bottomSideDivider [底边]带所需绘制信息的divider
     * @return selft
     */
    public XSidesDivider withBottomSideDivider(SideDivider bottomSideDivider) {
        this.bottomSideDivider = bottomSideDivider;
        return this;
    }

    @Override
    public String toString() {
        return "XSidesDivider{" +
                "leftSideDivider=" + leftSideDivider +
                ", topSideDivider=" + topSideDivider +
                ", rightSideDivider=" + rightSideDivider +
                ", bottomSideDivider=" + bottomSideDivider +
                '}';
    }
}
