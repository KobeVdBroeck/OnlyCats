package com.examenopdracht.onlycats.mock

import android.content.Context
import com.examenopdracht.onlycats.model.CatViewModel

fun FakeCatViewModel(context: Context) : CatViewModel {
    return CatViewModel(
        FakeRepository(),
        FakeRepository(),
        context
    )
}