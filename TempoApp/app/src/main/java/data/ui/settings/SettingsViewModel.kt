class SettingsViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    private val _hourFormat = MutableLiveData<HourFormat>()
    val hourFormat: LiveData<HourFormat> = _hourFormat

    private val _shouldShowNotifications = MutableLiveData<Boolean>()
    val shouldShowNotifications: LiveData<Boolean> = _shouldShowNotifications

    init {
        _hourFormat.value = getSavedHourFormat()
        _shouldShowNotifications.value = getSavedShouldShowNotifications()
    }

    fun setHourFormat(hourFormat: HourFormat) {
        sharedPreferences.edit().putString(PREF_HOUR_FORMAT, hourFormat.name).apply()
        _hourFormat.value = hourFormat
    }

    fun setShouldShowNotifications(shouldShowNotifications: Boolean) {
        sharedPreferences.edit().putBoolean(PREF_SHOULD_SHOW_NOTIFICATIONS, shouldShowNotifications).apply()
        _shouldShowNotifications.value = shouldShowNotifications
    }

    private fun getSavedHourFormat(): HourFormat {
        val hourFormatName = sharedPreferences.getString(PREF_HOUR_FORMAT, HourFormat.TWELVE.name)
        return HourFormat.valueOf(hourFormatName ?: HourFormat.TWELVE.name)
    }

    private fun getSavedShouldShowNotifications(): Boolean {
        return sharedPreferences.getBoolean(PREF_SHOULD_SHOW_NOTIFICATIONS, true)
    }

    companion object {
        private const val PREF_HOUR_FORMAT = "pref_hour_format"
        private const val PREF_SHOULD_SHOW_NOTIFICATIONS = "pref_should_show_notifications"
    }
}
