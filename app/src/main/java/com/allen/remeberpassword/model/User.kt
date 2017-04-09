package com.allen.remeberpassword.model

import io.realm.RealmObject

/**
 * Created by hasee on 2017/3/24.
 */
open class User : RealmObject() {
    open var account: String? = null
    open var password:String? = null
}
