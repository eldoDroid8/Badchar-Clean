package `in`.ev.badchar.ui.home

import `in`.ev.badchar.R
import `in`.ev.badchar.ui.widgets.recyclerview.BaseRecyclerAdapter
import `in`.ev.badchar.utlis.filterCharacterBySeason
import `in`.ev.badchar.utlis.searchThroughArray
import `in`.ev.domain.model.Character
import android.widget.Filter
import android.widget.Filterable

class HomeBadCharAdapter : BaseRecyclerAdapter<Character, HomeItemViewModel>(
    layoutId = R.layout.list_item_home
), Filterable {
    private var itemSelected: RecyclerviewItemSelected ?= null
    override fun getItemCount(): Int {
        return super.filterdCharacters.size
    }

    override fun getObjForPosition(position: Int): HomeItemViewModel {
        return HomeItemViewModel(super.filterdCharacters[position], itemSelected)
    }

    internal  fun setItemClickListener(clickListener: RecyclerviewItemSelected) {
        itemSelected = clickListener
    }

    fun filterBySeason(selectedSeason: MutableList<Int>) {
        if(selectedSeason.isEmpty()) {
            removeSeasonFilter()
        } else {
            val filterCharacters: MutableList<Character> = filterCharacterBySeason(selectedSeason, super.filterdCharacters)
            this.updateListBySeasonFilter(filterCharacters.toMutableList())
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filterdCharacters = if (charSearch.isEmpty()) {
                    listItems as ArrayList<Character>
                } else {
                    val resultList = searchThroughArray(constraint, listItems)
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterdCharacters
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterdCharacters = results?.values as MutableList<Character>
                notifyDataSetChanged()
            }
        }
    }

}

typealias  RecyclerviewItemSelected = (Character) -> Unit