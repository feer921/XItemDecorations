package com.fee.xitemdecoration;

import android.support.annotation.Nullable;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2019/10/31<br>
 * Time: 21:28<br>
 * <P>DESC:
 * 给RecyclerView使用 GridLayoutManager 来布局ItemView的 ItemDecoration
 * 本类为标准实现，即给RecyclerView设置GridLayoutManager后，并且每个ItemView占据一个SPAN，并且 适合item间左<-->右间距 和 item间上<-->下间距 相等的情况
 * 如果存在ItemView占据多个SPAN的情况，则不适用(主要是本类根据Posistion来判断所处在的行序、列序不正确)
 * </p>
 * ******************(^_^)***********************
 */
public class GridItemDivider extends XColorWidthDivider {
    protected int gridSpanCount = 1;

    /**
     * 是否需要考虑绘制顶部的Divider
     * def:false;
     */
    private boolean isNeedDrawTopDivider = false;

    /**
     * 是否需要考虑绘制网格的最后一行的底部Divider
     * def:true;
     */
    private boolean isNeedDrawBottomDividerAtLastRow = true;

    /**
     * 网格中 最后一行的items 的底部 divider高度，为了满足可能网格列表中最后一行 有底部的间隔
     */
    private float lastRowBottomDividerWidthDpOrPxValue;

    public GridItemDivider(int gridSpanCount) {
        this.gridSpanCount = gridSpanCount;
    }

    /**
     * 网格中第一行 顶部Divider,如果 >0 则第一行的item的 顶都要绘制，并且值为此值
     */
    protected float firstRowTopDividerDpOrPxValue;

    /**
     * 网格布局中 每行间的Divider，如果 >0,则上、下两行的item 的底、顶Divider宽度不能使用 父类的Divider的值
     */
    protected float perRowDividerDpOrPxValue;

    /**
     * 第一列 左侧的Divider，如果>0,则需要绘制出来
     */
    protected float firstColumnLeftDividerDpOrPxValue;

    /**
     * 最后一列 右侧的Divider，如果 >0,则需要绘制出来
     */
    protected float lastColumnRightDividerDpOrPxValue;


    /**
     * 根据当前Adapter中 列表数据集的数量来计算网格布局时，总共需要的行数
     * @return 计算出的总行数
     */
    protected int calculateTotalRow() {
        if (gridSpanCount > 0) {
            int totalRowCount = totalItemViewCount / gridSpanCount; //举例：网格为 3列的网格；items数量 有 3、6、9，则行数即为整除
            if (totalItemViewCount % gridSpanCount != 0) {//如果不是 列数的倍数，则整除后 +1即为行数，如 items数量 有: 1、4、5、7
                totalRowCount++;
            }
            return totalRowCount;
        }
        return 0;
    }

