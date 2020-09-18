import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class GutencraftTest {
  @Test fun page() {
    val text = "${"a".repeat(10)} ".repeat(15)
    val result = pages(text)
    assertEquals(listOf("${"a".repeat(10)} ".repeat(14).trimEnd(), "a".repeat(10)), result)
  }

  @Test fun repeatingNewLines() {
    val text = "a${"\n".repeat(14)}a"
    val result = pages(text)
    assertEquals(listOf("a", "a"), result)
  }

  @Test fun lastPageDoesNotAddExtraPageWhenCompletelyFull() {
    val text = ("a".repeat(19) + " ").repeat(13) + "a".repeat(19)
    val result = pages(text)
    assertEquals(listOf(("a".repeat(19) + " ").repeat(13) + "a".repeat(19)), result)
  }

  @Ignore @Test fun wordLengthGreaterThanLineLength() {
    var text = "a".repeat(20)
    var result = pages(text)
    assertEquals(listOf("a".repeat(20)), result)
    text = "a".repeat(40)
    result = pages(text)
    assertEquals(listOf("a".repeat(40)), result)
  }

  @Ignore @Test fun wordLengthGreaterThanLineLengthInMiddleOfLine() {
    var text = "a " + "a".repeat(20)
    var result = pages(text)
    assertEquals(listOf("a " + "a".repeat(20)), result)
    text = "a " + "a".repeat(40)
    result = pages(text)
    assertEquals(listOf("a " + "a".repeat(40)), result)
  }

  @Ignore @Test fun longWordOverflowsPage() {
    val text = "a".repeat(19 * 11 + 10) + " " + "a".repeat(19 * 11 + 10)
    val result = pages(text)
    assertEquals(listOf(
        "a".repeat(19 * 11 + 10) + " " + "a".repeat(19 * 2),
        "a".repeat(19 * 9 + 10)
    ), result)
  }

  @Ignore @Test fun oneWordPage() {
    var text = "a".repeat(19 * 14)
    var result = pages(text)
    assertEquals(listOf("a".repeat(19 * 14)), result)
    text = "a".repeat(19 * 14) + "i"
    result = pages(text)
    assertEquals(listOf("a".repeat(19 * 14), "i"), result)
  }
}
