package com.example.drawerpessionerivas.ui.home

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.drawerpessionerivas.ApiClient
import com.example.drawerpessionerivas.model.ColorOfTheDay
import com.example.drawerpessionerivas.model.NbColors
import com.example.drawerpessionerivas.tempoApi
import com.example.drawerpessionerivas.R
import com.example.drawerpessionerivas.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var blueTextView: TextView
    private lateinit var redTextView: TextView
    private lateinit var whiteTextView: TextView
    private lateinit var temp: TextView
    private lateinit var temp2: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        blueTextView = root.findViewById(R.id.BlueRest)
        redTextView = root.findViewById(R.id.RedRest)
        whiteTextView = root.findViewById(R.id.WhiteRest)
        temp = root.findViewById(R.id.temp)
        temp2 = root.findViewById(R.id.temp2)

        return root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        loadDataRestColors()
        loadDataColorsOfTheDay()
    }
    private fun loadDataRestColors() {
        val apiService = ApiClient.instance.create(tempoApi::class.java)
        val call = let { apiService.getNbColors() }
        call.enqueue(object : Callback<NbColors> {
            override fun onResponse(call: Call<NbColors>, response: Response<NbColors>) {
                if (response.isSuccessful) {
                    Log.d("SUCCESS", response.body().toString())
                    val nbColors = response.body()
                    if (nbColors != null) {
                        blueTextView.text = nbColors.bleu.toString()
                        redTextView.text = nbColors.rouge.toString()
                        whiteTextView.text = nbColors.blanc.toString()
                    }
                }
            }
            override fun onFailure(call: Call<NbColors>, t: Throwable) {
                Log.e("ERROR: ", t.message.toString())
            }
        })
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadDataColorsOfTheDay() {
        val apiService = ApiClient.instance.create(tempoApi::class.java)
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = currentDate.format(formatter)
        val call = let { apiService.getTodayColor(formattedDate) }
        call.enqueue(object : Callback<ColorOfTheDay> {
            override fun onResponse(call: Call<ColorOfTheDay>, response: Response<ColorOfTheDay>) {
                if (response.isSuccessful) {
                    Log.d("SUCCESS", response.body().toString())
                    val colorOfTheDay = response.body()
                    if (colorOfTheDay != null) {
                        temp.text = colorOfTheDay.couleurJourJ
                        temp2.text = colorOfTheDay.couleurJourJ1
                        when (colorOfTheDay.couleurJourJ) {
                            "TEMPO_BLEU" -> {
                                temp.setBackgroundColor(Color.BLUE)
                                temp.text = "BLEU"
                            }
                            "TEMPO_ROUGE" -> {
                                temp.setBackgroundColor(Color.RED)
                                temp.text = "ROUGE"
                            }
                            "TEMPO_BLANC" -> {
                                temp.setBackgroundColor(Color.WHITE)
                                temp.text = "BLANC"
                                temp.setTextColor(Color.BLACK)
                            }
                        }
                        when (colorOfTheDay.couleurJourJ1) {
                            "TEMPO_BLEU" -> {
                                temp2.setBackgroundColor(Color.BLUE)
                                temp2.text = "BLEU"
                            }
                            "TEMPO_ROUGE" -> {
                                temp2.setBackgroundColor(Color.RED)
                                temp2.text = "ROUGE"
                            }
                            "TEMPO_BLANC" -> {
                                temp2.setBackgroundColor(Color.WHITE)
                                temp2.text = "BLANC"
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ColorOfTheDay>, t: Throwable) {
                Log.e("ERROR: ", t.message.toString())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}