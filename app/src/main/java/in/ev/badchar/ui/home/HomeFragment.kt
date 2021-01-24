package `in`.ev.badchar.ui.home

import `in`.ev.badchar.BR
import `in`.ev.badchar.R
import `in`.ev.badchar.databinding.FragmentHomeBinding
import `in`.ev.badchar.ui.ViewState
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.github.guilhe.recyclerpickerdialog.Item
import com.github.guilhe.recyclerpickerdialog.RecyclerPickerDialogFragment
import com.github.guilhe.recyclerpickerdialog.SelectionType
import com.github.guilhe.recyclerpickerdialog.SelectorType
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homeLayoutbinding: FragmentHomeBinding
    private lateinit var filterDialog: RecyclerPickerDialogFragment
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        homeLayoutbinding = FragmentHomeBinding.inflate(inflater, container, false)
        homeLayoutbinding.lifecycleOwner = viewLifecycleOwner
        return homeLayoutbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDataBinding()
        initRecycerView()
        setUpSearch()
        observeData()
    }

    private fun initDataBinding() {
        homeLayoutbinding.setVariable(BR.viewModel, homeViewModel)
        homeLayoutbinding.executePendingBindings()
        homeLayoutbinding.shimmerViewContainer.startShimmer()
    }

    private fun initRecycerView() {
        val mLayoutManager = GridLayoutManager(activity, 2)
        homeLayoutbinding.rvHomeCharacter.layoutManager = mLayoutManager
    }

    private fun observeData() {
        homeViewModel.stateNav.observe(viewLifecycleOwner, {
            when (it) {
                is ViewState.Success -> {
                    val largestLength = it.output.toList()[0].appearance?.size ?: 0
                    val filterdChar = it.output.filter { char ->
                        val size = char.appearance?.size ?: 0
                        largestLength <= size
                    }
                    println("size" + filterdChar[0].appearance?.size)
                    setupFilter(filterdChar[0].appearance?.size ?: 0)
                }
                is ViewState.Failure -> {
                    it.throwable.status_message?.let { msg ->
                        showMsg(msg)
                    }
                }
                else -> {
                    showMsg("Something went wrong")
                }
            }
        })

       homeViewModel.stateHomeEvents.observe(viewLifecycleOwner, {
            when (it) {
                is HomeNavigation.NavigateToDetail -> {
                    val action =
                            HomeFragmentDirections.actionBadCharHomeFragmentToBadCharDetailFragment(
                                    badCharSelected = it.character
                            )
                    findNavController().navigate(action)
                }
                is HomeNavigation.OpenFilterDialog -> {
                    filterDialog.show(childFragmentManager, "Show Filter")
                }
            }
        })
    }

    private fun setupFilter(numberOfSeasons: Int) {
       filterDialog =
                RecyclerPickerDialogFragment.newInstance(
                        SelectionType.MULTIPLE,
                        SelectorType.CHECK_BOX,
                        R.style.DialogB,
                        onItemsPicked = { selected ->
                            val selectedSeason = arrayListOf<Int>()
                            selected.forEach {
                                val seasonNumber = it.text.filter { character -> character.isDigit() }
                                selectedSeason.add(seasonNumber.toInt())
                            }
                            val homeAdapter = getRecyclerAdapter()
                            homeAdapter.filterBySeason(selectedSeason)
                        }
                ).apply {
                    title = "Choose Season To Filter"
                    data = arrayListOf<Item>().also {
                        for (i in 1..numberOfSeasons) {
                            it.add(Item("Season $i"))
                        }
                    }
                    dialogHeight = (350 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                    lifecycleOwner = activity
                    isChoiceMandatory = false
                    resetValuesOnShow = false
                }
    }

    private fun getRecyclerAdapter(): HomeBadCharAdapter {
        return homeLayoutbinding.rvHomeCharacter.adapter as HomeBadCharAdapter
    }

    private fun setUpSearch() {
        homeLayoutbinding.searchCharacter.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val homeAdapter = homeLayoutbinding.rvHomeCharacter.adapter as HomeBadCharAdapter
                homeAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun showMsg(msg: String) {
        val snackbar = Snackbar.make(homeLayoutbinding.containerMain, msg, Snackbar.LENGTH_LONG)
        snackbar.show()
    }
}