package com.example.todorealm

import android.app.Application
import com.example.todorealm.models.TodoItem
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class MyApp: Application() {

    companion object{
        lateinit var realm: Realm
    }

    override fun onCreate() {
        super.onCreate()
        realm = Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(
                    TodoItem::class
                )
            )
        )
    }
}