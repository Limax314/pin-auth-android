
package com.example.pinauth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pinauth.ui.theme.PinauthandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PinauthandroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PinAuthScreen()
                }
            }
        }
    }
}

@Composable
fun PinAuthScreen() {
    var pin by remember { mutableStateOf("") }
    val correctPin = "1234"
    var authStatus by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Enter PIN", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = pin,
            onValueChange = { },
            label = { Text("PIN") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(authStatus, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Number Keypad
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                NumberButton("1") { pin += "1" }
                Spacer(modifier = Modifier.width(8.dp))
                NumberButton("2") { pin += "2" }
                Spacer(modifier = Modifier.width(8.dp))
                NumberButton("3") { pin += "3" }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                NumberButton("4") { pin += "4" }
                Spacer(modifier = Modifier.width(8.dp))
                NumberButton("5") { pin += "5" }
                Spacer(modifier = Modifier.width(8.dp))
                NumberButton("6") { pin += "6" }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                NumberButton("7") { pin += "7" }
                Spacer(modifier = Modifier.width(8.dp))
                NumberButton("8") { pin += "8" }
                Spacer(modifier = Modifier.width(8.dp))
                NumberButton("9") { pin += "9" }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Spacer(modifier = Modifier.width(88.dp)) // Placeholder for alignment
                NumberButton("0") { pin += "0" }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { pin = pin.dropLast(1) }) {
                    Text("Del")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (pin == correctPin) {
                authStatus = "Authentication Successful!"
            } else {
                authStatus = "Invalid PIN"
            }
        }) {
            Text("Submit")
        }
    }
}

@Composable
fun NumberButton(number: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.size(80.dp)) {
        Text(number, style = MaterialTheme.typography.headlineMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun PinAuthScreenPreview() {
    PinauthandroidTheme {
        PinAuthScreen()
    }
}
