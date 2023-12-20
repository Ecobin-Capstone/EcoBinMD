package com.dicoding.ecobin.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ecobin.data.response.DataPickup
import com.dicoding.ecobin.data.response.WastePickupResponse
import com.dicoding.ecobin.data.retrofit.ApiConfig
import com.dicoding.ecobin.databinding.FragmentHistoryBinding
import com.dicoding.ecobin.ui.adapter.ListHistoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    private var id: String? = null
    var position: Int = 0
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                          savedInstanceState: Bundle?): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvHistory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvHistory.addItemDecoration(itemDecoration)
        return binding.root
    }

    private fun findPending() {
        showLoading(true)
        val client = ApiConfig.getApiService().getPending(id!!)
        client.enqueue(object : Callback<WastePickupResponse> {
            override fun onResponse(
                call: Call<WastePickupResponse>,
                response: Response<WastePickupResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        val dataPickups = it.data
                        setReviewData(dataPickups)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<WastePickupResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun findAccept() {
        showLoading(true)
        val client = ApiConfig.getApiService().getAccept(id!!)
        client.enqueue(object : Callback<WastePickupResponse> {
            override fun onResponse(
                call: Call<WastePickupResponse>,
                response: Response<WastePickupResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        val dataPickups = it.data
                        setReviewData(dataPickups)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<WastePickupResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun findDecline() {
        showLoading(true)
        val client = ApiConfig.getApiService().getDecline(id!!)
        client.enqueue(object : Callback<WastePickupResponse> {
            override fun onResponse(
                call: Call<WastePickupResponse>,
                response: Response<WastePickupResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        val dataPickups = it.data
                        setReviewData(dataPickups)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<WastePickupResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setReviewData(order: List<DataPickup?>?) {
        val adapter = ListHistoryAdapter()
        adapter.submitList(order)
        binding.rvHistory.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_SECTION_NUMBER)
            id = it.getString(ARG_ID)
        }
        if (position == 1){
            findPending()
        } else if(position == 2){
            findAccept()
        }else{
            findDecline()
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_ID = "id"
        private const val TAG = "HistoryFragment"
    }
}