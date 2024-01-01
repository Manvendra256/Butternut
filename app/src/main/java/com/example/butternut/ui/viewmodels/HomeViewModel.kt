package com.example.butternut.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.butternut.data.Class
import com.example.butternut.data.ClassesRepository
import com.example.butternut.data.DecksRepository
import com.example.butternut.di.OfflineRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @OfflineRepo private val classesRepository: ClassesRepository,
    @OfflineRepo private val decksRepository: DecksRepository
) : ViewModel() {
    private val _classes: MutableStateFlow<List<Class>> = MutableStateFlow(emptyList());
    val classes: StateFlow<List<Class>> = _classes

    init {
        viewModelScope.launch {
            classesRepository.getClasses().collect {
                _classes.value = it;
            };
        }
    }

    suspend fun insertClass(class_: Class) {
        viewModelScope.launch(Dispatchers.Default) { classesRepository.insertClass(class_) }
    }

    suspend fun deleteClass(class_: Class) {
        if (decksRepository.getDecksCountByClassId(class_.classId) == 0) {
            viewModelScope.launch(Dispatchers.Default) { classesRepository.deleteClass(class_) }
        } else
            throw IllegalArgumentException()
    }

    suspend fun updateClass(class_: Class) {
        viewModelScope.launch(Dispatchers.Default) { classesRepository.updateClass(class_) }
    }
}