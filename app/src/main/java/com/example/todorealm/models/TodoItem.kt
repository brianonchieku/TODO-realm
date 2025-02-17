package com.example.todorealm.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId


class TodoItem: RealmObject {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var title: String = ""
    var description: String = ""

}