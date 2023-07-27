import kotlin.test.Test
import kotlin.test.assertEquals

class GutencraftTest {
  @Test fun page() {
    val text = "${"a".repeat(10)} ".repeat(15)
    val result = pagesJava(text)
    assertEquals(listOf("${"a".repeat(10)} ".repeat(14).trimEnd(), "a".repeat(10)), result)
  }

  @Test fun repeatingNewLines() {
    val text = "a${"\n".repeat(14)}a"
    val result = pagesJava(text)
    assertEquals(listOf("a", "a"), result)
  }

  @Test fun fullLine() {
    var text = "i".repeat(57)
    var result = pagesJava(text)
    assertEquals(listOf("i".repeat(57)), result)

    text = "${"i".repeat(57)} ${"i".repeat(57)} "
    result = pagesJava(text)
    assertEquals(listOf("${"i".repeat(57)} ${"i".repeat(57)}"), result)
  }

  @Test fun lastPageDoesNotAddExtraPageWhenCompletelyFull() {
    val text = ("a".repeat(19) + " ").repeat(13) + "a".repeat(19)
    val result = pagesJava(text)
    assertEquals(listOf(("a".repeat(19) + " ").repeat(13) + "a".repeat(19)), result)
  }

  @Test fun wordLengthGreaterThanLineLength() {
    var text = "a".repeat(20)
    var result = pagesJava(text)
    assertEquals(listOf("a".repeat(20)), result)

    text = "a".repeat(40)
    result = pagesJava(text)
    assertEquals(listOf("a".repeat(40)), result)
  }

  @Test fun wordLengthGreaterThanLineLengthInMiddleOfLine() {
    var text = "a " + "a".repeat(20)
    var result = pagesJava(text)
    assertEquals(listOf("a " + "a".repeat(20)), result)

    text = "a " + "a".repeat(40)
    result = pagesJava(text)
    assertEquals(listOf("a " + "a".repeat(40)), result)
  }

  @Test fun longWordOverflowsPage() {
    var text = "a".repeat(19 * 11 + 10) + " " + "a".repeat(19 * 11 + 10)
    var result = pagesJava(text)
    assertEquals(
      listOf(
        "a".repeat(19 * 11 + 10) + " " + "a".repeat(19 * 2),
        "a".repeat(19 * 9 + 10)
      ), result
    )

    text = "a".repeat(19 * 11 + 10) + " " + "a".repeat(19 * 14 + 19 * 11 + 10)
    result = pagesJava(text)
    assertEquals(
      listOf(
        "a".repeat(19 * 11 + 10) + " " + "a".repeat(19 * 2),
        "a".repeat(19 * 14),
        "a".repeat(19 * 9 + 10)
      ), result
    )
  }

  @Test fun oneWordPage() {
    var text = "a".repeat(19 * 14)
    var result = pagesJava(text)
    assertEquals(listOf("a".repeat(19 * 14)), result)

    text = "a".repeat(19 * 14) + "i"
    result = pagesJava(text)
    assertEquals(listOf("a".repeat(19 * 14), "i"), result)
  }

  @Test fun overflowPageWithNewLine() {
    var text = ("a".repeat(19) + " ").repeat(12) + "b".repeat(18) + " c\nd"
    var result = pagesJava(text)
    assertEquals(listOf(("a".repeat(19) + " ").repeat(12) + "b".repeat(18) + " c", "d"), result)

    text = ("a".repeat(19) + " ").repeat(13) + "b".repeat(19) + "\nc"
    result = pagesJava(text)
    assertEquals(listOf(("a".repeat(19) + " ").repeat(13) + "b".repeat(19), "c"), result)

    text = ("a".repeat(19) + " ").repeat(13) + "b".repeat(18) + "\nc"
    result = pagesJava(text)
    assertEquals(listOf(("a".repeat(19) + " ").repeat(13) + "b".repeat(18), "c"), result)
  }

  @Test fun newLineInMiddleOfPage() {
    var text = ("a".repeat(19) + "\nb")
    var result = pagesJava(text)
    assertEquals(listOf("a".repeat(19) + "\nb"), result)

    text = ("a".repeat(18) + "\nb")
    result = pagesJava(text)
    assertEquals(listOf("a".repeat(18) + "\nb"), result)
  }

  @Test fun pagesDoNotStartWithNewLines() {
    val text = "\na"
    val result = pagesJava(text)
    assertEquals(listOf("a"), result)
  }

  @Test fun newLineAfterWordOverflowsToNewPage() {
    val text = "a\n".repeat(13) + "i".repeat(56) + " b\nc" + "\na".repeat(14)
    val result = pagesJava(text)
    assertEquals(
      listOf("a\n".repeat(13) + "i".repeat(56), "b\nc" + "\na".repeat(12), "a\na"),
      result
    )
  }

  @Test fun noBlankPages() {
    var text = " "
    var result = pagesJava(text)
    assertEquals(listOf(), result)

    text = " ".repeat(100)
    result = pagesJava(text)
    assertEquals(listOf(), result)

    text = " ".repeat(100) + "a"
    result = pagesJava(text)
    assertEquals(listOf(" ".repeat(100) + "a"), result)

    text = " ".repeat(407) + "a"
    result = pagesJava(text)
    assertEquals(listOf(" a"), result)

    text =
      "a".repeat(100) + "\n".repeat(100) + "b".repeat(1) + " ".repeat(1000) + "c".repeat(1) + " ".repeat(
        1000
      ) + "\n".repeat(
        100
      )
    result = pagesJava(text)
    assertEquals(listOf("a".repeat(100), "b", " ".repeat(189) + "c"), result)
  }
}
