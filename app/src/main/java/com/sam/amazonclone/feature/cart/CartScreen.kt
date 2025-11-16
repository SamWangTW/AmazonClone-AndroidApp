package com.sam.amazonclone.feature.cart
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sam.amazonclone.core.model.CartItem

/*Goal:
* 1. Display all items in the user’s shopping cart
  2. Let users increase or decrease item quantities
  3. Show the subtotal
  4. Allow checkout or return to the previous page
* */
// The @Composable annotation means this function is a UI component in Jetpack Compose.
// @OptIn(ExperimentalMaterial3Api::class) allows use of experimental Material 3 UI components (like TopAppBar and BottomAppBar).
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    items: List<CartItem>,
    subtotal: Double,
    onInc: (String) -> Unit,
    onDec: (String) -> Unit,
    onCheckout: () -> Unit,
    onBack: () -> Unit
) {
    // Scaffold helps you build a consistent layout
    Scaffold(
        topBar = {
            // topBar: Displays a title and a Back button.
            TopAppBar(
                title = { Text("Your Cart") },
                // When “Back” is clicked, it calls the function onBack(), which is provided from a higher-level screen (like a navigation controller).
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
            )
        },
        bottomBar = {
            // bottomBar: holds checkout info or main actions
            BottomAppBar {
                // Spacer(Modifier.weight(1f)) pushes elements to the right (acts like flexible space).
                Spacer(Modifier.weight(1f))
                Text("Subtotal: $${"%.2f".format(subtotal)}",
                    modifier = Modifier.padding(16.dp))
                // When “Checkout” is pressed, it calls the onCheckout() function.
                Button(onClick = onCheckout, modifier = Modifier.padding(16.dp)) {
                    Text("Checkout")
                }
            }
        }
    ) { padding ->
        // Displays all cart items in a scrollable list.
        LazyColumn(Modifier.padding(padding)) {
            items(items) { item ->
                CartRow(item, onInc, onDec)
            }
        }
    }
}

@Composable
private fun CartRow(item: CartItem, onInc:(String)->Unit, onDec:(String)->Unit) {
    ListItem(
        headlineContent = { Text(item.product.title) },
        supportingContent = { Text("$${"%.2f".format(item.product.price)} x ${item.qty}") },
        trailingContent = {
            Row {
                TextButton(onClick = { onDec(item.product.id) }) { Text("-") }
                TextButton(onClick = { onInc(item.product.id) }) { Text("+") }
            }
        }
    )
    // adds a thin line between rows.
    HorizontalDivider()
}
