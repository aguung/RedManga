package com.redmanga.apps.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.redmanga.apps.databinding.ActivitySplashScreenBinding
import com.redmanga.apps.ui.home.MangaViewModel
import com.redmanga.apps.ui.home.MangaViewModelFactory
import com.redmanga.apps.utils.Coroutines
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SplashScreenActivity : AppCompatActivity(),KodeinAware {

    override val kodein by kodein()

    private lateinit var mangaViewModel: MangaViewModel
    private val factory: MangaViewModelFactory by instance()

    private val time: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySplashScreenBinding =
            ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mangaViewModel = ViewModelProvider(this, factory).get(MangaViewModel::class.java)
        getManga()

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, time)
    }


    private fun getManga() = Coroutines.main {
        mangaViewModel.manga.await()
    }
}
