package com.victor.myan.baseFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.R
import com.victor.myan.databinding.FragmentBaseGenreDetailBinding
import com.victor.myan.fragments.HomeFragment
import com.victor.myan.viewpager.GenreDetailViewPager
import com.victor.myan.viewpager.TypeViewPager

class BaseGenreDetailFragment : Fragment() {

    private lateinit var binding : FragmentBaseGenreDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseGenreDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getInt("mal_id")!!
        val name = arguments?.getString("name")
        val toolbarTitle = binding.toolbar.toolbar
        val tabLayoutType = binding.tabLayoutType
        val viewPagerType = binding.viewPagerType
        val sizePagerType = 2
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager
        val sizePager = 4
        val adapterType = TypeViewPager(parentFragmentManager, lifecycle, sizePagerType)
        val adapter = GenreDetailViewPager(parentFragmentManager, lifecycle, malID, sizePager, "Anime")

        viewPagerType.adapter = adapterType
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayoutType, viewPagerType){tab, position ->
            when(position) {
                0 -> tab.text = "Anime"
                1 -> tab.text = "Manga"
            }
        }.attach()

        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            when(position) {
                0 -> tab.text = "Airing"
                1 -> tab.text = "Complete"
                2 -> tab.text = "Score"
                3 -> tab.text = "Upcoming"
            }
        }.attach()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val homeFragment = HomeFragment.newInstance()
                val fragmentManager = activity?.supportFragmentManager
                fragmentManager?.popBackStack()
                fragmentManager?.beginTransaction()?.replace(R.id.fragment_layout, homeFragment)
                    ?.addToBackStack(null)?.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        toolbarTitle.title = name
    }

    /*
    val genreID = arguments?.getInt("mal_id")
        val name = arguments?.getString("name")
        val api = JikanApiInstance.getJikanApiInstance().create(CategoryApi::class.java)
        val toolbar = binding.toolbar
        val highestScoreNull = binding.highestScoreNull
        val upcomingNull = binding.upcomingNull
        val currentlyAiringNull = binding.currentlyAiringNull
        val completedNull = binding.completedNull
        var recyclerView : RecyclerView

        toolbar.toolbar.title = name
        toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        toolbar.toolbar.setNavigationOnClickListener {
            val categoriesListFragment = GenreFragment()
            (view.context as FragmentActivity)
                .supportFragmentManager
                .beginTransaction()
                .remove(this)
                .replace(R.id.content, categoriesListFragment)
                .addToBackStack(null)
                .commit()
        }

        val categories : List<CategoriesEnum> =
            listOf(
                CategoriesEnum.HighestScore, CategoriesEnum.Airing,
                CategoriesEnum.Completed, CategoriesEnum.Upcoming
            )

        categories.forEach {
            when(it) {
                CategoriesEnum.HighestScore -> {
                    recyclerView = binding.recyclerViewByScore
                    buildRecyclerView(
                        recyclerView,
                        api.categoryByScore(genreID!!, CategoriesEnum.Score, CategoriesEnum.Tv),
                        highestScoreNull
                    )
                }
                CategoriesEnum.Airing -> {
                    recyclerView = binding.recyclerViewByAiring
                    buildRecyclerView(
                        recyclerView,
                        api.categoryByAiring(
                            genreID!!, CategoriesEnum.Airing,
                            CategoriesEnum.Score, CategoriesEnum.Tv
                        ),
                        currentlyAiringNull
                    )
                }
                CategoriesEnum.Completed -> {
                    recyclerView = binding.recyclerViewByCompleted
                    buildRecyclerView(
                        recyclerView,
                        api.categoryByCompleted(
                            genreID!!, CategoriesEnum.Completed,
                            CategoriesEnum.Score, CategoriesEnum.Tv
                        ),
                        completedNull
                    )
                }
                CategoriesEnum.Upcoming -> {
                    recyclerView = binding.recyclerViewByUpcoming
                    buildRecyclerView(
                        recyclerView,
                        api.categoryByUpcoming(
                            genreID!!, CategoriesEnum.Upcoming, CategoriesEnum.Tv),
                        upcomingNull
                    )
                }
                else -> {

                }
            }
        }
    }

    private fun buildRecyclerView(
        recyclerView : RecyclerView,
        request : Call<JsonObject>,
        resultNull : TextView
    ) {
        /*
        val animeList = arrayListOf<Anime>()
        recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val animeAdapter = AnimeAdapter(animeList)
        recyclerView.adapter = animeAdapter

        request.enqueue(object :
            Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (response.isSuccessful) {
                    val animeResponse = response.body()
                    animeAdapter.anime.clear()
                    if (animeResponse != null) {
                        val results: JsonArray? =
                            animeResponse.getAsJsonArray("results")
                        if (results != null && results.size() > 0) {
                            for (result in 0 until results.size()) {
                                val animeFound: JsonObject? =
                                    results.get(result) as JsonObject?
                                if (animeFound != null) {
                                    val anime = Anime()

                                    anime.title = animeFound.get("title").asString
                                    anime.mal_id =
                                        animeFound.get("mal_id").asInt.toString()
                                    anime.episodes = animeFound.get("episodes").asInt
                                    anime.image_url =
                                        animeFound.get("image_url").asString
                                    anime.score = animeFound.get("score").asDouble

                                    if(animeFound.get("start_date").toString() == "null") {
                                        anime.airing_start = ""
                                    } else {
                                        anime.airing_start = animeFound.get("start_date").asString
                                    }

                                    animeAdapter.anime.add(anime)
                                }
                            }
                            animeAdapter.notifyDataSetChanged()
                        } else {
                            resultNull.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })

         */
    }
     */
}