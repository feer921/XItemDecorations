package com.fee.xitemdecorationDemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestItemDecratorActivity.this,TestOfficialItemDecratorAct.class));
            }
        });
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
        DividerItemDecoration2 dividerItemDecoration = new DividerItemDecoration2();
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
