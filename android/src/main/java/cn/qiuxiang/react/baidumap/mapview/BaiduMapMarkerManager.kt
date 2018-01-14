package cn.qiuxiang.react.baidumap.mapview

import android.view.View
import cn.qiuxiang.react.baidumap.initialize
import cn.qiuxiang.react.baidumap.toLatLng
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.annotations.ReactProp

@Suppress("unused")
class BaiduMapMarkerManager : ViewGroupManager<BaiduMapMarker>() {
    override fun getName(): String {
        return "BaiduMapMarker"
    }

    override fun createViewInstance(context: ThemedReactContext): BaiduMapMarker {
        initialize(context)
        return BaiduMapMarker(context)
    }

    override fun addView(marker: BaiduMapMarker, view: View, index: Int) {
        when (view) {
            is BaiduMapCallout -> marker.setInfoWindow(view)
            else -> super.addView(marker, view, index)
        }
    }

    override fun getExportedCustomDirectEventTypeConstants(): Map<String, Any> {
        return MapBuilder.of(
            "onPress", MapBuilder.of("registrationName", "onBaiduMapPress")
        )
    }

    companion object {
        val SELECT = 0
    }

    override fun getCommandsMap(): Map<String, Int> {
        return mapOf(
            "select" to SELECT
        )
    }

    override fun receiveCommand(marker: BaiduMapMarker, commandId: Int, args: ReadableArray?) {
        when (commandId) {
            SELECT -> marker.select()
        }
    }

    @ReactProp(name = "coordinate")
    fun setCoordinate(view: BaiduMapMarker, coordinate: ReadableMap) {
        view.setPosition(coordinate.toLatLng())
    }

    @ReactProp(name = "title")
    fun setTitle(view: BaiduMapMarker, title: String) {
        view.setTitle(title)
    }

    @ReactProp(name = "color", customType = "Color")
    fun setColor(view: BaiduMapMarker, color: Int) {
        view.setColor(color)
    }

    @ReactProp(name = "image")
    fun setImage(view: BaiduMapMarker, image: String) {
        view.setImage(image)
    }
}
