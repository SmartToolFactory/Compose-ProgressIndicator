package com.smarttoolfactory.composeprogressindicator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.smarttoolfactory.composeprogressindicator.ui.theme.composeprogressindicatorTheme
import com.smarttoolfactory.progressindicator.SpinningCircleProgressIndicator
import com.smarttoolfactory.progressindicator.SpinningProgressIndicator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            composeprogressindicatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Column {
                        Box(
                            modifier = Modifier
                                .background(Color.Black)
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
//                            SpinningProgressIndicator(show = true)
                            SpinningCircleProgressIndicator(show = true)
                        }
                    }
                }
            }
        }
    }
}
