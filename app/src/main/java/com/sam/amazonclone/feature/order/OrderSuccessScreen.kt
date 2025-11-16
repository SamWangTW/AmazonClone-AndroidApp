package com.sam.amazonclone.feature.order

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/*
* This screen is what users see right after completing a purchase —
* it shows a success message and a single “Continue Shopping” button that takes them back to browse more products.
* */

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun OrderSuccessScreen(
    onContinueShopping: () -> Unit // a callback (lambda) that runs when the user presses the “Continue Shopping” button
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Order Placed") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp), // adds your custom margin inside the safe area
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🎉 Thank you!",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Your order has been placed successfully.\nA confirmation email will arrive shortly.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(24.dp))
            Button(onClick = onContinueShopping) {
                Text("Continue Shopping")
            }
        }
    }
}
/*
* This padding is automatically provided by Scaffold.
It ensures that your content doesn’t overlap with system UI elements like:
- the status bar (top area with clock, Wi-Fi, etc.),
- the navigation bar (bottom gestures/buttons),
- or the TopAppBar you defined inside the Scaffold.
* */