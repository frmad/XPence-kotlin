package com.example.valuevroom.ui.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GroupsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "THIS FRAGMENT FOR THE GROUP VIEW"
    }
    val text: LiveData<String> = _text
}