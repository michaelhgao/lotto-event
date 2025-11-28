package com.example.lottoevent.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lottoevent.singletons.UserEventStore
import com.example.lottoevent.ui.theme.LottoEventTheme

class OrganizerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = UserEventStore.getUser();
        val userName = user?.name ?: "Organizer"
        setContent {
            LottoEventTheme {
                OrganizerActivityLayout(userName);
            }
        }
    }
}

@Composable
fun OrganizerActivityLayout(name: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Welcome Text
        Text(
            text = "Welcome, $name",
            fontSize = 28.sp,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Create Event Button
        Button(
            onClick = {
                println("Create Event button clicked")
            }
        ) {
            Text("Create Event")
        }

        // View Events Button
        Button(
            onClick = {
                println("View Events button clicked")
            }
        ) {
            Text("View Events")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrganizerActivityLayoutPreview() {
    LottoEventTheme {
        OrganizerActivityLayout("Organizer")
    }
}