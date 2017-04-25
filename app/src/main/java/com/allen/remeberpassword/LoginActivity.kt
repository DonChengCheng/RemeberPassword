package com.allen.remeberpassword

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.allen.remeberpassword.model.User
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(){
    private var mAuthTask: UserLoginTask? = null

    private var mEmailView: AutoCompleteTextView? = null
    private var mPasswordView: EditText? = null
    private var mProgressView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mEmailView = findViewById(R.id.email) as AutoCompleteTextView
        mPasswordView = findViewById(R.id.password) as EditText
        mPasswordView!!.setOnEditorActionListener(TextView.OnEditorActionListener { textView, id, keyEvent ->
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        val mEmailSignInButton = findViewById(R.id.email_sign_in_button) as Button
        mEmailSignInButton.setOnClickListener { attemptLogin() }

        mProgressView = findViewById(R.id.login_progress)
        findViewById(R.id.register_text).setOnClickListener {
            var intent = Intent(LoginActivity@ this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }




    private fun attemptLogin() {
        // Reset errors.
        mEmailView!!.error = null
        mPasswordView!!.error = null

        // Store values at the time of the login attempt.
        val email = mEmailView!!.text.toString()
        val password = mPasswordView!!.text.toString()

        var cancel = false
        var focusView: View? = null

        if (TextUtils.isEmpty(email)) {
            mEmailView!!.error = getString(R.string.error_field_required)
            focusView = mEmailView
            cancel = true
        }

        if (cancel) {
            focusView!!.requestFocus()
        } else {
            mAuthTask = UserLoginTask(email, password)
            mAuthTask!!.execute(null as Void?)
        }
    }



    inner class UserLoginTask internal constructor(private val account: String, private val mPassword: String) : AsyncTask<Void, Void, List<User>>() {

        override fun doInBackground(vararg params: Void): List<User>? {
            var realm = Realm.getDefaultInstance()
            var results = realm.where(User::class.java).equalTo("account", account).equalTo("password", mPassword).findAll()
            return realm.copyFromRealm(results)
        }

        override fun onPostExecute(results: List<User>?) {
            showProgress(false)
            if (results != null && !results.isEmpty()) {
                startActivity(UserInfoActivity.newIntent(this@LoginActivity))
                finish()
            } else {
                mPasswordView!!.error = getString(R.string.error_incorrect_password)
                mPasswordView!!.requestFocus()
            }
        }

        override fun onCancelled() {
            mAuthTask = null
            showProgress(false)
        }
    }

    private fun showProgress(visible: Boolean) {
        if(visible) {
            login_progress.visibility = View.VISIBLE
        } else {
            login_progress.visibility = View.GONE
        }
    }

}

