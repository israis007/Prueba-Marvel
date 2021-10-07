package com.test.app.ui.tools

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class CalendarUtils @Inject constructor() {

    @Provides
    fun getLocalFromMarvel(calendar: String): String {
        val date = calendar.replace("T", " ").substringBeforeLast("-")
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val simpleDateFormatEsp = SimpleDateFormat("dd 'de' MMMM 'del' yyyy 'a las' hh:mm:ss a", Locale.getDefault())
        val cal1 = simpleDateFormat.parse(date)
        return simpleDateFormatEsp.format(cal1)
    }

}