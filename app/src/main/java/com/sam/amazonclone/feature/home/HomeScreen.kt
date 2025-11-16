package com.sam.amazonclone.feature.home
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sam.amazonclone.R
import com.sam.amazonclone.core.model.Product



// @OptIn: This line allows use of experimental Material3 components, like TopAppBar, that might change in the future.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
// builds the main home UI showing all products.
fun HomeScreen(
    products: List<Product>,
    onAddToCart: (Product) -> Unit, // callback for when “Add to cart” is clicked. A function that takes a Product and returns nothing.
    onOpenCart: () -> Unit, // callback for when the “Cart” button in the top bar is clicked. A function that takes nothing and returns nothing.
    onOpenProduct: (String) -> Unit,
    onRemoveProduct: (Product) -> Unit,
    getCartCount: (String) -> Int
) {
    // Scaffold gives you a standard page structure — with top bar, bottom bar, floating button, and content area.
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AmazonClone") },
                actions = { TextButton(onClick = onOpenCart) { Text("Cart") } }
            )
        }
    ) { padding ->
        LazyColumn( // a scrollable list that only renders visible items (efficient for long lists).
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) { // iterates over each product.
            items(products) { p -> // items(products) loops through your products: List<Product>. p is the current product in the list.
                ProductCard(
                    p,
                    onAddToCart = onAddToCart,
                    onOpenProduct = onOpenProduct,
                    onRemoveProduct = onRemoveProduct,
                    countInCart = getCartCount(p.id)
                ) // Each item calls ProductCard(...) to display that product.
            }
        }
    }
}

@Composable
private fun ProductCard(
    p: Product,
    onAddToCart: (Product) -> Unit,
    onOpenProduct: (String) -> Unit,
    onRemoveProduct: (Product) -> Unit,
    countInCart: Int
) {
    val imageRes = when (p.category) {
        "Electronics" -> R.drawable.electronics
        "Books"       -> R.drawable.books
        "Home"        -> R.drawable.home
        "Toys"        -> R.drawable.toys
        else          -> R.drawable.electronics
    }
    Card(Modifier.padding(12.dp).clickable { onOpenProduct(p.id) }) {
        Row(Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = p.title,
                modifier = Modifier.size(84.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(p.title, style = MaterialTheme.typography.titleMedium)
                Text("$${"%.2f".format(p.price)} • ${p.category}")
                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = { onAddToCart(p) }) {
                        Text("Add")
                    }

                    Spacer(Modifier.width(8.dp))

                    OutlinedButton(onClick = { onRemoveProduct(p) }) {
                        Text("Remove")
                    }

                    Spacer(Modifier.width(12.dp))

                    Text(
                        text = "In cart: $countInCart",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
