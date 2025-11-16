package com.sam.amazonclone.core.data
import com.sam.amazonclone.core.model.Product


/* interface = a contract or blueprint for data sources.
It says: “any class that implements me must have a getProducts() function.” */
interface ProductRepository {
    //page = which page of results to get (default = 1)
    //pageSize = how many products per page (default = 20)
    suspend fun getProducts(page: Int = 1, pageSize: Int = 20): List<Product>
}
// We’re creating a fake version of the repository that follows the same contract.
// Testing (no need for a real backend)
// Early UI development before APIs exist
class FakeProductRepository : ProductRepository {

    // Categories
    private val categories = listOf("Electronics", "Books", "Home", "Toys")

    // Title lists by category
    private val electronicNames = listOf(
        "Wireless Headphones",
        "Bluetooth Speaker",
        "4K Action Camera",
        "Smartwatch Pro",
        "Gaming Mouse",
        "Portable Power Bank",
        "Noise-Cancelling Headset",
        "USB-C Fast Charger"
    )

    private val bookNames = listOf(
        "Mystery Novel",
        "Self-Help Guide",
        "Fantasy Adventure",
        "Sci-Fi Epic",
        "Historical Biography",
        "Beginner Coding Handbook"
    )

    private val homeNames = listOf(
        "Ceramic Vase",
        "LED Desk Lamp",
        "Stainless Steel Flask",
        "Soft Cotton Blanket",
        "Essential Oil Diffuser",
        "Modern Wall Clock"
    )

    private val toyNames = listOf(
        "Lego Building Set",
        "Mini Drone",
        "Puzzle Cube",
        "Kids Painting Kit",
        "Remote Control Car",
        "STEM Robot Kit"
    )

    // Pick a random title based on category
    private fun randomTitle(category: String): String {
        val list = when (category) {
            "Electronics" -> electronicNames
            "Books" -> bookNames
            "Home" -> homeNames
            "Toys" -> toyNames
            else -> listOf("Unknown Product")
        }
        return list.random()
    }


    // Generate 200 fake products
    private val seed = (1..200).map { index ->
        val category = categories[index % categories.size]
        Product(
            id = index.toString(),
            title = randomTitle(category) + " #$index",
            price = 10.0 + (index % 50),
            rating = 3.0 + (index % 20) / 10.0,
            category = category
        )
    }

    // It takes your 200 hard-coded products (seed) and returns a chunk of them — like a real API would return page 1, page 2, etc.
    override suspend fun getProducts(page: Int, pageSize: Int): List<Product> {
        val from = (page - 1) * pageSize
        val to = (from + pageSize).coerceAtMost(seed.size)
        return if (from < seed.size) seed.subList(from, to) else emptyList()
    }
}
