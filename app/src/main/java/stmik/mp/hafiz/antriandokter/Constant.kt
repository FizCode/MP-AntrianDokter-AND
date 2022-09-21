package stmik.mp.hafiz.antriandokter

import androidx.datastore.preferences.core.stringPreferencesKey

object Constant {
    object PrefDatastore {
        const val PREF_NAME = "MediQ"
        val TOKEN = stringPreferencesKey("TOKEN")
    }

    object Named {
        const val BASE_URL = "BASE_URL"
        const val RETROFIT = "RETROFIT"
    }
}