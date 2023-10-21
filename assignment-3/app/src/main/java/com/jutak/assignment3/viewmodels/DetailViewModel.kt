package com.jutak.assignment3.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jutak.assignment3.data.vo.WordListVO
import com.jutak.assignment3.network.MainRestApi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class DetailViewModel @AssistedInject constructor(
    private val restApi: MainRestApi,
    @Assisted val wordListId: Int,
): ViewModel() {

    private val _wordListDetail = MutableLiveData<WordListVO>()
    val wordListDetail: LiveData<WordListVO> get() = _wordListDetail

    init {
        getWordListDetail(wordListId)
    }

    fun getWordListDetail(id: Int) {
         viewModelScope.launch {
             _wordListDetail.value = restApi._getWordListDetail(id)
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