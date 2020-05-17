package com.example.smart_home

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Browser
import kotlinx.android.synthetic.main.activity_main.*
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import kotlin.math.log
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebViewRenderProcessClient
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.concurrent.thread


@ExperimentalStdlibApi
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Browser.settings.javaScriptEnabled = true

        var argument = intent.getStringExtra("ip")
        Browser.loadUrl("http://" + argument)

        Browser.webViewClient  = object : WebViewClient()
        {
            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = ProgressBar.INVISIBLE
                textView3.visibility = TextView.INVISIBLE
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility = ProgressBar.VISIBLE
                textView3.visibility = TextView.VISIBLE
            }
        }
    }
    fun onClick(View: View)
    {
        var intent = Intent(this,Initial::class.java)
        startActivity(intent)
    }



}
