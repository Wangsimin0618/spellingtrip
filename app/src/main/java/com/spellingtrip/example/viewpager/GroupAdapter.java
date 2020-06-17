package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMGroup;
import com.spellingtrip.example.R;
import com.spellingtrip.example.huanxin.ChatActivity;
import com.spellingtrip.example.huanxin.chatframgnet.EaseConversationListFragment;
import com.spellingtrip.example.view.ShapedImageView;

import java.util.List;

public class GroupAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private List<EMGroup> groups;
    private  Activity activity;
    public GroupAdapter(Activity activity, List<EMGroup> groups) {
        this.inflater = LayoutInflater.from(activity);
      this.groups= groups;
      this.activity=activity;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.em_row_add_group, parent, false);
            }
        ShapedImageView shapedImageView=convertView.findViewById(R.id.avatar);
          //  Glide.with(activity).load(groups.get(0).getMembers().get(0)).into(shapedImageView);
            ((TextView) convertView.findViewById(R.id.name)).setText(groups.get(position).getGroupName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, ChatActivity.class);
                // it is group chat
                intent.putExtra("chatType","2");
                intent.putExtra("userId", groups.get(position).getGroupId());
                activity.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Object getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}

