package com.example.flutter_android_native

import android.widget.Toast
import io.flutter.Log
import io.flutter.embedding.android.FlutterActivity

import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
 
class MainActivity: FlutterActivity() {
    private val CHANNEL = "yourpackageName/channelName";
    private lateinit var channel: MethodChannel
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
        channel.setMethodCallHandler { call, result ->
            var args = call.arguments() as Map<String, String>;
            var message = args["message"];
            if (call.method == "showToastMessage") {
                Log.d("TAG", "message");
                if (message != null) {
                    showToastMessage(message)
                };
            }
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}


