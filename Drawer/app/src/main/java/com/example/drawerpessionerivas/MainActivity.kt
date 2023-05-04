package com.example.drawerpessionerivas

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.work.NetworkType
import com.example.drawerpessionerivas.databinding.ActivityMainBinding
import com.example.drawerpessionerivas.ui.home.HomeFragment
import java.text.SimpleDateFormat
import java.util.*

const val NOTIFICATION_CHANNEL_BLUE= "color_blue"
const val NOTIFICATION_CHANNEL_RED= "color_red"
const val NOTIFICATION_CHANNEL_WHITE= "color_white"
const val TEMPO_WORK_REQUEST_TAG="tempo-date"
class MainActivity : AppCompatActivity() {
    private val LOGTAG = MainActivity::class.java.simpleName

    private var isNotifPermGranted = false
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var daysColor = arrayListOf<DaysColor>()
    private var totalResult = 0


    private val requestePermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission())
        { isGranted: Boolean ->
            isNotifPermGranted = isGranted
            if(isGranted){
                Log.d(LOGTAG, "Notification permission granted")
            }else{
                Log.d(LOGTAG, "Notification permission denied")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()
        val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date())
        initWorkManager()
        setSupportActionBar(binding.appBarMain.toolbar)
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if(savedInstanceState == null){
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.nav_host_fragment_content_main, HomeFragment())
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun check4NotificationPermission():Any = when {
        ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED -> {
            Log.i(LOGTAG, "notification permission already granted")
            isNotifPermGranted = true
        }
        shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS) -> {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected, and what
            // features are disabled if it's declined. In this UI, include a
            // "cancel" or "no thanks" button that lets the user continue
            // using your app without granting the permission.
            Log.i(LOGTAG, "show a permission rationale explanation dialog")
        }
        else -> {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel.
            val name_blue = getString(R.string.notification_channel_name_blue)
            val name_red = getString(R.string.notification_channel_name_red)
            val name_white = getString(R.string.notification_channel_name_white)
            val descriptionText = getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannelBlue = NotificationChannel(NOTIFICATION_CHANNEL_BLUE, name_blue, importance)
            val mChannelRed = NotificationChannel(NOTIFICATION_CHANNEL_RED, name_red, importance)
            val mChannelWhite = NotificationChannel(NOTIFICATION_CHANNEL_WHITE, name_white, importance)
            mChannelBlue.description = descriptionText
            mChannelRed.description = descriptionText
            mChannelWhite.description = descriptionText
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannelBlue)
            notificationManager.createNotificationChannel(mChannelRed)
            notificationManager.createNotificationChannel(mChannelWhite)
        }
    }
    private fun initWorkManager() {
        val currentTime = Calendar.getInstance()
        val scheduleTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 13)
            set(Calendar.MINUTE, 0)
            if(before(currentTime)){
                add(Calendar.DATE, 1)
            }
        }
        val initialDelay = scheduleTime.timeInMillis - currentTime.timeInMillis
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        fun showDayColor(view: View){
            val intent = Intent()
            intent.setClass(this, DayColorActivity::class.java)
            intent.putExtra("color", view.tag.toString())
            startActivity(intent)
        }
    }
}