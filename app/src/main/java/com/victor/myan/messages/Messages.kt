package com.victor.myan.messages

enum class Messages(val message : String) {

    // PresentationActivity
    FirstPresentationTitle("everything in same place!"),
    FirstPresentationDescription("know the top anime and manga, " +
            "search for them and much more, all in one place!!!"),
    SecondPresentationTitle("every day, one new story!"),
    SecondPresentationDescription("follow the weekly anime, discover new stories and paths!!!"),

    // MessagesUserLogin and Register
    FillAllFields("please, fill all fields"),
    InvalidCredentials("email or password are incorrect!"),
    WithOutConnection("without connection with internet!"),
    ErrorLoginUser("error login user!"),

    Undefined("undefined"),
    MissingProducers("not found the producers for this anime"),
    MissingPreview("this anime doesn't have a preview yet"),
    FailureLoad("not was possible load this now, try again later"),
    MissingAuthors("not was found author from this manga"),
    MissingAdaptations("not was found adaptations from this manga"),
    MissingGenres("not found the genres"),
    MissingSpinOff("not found spin-offs from this manga")
}