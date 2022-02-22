package com.example.mynotes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.mynotes.databinding.ListDetailActivityBinding
import com.example.mynotes.ui.detail.ListDetailFragment
import com.example.mynotes.ui.detail.ListDetailViewModel

class ListDetailActivity : AppCompatActivity() {
    private lateinit var binding: ListDetailActivityBinding
    private lateinit var viewModel: ListDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListDetailViewModel::class.java)
        binding = ListDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addTaskButton.setOnClickListener {
            popupCreateTask()
        }

        viewModel.list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!
        title = viewModel.list.name

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListDetailFragment.newInstance())
                .commitNow()
        }
    }

    private fun popupCreateTask() {
        val editText = EditText(this)
        editText.inputType = InputType.TYPE_CLASS_TEXT

        AlertDialog.Builder(this).setTitle(R.string.task_name).setView(editText).setPositiveButton(R.string.add_task) { dialog, _ ->
            val task = editText.text.toString()
            viewModel.addTask(task)
            dialog.dismiss()
        }.create().show()
    }

    override fun onBackPressed() {
        val bundle = Bundle()
        bundle.putParcelable(MainActivity.INTENT_LIST_KEY, viewModel.list)
        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }
}