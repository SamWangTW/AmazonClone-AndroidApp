// File: feature/product/ProductDetailViewModel.kt
package com.sam.amazonclone.feature.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.amazonclone.core.data.ProductRepository
import com.sam.amazonclone.core.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/* Purpose:
- Manage data for the Product Detail screen.
- Fetch a product by its ID (from repository).
- Expose loading, success, and error states to the UI.
*/

// This defines UI state for the product detail screen
data class ProductDetailUiState(
    // true -> The product is still being loaded → show a spinner or progress indicator
    // false -> Loading is finished (either success or error)
    val loading: Boolean = false,
    // Product? means this variable can be either a Product object or null.
    // ? in Kotlin means nullable — it’s allowed to hold null.
    val product: Product? = null,
    // String? means it can hold: a String (error message text), or null (no error).
    val error: String? = null
)

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repo: ProductRepository
) : ViewModel() {

    /*
    StateFlow is a special data holder that:
    - Always has a current value, and
    - Can be observed (or “collected”) by other parts of your app, such as your UI.
    // _uiState: Private, mutable flow inside the ViewModel
    // uiState: Public, read-only flow exposed to the UI.
    */

    private val _uiState = MutableStateFlow(ProductDetailUiState(loading = true))
    val uiState: StateFlow<ProductDetailUiState> = _uiState

    // main entry point the UI (your ProductDetailScreen) calls when it needs to load product details.
    // It starts a background job that finds a product by ID and updates the UI state (loading → success/error)
    fun load(productId: String) {
        viewModelScope.launch { // Runs in a coroutine (viewModelScope.launch) to avoid blocking the main thread.
            // Updates the state to show a loading spinner or indicator in the UI.
            _uiState.value = ProductDetailUiState(loading = true)

            //Try this risky operation — but if something goes wrong, catch it and decide what to do instead of crashing
            try {
                val found = findById(productId)
                _uiState.value = if (found != null) {
                    ProductDetailUiState(product = found)
                } else {
                    ProductDetailUiState(error = "Product not found")
                }
            } catch (t: Throwable) {
                _uiState.value = ProductDetailUiState(error = t.message ?: "Unknown error")
            }
        }
    }

    // suspend: This means the function can pause and resume — it’s used with coroutines.
    // In other words, it can safely do slow tasks (like API calls or reading a database) without blocking the main thread.
    // It looks for a product with a specific ID by fetching products page by page from the repository.
    private suspend fun findById(id: String, pageSize: Int = 50, maxPages: Int = 20): Product? {
        var page = 1 //Start with page 1
        repeat(maxPages) {
            val pageItems = repo.getProducts(page = page, pageSize = pageSize)
            if (pageItems.isEmpty()) return null // it means there are no more pages left.
            val hit = pageItems.firstOrNull { it.id == id } //This line scans the current page and looks for a product whose id matches the one we’re searching for.
            if (hit != null) return hit
            page++
        }
        return null
    }
}
