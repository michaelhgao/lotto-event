package com.example.lottoevent.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.lottoevent.singletons.FirebaseManager
import com.example.lottoevent.models.User
import com.example.lottoevent.singletons.UserEventStore
import com.example.lottoevent.ui.theme.LottoEventTheme
import kotlinx.coroutines.launch

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LottoEventTheme {
                SignUpActivityLayout(
                        onFinishSetup = ::signUp
                )
            }
        }
    }

    private fun signUp(name: String, email: String, setLoading: (Boolean) -> Unit) {
        lifecycleScope.launch {
            setLoading(true)

            val firebaseUser = FirebaseManager.currentUser

            if (firebaseUser == null) {
                // this shouldnt happen if MainActivity worked
                setLoading(false)
                Toast.makeText(this@SignUpActivity, "Authentication required. Please restart.", Toast.LENGTH_LONG).show()
                return@launch
            }

            val newUser = User(
                id = firebaseUser.uid,
                name = name,
                email = email,
                phone = ""
            )

            val res = FirebaseManager.addUser(newUser)
            res.onSuccess {
                UserEventStore.setUser(newUser);
                launchHomeActivity()
            }.onFailure { e ->
                setLoading(false)
                Log.e("SignUp", "Failed to add user to db: $e")
                Toast.makeText(this@SignUpActivity, "Setup failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun launchHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}

@Composable
fun SignUpActivityLayout(onFinishSetup: (String, String, (Boolean) -> Unit) -> Unit) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Enter Your Details", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(32.dp))

            // Name Field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(32.dp))

            // Finish Setup Button
            Button(
                onClick = {
                    if (name.isNotBlank() && email.isNotBlank()) {
                        onFinishSetup(name, email) { loadingState ->
                            isLoading = loadingState
                        }
                    } else {
                        Toast.makeText(context, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 3.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Finish Setup")
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Sign Up Form Default")
@Composable
fun SignUpActivityLayoutPreview() {
    LottoEventTheme {
        // Pass a placeholder lambda for the onFinishSetup callback
        SignUpActivityLayout(onFinishSetup = { _, _, _ ->
            // In a preview, this logic won't execute, so we leave it empty
        })
    }
}

@Preview(showBackground = true, name = "Sign Up Form Loading")
@Composable
fun SignUpActivityLayoutLoadingPreview() {
    LottoEventTheme {
        // Use a state to simulate the loading state for the preview
        var isLoading by remember { mutableStateOf(true) }
        var name by remember { mutableStateOf("John Doe") }
        var email by remember { mutableStateOf("john.doe@example.com") }

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Enter Your Details", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(32.dp))

                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, enabled = !isLoading, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(16.dp))

                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, enabled = !isLoading, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(32.dp))

                Button(
                    onClick = { /* No-op in preview */ },
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 3.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Finish Setup")
                    }
                }
            }
        }
    }
}