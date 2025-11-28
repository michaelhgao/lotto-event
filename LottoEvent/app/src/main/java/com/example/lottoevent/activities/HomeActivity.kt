package com.example.lottoevent.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lottoevent.ui.theme.LottoEventTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LottoEventTheme {
                    HomeActivityLayout();
            }
        }
    }
}

@Composable
fun HomeActivityLayout(modifier: Modifier = Modifier) {
    val context = LocalContext.current;

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = {
                val intent = Intent(context, AttendeeActivity::class.java);
                context.startActivity(intent);
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            Text("Attend Event")
        }
        Button(
            onClick = {
                val intent = Intent(context, OrganizerActivity::class.java);
                context.startActivity(intent);
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            Text("Organize Event")
        }
        Button(
            onClick = {
                val intent = Intent(context, AdminActivity::class.java);
                context.startActivity(intent);
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            Text("Admin Login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeActivityLayoutPreview() {
    LottoEventTheme {
        HomeActivityLayout()
    }
}