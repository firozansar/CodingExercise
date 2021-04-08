package info.firozansari.codingexercise.testutil

import androidx.test.platform.app.InstrumentationRegistry
import info.firozansari.codingexercise.BaseMockParser

class InstrumentedMockParser : BaseMockParser() {

    override fun getFileAsString(filePath: String): String =
        InstrumentationRegistry.getInstrumentation().context.classLoader
            .getResource(filePath).readText()
}
