package com.jutak.assignment3.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jutak.assignment3.data.vo.WordListVO
import com.jutak.assignment3.data.vo.WordVO
import com.jutak.assignment3.network.MainRestApi
import com.jutak.assignment3.network.dto.DeleteWordListParams
import com.jutak.assignment3.network.dto.PostHasPermissionParams
import com.jutak.assignment3.network.dto.PutWordListParams
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailViewModel @AssistedInject constructor(
    private val restApi: MainRestApi,
    @Assisted val wordListId: Int,
): ViewModel() {

    private val _wordListDetail = MutableLiveData<WordListVO>()
    val wordListDetail: LiveData<WordListVO> get() = _wordListDetail

    private val _hasPermission = MutableLiveData<Boolean>()
    val hasPermission: LiveData<Boolean> get() = _hasPermission

    private var permissionPassword = ""

    suspend fun getWordListDetail() {
        val response = restApi._getWordListDetail(wordListId)
         withContext(Dispatchers.Main) {
             _wordListDetail.value = response
         }
    }

    suspend fun authenticate(password: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val response = restApi._postHasPermission(wordListId, PostHasPermissionParams(password))
        withContext(Dispatchers.Main) {
            _hasPermission.value = response.valid
            if (response.valid) {
                permissionPassword = password
                onSuccess()
            } else {
                onFailure()
            }
        }
    }

    suspend fun deleteWordList() {
        restApi._deleteWordList(wordListId, DeleteWordListParams(permissionPassword))
    }

    suspend fun addWordToWordList(
        spell: String,
        meaning: String,
        synonym: String,
        antonym: String,
        sentence: String,
        onSuccess: () -> Unit,
    ) {
        if (spell.isNotEmpty() && meaning.isNotEmpty()) {
            val response = restApi._putWordList(
                id = wordListId,
                PutWordListParams(
                    password = permissionPassword,
                    word = WordVO(
                        spell = spell,
                        meaning = meaning,
                        synonym = synonym,
                        antonym = antonym,
                        sentence = sentence
                    )
                )
            )
            withContext(Dispatchers.Main) {
                _wordListDetail.value = response
                onSuccess()
            }
        }
    }

    @AssistedFactory
    interface AssistedFactoryWithId {
        fun create(id: Int): DetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactoryWithId,
            id: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(id) as T
            }
        }
    }
}