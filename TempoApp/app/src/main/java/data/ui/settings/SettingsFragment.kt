class TempoSettingsFragment : PreferenceFragmentCompat() {

    private lateinit var tempoSharedPreferences: TempoSharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        tempoSharedPreferences = TempoSharedPreferences(requireContext())

        val notificationsEnabledPreference =
            findPreference<SwitchPreferenceCompat>(PREF_NOTIFICATIONS_ENABLED_KEY)
        notificationsEnabledPreference?.isChecked = tempoSharedPreferences.isNotificationEnabled()
        notificationsEnabledPreference?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue is Boolean) {
                tempoSharedPreferences.setNotificationEnabled(newValue)
                true
            } else {
                false
            }
        }
    }

}
