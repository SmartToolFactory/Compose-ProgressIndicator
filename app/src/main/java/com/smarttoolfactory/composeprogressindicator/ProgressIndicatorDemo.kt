package com.smarttoolfactory.composeprogressindicator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.progressindicator.*

@Composable
fun ProgressIndicatorDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(10.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpinningProgressIndicator()

            Spacer(modifier = Modifier.width(16.dp))
            SpinningProgressIndicator(
                dynamicItemColor = MaterialTheme.colorScheme.primary,
                staticItemColor = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(16.dp))
            SpinningProgressIndicator(
                dynamicItemColor = MaterialTheme.colorScheme.primary,
                staticItemColor = MaterialTheme.colorScheme.onPrimary,
                dynamicItemCount = 12
            )
            Spacer(modifier = Modifier.width(16.dp))
            SpinningProgressIndicator(
                staticItemCount = 8
            )
            Spacer(modifier = Modifier.width(16.dp))
            SpinningProgressIndicator(
                staticItemCount = 8,
                dynamicItemCount = 6
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpinningProgressIndicator(
                modifier = Modifier.size(100.dp),
                spinnerShape = SpinnerShape.Rect
            )
            Spacer(modifier = Modifier.width(10.dp))
            SpinningProgressIndicator(
                modifier = Modifier.size(60.dp),
                spinnerShape = SpinnerShape.Rect,
                staticItemColor = IndicatorDefaults.DynamicItemColor,
                dynamicItemColor = Color(0xffFF9800)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpinningCircleProgressIndicator()
            Spacer(modifier = Modifier.width(10.dp))
            SpinningCircleProgressIndicator(
                modifier = Modifier.size(60.dp),
                dynamicItemColor = Color(0xffE1F5FE),
                staticItemColor = Color(0xff039BE5),
                durationMillis = 500
            )
            Spacer(modifier = Modifier.width(10.dp))
            SpinningCircleProgressIndicator(
                modifier = Modifier.size(100.dp),
                dynamicItemColor = Color(0xffC8E6C9),
                staticItemColor = Color(0xff2E7D32)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row {
            GooeyProgressIndicator(
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            GooeyProgressIndicator(
                modifier = Modifier.size(100.dp),
                style = IndicatorStyle.Stroke
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScaledCircleProgressIndicator(color = Color(0xff29B6F6))
            Spacer(modifier = Modifier.width(10.dp))
            ScaledCircleProgressIndicator(
                modifier = Modifier.size(100.dp),
                color = Color(0xffEC407A)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DotProgressIndicator(modifier = Modifier.size(60.dp, 30.dp))
            Spacer(modifier = Modifier.width(10.dp))
            DotProgressIndicator()
            Spacer(modifier = Modifier.width(10.dp))
            DotProgressIndicator(
                initialColor = Color(0xffF44336),
                animatedColor = Color(0xff29B6F6)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BouncingDotProgressIndicator(modifier = Modifier.size(60.dp, 30.dp))
            Spacer(modifier = Modifier.width(10.dp))
            BouncingDotProgressIndicator()
            Spacer(modifier = Modifier.width(10.dp))
            BouncingDotProgressIndicator(
                initialColor = Color(0xffF44336),
                animatedColor = Color(0xff29B6F6)
            )
        }
    }
}