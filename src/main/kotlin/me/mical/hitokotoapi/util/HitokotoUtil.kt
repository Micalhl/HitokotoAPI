package me.mical.hitokotoapi.util

import com.google.gson.Gson
import io.izzel.taboolib.module.locale.TLocale
import me.mical.hitokotoapi.feature.Hitokoto
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

/**
 * 获取 一言 句子的工具类
 * @author Mical
 * @date 2021/1/16 15:25
 */
object HitokotoUtil {

    /**
     * 类型列表
     * 详见 HitokotoType.kt
     */
    private val typeList = arrayListOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l")

    /**
     * 获取一言
     * 返回为一串json, 需要自己手动转换
     * @return json
     */
    fun get(type: List<String>?, min_length: Int, max_length: Int): String? {
        if (type != null) {
            if (type.isNotEmpty()) {
                for (type0 in type) {
                    if (type0 != "random" && !typeList.contains(type0)) return null
                }
            }
        }
        var connection: HttpURLConnection? = null
        val apiUrl = "https://v1.hitokoto.cn/"
        var subUrl: String = if (type != null) {
            when {
                type.size > 1 -> {
                    val temp = StringBuilder(255).append("?")
                    for (type0 in type) {
                        temp.append("c=$type0&")
                    }
                    temp.deleteCharAt(temp.lastIndex)
                    temp.toString().trim()
                }
                type.size == 1 -> "?c=${type[0]}"
                else -> ""
            }
        } else ""
        val limit = "min_length=${min_length}&max_length=${max_length}"
        subUrl = if (subUrl.isEmpty()) "?${limit}" else "${subUrl}&${limit}"
        try {
            val timeout = 5000
            val url =
                URL(apiUrl + subUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = timeout
            val buffer = StringBuilder(255)
            BufferedReader(InputStreamReader(connection.inputStream, StandardCharsets.UTF_8)).use { reader ->
                val buffer0 = CharArray(255)
                while (true) {
                    val length = reader.read(buffer0)
                    if (length == -1) break
                    buffer.append(buffer0, 0, length)
                }
            }
            return buffer.toString().trim { it <= ' ' }
        } catch (e: IOException) {
            if (e.localizedMessage.contains("514")) {
                TLocale.sendToConsole("Error")
                return get(type, min_length, max_length)
            }
        } finally {
            connection?.disconnect()
        }
        return null
    }

    /**
     * 获取一言对应的 Hitokoto 对象
     * 用 get 方法获取到的json content转为 Hitokoto 对象
     * @return Hitokoto
     */
    fun getHitokoto(
        type: List<String>?,
        min_length: Int,
        max_length: Int,
    ): Hitokoto = Gson().fromJson(get(type, min_length, max_length), Hitokoto::class.java)

    /**
     * 获取一言对应的 Hitokoto 对象
     * 与上一个方法不同的是, 此方法只需要传入 content 即可获取
     * @return Hitokoto
     */
    fun getHitokoto(
            content: String
    ): Hitokoto {
        kotlin.runCatching {
            return Gson().fromJson(content, Hitokoto::class.java)
        }.onFailure {
            return getHitokoto(null, 5, 30)
        }
        return getHitokoto(null, 5, 30)
    }
}