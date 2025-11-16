package com.sam.amazonclone

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * AmazonCloneApp.kt
 *
 * This is the entry point of your Android application.
 * It initializes Hilt's dependency injection framework at the app level,
 * so you can inject dependencies (@Inject) anywhere in your app such as
 * Activities, Fragments, ViewModels, or Services.
 */

@HiltAndroidApp
class AmazonCloneApp : Application()
