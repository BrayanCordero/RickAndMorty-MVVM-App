package com.example.rickmorty_mvvm_app.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rickmorty_mvvm_app.rest.RickAndMortyRepository
import io.mockk.clearAllMocks
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
class RickAndMortyViewModelTest{

    @get:Rule val mainRule = InstantTaskExecutorRule()

    private val testIODispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testIODispatcher)

    private val mockRepository = mockk<RickAndMortyRepository>()

    private lateinit var testObject: RickAndMortyViewModel

    @Before
    fun setUp(){
        Dispatchers.setMain(testIODispatcher)
        testObject = RickAndMortyViewModel(mockRepository,testIODispatcher)
    }

    @After
    fun tearDown(){
    clearAllMocks()
    Dispatchers.resetMain()
    }
}