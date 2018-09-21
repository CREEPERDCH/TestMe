package xyz.creeperdch.testme.util

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by creeper on 2018/9/19
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
object MD5Util {

    fun getMD5(info: String): String {
        try {
            val md5 = MessageDigest.getInstance("MD5")
            md5.update(info.toByteArray(charset("UTF-8")))
            val encryption = md5.digest()
            val strBuf = StringBuffer()
            for (i in encryption.indices) {
                if (Integer.toHexString(0xff and encryption[i].toInt()).length == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff and encryption[i].toInt()))
                } else {
                    strBuf.append(Integer.toHexString(0xff and encryption[i].toInt()))
                }
            }
            return strBuf.toString()
        } catch (e: NoSuchAlgorithmException) {
            return ""
        } catch (e: UnsupportedEncodingException) {
            return ""
        }

    }

}