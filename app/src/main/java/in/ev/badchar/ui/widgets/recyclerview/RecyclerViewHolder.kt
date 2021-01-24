package `in`.ev.badchar.ui.widgets.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(obj: Any?) {
        binding.setVariable(BR.obj, obj)
        binding.executePendingBindings()
    }
}