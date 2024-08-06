package com.example.registration.view.util

import android.R
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import java.util.Calendar

class Util {


    object SpinnerUtils {
        fun generateYearList(startYear: Int = 1999): List<Int> {
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            return (startYear..currentYear).toList()
        }

        fun <T> populateSpinner(
            context: Context,
            spinner: Spinner,
            items: List<T>,
            hint: String = "Select an item",
            onItemSelected: (T?) -> Unit
        ) {
            // Create a mutable list with hint item
            val itemList = mutableListOf(hint)
            itemList.addAll(items.map { it.toString() })

            // Create and set the adapter
            val adapter = ArrayAdapter(
                context,
                R.layout.simple_spinner_item,
                itemList
            ).apply {
                setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            }
            spinner.adapter = adapter
            spinner.setSelection(0, false)

            // Set the item selected listener
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
                    // Handle case where no item is selected
                }
            }
        }
    }

}