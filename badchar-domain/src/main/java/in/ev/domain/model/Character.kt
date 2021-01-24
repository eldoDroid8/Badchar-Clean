package `in`.ev.domain.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val appearance: List<Int>? = null,
    val betterCallSaulAppearance: List<Int>? = null,
    val birthday: String,
    val category: String,
    val charId: Int,
    val img: String,
    val name: String,
    val nickname: String,
    val occupation: List<String>? = null,
    val portrayed: String,
    val status: String
) : Parcelable
