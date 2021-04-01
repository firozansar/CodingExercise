package info.firozansari.codingexercise.testutil

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.mockito.MockitoAnnotations

abstract class UnitTestSetup  {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    open fun initialise() {
        MockitoAnnotations.initMocks(this)
        initialiseClassUnderTest()
    }

    abstract fun initialiseClassUnderTest()

}