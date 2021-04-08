package info.firozansari.codingexercise.testutil

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import info.firozansari.codingexercise.BaseMockParser
import org.junit.Rule
import org.mockito.MockitoAnnotations

open class UnitTestSetup : BaseMockParser() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    fun initialise() {
        MockitoAnnotations.initMocks(this)
    }

}