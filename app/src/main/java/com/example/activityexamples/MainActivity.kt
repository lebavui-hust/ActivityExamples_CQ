package com.example.activityexamples

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    var actionMode: ActionMode? = null
    val actionModeCallback = object: ActionMode.Callback {
        override fun onCreateActionMode(
            mode: ActionMode?,
            menu: Menu?
        ): Boolean {
            menuInflater.inflate(R.menu.context_menu, menu)
            return true
        }

        override fun onPrepareActionMode(
            mode: ActionMode?,
            menu: Menu?
        ): Boolean {
            return false
        }

        override fun onActionItemClicked(
            mode: ActionMode?,
            item: MenuItem?
        ): Boolean {
            when (item?.itemId) {
                R.id.action_download -> {}
                R.id.action_share -> {}
                R.id.action_settings -> {}
            }
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Main Screen"

        val edit1 = findViewById<EditText>(R.id.edit1)
        val edit2 = findViewById<EditText>(R.id.edit2)
        val textResult = findViewById<TextView>(R.id.text_result)

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val result = it.data?.getIntExtra("Result", 0)
                textResult.text = "Result: $result"
            } else {
                val message = it.data?.getStringExtra("Message")
                textResult.text = "Failed: $message"
            }
        }

        findViewById<Button>(R.id.button_calc).setOnClickListener {
            val intent = Intent(this, CalcActivity::class.java)
            intent.putExtra("param1", edit1.text.toString())
            intent.putExtra("param2", edit2.text.toString())
            launcher.launch(intent)
        }

        val imageView = findViewById<ImageView>(R.id.imageView)
        val launcher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val result = it.data?.getParcelableExtra<Bitmap>("data")
                imageView.setImageBitmap(result)
            }
        }

        findViewById<Button>(R.id.button_take_photo).setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            launcher2.launch(intent)
        }

        findViewById<Button>(R.id.button_open_list).setOnClickListener {
            val intent = Intent(this, SimpleListActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_action_mode).setOnClickListener {
            if (actionMode == null)
                actionMode = startSupportActionMode(actionModeCallback)
        }

        findViewById<Button>(R.id.button_popup).setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.inflate(R.menu.context_menu)
            popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when (item?.itemId) {
                        R.id.action_download -> {}
                        R.id.action_share -> {}
                        R.id.action_settings -> {}
                    }
                    return true
                }
            })
            popupMenu.show()
        }

        registerForContextMenu(imageView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_download -> {}
            R.id.action_share -> {}
            R.id.action_settings -> {}
        }
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_download -> {}
            R.id.action_share -> {}
            R.id.action_settings -> {}
        }
        return true
    }
}