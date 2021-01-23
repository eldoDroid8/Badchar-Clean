package `in`.ev.data.mappers
import `in`.ev.data.model.CharacterEntity
import `in`.ev.data.model.ErrorEntity
import `in`.ev.domain.model.Character
import `in`.ev.domain.model.Error

fun CharacterEntity.toDomain (type: CharacterEntity): Character {
    return Character(type.appearance,type.betterCallSaulAppearance,type.birthday,type
        .category,type.charId,type.img,type.name,type.nickname,type.occupation,type
        .portrayed,type.status)
}

fun ErrorEntity.toDomain(errorEntity: ErrorEntity): Error {
    return Error(errorEntity.status_code, errorEntity.status_message)
}

