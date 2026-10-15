package com.javcombita.pokesurvivor;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.graphics.Bitmap;

public class MainActivity extends Activity {
    private static final String ONLINE_URL = "https://pokesurvivor.com";
    private static final String OFFLINE_URL = "file:///android_asset/game/index.html";
    private WebView webView;
    private boolean onlineMode;
    private boolean fallbackUsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        applyImmersiveMode();

        webView = new WebView(this);
        setContentView(webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String scheme = request.getUrl().getScheme();
                return !("file".equals(scheme) || "https".equals(scheme) || "http".equals(scheme));
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (onlineMode && !fallbackUsed && request.isForMainFrame()) {
                    fallbackUsed = true;
                    onlineMode = false;
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this,
                            "No se pudo conectar. Se abrirá el modo offline.", Toast.LENGTH_LONG).show();
                        webView.loadUrl(OFFLINE_URL);
                    });
                }
            }
        });

        if (savedInstanceState != null) {
            onlineMode = savedInstanceState.getBoolean("onlineMode", false);
            fallbackUsed = savedInstanceState.getBoolean("fallbackUsed", false);
            webView.restoreState(savedInstanceState);
            if (webView.getUrl() == null) loadSelectedMode();
        } else {
            showModeChooser();
        }
    }

    private void showModeChooser() {
        new AlertDialog.Builder(this)
            .setTitle("PokéSurvivor")
            .setMessage("¿Cómo quieres jugar?")
            .setCancelable(false)
            .setPositiveButton("Online", (dialog, which) -> {
                onlineMode = true;
                fallbackUsed = false;
                loadSelectedMode();
            })
            .setNegativeButton("Offline", (dialog, which) -> {
                onlineMode = false;
                fallbackUsed = false;
                loadSelectedMode();
            })
            .show();
    }

    private void loadSelectedMode() {
        webView.loadUrl(onlineMode ? ONLINE_URL : OFFLINE_URL);
    }

    private void applyImmersiveMode() {
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("onlineMode", onlineMode);
        outState.putBoolean("fallbackUsed", fallbackUsed);
        if (webView != null) webView.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
