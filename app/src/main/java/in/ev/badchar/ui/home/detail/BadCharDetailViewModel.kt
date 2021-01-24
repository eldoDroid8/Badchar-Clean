package `in`.ev.badchar.ui.home.detail

import `in`.ev.domain.model.Character
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BadCharDetailViewModel  @ViewModelInject constructor(
) : ViewModel() {
    var imageUrl: LiveData<String>? = null
    var charName: LiveData<String>? = null
    var occupation: LiveData<String>? = null
    var status: LiveData<String>? = null
    var nickName: LiveData<String>? = null
    var season: LiveData<String>? = null

    fun showCharacterDetails(character: Character){
        imageUrl = MutableLiveData(character.img)
        charName = MutableLiveData(character.name)
        occupation = MutableLiveData(character.occupation?.joinToString { it -> "\'${it}\'" })
        status = MutableLiveData(character.status)
        nickName = MutableLiveData(character.nickname)
        season = MutableLiveData(character.appearance?.joinToString { it -> "\'${it}\'" })
    }

}