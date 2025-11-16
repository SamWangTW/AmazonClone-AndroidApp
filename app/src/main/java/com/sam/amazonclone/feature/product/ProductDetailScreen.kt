// File: feature/product/ProductDetailScreen.kt
package com.sam.amazonclone.feature.product


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sam.amazonclone.R
import com.sam.amazonclone.core.model.Product


@Composable
fun ProductDetailScreen(
    product: Product,
    onBack: () -> Unit,
    onAddToCart: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val imageRes = when (product.category) {
        "Electronics" -> R.drawable.electronics
        "Books"       -> R.drawable.books
        "Home"        -> R.drawable.home
        "Toys"        -> R.drawable.toys
        else          -> R.drawable.electronics
    }

    Column( // full-screen, scrollable content.
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = product.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
        /*
          This block is a Column — think of it as a vertical stack of UI elements (like stacking cards on top of each other).
          - Each child inside the Column is one part of the product info:
          - 📦 title → ⭐ rating → 💰 price → 🏷️ category → 📝 description → 🛒 buttons
        */
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(8.dp)) // Small gap between title and rating

            Row(verticalAlignment = Alignment.CenterVertically) {
                RatingStars(rating = product.rating)
                Spacer(Modifier.width(8.dp))
                Text(String.format("%.1f", product.rating), style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = "$" + String.format("%.2f", product.price),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(Modifier.height(6.dp))

            AssistChip(
                onClick = { /* no-op */ }, //onClick is empty here because it’s just for display.
                label = { Text(product.category) }
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "About this item",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Great value Wireless Headphones with balanced sound, comfy earcups, and long battery life. " +
                        "Image is placeholder (Picsum). Category: ${product.category}.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { onAddToCart(product) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add to Cart")
            }

            Spacer(Modifier.height(8.dp))

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}

@Composable
private fun RatingStars(rating: Double, max: Int = 5) {
    val full = rating.toInt().coerceIn(0, max)
    val remainder = max - full
    Row {
        repeat(full) { Icon(Icons.Filled.Star, contentDescription = null) }
        repeat(remainder) { Icon(Icons.Outlined.Star, contentDescription = null) }
    }
}


