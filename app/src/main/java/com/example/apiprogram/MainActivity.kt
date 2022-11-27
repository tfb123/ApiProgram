package com.example.apiprogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apiprogram.databinding.ActivityMainBinding
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchCurrencyData().start()
    }

    private fun fetchCurrencyData(): Thread
    {
        return Thread {
            val url = URL("https://v6.exchangerate-api.com/v6/b63d0862c333128cdf156ac0/latest/USD")
            val connection = url.openConnection() as HttpsURLConnection

            if (connection.responseCode == 200)
            {
                val inputSystem = connection.inputStream
                println(inputSystem.toString())
            }
            else
            {
                binding.baseCurrency.text = "Failed Connection"
            }
        }
    }

}