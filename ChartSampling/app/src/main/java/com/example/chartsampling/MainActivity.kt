package com.example.chartsampling

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chartsampling.databinding.ActivityMainBinding
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val count = 12

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        setClickEvent()
        setLineChartData()
    }

    private fun setClickEvent() {
        binding.btnReset.setOnClickListener {
            setLineChartData()
        }
        binding.btnBarChart.setOnClickListener {

        }
    }

    private fun setLineChartData() {

        val xvalue = arrayListOf<String>()
        val entries = arrayListOf<Entry>()

        for (i in 0..100) {
            xvalue.add(i.toString())
            entries.add(Entry(Random.nextInt(0, 100).toFloat(), i))
        }

        val dataset = LineDataSet(entries, "line chart")
        dataset.color = Color.GREEN
        dataset.circleRadius = 0f
        // Area Chart(영역)
//        dataset.setDrawFilled(true)
//        dataset.fillColor = Color.GREEN

        // Multi Chart(2개 3개)
//        val dataset2 = LineDataSet(entries, "line chart2")
//        dataset.color = Color.BLUE
//        dataset.circleRadius = 0f
//        val finalDataset = arrayListOf<LineDataSet>(dataset, dataset2)
//        val data = LineData(xvalue, finalDataset)

        val data = LineData(xvalue, dataset)

        binding.chart.data = data
        binding.chart.setBackgroundColor(Color.WHITE)
        binding.chart.animateXY(3000, 3000)
        binding.chart.setVisibleXRangeMaximum(10f)
        binding.chart.moveViewToX(xvalue.size.toFloat())
    }
}