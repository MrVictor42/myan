package com.victor.myan.enums

enum class MessagesEnum(val message : String) {

    // Presentation
    FirstPresentationTitle("everything in same place!"),
    FirstPresentationDescription("know the top anime and manga, " +
            "search for them and much more, all in one place!!!"),
    SecondPresentationTitle("every day, one new story!"),
    SecondPresentationDescription("follow the weekly anime, discover new stories and paths!!!"),

    // MessagesUserLogin and Register
    FillAllFields("please, fill all fields"),
    InvalidCredentials("email or password are incorrect!"),
    ErrorLoginUser("error login user!"),
    EmailEmpty("fill the field email!"),
    PasswordEmpty("fill the field password!"),
    PasswordWeak("insert a password with 6 no minimum characters!"),
    RegisterSuccessfully("the user was successfully registered!"),
    ExistsThisAccount("this account already exists!"),

    // WithoutConnection
    WithoutConnection("you haven't connection Wifi/4G in this moment, please able some " +
            "connection and try again"),
    WithoutConnectionError("without connection"),

    // Search
    EmptyQuery("please, insert a name to anime or manga"),
    MinLengthQuery("please, insert a name to anime or manga with more 2 characters"),
    NotFoundQuery("not found this anime, try another please"),

    Undefined("undefined"),
    MissingProducers("not found the producers for this anime"),
    MissingPreview("this anime doesn't have a preview yet"),
    FailureLoad("not was possible load this now, try again later"),
    MissingAuthors("not was found author from this manga"),
    MissingAdaptations("not was found adaptations from this manga"),
    MissingGenres("not found the genres"),
    MissingSpinOff("not found spin-offs from this manga")
}