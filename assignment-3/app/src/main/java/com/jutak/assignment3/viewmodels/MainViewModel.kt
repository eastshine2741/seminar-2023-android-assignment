package com.jutak.assignment3.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jutak.assignment3.data.vo.BriefWordListVO
import com.jutak.assignment3.network.MainRestApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val restApi: MainRestApi,
): ViewModel() {

    private val _wordLists = MutableLiveData<List<BriefWordListVO>>(emptyList())
    val wordLists: LiveData<List<BriefWordListVO>> get() = _wordLists

    init {
        getWordLists()
    }

    fun getWordLists() {
        viewModelScope.launch {
            _wordLists.value = restApi._getWordLists()
        }
    }
}