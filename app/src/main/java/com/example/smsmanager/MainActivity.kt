package com.example.smsmanager

import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.smsmanager.navigation.BottomNavigationBar
import com.example.smsmanager.navigation.NavGraph
import com.example.smsmanager.ui.theme.SmsManagerTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val permissions = arrayOf(
        android.Manifest.permission.RECEIVE_SMS,
        android.Manifest.permission.READ_SMS
    )

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmsManagerTheme {

                LaunchedEffect(key1 = arePermissionsGranted()) {
                    if (!arePermissionsGranted()) {
                        requestPermissions()
                    }
                }
                navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(

                        // in scaffold we are specifying the top bar.
                        topBar = {

                            // inside top bar we are specifying
                            // background color.
                            TopAppBar(
                                colors =
                                TopAppBarDefaults.topAppBarColors(
                                    containerColor = Color.Black
                                ),

                                // along with that we are specifying
                                // title for our top bar.
                                title = {

                                    // in the top bar we are specifying
                                    // tile as a text
                                    Text(
                                        // on below line we are specifying
                                        // text to display in top app bar.
                                        text = "Sms Messenger",

                                        // on below line we are specifying
                                        // modifier to fill max width.
                                        modifier = Modifier.fillMaxWidth(),

                                        // on below line we are specifying
                                        // text alignment.
                                        textAlign = TextAlign.Center,

                                        // on below line we are specifying
                                        // color for our text.
                                        color = Color.White
                                    )
                                })
                        },
                        bottomBar = {
                            BottomNavigationBar(navController = navController) {
                                navController.navigate(it.route)
                            }
                        }
                    )
                    {
                        // on below line we are calling connection
                        // information method to display UI
                        NavGraph(
                            context = this, navController = navController,
                            modifier = Modifier.padding(
                                bottom = it.calculateBottomPadding(),
                                top = it.calculateTopPadding()
                            )
                        )

                    }
                }
            }
        }
    }


    private fun arePermissionsGranted(): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_REQUEST_CODE)
    }


    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 123
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(SmsReceiver(), IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(SmsReceiver())
    }
}
