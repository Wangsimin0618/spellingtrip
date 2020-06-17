package com.spellingtrip.example.activity;

import android.os.Handler;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.spellingtrip.example.R;

import butterknife.BindView;

/**
 * 用户协议和隐私政策
 */
public class ArgeeTextActivity extends BaseActivity {
    @BindView(R.id.webview)
    public WebView webView;
    @BindView(R.id.progtessBar)
    public ProgressBar progressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_aregeetext;
    }

    @Override
    protected void initView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBlockNetworkImage(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);//设置渲染的优先级
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //显示进度条
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    //加载完毕隐藏进度条
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                        }
                    }, 1000);

                    webView.getSettings().setBlockNetworkImage(false);
                }

            }
        });
        webView.loadUrl("http://pinpinlx.mikecrm.com/N716d7b");
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("用户隐私协议");
    }
}
