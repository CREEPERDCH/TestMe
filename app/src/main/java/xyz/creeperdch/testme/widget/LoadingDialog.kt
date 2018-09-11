package xyz.creeperdch.testme.widget

import android.app.Dialog
import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import xyz.creeperdch.testme.R

class LoadingDialog(context: Context, private var isCancel: Boolean? = true) : Dialog(context, R.style.Theme_Dialog_Alpha) {

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_progress_layout, null)
        setContentView(view)
        this.setCancelable(isCancel!!)
        this.setCanceledOnTouchOutside(false)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && isCancel!!)
            false
        else
            super.onKeyDown(keyCode, event)
    }
}