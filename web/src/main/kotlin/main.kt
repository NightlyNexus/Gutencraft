import kotlinx.dom.appendElement
import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLTextAreaElement
import kotlinx.browser.document
import kotlinx.browser.window

fun main() {
  val input = document.getElementById("input") as HTMLTextAreaElement
  val printJava = document.getElementById("print_java") as HTMLButtonElement
  val printBedrock = document.getElementById("print_bedrock") as HTMLButtonElement

  var result: Element? = null
  fun printResult(javaEdition: Boolean) {
    result?.remove()
    val inputText = input.value
    val pages: List<String>
    try {
      pages = if (javaEdition) {
        pagesJava(inputText)
      } else {
        pagesBedrock(inputText)
      }
    } catch (e: UnsupportedCharacterException) {
      val errorMessage = errorMessage(inputText)
      result = document.body!!.appendElement("p") {
        setAttribute("style", "white-space: pre-line; margin-top: 40px; color:red;")
        textContent = errorMessage
      }
      return
    }
    result = document.body!!.appendElement("ol") {
      setAttribute("style", "list-style-type: none;")
      val command = command(pages)
      addItem(command, "Copy command")
      for (i in pages.indices) {
        val page = pages[i]
        addItem(page, "${i + 1}. Copy")
      }
    }
  }

  printJava.addEventListener("click", {
    printResult(true)
    printJava.select()
    printBedrock.deselect()
  })
  printBedrock.addEventListener("click", {
    printResult(false)
    printJava.deselect()
    printBedrock.select()
  })
}

private fun Element.addItem(content: String, copyLabel: String) {
  appendElement("li") {
    setAttribute("style", "white-space: pre-line; margin-top: 40px; ")
    appendElement("pre") {
      setAttribute("style", "background-color: #C8C8C8; display: inline-block;")
      textContent = content
    }
    appendElement("br") {}
    appendElement("button") {
      textContent = copyLabel
      addEventListener("click", {
        val clipboard = window.navigator.clipboard
        clipboard.writeText(content)
      })
    }
  }
}

private fun HTMLButtonElement.select() {
  setAttribute("style", "color: #4EB02D; font-weight: bold;")
}

private fun HTMLButtonElement.deselect() {
  setAttribute("style", "")
}
