import kotlinx.dom.appendElement
import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLTextAreaElement
import kotlinx.browser.document
import kotlinx.browser.window

fun main() {
  val input = document.getElementById("input") as HTMLTextAreaElement
  val run = document.getElementById("print") as HTMLButtonElement
  val clipboard = window.navigator.clipboard
  var result: Element? = null
  run.addEventListener("click", {
    result?.remove()
    val inputText = input.value
    val pages: List<String>
    try {
      pages = pages(inputText)
    } catch (e: UnsupportedCharacterException) {
      val errorMessage = errorMessage(inputText)
      result = document.body!!.appendElement("p") {
        setAttribute("style", "white-space: pre-line; margin-top: 40px; color:red;")
        textContent = errorMessage
      }
      return@addEventListener
    }
    result = document.body!!.appendElement("ol") {
      setAttribute("style", "list-style-type: none;")
      for (i in pages.indices) {
        val page = pages[i]
        appendElement("li") {
          setAttribute("style", "white-space: pre-line; margin-top: 40px; ")
          appendElement("pre") {
            setAttribute("style", "background-color: #C8C8C8; display: inline-block;")
            textContent = page
          }
          appendElement("br") {}
          appendElement("button") {
            textContent = "${i + 1}. Copy"
            addEventListener("click", {
              clipboard.writeText(page)
            })
          }
        }
      }
    }
  })
}
