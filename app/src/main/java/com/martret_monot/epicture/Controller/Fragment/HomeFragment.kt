package com.martret_monot.epicture.Controller.Fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.SearchView;
import android.view.*
import android.view.inputmethod.InputMethodManager
import com.martret_monot.epicture.Client.ImgurClient
import com.martret_monot.epicture.Models.Content
import com.martret_monot.epicture.Models.ImgurResponse
import com.martret_monot.epicture.Preference
import com.martret_monot.epicture.R
import retrofit2.Call
import retrofit2.Callback


class HomeFragment : Fragment() {

    var rvMain: RecyclerView? = null
    var imageList: List<Content>? = null
    var searchBar: SearchView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val preference = Preference(this.requireContext().applicationContext)

        val section: String = preference.getRequestSection()
        val sort: String = preference.getRequestSort()

        fetchTrendImgur(section, sort)

        val accessToken = preference.getAccessToken()
        val refreshToken = preference.getRefreshToken()
        val accountUsername = preference.getAccountUsername()

        rvMain = view.findViewById(R.id.recyclerView_main) as RecyclerView
        rvMain?.layoutManager = LinearLayoutManager(this.requireContext())
        searchBar = view.findViewById(R.id.searchBar)

        setHasOptionsMenu(true)

        searchBar?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query == null || query.isEmpty())
                    fetchTrendImgur(section, sort)
                else
                    fetchSearchImgur(sort, query)

                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                return true
            }
        })
        println("Access == " + accessToken + " " + refreshToken + " " + accountUsername)

        return view
    }

    /// https://api.imgur.com/3/gallery/user/rising/0.json
    fun fetchTrendImgur(section: String, sort: String) {

        val client = ImgurClient.create()
        val call = client.getTrendImages(section, sort,"Bearer " + Preference(this.requireContext()).getAccessToken(), "Epicture")

        call.enqueue(object: Callback<ImgurResponse> {
            override fun onResponse(call: Call<ImgurResponse>, response: retrofit2.Response<ImgurResponse>) {
                val body = response.body()

                imageList = body?.data
                rvMain?.adapter = MainAdapter(imageList, requireContext())

                println("REQUEST " + body)
            }

            override fun onFailure(call: Call<ImgurResponse>, t: Throwable) {
                println("REQUEST FAILED")
                println(t.message)
            }
        })

    }

    fun fetchSearchImgur(sort: String, search: String) {

        val client = ImgurClient.create()
        val call = client.getSearch(sort ,search, "Bearer " + Preference(this.requireContext()).getAccessToken(), "Epicture")

        call.enqueue(object: Callback<ImgurResponse> {
            override fun onResponse(
                call: Call<ImgurResponse>, response: retrofit2.Response<ImgurResponse>) {
                val body = response.body()

                imageList = body?.data
                rvMain?.adapter = MainAdapter(imageList, requireContext())
                println("REQUEST " + body)
            }

            override fun onFailure(call: Call<ImgurResponse>, t: Throwable) {
                println("REQUEST FAILED")
                println(t.message)
            }
        })
    }

}