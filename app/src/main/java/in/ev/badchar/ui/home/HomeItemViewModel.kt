package `in`.ev.badchar.ui.home

import `in`.ev.domain.model.Character
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HomeItemViewModel(val character: Character,private val itemSelected:
RecyclerviewItemSelected?) {
    val imageUrl: LiveData<String> = MutableLiveData(character.img)
    val label: LiveData<String> = MutableLiveData(character.name)

    fun onCharacterSelected() {
        itemSelected?.invoke(character)
    }

}