package com.victor.myan.enums

enum class MessagesEnum(val message : String) {
    Undefined("undefined"),
    MissingProducers("not found the producers for this anime"),
    MissingPreview("this anime doesn't have a preview yet"),
    FailureLoad("not was possible load this now, try again later"),
    MissingAuthors("not was found author from this manga"),
    MissingAdaptations("not was found adaptations from this manga"),
    MissingGenres("not found the genres"),
    MissingSpinOff("not found spin-offs from this manga")
}