import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

object Loader {
    fun load (filename: String): MutableList<MutableList<Double>>{ //функция для загрузки и прочтения файла
        val res = mutableListOf<MutableList<Double>>() //создаём объект MutableList
        try{
            val br = BufferedReader(InputStreamReader(FileInputStream(filename), "UTF-8"))
            br.readLines().forEach{
                res.add(it.split(";").map{it.toDouble()}.toMutableList())
            }
        }catch(e:Exception){println("Ошибка при чтении из файла: ${e.message}")}
        return res
    }
}