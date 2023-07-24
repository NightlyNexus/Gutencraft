import kotlin.test.Test
import kotlin.test.assertEquals

class CommandTest {
  @Test fun page() {
    val text = "${"a".repeat(10)} ".repeat(15)
    val result = pages(text)
    assertEquals(listOf("${"a".repeat(10)} ".repeat(14).trimEnd(), "a".repeat(10)), result)
    val command = command(result)
    assertEquals(
      """/give @p written_book{pages:['{"text":"aaaaaaaaaa aaaaaaaaaa aaaaaaaaaa aaaaaaaaaa aaaaaaaaaa aaaaaaaaaa aaaaaaaaaa aaaaaaaaaa aaaaaaaaaa aaaaaaaaaa aaaaaaaaaa aaaaaaaaaa aaaaaaaaaa aaaaaaaaaa"}','{"text":"aaaaaaaaaa"}'],title:"<Insert book name>",author:"<Insert author>"}""",
      command
    )
  }

  @Test fun escapes() {
    val pages = pages("begin... \\\nf\" ' ...end")
    assertEquals(
      """/give @p written_book{pages:['{"text":"begin... \\\\\\nf\\" ' ...end"}'],title:"<Insert book name>",author:"<Insert author>"}""".trimMargin(),
      command(pages)
    )
  }
}
