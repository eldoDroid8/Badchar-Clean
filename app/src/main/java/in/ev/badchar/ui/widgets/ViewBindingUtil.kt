package `in`.ev.badchar.ui.widgets
import android.view.View
import androidx.databinding.BindingAdapter
import com.facebook.shimmer.ShimmerFrameLayout

@BindingAdapter("shimmerVisibility")
fun adjustShimmerVisibility(shimmerFrameLayout: ShimmerFrameLayout, showShimmerAnimation: Boolean) {
    if(showShimmerAnimation) {
       shimmerFrameLayout.startShimmer()
    } else {
        shimmerFrameLayout.stopShimmer()
        shimmerFrameLayout.visibility = View.GONE
    }
}