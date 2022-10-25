package com.example.network_test

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.network_test.databinding.FragmentFirstBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.btnNetwork.setOnClickListener {
            val result = getNetworkData();

        }
    }

    fun getNetworkData() {

        val baseUrl: String = "http://192.168.0.135:3000"
        // Retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val networkDataService = retrofit.create(NetworkDataService::class.java)

        val call: Call<ResponseModel> = networkDataService.getNetworkData()
        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) { // Response Success
                val resBody = response.body() as ResponseModel
                binding.textviewFirst.text = resBody.name
                Log.e("retrofit", "result: ${response.body()}");
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {   // Response Fail
                Log.e("retrofit", "실패 : $t")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    data class ResponseModel(val name: String)
}

interface NetworkDataService {

    @GET("/")
//    fun getNetworkData(): Call<FirstFragment.ResponseModel>
    fun getNetworkData(): Call<FirstFragment.ResponseModel>
}