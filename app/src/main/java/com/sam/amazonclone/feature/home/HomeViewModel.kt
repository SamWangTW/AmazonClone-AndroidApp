package com.sam.amazonclone.feature.home
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.amazonclone.core.data.ProductRepository
import com.sam.amazonclone.core.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel // tells Hilt (dependency injection framework) that this ViewModel can receive dependencies automatically.
class HomeViewModel @Inject constructor(
    private val repo: ProductRepository
) : ViewModel() {
    // ViewModel() here is calling the parent class’s constructor
    // ViewModel → A predefined class from Android that provides lifecycle-aware behavior.

    // _products → a mutable data stream (StateFlow) holding the list of Product objects.
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    // products → an immutable (read-only) version exposed to the UI.
    val products: StateFlow<List<Product>> = _products

    // Runs as soon as the ViewModel is created.
    // Calls refresh() to load product data immediately.
    init { refresh() }

    // viewModelScope.launch { ... } runs a coroutine tied to the ViewModel lifecycle.
    // Calls repo.getProducts() (likely a suspend function) to fetch products.
    // Updates _products.value, which triggers recomposition in any UI observing products
    fun refresh() = viewModelScope.launch { _products.value = repo.getProducts() }
}

/*
* A ViewModel acts as the middle layer between your app’s UI (View) and your data (Repository, API, Database).
* It makes sure:
- The data survives screen rotations (so you don’t lose it).
- The UI doesn’t have to fetch or process data directly.
- The UI automatically updates when the data changes
* */