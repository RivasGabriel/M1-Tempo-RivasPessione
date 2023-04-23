class HomeViewModel(private val tempoRepository: TempoRepository) : ViewModel() {

    private val _tempoDays: MutableLiveData<List<TempoDay>> = MutableLiveData()
    val tempoDays: LiveData<List<TempoDay>> = _tempoDays

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage: MutableLiveData<String?> = MutableLiveData(null)
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadTempoDays() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = tempoRepository.getTempoDays()

            when (result) {
                is Result.Success -> {
                    val days = result.data
                    _tempoDays.value = days
                }
                is Result.Error -> {
                    _errorMessage.value = result.message
                }
            }

            _isLoading.value = false
        }
    }
}
