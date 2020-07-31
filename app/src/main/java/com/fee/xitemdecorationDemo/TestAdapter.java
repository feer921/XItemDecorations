package com.fee.xitemdecorationDemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2018/9/20<br>
 * Time: 10:31<br>
 * <P>DESC:
 * </p>
 * ******************(^_^)***********************
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.AViewHolder> {

    private List<String> datas;

    public void setDatas(String... datas) {
        if (this.datas == null) {
            this.datas = new ArrayList<>();
        }
        if (datas != null && datas.length > 0) {
            for (String data : datas) {
                this.datas.add(data);
            }
            notifyDataSetChanged();
        }
    }
    @NonNull
    @Override
    public AViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
//        View itemView = View.inflate(parent.getContext(), R.layout.item_view, null);//如果 root参数为null,则布局中的layoutParams无效
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);//这个是可以保留布局中的布局参数的
//        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(-1, -2);
//        itemView.setLayoutParams(vlp);
        return new AViewHolder(itemView);
    }

    public String getItemData(int itemPos) {
        return datas != null && !datas.isEmpty() ? datas.get(itemPos) : null;
    }

    @Override
    public void onBindViewHolder(@NonNull AViewHolder holder, int position) {
        //先这里，后ItemDecoration onDraw()
        TextView tvItemView = (TextView) holder.itemView;
        ViewGroup.LayoutParams vlpSrc = tvItemView.getLayoutParams();
        Log.i("info", "--> onBindViewHolder() position = " + position + " item layoutParams = " + vlpSrc);
//        if (vlpSrc == null) {
//            RecyclerView.LayoutParams vlp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            vlp.setMarginStart(30);
//            vlp.bottomMargin = 30;
//            tvItemView.setLayoutParams(vlp);
//        }
        tvItemView.setText(position + "-" + getItemData(position));
    }


    @Override
    public int getItemCount() {
        return this.datas != null ? datas.size() : 0;
    }

    public static class AViewHolder extends RecyclerView.ViewHolder {

        public AViewHolder(View itemView) {
            super(itemView);
        }
    }

}
