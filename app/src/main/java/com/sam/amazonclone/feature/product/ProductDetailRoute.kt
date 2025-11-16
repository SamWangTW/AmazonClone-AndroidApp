// File: feature/product/ProductDetailRoute.kt
package com.sam.amazonclone.feature.product

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.sam.amazonclone.feature.cart.CartViewModel

/*
- fetching product data
- observing ViewModel state
- showing loading/error/content
- wiring cart actions
- providing navigation header
*/
/*
 MainActivity -> ProductDetailRoute -> ProductDetailScreen
                                    -> ProductDetailViewModel
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailRoute(
    productId: String,
    onBack: () -> Unit,
    cartViewModel: CartViewModel = hiltViewModel(), // “If no cartViewModel is provided, automatically get the CartViewModel instance for this screen using Hilt.”
    vm: ProductDetailViewModel = hiltViewModel()    // default value, optional
) {
    val ui by vm.uiState.collectAsState() //converts the StateFlow into Compose state so UI reacts to changes.

    LaunchedEffect(productId) { vm.load(productId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(ui.product?.title ?: "Product") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        when {
            // The page is still loading — you see a spinning wheel (“We’re fetching your order info”).
            ui.loading -> Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            // Something went wrong — you see an error message (“Failed to load order”).
            ui.error != null -> Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Failed to load: ${ui.error}")
            }
            // It worked! — you see your actual product details (title, price, etc.).
            ui.product != null -> ProductDetailScreen(
                product = ui.product!!, // !! is the not-null assertion operator. “I guarantee this value is not null. If it is null, crash the app.”
                onBack = onBack,
                onAddToCart = { cartViewModel.add(it) },
                modifier = Modifier.padding(padding)
            )
        }
    }
}
/*
Modifier is like a list of instructions for how a Composable should be drawn —
- things like its size, color, padding, alignment, scrolling, etc.
-  modifier = Modifier.padding(padding): tells your artwork (the main screen content) to stay inside the visible area — not overlap the frame.
*/

/*
hiltViewModel() is a Jetpack Compose + Hilt helper that:
✔ Automatically retrieves the correct ViewModel instance
✔ Injects dependencies through Hilt
✔ Ties the ViewModel to the current navigation back stack (correct lifecycle)
*/