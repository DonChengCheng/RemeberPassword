package com.allen.remeberpassword

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.allen.remeberpassword.model.UserInfo
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : AppCompatActivity() {
    companion object {
        val REQUEST: Int = 0
        fun newIntent(context: Context): Intent {
            return Intent(context, UserInfoActivity::class.java)
        }
    }

    var userInfoAdapter: UserInfoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        toolbar.title = "用户信息"
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_action_back)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.inflateMenu(R.menu.add_info)
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.add) {
                startActivityForResult(AddUserInfoActivity.newIntent(this@UserInfoActivity), REQUEST)
                true
            }
            false
        }
        recyceView.layoutManager = LinearLayoutManager(this)
        userInfoAdapter = UserInfoAdapter(this)
        recyceView.adapter = userInfoAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST) {
            var realm: Realm = Realm.getDefaultInstance()
            var realmResults: RealmResults<UserInfo> = realm.where(UserInfo::class.java).findAll()
            var userInfoList: List<UserInfo> = realm.copyFromRealm(realmResults)
            userInfoAdapter?.addData(userInfoList)
        }
    }
}
