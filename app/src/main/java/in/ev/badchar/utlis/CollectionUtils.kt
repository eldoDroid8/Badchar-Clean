package `in`.ev.badchar.utlis

import `in`.ev.domain.model.Character

fun searchCharacter(data: Character, constraint: CharSequence?): Character? {
    var result: Character? = null
    if (data.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
        result =  data
    }
    return result
}

fun searchThroughArray(constraint: CharSequence?, listItems: List<Character>): MutableList<Character> {
    val resultList = ArrayList<Character>()
    for (row in listItems) {
        searchCharacter(row, constraint)?.let {
            resultList.add(it)
        }
    }
    return resultList
}

fun filterCharacterBySeason(selectedSeason: MutableList<Int>, filterdCharacters: List<Character>): MutableList<Character>{
    val filterCharacters = filterdCharacters.filter {
        val seasons = it?.appearance?.size?.let { it1 -> it?.appearance?.take(it1) }
        seasons?.intersect(selectedSeason.asIterable())?.isNotEmpty() ?: false
    }
    return filterCharacters.toMutableList()
}