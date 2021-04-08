package info.firozansari.codingexercise.testutil

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import info.firozansari.codingexercise.BaseMockParser
import org.junit.Rule
import org.mockito.MockitoAnnotations

open class UnitTestSetup : BaseMockParser() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val mockNorthBound = 44.1f
    val mockSouthBound = -9.9f
    val mockEastBound = -22.4f
    val mockWestBound = 55.2f
    val mockUsername = "testuser"

    fun initialise() {
        MockitoAnnotations.initMocks(this)
    }

    override fun getFileAsString(filePath: String): String =
        ClassLoader.getSystemResource(filePath).readText()
}