package com.orz.im.globallib.util

import android.annotation.SuppressLint
import com.tencent.mmkv.MMKV
import java.io.*
import kotlin.reflect.KProperty

/**
 * Created by orz on 2017/12/11.
 * 微信中使用的高效，小型，易于使用的移动键值存储工具类
 * 替换SharePreference方案
 */
class PrefsUtil<T>(val name:String, private val default:T) {

    companion object {
        val prefs: MMKV by lazy {
            MMKV.defaultMMKV()
        }

        /**
         * 查询某个key是否已经存在
         *
         * @param key
         * @return
         */
        fun contains(key: String): Boolean {
            return prefs.contains(key)
        }

        /**
         * 返回所有的键值对
         * @return
         */
        fun getAll(): Map<String, *> {
            return prefs.all
        }

        /**
         * 删除全部数据
         */
        fun clearAllPreference(){
            prefs.clearAll()
        }

        /**
         * 根据key删除存储数据
         */
        fun removePreference(key : String){
            prefs.remove(key)
        }
    }

    operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        return getPreference(name, default)
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    @SuppressLint("CommitPrefEdits")
    private fun putPreference(name: String, value: T) = with(prefs) {
        when (value) {
            is Long -> encode(name, value)
            is String -> encode(name, value)
            is Int -> encode(name, value)
            is Boolean -> encode(name, value)
            is Float -> encode(name, value)
            else -> encode(name,serialize(value))
        }
    }

    @Suppress("UNCHECKED_CAST")
     fun getPreference(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> decodeLong(name, default)
            is String -> decodeString(name, default)
            is Int -> decodeInt(name, default)
            is Boolean -> decodeBool(name, default)
            is Float -> decodeFloat(name, default)
            else ->  deSerialization(decodeString(name,serialize(default))?:"")
        }
        return res as T
    }



    /**
     * 序列化对象
     * @param obj
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun<A> serialize(obj: A): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(
                byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    /**
     * 反序列化对象

     * @param str
     * *
     * @return
     * *
     * @throws IOException
     * *
     * @throws ClassNotFoundException
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun<A> deSerialization(str: String): A {
        val redStr = java.net.URLDecoder.decode(str, "UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(
                redStr.toByteArray(charset("ISO-8859-1")))
        val objectInputStream = ObjectInputStream(
                byteArrayInputStream)
        val obj = objectInputStream.readObject() as A
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }

}
