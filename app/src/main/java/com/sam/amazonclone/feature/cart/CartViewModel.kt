package com.sam.amazonclone.feature.cart
import androidx.lifecycle.ViewModel
import com.sam.amazonclone.core.data.CartRepository
import com.sam.amazonclone.core.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

// A ViewModel acts as the bridge between UI (Jetpack Compose screens) and the data layer (repositories).
// UI (CartScreen) → talks to → CartViewModel
// @HiltViewModel: tells Hilt this ViewModel can have dependencies automatically injected.
// @Inject constructor(...): Hilt will automatically provide an instance of CartRepository (defined in your AppModule)
@HiltViewModel
class CartViewModel @Inject constructor(
    private val cart: CartRepository
) : ViewModel() {
    // Because cart.items is a StateFlow, your UI (like CartScreen) can collect it to automatically refresh when items change.
    val items = cart.items
    fun add(p: Product) = cart.add(p)
    fun addById(id: String) = cart.addById(id)
    fun dec(id: String) = cart.dec(id)
    fun remove(id: String) = cart.remove(id)
    fun subtotal(): Double = cart.subtotal()

    fun clear() = cart.clear()
    /*
      This design ensures:
      The UI never touches the repository directly.
      All cart operations go through the ViewModel, keeping logic centralized.
    */

}
