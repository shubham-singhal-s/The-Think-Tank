package com.thethinktankmit.thethinktank;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class newsletter extends Fragment implements ViewTreeObserver.OnScrollChangedListener {

    WebView webView;
    String url;
    String link_news_old="http://ssmun.in/ttt2/newsletter.html";
    String link_news="http://tedxmanipaluniversity.com/app/ttt.html";
    SwipeRefreshLayout swipeLayout;

    public static newsletter newInstance() {
        newsletter fragment = new newsletter();
        return fragment;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Newsletter");
    }
    public newsletter() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.newsletter, container, false);
        webView = (WebView) rootView.findViewById(R.id.webView_nl);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getViewTreeObserver().addOnScrollChangedListener(this);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);

        //Initialize Tabs

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        final CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.intent.setPackage("com.android.chrome");
        builder.setShowTitle(true);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)
            builder.setToolbarColor(getResources().getColor(R.color.basic,getActivity().getTheme()));
        else
            builder.setToolbarColor(getResources().getColor(R.color.basic));

        //Webview Functionality
        WebViewClient aw = new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                openLetter(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //hide loading image
                rootView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                //show webview
                rootView.findViewById(R.id.webView_nl).setVisibility(View.VISIBLE);
                view.clearCache(true);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.loadUrl("file:///android_asset/error.html");
                Snackbar snackbar = Snackbar
                        .make(rootView, "Connection Error", Snackbar.LENGTH_LONG);

                snackbar.show();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
                webView.loadUrl("file:///android_asset/error.html");
                Snackbar snackbar = Snackbar
                        .make(rootView, "Connection Error", Snackbar.LENGTH_LONG);

                snackbar.show();
            }

        };

        webView.setWebViewClient(aw);

        webView.loadUrl(link_news);


        //Swipe to reload
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Insert your code here
                swipeLayout.setRefreshing(false);
                webView.loadUrl(link_news); // refreshes the WebView
            }
        });
        return rootView;
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {

        }
    }

    @Override
    public void onScrollChanged() {
        if (webView.getScrollY() == 0) {
            swipeLayout.setEnabled(true);
        } else {
            swipeLayout.setEnabled(false);
        }
    }

    //Open a new custom tabs
    public void openLetter(String str){
        url=str;
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        final CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.intent.setPackage("com.android.chrome");
        builder.setShowTitle(true);
        builder.addDefaultShareMenuItem();
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)
            builder.setToolbarColor(getResources().getColor(R.color.basic,getActivity().getTheme()));
        else
            builder.setToolbarColor(getResources().getColor(R.color.basic));
        try{
        customTabsIntent.launchUrl(getActivity(), Uri.parse(str));}
        catch(Exception e){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Your phone might be outdated.\nDo you want to read the Newsletter in your Browser?");
                    alertDialogBuilder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(intent);
                                }
                            });

            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getActivity(),"Please update your Google Chrome to view this within the app.", Toast.LENGTH_LONG).show();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            /**Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str));
            startActivity(intent);**/
        }

    }
}

