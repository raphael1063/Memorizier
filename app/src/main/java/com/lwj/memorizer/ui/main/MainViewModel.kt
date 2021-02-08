package com.lwj.memorizer.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lwj.memorizer.Event
import com.lwj.memorizer.R
import com.lwj.memorizer.base.BaseViewModel
import com.lwj.memorizer.data.Repository
import com.lwj.memorizer.data.entities.Cardbook
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private val _currentNavigationItem = MutableLiveData<Int>()
    val currentNavigationItem: LiveData<Int>
        get() = _currentNavigationItem

    private val _currentViewPagerItem = MutableLiveData<Int>()
    val currentViewPagerItem: LiveData<Int>
        get() = _currentViewPagerItem

    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle: LiveData<String>
        get() = _toolbarTitle

    private val _isCardbookView = MutableLiveData<Boolean>()
    val isCardbookView: LiveData<Boolean>
        get() = _isCardbookView

    private val _isSearchMenuActivate = MutableLiveData<Boolean>()
    val isSearchMenuActivate: LiveData<Boolean>
        get() = _isSearchMenuActivate

    private val _isGridView = MutableLiveData<Boolean>()
    val isGridView: LiveData<Boolean> = _isGridView

    /* Action */
    private val _isSearchBarOpened = MutableLiveData(false)
    val isSearchBarOpened: LiveData<Boolean>
        get() = _isSearchBarOpened

    private val _actionSettingButtonClicked = MutableLiveData<Event<Unit>>()
    val actionSettingButtonClicked: LiveData<Event<Unit>>
        get() = _actionSettingButtonClicked

    private val _actionSupportButtonClicked = MutableLiveData<Event<Unit>>()
    val actionSupportButtonClicked: LiveData<Event<Unit>>
        get() = _actionSupportButtonClicked

    private val _actionAboutButtonClicked = MutableLiveData<Event<Unit>>()
    val actionAboutButtonClicked: LiveData<Event<Unit>>
        get() = _actionAboutButtonClicked

    private val _layoutSubject: PublishSubject<RecyclerLayoutManager> = PublishSubject.create()

    private val _layoutState = MutableLiveData<RecyclerLayoutManager>()
    val layoutState: LiveData<RecyclerLayoutManager>
        get() = _layoutState

    init {
        _isGridView.value = false

        _layoutSubject.throttleFirst(1, TimeUnit.SECONDS)
            .subscribe { _layoutState.value = it }
            .let(compositeDisposable::add)
    }

    fun setCurrentNavId(id: Int) {
        when (id) {
            R.id.nav_home -> {
                _currentViewPagerItem.value = 0
                _toolbarTitle.value = "Home"
                _isCardbookView.value = false
            }
            R.id.nav_cardbook -> {
                _currentViewPagerItem.value = 1
                _toolbarTitle.value = "Cardbook"
                _isCardbookView.value = true
            }
            R.id.nav_training -> {
                _currentViewPagerItem.value = 2
                _toolbarTitle.value = "Training"
                _isCardbookView.value = false
            }
            R.id.nav_my_account -> {
                _currentViewPagerItem.value = 3
                _toolbarTitle.value = "My Account"
                _isCardbookView.value = false
            }
        }
    }

    fun setCurrentViewPagerPosition(position: Int) {
        when (position) {
            0 -> _currentNavigationItem.value = R.id.nav_home
            1 -> _currentNavigationItem.value = R.id.nav_cardbook
            2 -> _currentNavigationItem.value = R.id.nav_training
            3 -> _currentNavigationItem.value = R.id.nav_my_account
        }
    }

    fun onReorderMenuClicked() {
        _isGridView.value = !_isGridView.value!!
    }

    fun changeLayoutManagerLinear() = _layoutSubject.onNext(RecyclerLayoutManager.LINEAR)
    fun changeLayoutManagerGird() = _layoutSubject.onNext(RecyclerLayoutManager.GRID)

    fun onSearchMenuClicked() {
        isSearchBarOpened.value?.let {
            _isSearchBarOpened.value = !it
            _isSearchMenuActivate.value = !it
        }
    }

    fun onSettingMenuClicked() {
        _actionSettingButtonClicked.value = Event(Unit)
    }

    fun onSupportMenuClicked() {
        _actionSupportButtonClicked.value = Event(Unit)
    }

    fun onAboutMenuClicked() {
        _actionAboutButtonClicked.value = Event(Unit)
    }

    var index = 0
    fun onAddCardbookClicked() {
        viewModelScope.launch {
            repository.insertCardbook(Cardbook( "title $index", false, null))
        }
        index++
    }

    enum class RecyclerLayoutManager {
        LINEAR,
        GRID
    }
}