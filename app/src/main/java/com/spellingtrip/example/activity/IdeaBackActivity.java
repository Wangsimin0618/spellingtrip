package com.spellingtrip.example.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.spellingtrip.example.R;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.dialog.OnButtonClick;

import butterknife.BindView;

public class IdeaBackActivity extends BaseActivity{
    @BindView(R.id.ideaWebView)
    public WebView webView;
    @BindView(R.id.ideaProgtessBar)
    public ProgressBar progressBar;
    private ValueCallback<Uri> mUploadMessage;
    private static final int FILE_SELECT_CODE=101;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ideaback;
    }

    @Override
    protected void initView() {
        String url=getIntent().getStringExtra("title");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBlockNetworkImage(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);//设置渲染的优先级
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (!TextUtils.isEmpty(url)){
            webView.loadUrl(url);
        }

    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {
        String id=getIntent().getStringExtra("id");
        backClick();
        if (id.equals("21")){
            setCenterTitle("意见反馈");
        }else {
            setCenterTitle("商务合作");
        }

    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //显示进度条
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                //加载完毕隐藏进度条
                progressBar.setVisibility(View.GONE);

                webView.getSettings().setBlockNetworkImage(false);
            }

        }
        public boolean onJsConfirm(WebView view, final String url, final String message,
                                   final JsResult result) {
            CommonDialog.getDialog(IdeaBackActivity.this, "", message, "取消", "确认", new OnButtonClick() {
                @Override
                public void button01ClickListener() {
                    result.cancel();
                }

                @Override
                public void button02ClickListener() {
                    result.confirm();
                }
            });

            return true;

        }

        @Override
        public boolean onJsAlert(WebView view, String url, final String message, final JsResult result) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CommonDialog.getDialog(IdeaBackActivity.this, "", message, "取消", "确认", new OnButtonClick() {
                        @Override
                        public void button01ClickListener() {
                            result.cancel();
                        }

                        @Override
                        public void button02ClickListener() {
                            result.confirm();
                        }
                    });
                }
            });
            return super.onJsAlert(view, url, message, result);
        }
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = filePathCallback;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            IdeaBackActivity.this.startActivityForResult(
                    Intent.createChooser(i, "File Browser"),
                    FILE_SELECT_CODE);
            return true;
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_SELECT_CODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
    }
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILE_SELECT_CODE
                || mUploadCallbackAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();

                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
        return;
    }



}
