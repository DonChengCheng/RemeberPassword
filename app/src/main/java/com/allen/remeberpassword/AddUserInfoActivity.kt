package com.allen.remeberpassword

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.allen.remeberpassword.model.UserInfo
import com.allen.remeberpassword.utilty.ToastUtilty
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_add_user_info.*

class AddUserInfoActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AddUserInfoActivity::class.java)
        }

        fun newIntent(context: Context, userInfo: UserInfo): Intent {
            var intent = Intent(context, AddUserInfoActivity::class.java)
            intent.putExtra(DATA, userInfo)
            return intent
        }

        val DATA: String = "DATA"
    }

    var mUserInfo: UserInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user_info)
        toolbar.title = getString(R.string.add_user_account_info)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_action_back)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.inflateMenu(R.menu.save_info)
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.save) {
                saveInfo()
                true
            }
            false
        }
        mUserInfo = intent.getSerializableExtra(DATA) as UserInfo
        titleEdit.setText(mUserInfo?.title)
        accountEdit.setText(mUserInfo?.account)
        passwordEdit.setText(mUserInfo?.password)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_info, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.save) {
            saveInfo()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveInfo() {
        var title = titleEdit.text.toString()
        var account = accountEdit.text.toString()
        var password = passwordEdit.text.toString()

        var realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            if(mUserInfo == null) {
                var passwordInfo = UserInfo()
                passwordInfo.title = title
                passwordInfo.account = account
                passwordInfo.password = password
                realm.copyToRealm(passwordInfo)
            } else {
                var userInfo = realm.where(UserInfo::class.java).equalTo("title", mUserInfo?.title).findFirst()
                userInfo.title = title
                userInfo.account = account
                userInfo.password = password
            }
            ToastUtilty.longToast(this, getString(R.string.save_info_success))
            setResult(Activity.RESULT_OK)
            finish()

        }

    }
}
