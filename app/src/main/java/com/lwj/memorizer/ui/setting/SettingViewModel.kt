package com.lwj.memorizer.ui.setting

import com.lwj.memorizer.base.BaseViewModel
import com.lwj.memorizer.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {
}