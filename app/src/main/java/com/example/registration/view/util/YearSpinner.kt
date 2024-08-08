package com.example.registration.view.util

import android.content.Context
import android.graphics.Typeface
import android.view.View
import com.example.registration.R
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.util.Calendar


fun generateYearList(startYear: Int = 1999): List<Int> {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    return (startYear..currentYear).toList()
}

fun <T> populateSpinner(
    context: Context,
    spinner: Spinner,
    items: List<T>,
    hint: String = "Select passing year",
    selectedItem: Int = 0,
    onItemSelected: (T?) -> Unit,
) {
    val itemList = mutableListOf(hint)
    itemList.addAll(items.map { it.toString() })

    val adapter = object : ArrayAdapter<String>(
        context,
        android.R.layout.simple_spinner_item,
        itemList
    ) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent) as TextView
            customizeView(view, position)
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent) as TextView
            customizeView(view, position)
            return view
        }

        private fun customizeView(view: TextView, position: Int) {
            val item = getItem(position) ?: return
            if (item == hint) {
                view.setTextColor(ContextCompat.getColor(context, R.color.hintColor))
                view.setTypeface(view.typeface, Typeface.ITALIC)
            } else {
                view.setTextColor(ContextCompat.getColor(context, R.color.black))
                view.typeface = Typeface.DEFAULT
            }
        }
    }.apply {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    spinner.adapter = adapter
    spinner.setSelection(selectedItem, false)

    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            if (position == 0) {
                onItemSelected(null)
            } else {
                onItemSelected(items[position - 1])
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }
}

