package com.kdx.dreamboxdemo

import android.Manifest
import android.annotation.TargetApi
import android.os.Build
import android.webkit.*
import com.kdx.dreamboxdemo.databinding.ActivityMainBinding
import com.kdx.dreamboxdemo.util.showToast
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Use H5 play local Video
 * @author Eason
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutID(): Int = R.layout.activity_main

    override fun initView() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
            requestPermission(Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE)

        bindView.webView.settings.apply {
            javaScriptEnabled = true
            setSupportZoom(false)
            builtInZoomControls = false
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            pluginState = WebSettings.PluginState.ON
        }

        bindView.webView.apply {
            loadUrl("file:///android_asset/index.html")
            webChromeClient = WebChromeClient()
        }

        bindView.webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                //execute js method
                view?.loadUrl("javascript:play()")
                showToast("Start to Play")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //release webView resource
        webView.destroy()
    }

    /**
     * request Permission, SDK must >= Android 6.0
     */
    @TargetApi(value = Build.VERSION_CODES.M)
    private fun requestPermission(vararg permission: String) {
        RxPermissions(this).request(*permission)
                //lambda 和 SAM 转换的结合使用
                .subscribe({ granted -> if (!granted) finish() }, { err -> println(err) })
    }
}
