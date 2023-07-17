package com.abh80.smartedge.flashalert.db

import com.abh80.smartedge.flashalert.poko.AppItem
import kotlinx.coroutines.*

class AppEntryRepository(private val mDao: AppSelectorDao) {

    private val bddScope = MainScope()

    suspend fun getAllApps(): List<AppItem>{
        return withContext(Dispatchers.Default) {
            mDao.getAppItem()
        }
    }

    suspend fun getItemByPackage(packageId: String): AppItem? {
        return withContext(Dispatchers.Default){
            mDao.getAppItemByPackageId(packageId)
        }
    }
    fun inserAppItem(appItem: AppItem): Job{
        return bddScope.launch(Dispatchers.IO) {
            mDao.insertApp(appItem)
        }
    }

    fun deleteAppItem(appItem: AppItem): Job{
        return bddScope.launch(Dispatchers.IO)  {
            mDao.deleteApp(appItem)
        }
    }

    fun updateItemFromPackage(packageId: String,selected: Boolean): Job{
        return bddScope.launch(Dispatchers.IO)  {
            mDao.updateFromPackage(packageId, selected)
        }
    }

    suspend fun clearAll() {
        withContext(Dispatchers.Default) {
            AppDatabase.INSTANCE?.clearAllTables()
            true
        }

    }
}



