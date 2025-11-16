package com.sam.amazonclone.core.data
import com.sam.amazonclone.core.model.CartItem
import com.sam.amazonclone.core.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/* Example cart:
[
  CartItem(product=Product(id="A1"), qty=2),
  CartItem(product=Product(id="B7"), qty=1),
  CartItem(product=Product(id="A1"), qty=3)
]
*/


// Purpose: Think of CartRepository like a cashier’s cart system
class CartRepository {
    //_items holds a mutable flow of a list of CartItem objects.
    private val _items = MutableStateFlow<List<CartItem>>(emptyList())
    // items exposes the same data as a read-only StateFlow, so the UI can observe changes but cannot modify it directly.
    val items: StateFlow<List<CartItem>> = _items

    // Used when the user adds a new product from the Home or Product screen — for example, clicking “Add to Cart” on a product listing.
    fun add(product: Product) = _items.update { list ->
        val idx = list.indexOfFirst { it.product.id == product.id }
        if (idx == -1) list + CartItem(product, 1)
        else list.toMutableList().also { it[idx] = it[idx].copy(qty = it[idx].qty + 1) }
    }

    // Used when the user increases quantity of something that’s already in the cart, like pressing the “+” button on your Cart screen.
    fun addById(productId: String) = _items.update { list ->
        val idx = list.indexOfFirst { it.product.id == productId }
        if (idx == -1) list // nothing to add if product isn’t in cart yet
        else list.toMutableList().also { it[idx] = it[idx].copy(qty = it[idx].qty + 1) }
    }


    // Decrease product quantity
    fun dec(productId: String) = _items.update { list ->
        val idx = list.indexOfFirst { it.product.id == productId }
        if (idx == -1) list
        else {
            val newQty = list[idx].qty - 1
            if (newQty <= 0) list.filterNot { it.product.id == productId }
            else list.toMutableList().also { it[idx] = it[idx].copy(qty = newQty) }
        }
    }

    // Remove a product completely
    fun remove(productId: String) = _items.update { it.filterNot { c -> c.product.id == productId } }

    // Clear the entire cart
    fun clear() = _items.update { emptyList() }

    //Calculate subtotal
    fun subtotal(): Double = _items.value.sumOf { it.product.price * it.qty }
}
