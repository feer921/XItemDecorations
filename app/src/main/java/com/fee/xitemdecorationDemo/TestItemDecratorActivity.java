package com.fee.xitemdecorationDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        XColorWidthDivider divider = new XColorWidthDivider(this){
            @Nullable
            @Override
            public XSidesDivider getItemDivider(int itemPosition) {
                SideDivider rightSideDivider = new SideDivider(true, 5, 0, 0, 0xff0000ff);
                XSidesDivider xSidesDivider  = super.getItemDivider(itemPosition).withRightSideDivider(rightSideDivider);
                return xSidesDivider;
            }
        };
        divider.withDividerColor(0xff00ff00)
                .withDividerWidthDp(5)
        ;
        divider.setDebugLog(true);
        recyclerView.addItemDecoration(divider);
        TestAdapter adapter = new TestAdapter();
        adapter.setDatas(createDatas(20));
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
