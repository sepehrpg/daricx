package com.example.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.HorizontalYearCalendar
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.VerticalYearCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.compose.yearcalendar.rememberYearCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.ExperimentalCalendarApi
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.Year
import com.kizitonwose.calendar.core.YearMonth
import com.kizitonwose.calendar.core.atEndOfMonth
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.minusMonths
import com.kizitonwose.calendar.core.minusYears
import com.kizitonwose.calendar.core.now
import com.kizitonwose.calendar.core.plusMonths
import com.kizitonwose.calendar.core.plusYears
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            )
        }
    }
}

@Composable
fun  MainScreen1() {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(0) } // Adjust as needed usually = 100
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library
    val daysOfWeek = daysOfWeek()
    //val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.THURSDAY)

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        //firstDayOfWeek = firstDayOfWeek,
        firstDayOfWeek = daysOfWeek.first()
    )

    /*Column {
        DaysOfWeekTitle(daysOfWeek = daysOfWeek) // Use the title here
        *//*HorizontalCalendar(
        state = state,
        dayContent = { Day(it) }
        )*//*
        VerticalCalendar(
            state = state,
            dayContent = { Day(it) },
        )
    }*/

    VerticalCalendar(
        state = state,
        dayContent = { Day(it) },
        monthHeader = {
            DaysOfWeekTitle(daysOfWeek = daysOfWeek) // Use the title as month header
        }
        /*monthHeader = { month ->
            // You may want to use `remember {}` here so the mapping is not done
            // every time as the days of week order will never change unless
            // you set a new value for `firstDayOfWeek` in the state.
            val daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }
            MonthHeader(daysOfWeek = daysOfWeek)
        }*/
    )

}

@Composable
fun MainScreen2() {
    val currentDate = remember { LocalDate.now() }
    val currentMonth = remember { YearMonth.now() }
    val startDate = remember { currentMonth.minusMonths(100).atStartOfMonth() } // Adjust as needed
    val endDate = remember { currentMonth.plusMonths(100).atEndOfMonth() } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = firstDayOfWeek
    )

    WeekCalendar(
        state = state,
        dayContent = { Day(it) }
    )
}

@OptIn(ExperimentalCalendarApi::class)
@Composable
fun MainScreen3() {
    val currentYear = remember { Year.now() }
    val startYear = remember { currentYear.minusYears(100) } // Adjust as needed
    val endYear = remember { currentYear.plusYears(100) } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

    val state = rememberYearCalendarState(
        startYear = startYear,
        endYear = endYear,
        firstVisibleYear = currentYear,
        firstDayOfWeek = firstDayOfWeek,
    )
    HorizontalYearCalendar(
        state = state,
        dayContent = { Day(it) },
    )

    /*VerticalYearCalendar(
        state = state,
        dayContent = { Day(it) }
    )*/
}

@Composable
fun Day(day: CalendarDay) {
    Box(
        modifier = Modifier
            .aspectRatio(1f), // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {
        Text(text = day.date.dayOfMonth.toString(),color = if (day.position == DayPosition.MonthDate) Color.Black else Color.Gray)

    }
}
@Composable
fun Day(day: WeekDay) {
    Box(
        modifier = Modifier
            .aspectRatio(1f), // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {
        Text(text = day.date.dayOfMonth.toString())
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CalenderPreview(){
    MainScreen1()
    //MainScreen2()
    //MainScreen3()
}





























//Some Library :
/*
-https://github.com/kizitonwose/Calendar?tab=readme-ov-file
-https://github.com/hi-manshu/Kalendar?tab=readme-ov-file
-https://github.com/aminography/PrimeDatePicker *(persian)
-https://github.com/WojciechOsak/Calendar
-https://github.com/andrewjapar/rangedatepicker
-https://github.com/ozcanalasalvar/picker
-https://github.com/epicarchitect/epic-calendar
-https://github.com/mateusz800/Expandable-Compose-Calendar
-https://github.com/rafsanjani/datepickertimeline
-https://github.com/razaghimahdi/Compose-Persian-Date-Picker
-https://github.com/alireza-milani/persian-date-range-picker *(persian)
-https://github.com/playmoweb/ComposeMultiDatePicker
-https://github.com/appmonkey8010/AMCalendar
-https://github.com/HeyPouya/PersianLinearDatePicker *(persian)
-https://github.com/khoyron/DatePickerView
-https://github.com/JoshHalvorson/calendar-date-range-picker
-https://github.com/razavioo/PersianDatePicker *(persian)
-https://github.com/uuranus/schedule-calendar-compose?tab=readme-ov-file
-https://github.com/Park-SM/ParkDateTimePicker
-https://github.com/hamooo90/jalali-datepicker-compose *(persian)
-https://github.com/AhmadNosratian/DatePickerDialog *(persian)
-https://github.com/aminography/PrimeCalendar *(persian)
-https://github.com/the-best-is-best/TComposeDateTimePicker *(persian)
 */