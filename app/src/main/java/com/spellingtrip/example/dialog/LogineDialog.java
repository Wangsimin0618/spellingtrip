package com.spellingtrip.example.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spellingtrip.example.R;



public class LogineDialog extends Dialog {
    private Context mContext;
    private String message;
    private int type;

    public LogineDialog(Context context,String mesString) {
        super(context, R.style.login_dialog);
        this.mContext = context;
        this.message = mesString;
        setCanceledOnTouchOutside(false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.logine_dialog, null);
        TextView tvDialogTitle=v.findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setText(message+"");
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
        setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
    }

}

