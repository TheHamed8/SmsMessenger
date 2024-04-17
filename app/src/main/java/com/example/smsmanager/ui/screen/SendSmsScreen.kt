package com.example.smsmanager.ui.screen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SendSmsUI(
    context: Context) {
    // on below line creating variable for
    // service status and button value.
    var phoneNumber by remember {
        mutableStateOf("")
    }
    val message = remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true) {
        requestSmsPermission(activity = context as Activity, 101)
    }

    // on below line we are creating a column,
    Column(
        // on below line we are adding a modifier to it,
        modifier = Modifier
            .fillMaxSize()
            // on below line we are adding a padding.
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        // on below line we are creating a text field for our phone number.
        TextField(
            // on below line we are specifying value for our email text field.
            value = phoneNumber,
            // on below line we are adding on value change for text field.
            onValueChange = { phoneNumber = it },
            // on below line we are adding place holder as text
            // as "Enter your email"
            placeholder = { Text(text = "Enter your phone number") },
            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            // on below line we ar adding single line to it.
            singleLine = true,
        )
        Text(
            text = "example: +989123456789",
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Start,
        )
        // on below line we are adding a spacer.
        Spacer(modifier = Modifier.height(20.dp))

        // on below line we are creating a text field for our message number.
        TextField(
            // on below line we are specifying value for our message text field.
            value = message.value,
            // on below line we are adding on value change for text field.
            onValueChange = { message.value = it },
            // on below line we are adding place holder as text as "Enter your email"
            placeholder = { Text(text = "Enter your message") },
            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            // on below line we are adding single line to it.
            singleLine = true,
        )
        // on below line adding a spacer.
        Spacer(modifier = Modifier.height(20.dp))
        // on below line adding a button to send SMS
        Button(onClick = {
            // on below line running a try catch block for sending sms.
            try {
                // on below line initializing sms manager.
                val smsManager: SmsManager = SmsManager.getDefault()
                // on below line sending sms
                smsManager.sendTextMessage(phoneNumber, null, message.value, null, null)
                // on below line displaying
                // toast message as sms send.
                Toast.makeText(
                    context,
                    "Message Sent",
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: Exception) {
                // on below line handling error and
                // displaying toast message.
                Toast.makeText(
                    context,
                    "Error : " + e.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }) {
            // on below line creating a text for our button.
            Text(
                // on below line adding a text ,
                // padding, color and font size.
                text = "Send SMS",
                modifier = Modifier.padding(10.dp),
                color = Color.White,
                fontSize = 15.sp
            )
        }
    }
}

private fun requestSmsPermission(activity: Activity, requestCode: Int): Boolean {
    return if (activity.checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
        true
    } else {
        activity.requestPermissions(arrayOf(Manifest.permission.SEND_SMS), requestCode)
        false
    }
}