package com.sam.amazonclone

import com.sam.amazonclone.theme.AmazonCloneTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.*
import com.sam.amazonclone.feature.home.HomeViewModel
import com.sam.amazonclone.feature.home.HomeScreen
import com.sam.amazonclone.feature.cart.CartScreen
import com.sam.amazonclone.feature.cart.CartViewModel
import com.sam.amazonclone.feature.order.OrderSuccessScreen
import com.sam.amazonclone.feature.product.ProductDetailRoute
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.compose.collectAsStateWithLifecycle



/*
1. Sets up Jetpack Compose UI (no XML layouts).
2. Manages navigation between screens (home → cart → back).
3. Connects UI to ViewModels (HomeViewModel, CartViewModel) using Hilt dependency injection.
4. Passes callbacks (lambda functions) between screens to handle user actions like “add to cart,” “go to cart,” “checkout,” etc.
 */

/*
onCreate() is called once when your Activity is first created (like a constructor for the screen).
It’s where you:
- Initialize variables
- Set up the UI (e.g., setContent { ... } for Jetpack Compose or setContentView(R.layout.xyz) for XML)
- Set up navigation, listeners, ViewModels, etc.

* The savedInstanceState: Bundle? parameter:
- It can hold a snapshot of the Activity’s state (like form data, scroll position, etc.) that Android automatically saves if the app is killed and later recreated (e.g., after rotation or process death).
- You can read data from it to restore the previous UI state.
* */

@AndroidEntryPoint // Marks this Activity as a Hilt injection target.-> enables hiltViewModel() to provide HomeViewModel
class MainActivity : ComponentActivity() { // ComponentActivity → Base class for Compose-based activities
    override fun onCreate(savedInstanceState: Bundle?) {
        // Called when Activity is created
        super.onCreate(savedInstanceState) // Run Android's default setup first
        enableEdgeToEdge() // allows UI to draw behind the system bars (modern fullscreen behavior).
        setContent { //starts Jetpack Compose — everything inside is now Compose code
            AmazonCloneTheme { //Wraps the entire UI in a consistent color palette, typography
                val nav = rememberNavController()
                NavHost(navController = nav, startDestination = "home") { // NavHost defines all your screens and routes.
                    composable("home") { // home product list screen
                        val vm: HomeViewModel = hiltViewModel() // Create/inject the ViewModel (via Hilt)
                        val cartVm: CartViewModel = hiltViewModel()
                        val products by vm.products.collectAsStateWithLifecycle()
                        val cartItems by cartVm.items.collectAsStateWithLifecycle()

                        // Purpose: For product X, how many of it are currently in the cart?
                        // remember: store this value unless cart changes
                        // associate: transforms a list of things into a map of key → value pairs.
                        val countsById: Map<String, Int> = remember(cartItems) {
                            cartItems.associate { item ->
                                item.product.id to item.qty
                            }
                        }

                        // So HomeScreen only handles UI; logic (add to cart) stays in the ViewModel.
                        HomeScreen( // Pass the observed data into the UI
                            products = products,
                            onAddToCart = { cartVm.add(it) }, // when user clicks "Add", call cartVm.add(product).
                            onOpenCart = { nav.navigate("cart") }, // when user clicks “Cart,” navigate to cart screen.
                            onOpenProduct = { productId ->
                                nav.navigate("product/$productId")
                            },
                            onRemoveProduct = { product ->
                                cartVm.dec(product.id)
                            },
                            getCartCount = { productId ->
                                countsById[productId] ?: 0  // "?:": “If the map has a count for this product, return that count.
                                                            //        If the map does NOT contain this product, return 0.”
                            }
                        )
                    }
                    composable("cart") { // shopping cart screen
                        val cartVm: CartViewModel = hiltViewModel()
                        val items by cartVm.items.collectAsStateWithLifecycle()
                        CartScreen(
                            items = items,
                            subtotal = cartVm.subtotal(),
                            onInc = { id -> cartVm.addById(id) },
                            onDec = { id -> cartVm.dec(id) },
                            onCheckout = {
                                cartVm.clear()
                                nav.navigate("orderSuccess") {
                                    popUpTo("home") {
                                        inclusive = false
                                    }
                                }
                            },
                            onBack = { nav.popBackStack() }
                        )
                    }

                    composable("product/{productid}") { backStackEntry ->
                        // “Try to extract the productId.
                        // If it exists, continue.
                        // If it doesn't, stop running this screen safely.”
                        val productId =
                            backStackEntry.arguments?.getString("productid")
                                ?: return@composable //“Stop executing this screen's content, but don’t leave the entire function.” ex. If someone accidentally navigates to "product/" without an ID → safely exit.
                        ProductDetailRoute(
                            productId = productId,
                            onBack = { nav.popBackStack() }, // This tells the ProductDetail screen how to go back when user presses “Back”.
                        )
                    }

                    composable("orderSuccess") {
                        OrderSuccessScreen(
                            onContinueShopping = {
                                nav.navigate("home") {
                                    popUpTo("home") { inclusive = false }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

/*
composable() defines a screen (a route) in your navigation graph.
ex. “When the user navigates to the route 'home', run the UI inside this block.”
NavHost is the highway. Each composable("routeName") is one exit on that highway.
NavHost
 ├── composable("home") → HomeScreen
 ├── composable("cart") → CartScreen
 ├── composable("product/{productId}") → ProductDetailRoute
 └── composable("orderSuccess") → OrderSuccessScreen
 */