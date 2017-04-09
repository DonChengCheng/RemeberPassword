package com.allen.remeberpassword.utilty

import android.content.Context
import android.widget.Toast

/**
 * Created by hasee on 2017/3/20.
 */
class ToastUtilty {

    companion object {
        fun shortToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        fun longToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }
    }
}


