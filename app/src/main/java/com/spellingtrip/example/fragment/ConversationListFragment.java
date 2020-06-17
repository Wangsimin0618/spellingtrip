package com.spellingtrip.example.fragment;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.util.NetUtils;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.TourismUserShowActivity;
import com.spellingtrip.example.bean.ChatNumberBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.huanxin.ChatActivity;

import com.spellingtrip.example.huanxin.Constanthuan;
import com.spellingtrip.example.huanxin.chatframgnet.EaseConversationListFragment;
import com.spellingtrip.example.huanxin.db.InviteMessgeDao;
import com.spellingtrip.example.liaotian.UserCacheManager;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.HttpRequest;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;

public class ConversationListFragment extends EaseConversationListFragment {
    private TextView errorText;
    @Override
    protected void initView() {
        super.initView();
        View errorView = (LinearLayout) View.inflate(getActivity(),R.layout.em_chat_neterror_item, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
    }
    
    @Override
    protected void setUpView() {
        super.setUpView();
        // register context menu
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position-1);
                String username = conversation.conversationId();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    if(conversation.isGroup()){
                        if(conversation.getType() == EMConversationType.ChatRoom){
                            // it's group chat
                            intent.putExtra(Constanthuan.EXTRA_CHAT_TYPE, Constanthuan.CHATTYPE_CHATROOM);
                        }else{
                            intent.putExtra(Constanthuan.EXTRA_CHAT_TYPE, Constanthuan.CHATTYPE_GROUP);
                        }
                    }
                    // it's single chat
                    intent.putExtra(Constanthuan.EXTRA_USER_ID, username);
                    startActivity(intent);
                //  getChatNum(UserTask.getInstance().getUser().getUserId(),username,conversation,username);

                }
            }
        });
        super.setUpView();
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())){
        // errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
         // errorText.setText(R.string.the_current_network);
        }
    }
    
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu); 
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
        } else if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = false;
        }
    	EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position-1);
    	if (tobeDeleteCons == null) {
    	    return true;
    	}
        if(tobeDeleteCons.getType() == EMConversationType.GroupChat){
            EaseAtMessageHelper.get().removeAtMeGroup(tobeDeleteCons.conversationId());
        }
        try {
            // delete conversation
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.conversationId(), deleteMessage);
            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
            inviteMessgeDao.deleteMessage(tobeDeleteCons.conversationId());
            // To delete the native stored adked users in this conversation.
            if (deleteMessage) {
                EaseDingMessageHelper.get().delete(tobeDeleteCons);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();
        // update unread count
       // ((MainActivity) getActivity()).updateUnreadLabel();
        return true;
    }
    protected List<EMConversation> loadConversationList(){
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
     /*   try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }

        return list;
    }
}
