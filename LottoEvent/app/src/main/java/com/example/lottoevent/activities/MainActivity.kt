package com.example.lottoevent.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.lottoevent.singletons.FirebaseManager
import com.example.lottoevent.singletons.UserEventStore
import com.example.lottoevent.ui.theme.LottoEventTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LottoEventTheme {
                LoadingScreen();
            }
        }

        if (FirebaseManager.currentUser != null) {
            // already signed in
            launchHomeActivity();
        }

        lifecycleScope.launch {
            val res = FirebaseManager.loginAnonymously();
            res.onSuccess {
                UserEventStore.setUser(res.getOrElse { throw IllegalStateException("User is null after authentication") });
                launchHomeActivity();
            }.onFailure {
                launchSignUpActivity();
            }
        }
    }

    private fun launchHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun launchSignUpActivity() {
        startActivity(Intent(this, SignUpActivity::class.java))
        finish()
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Checking authentication...",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    LottoEventTheme {
        LoadingScreen()
    }
}