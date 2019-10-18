package com.jmarkstar.sampletest.repository

import com.jmarkstar.sampletest.models.FailureReason
import com.jmarkstar.sampletest.models.Result
import com.jmarkstar.sampletest.models.User
import com.jmarkstar.sampletest.repository.local.daos.UserDao
import com.jmarkstar.sampletest.repository.network.UserService
import com.jmarkstar.sampletest.repository.network.processError
import com.jmarkstar.sampletest.repository.network.processNetworkResult

class UserRepositoryImpl(private val userService: UserService, private val userDao: UserDao): UserRepository {

    override suspend fun getUsers(refresh: Boolean): Result<List<User>> {

        try {
            if(!refresh && userDao.count() > 0){
                return Result.Success(userDao.getUsers())
            }

            val getUsersResult = userService.getUsers()

            return processNetworkResult(getUsersResult.code()) network@ {

                val users = checkNotNull(getUsersResult.body()){
                    return@network Result.Failure(FailureReason.INTERNAL_ERROR)
                }

                userDao.deleteAll()
                if(userDao.insertAll(users).size != users.size){
                    return@network Result.Failure(FailureReason.DATABASE_OPERATION_ERROR)
                }

                Result.Success(users)
            }
        } catch(ex: Exception) {
            return processError(ex)
        }
    }

}