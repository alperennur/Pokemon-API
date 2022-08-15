package com.alperennur.pokemonapi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import com.alperennur.pokemonapi.R
import com.alperennur.pokemonapi.databinding.ActivityMainBinding
import com.alperennur.pokemonapi.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
       animation()
    }
    private fun animation(){

           binding.pokemonLottie.apply {
               setAnimation(R.raw.pokemon)
               animate()
               playAnimation()
               postDelayed({

                   val intent = Intent(this@SplashScreen,MainActivity::class.java)
                   startActivity(intent)
                   overridePendingTransition(R.anim.from_let,R.anim.from_let)
                   finish()

               },3000)


           }

    }

}