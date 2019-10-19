package com.jmarkstar.sampletest

import com.jmarkstar.sampletest.models.Photo
import com.jmarkstar.sampletest.models.User

val user1 = User(1, "user1","user1@gmail.com","1111111")
val user2 = User(2, "user2","user1@gmail.com","1111111")
val user3 = User(3, "user3","user1@gmail.com","1111111")
val user4 = User(4, "user4","user1@gmail.com","1111111")

val users = arrayOf(user1, user2, user3, user4).toList()
val userIds = arrayOf(1L,2L,3L,4L).toList()
val incompleteUserIds = arrayOf(1L,2L,3L).toList()

//photos user.json 1
val photo1 = Photo(1, 1, "photo1", "link", "link")
val photo2 = Photo(2, 1, "photo1", "link", "link")

//photos user.json 2
val photo3 = Photo(3, 2, "photo1", "link", "link")
val photo4 = Photo(4, 2, "photo1", "link", "link")

val photos = arrayOf(photo1, photo2, photo3, photo4).toList()
val photoIds = arrayOf(1L,2L,3L,4L).toList()
val incompletePhotoIds = arrayOf(1L,2L,3L).toList()

