package com.spellingtrip.example.activity;

import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ArticleBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 首页顶部进入webwiew
 */
public class WebShowActivity extends BaseActivity{
//    @BindView(R.id.showWebView)
//    public WebView showWebView;
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
        webView.loadUrl(ConstantsBean.HOME_WEBWIEW);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("携手迎春");
    }
//    private String codePrefixOne = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">" +
//            "<html>" +
//            "<head>" +
//            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=\">" ;
//    private String css = "<style type=\"text/css\"> img {" + "max-width: 100%;" +
//            "width:100%;" +//限定图片宽度填充屏幕
//            "height:auto;" +//限定图片高度自动
//            "}" ;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_webshow;
//    }
//
//    @Override
//    protected void initView() {
//        WebSettings webSettings = showWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setBlockNetworkImage(false);
//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//设置渲染的优先级
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
//            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//    }
//
//    @Override
//    protected void getData() {
//        String id=getIntent().getStringExtra("id");
//        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+"/api/article/detail/"+id).build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int i) {
//                ToastUtil.show(e.getMessage());
//            }
//
//            @Override
//            public void onResponse(String s, int i) {
//                if (!TextUtils.isEmpty(s)){
//                    ArticleBean   articleBean= (ArticleBean) JsonUtil.fromJson(s,ArticleBean.class);
//                    if (articleBean.getCode()==0){
//                        String codePrefixTwo = "</style>" + "</head>" + "<body>" + articleBean.getData().getContent() + "</body>" + "</html>";
//                        String webData = codePrefixOne+css + codePrefixTwo ;
//                        showWebView.loadDataWithBaseURL(null, webData, "text/html", "utf-8", null);
//                    }else {
//                        ToastUtil.show(articleBean.getMsg());
//                    }
//                }
//            }
//        });
//
//    }
//
//    @Override
//    protected void setData() {
//       String title= getIntent().getStringExtra("title");
//       if (!TextUtils.isEmpty(title)){
//           setCenterTitle(title);
//       }else {
//           setCenterTitle("文章详情");
//       }
//       backClick();
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    if (showWebView!=null){
//        showWebView.removeAllViews();
//        showWebView.clearHistory();
//    }
//    }
}
