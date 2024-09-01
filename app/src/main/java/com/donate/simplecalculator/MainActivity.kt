package com.donate.simplecalculator

import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.GestureDetector
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var editText: EditText
    private lateinit var addButton: Button
    private val items = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private var selectedItemPosition = -1

    private lateinit var gestureDetector: GestureDetector

    private val checkedStates = SparseBooleanArray() // To track CheckBox states

    private companion object {
        private const val INVALID_POSITION = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        editText = findViewById(R.id.editText)
        addButton = findViewById(R.id.addButton)

        // Create GestureDetector
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                // Handle double-tap
                val view = listView.findChildViewUnder(e?.x ?: 0f, e?.y ?: 0f)
                view?.let {
                    openContextMenu(it)
                }
                return true
            }
        })

        // Setup Adapter
        adapter = object : ArrayAdapter<String>(this, R.layout.list_item, R.id.textView, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)

                // Retrieve CheckBox and set its checked state
                val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
                checkBox.isChecked = checkedStates.get(position, false)
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    checkedStates.put(position, isChecked)
                }

                view.setOnTouchListener { v, event ->
                    // Handle gestures and clicks
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        selectedItemPosition = position
                    }
                    gestureDetector.onTouchEvent(event)
                    true
                }

                return view
            }
        }

        listView.adapter = adapter
        registerForContextMenu(listView)

        addButton.setOnClickListener {
            val task = editText.text.toString()
            if (task.isNotEmpty()) {
                items.add(task)
                editText.text.clear()
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit -> {
                showEditDialog()
                true
            }
            R.id.delete -> {
                items.removeAt(selectedItemPosition)
                checkedStates.delete(selectedItemPosition) // Remove the checked state
                adapter.notifyDataSetChanged()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun showEditDialog() {
        val currentTask = items[selectedItemPosition]

        val editText = EditText(this)
        editText.setText(currentTask)

        AlertDialog.Builder(this)
            .setTitle("Edit Task")
            .setMessage("Modify the task")
            .setView(editText)
            .setPositiveButton("Save") { dialog, _ ->
                val newTask = editText.text.toString()
                if (newTask.isNotEmpty()) {
                    items[selectedItemPosition] = newTask
                    adapter.notifyDataSetChanged()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun ListView.findChildViewUnder(x: Float, y: Float): View? {
        val position = pointToPosition(x.toInt(), y.toInt())
        return if (position == INVALID_POSITION) null else getChildAt(position - firstVisiblePosition)
    }
}
