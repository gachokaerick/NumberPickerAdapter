package com.gachokaerick.android.numberpicker

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Number picker recycler adapter
 * Configures your recyclerview to show a list of selectable items
 *
 * @property context activity context
 * @property dataList a list of strings to be displayed
 * @property selectedItemDrawable background drawable to use when item is selected
 * @property unselectedItemDrawable background drawable to use when item is deselected
 * @property selectedItemTextColor hex color code to use when item is selected e.g. #00ff00
 * @property unselectedItemTextColor hex color code to use when item is deselected e.g. #ffff00
 * @property onItemClicked callback function to be used when item is clicked
 * @property tvWidth width of textView in dp
 * @property tvHeight height of textView in dp
 * @property tvMarginTop top margin of textView in dp
 * @property tvMarginBottom bottom margin of textView in dp
 * @property tvMarginLeft left margin of textView in dp
 * @property tvMarginRight right margin of textView in dp
 * @property tvMarginPaddingTop top padding of textView in dp
 * @property tvMarginPaddingBottom bottom padding of textView in dp
 * @property tvMarginPaddingLeft left padding of textView in dp
 * @property tvMarginPaddingRight right padding of textView in dp
 * @property tvTextSize size of textView in sp
 * @constructor Create empty Number picker recycler adapter
 */
class NumberPickerRecyclerAdapter(
    private val context: Context,
    private val dataList: List<String>,
    private val selectedItemDrawable: Drawable?,
    private val unselectedItemDrawable: Drawable?,
    private val selectedItemTextColor: String?,
    private val unselectedItemTextColor: String?,
    var onItemClicked: ItemClickedLambda,
    private val tvWidth: Int = 90,
    private val tvHeight: Int = 50,
    private val tvMarginTop: Int = 0,
    private val tvMarginBottom: Int = 0,
    private val tvMarginLeft: Int = 0,
    private val tvMarginRight: Int = 0,
    private val tvMarginPaddingTop: Int = 12,
    private val tvMarginPaddingBottom: Int = 12,
    private val tvMarginPaddingLeft: Int = 12,
    private val tvMarginPaddingRight: Int = 12,
    private val tvTextSize: Int = 16
) :
    RecyclerView.Adapter<NumberPickerRecyclerAdapter.ViewHolder>() {
    private var selectedItemContent = "" // holds content of last selected item
    private val selectedItemsList = mutableListOf<TextView>() // list of selected textViews

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.number_picker_item, parent, false)
        val viewHolder = ViewHolder(view)

        configureTextView(viewHolder)

        return viewHolder
    }

    // configures the TextView with adapter constructor parameters
    private fun configureTextView(viewHolder: NumberPickerRecyclerAdapter.ViewHolder) {
        viewHolder.itemText.textSize = tvTextSize.spToPixels(context).toFloat()
        viewHolder.itemText.width = tvWidth.dpToPixels(context)
        viewHolder.itemText.height = tvHeight.dpToPixels(context)

        viewHolder.itemText.setPadding(
            tvMarginPaddingLeft,
            tvMarginPaddingTop,
            tvMarginPaddingRight,
            tvMarginPaddingBottom
        )

        (viewHolder.itemText.layoutParams as RelativeLayout.LayoutParams).apply {
            setMargins(
                tvMarginLeft.dpToPixels(context), // left/start margin
                tvMarginTop.dpToPixels(context), // top margin
                tvMarginRight.dpToPixels(context), // right/end margin
                tvMarginBottom.dpToPixels(context) // bottom margin
            )
        }
    }

    // converts dp value to px
    private fun Int.dpToPixels(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()

    // converts sp value to px
    private fun Int.spToPixels(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this.toFloat(), context.resources.displayMetrics
    ).toInt()

    // retrieve item at particular adapter position
    private fun getItem(position: Int): String {
        return dataList[position]
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = dataList[position]
        with(holder) {
            itemText.text = text

            // if we scroll to the item we clicked last, make it selected
            // and deselect any others that might have been selected
            if (text == selectedItemContent) {
                selectItem(itemText)
            } else {
                deSelectItem(itemText)
            }

            // select an item when it gets clicked
            itemText.setOnClickListener {
                onItemClicked(
                    itemText,
                    getItem(position)
                ) // call the callback function and pass in the clicked item
                selectedItemContent = text
                selectItem(it as TextView)
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemText: TextView = itemView.findViewById(R.id.tvScrollListItem)
    }

    // selects the textView passed in and deselects all previously selected items
    private fun selectItem(v: TextView) {
        v.background = selectedItemDrawable
        if (selectedItemTextColor != null) {
            v.setTextColor(Color.parseColor(selectedItemTextColor))
        }
        selectedItemsList.add(v) // add selected item to our list of selected items
        for (item in selectedItemsList) {
            // deselect previously selected items
            if (item.text != v.text) {
                deSelectItem(item)
            }
        }
    }

    // deselects the textView passed in
    private fun deSelectItem(v: TextView) {
        v.background = unselectedItemDrawable
        if (unselectedItemTextColor != null) {
            v.setTextColor(Color.parseColor(unselectedItemTextColor))
        }
    }

}