package com.elkhelj.ecommerece.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.elkhelj.ecommerece.R;
import com.elkhelj.ecommerece.databinding.ChatMessageLeftRowBinding;
import com.elkhelj.ecommerece.databinding.ChatMessageRightRowBinding;
import com.elkhelj.ecommerece.models.AllMessageModel;
import com.elkhelj.ecommerece.models.MessageModel;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Chat_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ITEM_MESSAGE_LEFT = 1;
    private final int ITEM_MESSAGE_RIGHT = 2;

    private final String lang;


    private List<AllMessageModel> messageModelList;
    private int current_user_id;
    private Context context;
    private LayoutInflater inflater;
    public Chat_Adapter(List<AllMessageModel> messageModelList, int current_user_id, Context context) {
        this.messageModelList = messageModelList;
        this.current_user_id = current_user_id;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_MESSAGE_RIGHT) {
            ChatMessageRightRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.chat_message_right_row, parent, false);
            return new RightMessageEventHolder(binding);

        } else {
            ChatMessageLeftRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.chat_message_left_row, parent, false);
            return new LeftMessageEventHolder(binding);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AllMessageModel messageModel = messageModelList.get(position);
       if (holder instanceof RightMessageEventHolder)
        {
            RightMessageEventHolder eventHolder = (RightMessageEventHolder) holder;

            eventHolder.binding.setMessagemodel(messageModel);
            eventHolder.binding.setLang(lang);


        }
        else   if (holder instanceof LeftMessageEventHolder)
        {
            LeftMessageEventHolder eventHolder = (LeftMessageEventHolder) holder;

            eventHolder.binding.setMessagemodel(messageModel);
            eventHolder.binding.setLang(lang);


        }


    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public class RightMessageEventHolder extends RecyclerView.ViewHolder {
        public ChatMessageRightRowBinding binding;
        public RightMessageEventHolder(@NonNull ChatMessageRightRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public class LeftMessageEventHolder extends RecyclerView.ViewHolder {
        public ChatMessageLeftRowBinding binding;
        public LeftMessageEventHolder(@NonNull ChatMessageLeftRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


    @Override
    public int getItemViewType(int position) {
       AllMessageModel messageModel = messageModelList.get(position);

        if (messageModel.getType()==1) {
                return ITEM_MESSAGE_LEFT;


        }
        else {
                return ITEM_MESSAGE_RIGHT;



        }


}}
