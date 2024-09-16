package com.donate.simplecalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TitleFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TitleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_title_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)

        // Dummy list of article titles
        val titles = listOf("The BMW S1000RR", "The KAWASAKI ZX-10RR", "The YAMAHA YZF-R1")
        adapter = TitleAdapter(titles) { title ->
            (activity as MainActivity).showArticleContent(title)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return view
    }
}
