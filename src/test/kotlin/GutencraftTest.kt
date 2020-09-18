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
}
