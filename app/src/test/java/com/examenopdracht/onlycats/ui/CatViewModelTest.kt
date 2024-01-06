package com.examenopdracht.onlycats.ui

import com.examenopdracht.onlycats.data.api.NetworkUiState
import com.examenopdracht.onlycats.data.db.LocalUiState
import com.examenopdracht.onlycats.mock.FakeActivity
import com.examenopdracht.onlycats.mock.FakeRepository
import com.examenopdracht.onlycats.model.CatViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CatViewModelTest {
    private lateinit var viewModel: CatViewModel
    private val amountToFetch = 5

    @Before
    fun setup() {
        var context = Robolectric.buildActivity(FakeActivity::class.java).create().get().applicationContext

        viewModel = CatViewModel(
            apiRepository = FakeRepository(),
            roomRepository = FakeRepository(),
            context = context)
    }

    @Test
    fun catViewModel_Initialized_ApiImagesLoaded() {
        var state: NetworkUiState = viewModel.networkUiState.value

        assertEquals(state::class, NetworkUiState.Success::class)

        // Check if images are loaded in imageProvider
        assertFalse((state as NetworkUiState.Success).imageProvider.selectedPhoto.value.id.isEmpty())
    }

    @Test
    fun catViewModel_Initialized_LocalImagesLoaded() {
        var state: LocalUiState = viewModel.localUiState.value

        assertEquals(state::class, LocalUiState.Success::class)

        assertFalse((state as LocalUiState.Success).photos.first().id.isEmpty())
    }

    @Test
    fun catViewModel_ImageLimitReached_MoreImagesFetched() {
        for(i in 0..amountToFetch - 1)
            ((viewModel.networkUiState.value) as NetworkUiState.Success).imageProvider.getNext()

        var curPhoto = ((viewModel.networkUiState.value) as NetworkUiState.Success).imageProvider.selectedPhoto

        assertTrue(curPhoto.value.dbId == 1)
    }

    @Test
    fun catViewModel_SaveImage_SavesImage() {
        var photo = (viewModel.networkUiState.value as NetworkUiState.Success).imageProvider.selectedPhoto.value

        photo.save()

        assertTrue((viewModel.localUiState.value as LocalUiState.Success).photos.last().id == photo.id)
    }

    @Test
    fun catViewModel_DeleteImage_DeletesImage() {
        var photo = (viewModel.localUiState.value as LocalUiState.Success).photos.first()

        photo.unsave()

        assertFalse((viewModel.localUiState.value as LocalUiState.Success).photos.last().id == photo.id)
    }
}

