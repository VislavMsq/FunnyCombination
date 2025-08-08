// HighScoreViewModel.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosiuk.funnycombination.data.HighScore
import com.mosiuk.funnycombination.data.HighScoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch

class HighScoreViewModel(
    private val repository: HighScoreRepository
) : ViewModel() {

    private val _scores = MutableStateFlow<List<HighScore>>(emptyList())
    val scores: StateFlow<List<HighScore>> = _scores

    fun loadScores() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { repository.getAll() }
                .onSuccess { list -> withContext(Dispatchers.Main) { _scores.value = list } }
                .onFailure { /* можно залогировать */ withContext(Dispatchers.Main) { _scores.value = emptyList() } }
        }
    }

    suspend fun isNewRecord(score: Int): Boolean = withContext(Dispatchers.IO) {
        runCatching { repository.getBestScore() ?: 0 }
            .getOrElse { 0 }
            .let { best -> score > best && score > 0 }
    }

    fun saveHighScore(score: Int) {
        if (score <= 0) return
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                    .format(java.util.Date())
                repository.insertIfBest(score, date)
                repository.getAll()
            }.onSuccess { list ->
                withContext(Dispatchers.Main) { _scores.value = list }
            }
        }
    }

}
