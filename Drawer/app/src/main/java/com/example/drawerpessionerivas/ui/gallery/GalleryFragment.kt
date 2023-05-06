package com.example.drawerpessionerivas.ui.gallery

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.drawerpessionerivas.ApiClient
import com.example.drawerpessionerivas.model.DateColorResponse
import com.example.drawerpessionerivas.tempoApi
import com.example.drawerpessionerivas.R
import com.example.drawerpessionerivas.databinding.FragmentGalleryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.*

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var gridLayout: GridView
    private lateinit var previousMonthButton: Button
    private lateinit var nextMonthButton: Button
    private lateinit var apiService: tempoApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        gridLayout = root.findViewById(R.id.calendar)
        previousMonthButton = root.findViewById(R.id.prev_month_button)
        nextMonthButton = root.findViewById(R.id.next_month_button)
        apiService = ApiClient.instance.create(tempoApi::class.java)

        val calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.YEAR) - 1

        // Load the dates for the current month
        val currentDate = LocalDate.now()
        loadDates(currentDate.year, currentDate.monthValue)

// Set the click listeners for the previous and next month buttons
        previousMonthButton.setOnClickListener {
            currentDate.minusMonths(1).let {
                loadDates(it.year, it.monthValue)
            }
        }
        nextMonthButton.setOnClickListener {
            currentDate.plusMonths(1).let {
                loadDates(it.year, it.monthValue)
            }
        }
        loadDates(year, month)

        // Set the click listeners for the previous and next month buttons

        // faire une grille
        return root
    }

    private fun loadDates(year: Int, prevYear: Int) {
        // Clear any existing dates
        gridLayout.removeViews(1, gridLayout.childCount - 1)


        // Get the dates for the specified month from the API
        val apiService = ApiClient.instance.create(tempoApi::class.java)
        val call = apiService.getDates(year,prevYear)
        val month = 1
        call.enqueue(object : Callback<DateColorResponse> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<DateColorResponse>,
                response: Response<DateColorResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.dates?.forEach { dateColor ->
                        //[DateColor(date=2022-5-9, color=TEMPO_BLEU), DateColor(date=2022-5-10, color=TEMPO_BLEU) ...
                        // separate date and set place in gridView and set for each day the background color
                        val date = dateColor.date?.split("-")
                        val day = date?.get(2)?.toInt()
                        val month = date?.get(1)?.toInt()
                        val year = date?.get(0)?.toInt()
                        val color = dateColor.color
                        // set the background color
                        // for each day set text in gridView and set for each day the background color
                        val textView = TextView(context)
                        textView.text = day.toString()
                        textView.gravity = Gravity.CENTER
                        textView.textSize = 20f
                        textView.setTextColor(Color.BLACK)
                        textView.setBackgroundColor(Color.parseColor(color))
                        val params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                        )
                        params.setMargins(5, 5, 5, 5)
                        textView.layoutParams = params
                        gridLayout.addView(textView)

                    }
                }
            }
            override fun onFailure(call: Call<DateColorResponse>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}