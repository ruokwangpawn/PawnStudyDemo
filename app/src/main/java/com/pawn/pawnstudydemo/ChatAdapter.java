package com.pawn.pawnstudydemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/*********************************************
 * @文件名称: ChatAdapter
 * @文件作者: Pawn
 * @文件描述:
 * @创建时间: 2018/7/30 17
 * @修改历史:
 *********************************************/
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatContent> datas;

    public ChatAdapter(List<ChatContent> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getTypeKey();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        public ChatViewHolder(View itemView) {
            super(itemView);
        }
    }
}
