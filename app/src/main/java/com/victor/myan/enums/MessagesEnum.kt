package com.victor.myan.enums

enum class MessagesEnum(val message : String) {
    Undefined("undefined"),
    MissingProducers("not found the producers for this anime"),
    MissingPreview("this anime doesn't have a preview yet"),
    FailureLoadAnime("not was possible load this anime now, try again later")
}