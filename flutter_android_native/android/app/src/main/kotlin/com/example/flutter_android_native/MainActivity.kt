package com.example.flutter_android_native

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

//import io.flutter.embedding.android.FlutterActivity

class MainActivity: FlutterActivity() {
private val CHANNEL = "com.example.flutter_android_native"

  override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
    super.configureFlutterEngine(flutterEngine)
    MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
      call, result ->
      if (call.method == "getBatteryPercentage") {
        val batteryPercentage = getBatteryPercentage();
        result.success(batteryPercentage)
      } else {
        result.notImplemented()
      }
    }
  }

    private fun getBatteryPercentage(): Int { 
      val batteryPercentage: Int 
      if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) { 
        val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager 
        batteryPercentage= batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) 
      } else { 
        val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED)) 
        batteryPercentage= intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1) 
      } 
        return batteryPercentage 
      }

  }