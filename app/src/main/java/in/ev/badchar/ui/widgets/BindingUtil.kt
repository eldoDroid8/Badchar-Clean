package `in`.ev.badchar.ui.widgets

import `in`.ev.badchar.ui.home.HomeBadCharAdapter
import `in`.ev.badchar.ui.home.RecyclerviewItemSelected
import `in`.ev.domain.model.Character
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


@BindingAdapter("homeAdapter","itemClickListener", requireAll = false)
fun addDataToCharacterList(
    recyclerView: RecyclerView,
    homeData: MutableList<Character>?,
    clickListener: RecyclerviewItemSelected
) {
    try {
        homeData?.let {
            val adapter =
                    HomeBadCharAdapter()
            recyclerView.adapter = adapter
            adapter.setItemClickListener(clickListener)
            adapter.addRecylerViewListData(it)
        }
    } catch (e: ClassCastException) {
        e.printStackTrace()
    }
}

@BindingAdapter("imageUrl", "placeholderData", requireAll = false)
fun setImageUrl(imageView: ImageView, url: String?, placeholderIcon: Drawable) {
    val context = imageView.context
    Glide.with(context).load(url)
        .centerInside()
        .placeholder(placeholderIcon)
        .into(imageView)
}

