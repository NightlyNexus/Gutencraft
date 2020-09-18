import kotlinx.dom.appendElement
import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLTextAreaElement
import kotlin.browser.document

fun main() {
  val input = document.getElementById("input") as HTMLTextAreaElement
  val run = document.getElementById("print") as HTMLButtonElement
  var result: Element? = null
  run.addEventListener("click", {
    result?.remove()
    val pages = pages(input.value)
    result = document.body!!.appendElement("ol") {
      for (page in pages) {
        appendElement("li") {
          setAttribute("style", "margin-top: 40px;")
          appendElement("pre") {
            textContent = page
          }
        }
      }
    }
  })
}
