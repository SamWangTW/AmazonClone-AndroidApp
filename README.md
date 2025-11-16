📱 AmazonClone — Android E-Commerce App (Jetpack Compose)

AmazonClone is a modern mobile e-commerce application built with Kotlin, Jetpack Compose, MVVM, and Hilt.
It features product browsing, item detail pages, cart management, and a clean Material3 UI — designed as a portfolio-ready MVP.

🚀 Features
🛒 Core E-Commerce Flow

Product List — Browse paginated products with images, titles, and pricing

Product Detail Screen — View detailed info, rating, and “Add to Cart”

Cart Management — Add, remove, and adjust items with real-time subtotal updates

Order Confirmation — Simple success screen after checkout

📱 UI/UX (Jetpack Compose)

Material3 theming

Multi-screen navigation using Navigation Compose

Responsive layout

Smooth user experience with reduced UI boilerplate

🧠 Architecture

MVVM pattern for clean separation of concerns

StateFlow for reactive UI state updates

Hilt Dependency Injection for scalable and testable components

Repository pattern to abstract data sources

🛠️ Data Layer

Fake repository (FakeProductRepository) providing:

Paginated product loading

Category-based images

Consistent dummy product data

Easily replaceable with real backend API

🧩 Tech Stack

Language:

Kotlin

Android Frameworks:

Jetpack Compose (UI)

Navigation Compose

Material3

ViewModel + StateFlow

Hilt (DI)

Development:

Gradle (KTS)

Coil (image loading)
