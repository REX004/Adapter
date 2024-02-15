//package com.tttrfge
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.tttrfge.rickmorty.R
//
//class FavoriteFragment : Fragment() {
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var characterAdapter: RickAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
//
//        recyclerView = view.findViewById(R.id.recyclerViewFavorite)
//        characterAdapter = RickAdapter(this)
//
//        val layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.layoutManager = layoutManager
//        recyclerView.adapter = characterAdapter
//
//
//
//        return view
//    }
//}
