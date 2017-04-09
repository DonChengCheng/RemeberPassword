package com.allen.remeberpassword

import android.app.Application
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider

import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by hasee on 2017/3/24.
 */

class RemberPasswordApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build())
    }
}
