package com.allen.remeberpassword.model

import io.realm.RealmObject

/**
 * Created by hasee on 2017/4/2.
 */
open class UserInfo : RealmObject() {
    open var title: String? = null
    open var account: String? = null
    open var password: String? = null
}