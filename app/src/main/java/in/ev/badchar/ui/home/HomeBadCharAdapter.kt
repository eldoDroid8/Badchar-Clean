package `in`.ev.badchar.ui.home

import `in`.ev.badchar.R
import `in`.ev.badchar.ui.widgets.recyclerview.BaseRecyclerAdapter
import `in`.ev.badchar.utlis.filterCharacterBySeason
import `in`.ev.badchar.utlis.searchThroughArray
import `in`.ev.domain.model.Character
import android.widget.Filter
import android.widget.Filterable

class HomeBadCharAdapter () : BaseRecyclerAdapter<Character, HomeItemViewModel>(
    layoutId = R.layout.list_item_home
), Filterable {
    var itemSelected: RecyclerviewItemSelected ?= null
    override fun getItemCount(): Int {
        return super.filterdCharacters.size
    }

    override fun getObjForPosition(position: Int): HomeItemViewModel {
        return HomeItemViewModel(super.filterdCharacters.get(position), itemSelected)
    }

    internal  fun setItemClickListener(clickListener: RecyclerviewItemSelected) {
        itemSelected = clickListener
    }

    fun filterBySeason(selectedSeason: MutableList<Int>) {
        if(selectedSeason.isEmpty()) {
            removeSeasonFilter()
        } else {
            val filterCharacters = filterCharacterBySeason(selectedSeason, super.filterdCharacters)
            filterCharacters?.let {
                updateListBySeasonFilter(filterCharacters.toMutableList())
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filterdCharacters = listItems as ArrayList<Character>
                } else {
                    val resultList = searchThroughArray(constraint, listItems)
                    filterdCharacters = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterdCharacters
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterdCharacters = results?.values as ArrayList<Character>
                notifyDataSetChanged()
            }
        }
    }

}

typealias  RecyclerviewItemSelected = (Character) -> Unit