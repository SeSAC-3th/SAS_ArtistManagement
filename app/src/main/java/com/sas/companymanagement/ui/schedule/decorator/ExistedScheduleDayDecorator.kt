package com.sas.companymanagement.ui.schedule.decorator

import android.graphics.Color
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
/**
 * Please explain the class!!
 *
 * @fileName             : ExistedScheduleDayDecorator
 * @auther               : 박지혜
 * @since                : 2023-10-18
 **/
class ExistedScheduleDayDecorator(day:CalendarDay): DayViewDecorator {

    private val date:CalendarDay = day
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(10F, Color.parseColor("#ff0000")))
    }
}