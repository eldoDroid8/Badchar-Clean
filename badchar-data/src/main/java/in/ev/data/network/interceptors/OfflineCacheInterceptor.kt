package `in`.ev.data.network.interceptors

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class OfflineCacheInterceptor @Inject constructor(@ApplicationContext val context: Context):
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
       var request = chain.request()
        val header = request.headers["Cacheable"]
        if(null != header) {
            if(header == "true" && hasNetworkConnected()) {
                val cacheControl = CacheControl.Builder().maxStale(1, TimeUnit.HOURS)
                    .build()

                request = request.newBuilder()
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", cacheControl.toString())
                    .build()
            }
        }
        return chain.proceed(request)
    }

    //example for definfing service
   /* @GET(PAYMENT_TYPES)
    @Headers("Cacheable: true")
    fun getPaymentTypesWithHeaders(): Call<MutableList<PaymentType>>*/

    fun hasNetworkConnected(): Boolean {
        var result = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as
                ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nwCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(nwCapabilities) ?: return false
            result = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
        else {
                connectivityManager.run {
                    connectivityManager.activeNetworkInfo?.run {
                       result = when (type) {
                           ConnectivityManager.TYPE_WIFI    ->  true
                           ConnectivityManager.TYPE_MOBILE  ->  true
                           ConnectivityManager.TYPE_ETHERNET    -> true
                           else ->  false
                    }
                }
            }
        }
        return result
    }
}