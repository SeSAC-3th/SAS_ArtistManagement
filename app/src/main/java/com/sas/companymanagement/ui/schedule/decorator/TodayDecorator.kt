package com.sas.companymanagement.ui.schedule.decorator

import android.graphics.Typeface
import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
/**
 * Please explain the class!!
 *
 * @fileName             : TodayDecorator
 * @auther               : 박지혜
 * @since                : 2023-10-18
 **/
class TodayDecorator: DayViewDecorator {

    private val date = CalendarDay.today()
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(StyleSpan(Typeface.BOLD))
    }
}