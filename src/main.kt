import gui.GraphPainter
import gui.MainWindow

fun main(){
    val data = Loader.load("matrix2.csv")
    val p = GraphPainter(data).apply {
        thickness = 3
    }
    with(MainWindow()){
        painter = p
        isVisible = true
    }
}