package com.example.lottoevent.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.lottoevent.models.FirebaseManager
import com.example.lottoevent.ui.theme.LottoEventTheme

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LottoEventTheme {
                SignUpActivityLayout(
                    onFinishSetup =  { name, email -> {
                        val newUser = FirebaseManager.
                    }}
                )
            }
        }
    }
}

@Composable
fun SignUpActivityLayout(onFinishSetup: (String, String) -> Unit) {

}