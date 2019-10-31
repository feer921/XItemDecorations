package com.fee.xitemdecorationDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fee.xitemdecoration.GridItemDivider;
import com.fee.xitemdecoration.SideDivider;
import com.fee.xitemdecoration.XColorWidthDivider;
import com.fee.xitemdecoration.XSidesDivider;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2018/9/19<br>
 * Time: 22:33<br>
 * <P>DESC:
 * </p>
 * ******************(^_^)***********************
 */
public class TestItemDecratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        XColorWidthDivider divider = new XColorWidthDivider(){
            @Nullable
            @Override
            public XSidesDivider getItemDivider(int itemPosition) {
                SideDivider rightSideDivider = new SideDivider(true, 5, 0, 0, 0xff0000ff);
                XSidesDivider xSidesDivider  = super.getItemDivider(itemPosition)
                        .withRightSideDivider(rightSideDivider);
                return xSidesDivider;
            }



        };
        divider.withDividerColor(0xff00ff00)
                .withDividerWidthValue(15)
        ;
        divider.setDebugLog(true)
            .setNeedConsiderItemViewMargin(false)
            .setNeedFillDividerCrossGap(false)
        ;
        GridItemDivider gridItemDivider = new GridItemDivider(2);
        gridItemDivider.withDividerColor(0xff00ff00)
                .withDividerWidthValue(15)
                .letSideValueUseDpValue(false)
                ;
        recyclerView.addItemDecoration(gridItemDivider);
//        recyclerView.addItemDecoration(divider);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration();
//        recyclerView.addItemDecoration(dividerItemDecoration);

        TestAdapter adapter = new TestAdapter();
        adapter.setDatas(createDatas(6));
        recyclerView.setAdapter(adapter);
    }

    private String[] createDatas(int dataCount) {
        String[] datas = new String[dataCount];
        for (int i = 0; i < dataCount; i++) {
            datas[i] = "test" + i;
        }
        return datas;
    }
}
