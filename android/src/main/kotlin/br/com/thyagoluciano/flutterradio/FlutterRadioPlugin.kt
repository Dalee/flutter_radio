package br.com.thyagoluciano.flutterradio

import android.content.Context
import br.com.thyagoluciano.flutterradio.player.RadioManager
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.PluginRegistry.Registrar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class FlutterRadioPlugin(val mRegistrar: Registrar): MethodCallHandler {

  lateinit var radioManager: RadioManager

  companion object {
    private lateinit var channel: MethodChannel
    @JvmStatic
    fun registerWith(registrar: Registrar): Unit {
      channel = MethodChannel(registrar.messenger(), "flutter_radio")
      channel.setMethodCallHandler(FlutterRadioPlugin(registrar))
      EventBus.getDefault().register(this)
    }

    @Subscribe
    fun onMessageEvent(event: String) {
      channel.invokeMethod("onMessage", event);
    }
  }

  override fun onMethodCall(call: MethodCall, result: Result): Unit {
      when {
        call.method.equals("audioStart") -> {
          this.startPlayer()
          result.success(null)
        }
        call.method.equals("play") -> {
          val url: String? = call.argument("url")
          if (url != null)
            radioManager.playOrPause(url)
          result.success(null)
        }
        call.method.equals("pause") -> {
          val url: String? = call.argument("url")
          if (url != null)
            radioManager.playOrPause(url)
          result.success(null)
        }
        call.method.equals("playOrPause") -> {
          val url: String? = call.argument("url")
          if (url != null)
            radioManager.playOrPause(url)
          result.success(null)
        }
        call.method.equals("stop") -> {
          radioManager.stop()
          result.success(null)
        }
        call.method.equals("isPlaying") -> {
            val play = isPlaying()
            result.success(play)
        }
        else -> result.notImplemented()
      }
  }

  private fun startPlayer() {
    val context: Context = mRegistrar.context()
    radioManager = RadioManager(context)
    radioManager.initPlayer()
  }

  private fun isPlaying() : Boolean {
      return radioManager.isPlaying()
  }

}
