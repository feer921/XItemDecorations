package com.fee.xitemdecorationDemo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/7/31<br>
 * Time: 17:20<br>
 * <P>DESC:
 * </p>
 * ******************(^_^)***********************
 */
public class TestOfficialItemDecratorAct extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        recyclerView.addItemDecoration(itemDecoration);

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
