package gq.kirmanak.mealient.ui.disclaimer

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gq.kirmanak.mealient.data.disclaimer.DisclaimerStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class DisclaimerViewModel @Inject constructor(
    private val disclaimerStorage: DisclaimerStorage
) : ViewModel() {
    private val _isAccepted = MutableLiveData(false)
    val isAccepted: LiveData<Boolean> = _isAccepted

    private val _okayCountDown = MutableLiveData(FULL_COUNT_DOWN_SEC)
    val okayCountDown: LiveData<Int> = _okayCountDown

    fun checkIsAccepted() {
        Timber.v("checkIsAccepted() called")
        viewModelScope.launch {
            _isAccepted.value = disclaimerStorage.isDisclaimerAccepted()
        }
    }

    fun acceptDisclaimer() {
        Timber.v("acceptDisclaimer() called")
        disclaimerStorage.acceptDisclaimer()
        _isAccepted.value = true
    }

    fun startCountDown() {
        Timber.v("startCountDown() called")
        tickerFlow(COUNT_DOWN_TICK_PERIOD_SEC.toLong(), TimeUnit.SECONDS)
            .take(FULL_COUNT_DOWN_SEC - COUNT_DOWN_TICK_PERIOD_SEC + 1)
            .onEach { _okayCountDown.value = FULL_COUNT_DOWN_SEC - it }
            .launchIn(viewModelScope)
    }

    /**
     * Sends an event every [period] of [timeUnit]. For example, if period = 3 and timeUnit = SECOND
     * then this will send an event every 3 seconds. Additionally to the event, it sends counter
     * of how many ticks there were. It doesn't depend on period or timeUnit parameters, it just
     * counts how many events it sent starting from 1.
     */
    @VisibleForTesting
    fun tickerFlow(period: Long, timeUnit: TimeUnit) = flow {
        Timber.v("tickerFlow() called with: period = $period, timeUnit = $timeUnit")
        val periodMillis = timeUnit.toMillis(period)
        var counter = 0
        while (true) {
            delay(periodMillis)
            counter++
            emit(counter)
        }
    }

    companion object {
        private const val FULL_COUNT_DOWN_SEC = 5
        private const val COUNT_DOWN_TICK_PERIOD_SEC = 1
    }
}