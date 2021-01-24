package `in`.ev.badchar.ui.widgets.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T, E>(private val layoutId: Int) : RecyclerView.Adapter<RecyclerViewHolder>(){
    var  filterdCharacters:  MutableList<T> = arrayListOf()
    val listItems: MutableList<T> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater, viewType, parent, false
        )
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val obj = getObjForPosition(position)
        holder.bind(obj)
    }

    override fun getItemViewType(position: Int): Int {
        return layoutId
    }


    open fun addRecylerViewListData(listData: MutableList<T>) {
        if(listItems.isNotEmpty()) {
            listItems.clear()
        }
        if(filterdCharacters.isNotEmpty()) {
            filterdCharacters.clear()
        }
        listItems.addAll(listData)
        filterdCharacters = listData
        notifyDataSetChanged()
    }

    fun clearItems() {
        if (null != listItems && listItems.size > 0) listItems.clear()
    }

    fun removeSeasonFilter(){
        filterdCharacters = listItems
        notifyDataSetChanged()
    }

    fun updateListBySeasonFilter(listData: MutableList<T>){
        filterdCharacters = listData
        notifyDataSetChanged()
    }

    abstract fun getObjForPosition(position: Int): Any
}


