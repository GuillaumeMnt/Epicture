package com.martret_monot.epicture.Controller

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.martret_monot.epicture.R
import kotlinx.android.synthetic.main.activity_auth_vc.*
import com.martret_monot.epicture.R.id.imgurWebView
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.Toast
import com.martret_monot.epicture.Constants
import com.martret_monot.epicture.Preference
import com.martret_monot.epicture.R.id.imgurWebView




class AuthVC : AppCompatActivity() {

    var _token: String? = null
    var _username: String? = null

    /// disable : Error disallowed_useragent
    val USER_AGENT = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_vc)

        handleWebView()
    }

    private fun handleWebView() {
        imgurWebView.setBackgroundColor(Color.TRANSPARENT)
        val settings = imgurWebView.settings
        settings.setSupportMultipleWindows(true)
        val url: String = Constants.BASE_URL + "oauth2/authorize?client_id=" + Constants.CLIENT_ID + "&response_type=" + Constants.RESPONSE_TYPE + "&state="

        imgurWebView.getSettings().setUserAgentString(USER_AGENT);
        imgurWebView.loadUrl(url)
        imgurWebView.settings.javaScriptEnabled = true
        imgurWebView.webViewClient= object: WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val urlString = request?.url?.toString()

                if (urlString != null && urlString.contains("https://imgur.com/?")) {
                    val accessToken: String = getStringFromUrl(urlString, "access_token=")
                    val refreshToken: String = getStringFromUrl(urlString, "refresh_token=")
                    val accountUsername: String = getStringFromUrl(urlString, "account_username=")
                    val sharedPref = Preference(applicationContext)
                    sharedPref.setAccessToken(accessToken)
                    sharedPref.setRefreshToken(refreshToken)
                    sharedPref.setAccountUsername(accountUsername)
                    pushToHome()
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

    }


    /// MARK : Helper for the Authentication (parsing + change view)
    private fun getStringFromUrl(ref: String, substr: String): String {

        val sequence: String = ref.substring(ref.indexOf(substr) + substr.length)
        return sequence.substring(0, sequence.indexOf('&'))
    }

    fun pushToHome() {
        // Create an Intent to start the second activity
        val randomIntent = Intent(this, HomeVC::class.java)

        // Start the new activity.
        randomIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(randomIntent)
    }
}
