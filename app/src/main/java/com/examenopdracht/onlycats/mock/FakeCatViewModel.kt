package com.examenopdracht.onlycats.mock

import android.content.Context
import com.examenopdracht.onlycats.model.CatViewModel

val FakeCatViewModel: (Context) -> CatViewModel = {context ->
    CatViewModel(
        FakeRepository(),
        FakeRepository(),
        context
    )
}