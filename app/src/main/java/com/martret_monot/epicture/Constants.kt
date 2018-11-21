package com.martret_monot.epicture


/// https://api.imgur.com/oauth2/authorize?client_id=84ad0adbf8cbc7a&response_type=token&state=APPLICATION_STATE
/// https://api.imgur.com/com/oauth2/authorize?client_id=41b465a704480d9&response_type=token&state=
public class Constants {

    companion object {

        const val CLIENT_ID: String = "41b465a704480d9"
        const val RESPONSE_TYPE: String = "token"
        const val BASE_URL: String = "https://api.imgur.com/"
    }
}