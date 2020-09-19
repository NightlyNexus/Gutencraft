import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextArea
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage

fun main() {
  Application.launch(GutencraftApplication::class.java)
}

class GutencraftApplication : Application() {
  override fun start(primaryStage: Stage) {
    primaryStage.title = "Gutencraft"
    val scrollPane = ScrollPane()
    val layout = VBox(10.0)
    scrollPane.content = layout
    val input = TextArea()
    val print = Button("Print")
    print.setOnAction {
      layout.children.remove(2, layout.children.size)
      val pages = pages(input.text)
      for (i in pages.indices) {
        val page = pages[i]
        layout.children.add(Label(page).apply {
          background =
              Background(
                  BackgroundFill(
                      Color.rgb(200, 200, 200),
                      CornerRadii(0.0),
                      Insets(0.0)
                  )
              )
        })
        layout.children.add(Button("${i + 1}. Copy").apply {
          setOnAction {
            val clipboard = Clipboard.getSystemClipboard()
            val content = ClipboardContent()
            content.putString(page)
            clipboard.setContent(content)
          }
        })
      }
    }
    layout.children.add(input)
    layout.children.add(print)
    primaryStage.scene = Scene(scrollPane, 600.0, 300.0)
    primaryStage.show()
  }
}
