package com.test.app.ui.tools

import com.test.app.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.security.MessageDigest
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class SignCreator @Inject constructor() {

    @Provides
    fun createSign(timestamp: Long) : String {
        val key = "$timestamp${BuildConfig.PRIVATE_KEY}${BuildConfig.PUBLIC_KEY}"
        return key.toMD5()
    }

    private fun String.toMD5() : String {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.toHex()
    }

    private fun ByteArray.toHex() : String {
        return joinToString("") { "%02x".format(it) }
    }

}