    /**
     * 根据当前的 item所处的位置 curItemPosition 来计算判断当前item所处的行序
     * @param curItemPosition 当前 item所处的 position 从0开始计
     * @return 当前 item 所处的 行数；从 0开始计(即0为第一行)
     */
    protected int judgeCurRowIndex(int curItemPosition) {
        if (gridSpanCount > 0) {
            return curItemPosition / gridSpanCount;
        }
        return -1;
    }
    /**
     * 根据 网格指定的列数 以及当前 item所处的 position 来计算判定，当前item所处的列序
     * 从0开始计(即 0 = 第1列),(gridSpanCount - 1) 为最后一列
     * @param curItemPosition 当前item所处的 position 从0开始计
     * @return 该 item 所处的网格列表中的列序，从0始计，但为(gridSpanCount - 1)时代表最后一列
     */
    protected int judgeItemCurColumnIndex(int curItemPosition) {
        if (gridSpanCount > 0) {
            return curItemPosition % gridSpanCount;
        }
        return -1;
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
        if (!isNeedDrawTopDivider) {//这里表示每个 item不需要绘制 top divider,即上、下行的 divider由 item 的底部承担绘制
            justBottomSideDivider = new SideDivider(true, dividerWidthDpOrPxValue,
                    0, 0, dividerColor);
            justBottomSideDivider.withNeedDrawAtLastPos(true);
        }
        //列与列间距(每个item绘制一半)，该间距为相等的
        SideDivider perColumnHalfDivider = new SideDivider(true,
                dividerWidthDpOrPxValue / 2.0f,
                0,
                0,
                dividerColor);

        //行与行间距(每个item绘制一半)，该间距默认与 dividerWidthDpOrPxValue 相等
        SideDivider perRowHalfDivider = new SideDivider(true,
                dividerWidthDpOrPxValue / 2.0f,
                0,
                0,
                dividerColor);

        //有设置行与行间距不等于 dividerWidthDpOrPxValue的情况下
        if (perRowDividerDpOrPxValue > 0 && perRowDividerDpOrPxValue != dividerWidthDpOrPxValue) {
            perRowHalfDivider.withSideWidthValue(perRowDividerDpOrPxValue / 2.0f);
            if (!isNeedDrawTopDivider) {//
                if (justBottomSideDivider != null) {
                    justBottomSideDivider.withSideWidthValue(perRowDividerDpOrPxValue);
                }
            }
        }
        xSidesDivider.withBottomSideDivider(isNeedDrawTopDivider ? perRowHalfDivider : justBottomSideDivider)//每个item有底部Divider
                     .withTopSideDivider(isNeedDrawTopDivider ? perRowHalfDivider : null)
                     .withRightSideDivider(perColumnHalfDivider)
                     .withLeftSideDivider(perColumnHalfDivider);

        int curRowIndex = itemPosition / gridSpanCount;//==0时为第一行
        debugInfo("-->getItemDivider() curRowIndex = " + curRowIndex + " totalRowCount = " + totalRowCount + " itemPosition = " + itemPosition
                + " totalItemViewCount = " + totalItemViewCount
        );
        if (curRowIndex == 0) {//第一行时不需要绘制顶部Divider
            if (firstRowTopDividerDpOrPxValue > 0) {//如果有指定 第一行有 top的divider值
                SideDivider assignTopSideDivider = new SideDivider(true, firstRowTopDividerDpOrPxValue, 0, 0,
                        dividerColor);
                xSidesDivider.withTopSideDivider(assignTopSideDivider);
            }
            else {//默认第一行 不需要绘制 顶部 divider
                xSidesDivider.withTopSideDivider(null);
            }
        }

        if (curRowIndex == totalRowCount - 1) {//最后一行不需要绘制底部？也可以绘制,不影响显示效果
            if (!isNeedDrawBottomDividerAtLastRow) {
                xSidesDivider.withBottomSideDivider(null);
            }
            else {//最后一行需要绘制 divider
                float theLastRowSideDividerValue = perRowDividerDpOrPxValue > 0 ? perRowDividerDpOrPxValue : dividerWidthDpOrPxValue;
                //要 构造一个新的 SideDivider,不然会影响 perRowHalfDivider
                SideDivider bottomSideDivider = new SideDivider(true,
                        theLastRowSideDividerValue,
                        0,
                        0,
                        dividerColor);
                bottomSideDivider.withNeedDrawAtLastPos(true);
                if (lastRowBottomDividerWidthDpOrPxValue > 0) {//如果有指定最后一行的底部divider 值,则让item的 bottom divider绘制该值
                    bottomSideDivider.withSideWidthValue(lastRowBottomDividerWidthDpOrPxValue);
                }
                xSidesDivider.withBottomSideDivider(bottomSideDivider);
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

        //处理列方向
        int posModResult = (itemPosition + 1) % gridSpanCount;//当前的position位置+1 与 当前列数取余
        if (posModResult == 0) {//表示当前Posistion在最后一列
            if (lastColumnRightDividerDpOrPxValue > 0) {//即表示最后一列的 right divider需要绘制
                SideDivider lastColumnDivider = new SideDivider(true,
                        lastColumnRightDividerDpOrPxValue,
                        0,
                        0,
                        dividerColor
                );
                lastColumnDivider.withNeedDrawAtLastPos(true);
                xSidesDivider.withRightSideDivider(lastColumnDivider);
            }
            else {
                xSidesDivider.withRightSideDivider(null);
            }
        }
        else {
            if (posModResult == 1) {//第一列默认不需要左Divider
                if (firstColumnLeftDividerDpOrPxValue > 0) {
                    SideDivider firstColumnSideDivider = new SideDivider(true,
                            firstColumnLeftDividerDpOrPxValue,
                            0,
                            0,
                            dividerColor);
                    firstColumnSideDivider.withNeedDrawAt1stPos(true);
                    xSidesDivider.withLeftSideDivider(firstColumnSideDivider);
                }
                else {
                    xSidesDivider.withLeftSideDivider(null);
                }
            }
        }
        debugInfo("-->getItemDivider() xSidesDivider = " + xSidesDivider.getRightSideDivider() + " itemPosition = " + itemPosition);
        return xSidesDivider;
    }

    /**
     * 配置RecyclerView 网格列表 的 items是否绘制 Top位置的Divider
     * def: false 即 items 默认不绘制 Top位置的Divider，而是由 items的 Bottom位置的Divider来 替代上、下间距
     * @param needDrawTopDivider true: 让items绘制Top位置的Divider，此时 divider的值为{@link #dividerWidthDpOrPxValue}的一半
     * @return self
     */
    public GridItemDivider setNeedDrawTopDivider(boolean needDrawTopDivider) {
        this.isNeedDrawTopDivider = needDrawTopDivider;
        return this;
    }

    public GridItemDivider setNeedDrawBottomDividerAtLastRow(boolean needDrawBottomDividerAtLastRow) {
        this.isNeedDrawBottomDividerAtLastRow = needDrawBottomDividerAtLastRow;
        return this;
    }

    /**
     * <p>列表 底</p>
     * 配置 RecyclerView 网格列表 最下面一行的 items的底部 divider
     * 场景为：RecyclerView 本身不设置 底padding或者 margin的情况下，让最后一行的 items绘制 底部 divider
     * 以满足列表 底部间距的需求
     * 注：不配置的情况下，默认最后一行的 items 底部 Divider不绘制
     * @param lastRowBottomDividerWidth 最后一行的 items的 Bottom位置的Divider的值
     * @return self
     */
    public GridItemDivider setLastRowBottomDividerWidth(float lastRowBottomDividerWidth) {
        this.lastRowBottomDividerWidthDpOrPxValue = lastRowBottomDividerWidth;
        isNeedDrawBottomDividerAtLastRow = true;
        return this;
    }

    /**
     * <p>列表 上</p>
     * 配置 RecyclerView 网格列表 第一行的 items的顶部 divider
     * 场景为：RecyclerView 本身不设置 上padding或者 margin的情况下，让第一行的 items绘制 顶部 divider
     * 以满足列表 顶部间距的需求
     * 注：不配置的情况下，默认第一行的 items Top Divider不绘制
     * @param firstRowTopDividerDpOrPxValue 第一行 items的顶部 Divider的值
     * @return self
     */
    public GridItemDivider setFirstRowTopDividerValue(float firstRowTopDividerDpOrPxValue) {
        this.firstRowTopDividerDpOrPxValue = firstRowTopDividerDpOrPxValue;
        return this;
    }

    /**
     * <p>列表 中</p>
     * 配置RecyclerView网格列表布局的 上、下行的间距 divider
     * 场景为：RecyclerView的 行方向的 items间距 divider与 列(垂直)方向的items上、下间距不一致的情况下，
     * 保持使用{@link #dividerWidthDpOrPxValue} 作为行方向的的 items间距，而使用此方法配置竖、垂直、上下行的间距值
     * 注：不配置的情况下，上下行间距和 左右列间距的值是相等的，使用{@link #dividerWidthDpOrPxValue}
     * @param perRowDividerDpOrPxValue 网格列表布局的 上、下行的间距 divider 值
     * @return self
     */
    public GridItemDivider setPerRowDividerValue(float perRowDividerDpOrPxValue) {
        this.perRowDividerDpOrPxValue = perRowDividerDpOrPxValue;
        return this;
    }

    /**
     * <p>列表 左</p>
     * 配置网格列表布局 的第一列 items 左侧divider
     * 场景为：RecyclerView本身不设置 左padding或者左margin的情况下，让第一列的 items 绘制左侧divider
     * 以满足列表左侧间距需求
     * 注：不配置情况下，默认第一列的 items 左侧都不绘制
     * @param firstColumnLeftDividerDpOrPxValue  RecyclerView网格列表 第一列items的左侧Divider的值
     * @return self
     */
    public GridItemDivider setFirstColumnLeftDividerValue(float firstColumnLeftDividerDpOrPxValue) {
        this.firstColumnLeftDividerDpOrPxValue = firstColumnLeftDividerDpOrPxValue;
        return this;
    }

    /**
     * <p>列表 右</p>
     * 配置网格列表布局 的最后一列 item 右侧divider
     * 场景为：RecyclerView本身不设置 右padding或者margin的情况下，让最后一列的 items 绘制右侧divider
     * 以满足列表右侧间距需求
     * 注：不配置情况下，默认最后一列的 items 右侧都不绘制
     * @param lastColumnRightDividerDpOrPxValue  RecyclerView网格列表 最后一列items的右侧Divider的值
     * @return self
     */
    public GridItemDivider setLastColumnRightDividerValue(float lastColumnRightDividerDpOrPxValue) {
        this.lastColumnRightDividerDpOrPxValue = lastColumnRightDividerDpOrPxValue;
        return this;
    }
}
