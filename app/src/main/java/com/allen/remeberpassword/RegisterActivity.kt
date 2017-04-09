package com.allen.remeberpassword

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.allen.remeberpassword.model.User
import com.allen.remeberpassword.utilty.ToastUtilty
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        toolbar.setNavigationIcon(R.drawable.ic_action_back)
        toolbar.title = "注册密码"
        toolbar.setNavigationOnClickListener { finish() }
        register_text.setOnClickListener { register() }
    }

    fun register() {
        var account = account_editText.text.toString()
        var password = password_editTex.text.toString()
        var rePassword = repassword_editText.text.toString()
        if (valid(account, password, rePassword)) {
            var realm = Realm.getDefaultInstance()
            realm.executeTransaction {
                var user = User()
                user.account = account
                user.password = password
                realm.copyToRealm(user)
                ToastUtilty.longToast(this, "注册成功")
            }
        }
    }


    private fun valid(account: String, password: String, rePassword: String): Boolean {
        if (account.isEmpty()) {
            ToastUtilty.longToast(this, "账号不能为空")
            return false
        }
        if (password.isEmpty()) {
            ToastUtilty.longToast(this, "密码不能为空")
            return false
        }
        if (rePassword.isEmpty()) {
            ToastUtilty.longToast(this, "确认密码不能为空")
            return false
        }

        if(!rePassword.equals(password)) {
            ToastUtilty.longToast(this, "确认密码和密码必须相等")
            return false
        }
        return true
    }

}
