package com.javcombita.pokesurvivor

import androidx.activity.ComponentActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    private lateinit var webView: WebView
    private var pendingExport = ""

    private val createSave = registerForActivityResult(
        ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        if (uri == null) return@registerForActivityResult
        try {
            contentResolver.openOutputStream(uri)?.bufferedWriter(Charsets.UTF_8)?.use {
                it.write(pendingExport)
            } ?: throw IllegalStateException("No se pudo crear el archivo.")
            toast(if (isEnglish()) "Save exported." else "Partida exportada.")
        } catch (e: Exception) {
            toast((if (isEnglish()) "Export failed: " else "Falló la exportación: ") + e.message)
        }
    }

    private val openSave = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri == null) return@registerForActivityResult
        try {
            val json = contentResolver.openInputStream(uri)?.bufferedReader(Charsets.UTF_8)?.use { it.readText() }
                ?: throw IllegalStateException("No se pudo leer el archivo.")
            webView.evaluateJavascript("window.onNativeSaveImported(${JSONObject.quote(json)});", null)
        } catch (e: Exception) {
            toast((if (isEnglish()) "Import failed: " else "Falló la importación: ") + e.message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (
            android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
                or android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        )
        webView = WebView(this)
        setContentView(webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true
        webView.settings.allowFileAccessFromFileURLs = false
        webView.settings.allowUniversalAccessFromFileURLs = false
        webView.settings.javaScriptCanOpenWindowsAutomatically = false
        webView.settings.setSupportMultipleWindows(false)
        webView.settings.blockNetworkLoads = true
        webView.webChromeClient = WebChromeClient()
        webView.addJavascriptInterface(OfflineBridge(), "Android")
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val uri = request.url
                if (uri.scheme == "file" && uri.toString().startsWith("file:///android_asset/www/")) return false
                if (uri.scheme == "https" && uri.host == "paypal.me" && uri.path == "/javcombita") {
                    openPayPal()
                    return true
                }
                return true
            }
        }
        webView.loadUrl("file:///android_asset/www/index.html")
    }

    private inner class OfflineBridge {
        @JavascriptInterface
        fun exportSave(json: String) {
            runOnUiThread {
                pendingExport = json
                createSave.launch("pokesurvivor-save.json")
            }
        }

        @JavascriptInterface
        fun importSave() {
            runOnUiThread { openSave.launch(arrayOf("application/json", "text/json", "text/plain")) }
        }

        @JavascriptInterface
        fun openPayPal(url: String) {
            runOnUiThread {
                if (url != "https://paypal.me/javcombita") {
                    toast("Enlace de apoyo no válido.")
                    return@runOnUiThread
                }
                openPayPal()
            }
        }
    }

    private fun openPayPal() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://paypal.me/javcombita")))
        } catch (_: Exception) {
            toast(if (isEnglish()) "No app can open PayPal." else "No hay una aplicación para abrir PayPal.")
        }
    }

    @Suppress("DEPRECATION")
    private fun isEnglish(): Boolean = resources.configuration.locale.language == "en"

    private fun toast(message: String) {
        runOnUiThread { Toast.makeText(this, message, Toast.LENGTH_LONG).show() }
    }

    @Deprecated("Deprecated in Android API")
    override fun onBackPressed() {
        if (::webView.isInitialized) {
            webView.evaluateJavascript(
                "if(window.G&&G.UI&&G.SettingsUI&&G.SettingsUI.isOpen){G.SettingsUI.close();}else if(window.G&&G.Game&&G.Game.state!=='ui'){G.Game.togglePause&&G.Game.togglePause();}",
                null
            )
        } else {
            super.onBackPressed()
        }
    }
}
