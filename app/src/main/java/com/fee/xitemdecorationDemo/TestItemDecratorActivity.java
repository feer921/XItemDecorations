package com.fee.xitemdecorationDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fee.xitemdecoration.XColorWidthDivider;

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
        XColorWidthDivider divider = new XColorWidthDivider(this);
        divider.withDividerColor(0xff00ff00)
                .withDividerWidthDp(5)
        ;
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
