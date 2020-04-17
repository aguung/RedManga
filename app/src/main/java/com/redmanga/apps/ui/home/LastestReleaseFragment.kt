package com.redmanga.apps.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.redmanga.apps.data.db.entities.Manga

import com.redmanga.apps.databinding.FragmentLastestReleaseBinding
import com.redmanga.apps.ui.detail.DetailMangaActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 */
class LastestReleaseFragment : Fragment(),KodeinAware {

    override val kodein by kodein()

    private lateinit var mangaViewModel: MangaViewModel
    private val factory: MangaViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentLastestReleaseBinding = FragmentLastestReleaseBinding.inflate(layoutInflater)
        mangaViewModel = ViewModelProvider(this, factory).get(MangaViewModel::class.java)

        val adapter = MangaAdapter(context!!)
        binding.rvManga.layoutManager = LinearLayoutManager(context)
        binding.rvManga.setHasFixedSize(true)
        binding.rvManga.adapter = adapter
        subscribeUi(adapter)
        return binding.root
    }

    private fun subscribeUi(adapter: MangaAdapter) {
        mangaViewModel.pagedListLiveDataLastRealeaseManga.observe(viewLifecycleOwner, Observer { names ->
            if (names != null) adapter.submitList(names)
        })

        adapter.setOnItemListener(object : MangaAdapter.OnItemListener {
            override fun onItemClick(item: Manga) {
                val intent = Intent(activity!!, DetailMangaActivity::class.java)
                intent.putExtra(DetailMangaActivity.EXTRA_DATA, item)
                startActivity(intent)
            }
        })
    }
}
