package com.example.apiprogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apiprogram.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.InputStreamReader
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
                var inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                var request = Gson().fromJson(inputStreamReader, Request::class.java)
                updateUI(request)
                inputStreamReader.close()
                inputSystem.close()
            }
            else
            {
                binding.baseCurrency.text = "Failed Connection"
            }
        }
    }

    private fun updateUI(request: Request)
    {
        runOnUiThread{
            kotlin.run{
                binding.lastUpdated.text = request.time_last_update_utc
                binding.aud.text = String.format("AUD: %.2f", request.rates.AUD)
                binding.gbp.text = String.format("GBP: %.2f", request.rates.GBP)
                binding.nzd.text = String.format("NZD: %.2f", request.rates.NZD)
            }
        }
    }

}