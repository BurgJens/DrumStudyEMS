package model

import com.google.gson.Gson
import java.io.File
import java.io.FileWriter
import java.io.IOException

data class ConfigData(
    val subjectName : String = "Subject_01",
    val maxIntensity : Int = 10,
    val version : Int = 3,
)
class ConfigManager {

    val folderName = "ConfigData"
    val fileName = "ConfigData.json"

    val gson = Gson()

    var configData : ConfigData
    init{
        try {
            val configDataJSON = File("$folderName/$fileName")

            if (!configDataJSON.exists()){
                println("CONFIG EXISTIERT NICHT")
                val folder = File(folderName)

                if (!folder.exists()) {
                    folder.mkdirs()
                }
                val jsonString = gson.toJson(ConfigData())
                File("$folderName/$fileName").writeText(jsonString)
            } else{
                println("CONFIG EXISTIERT")
                if (convertConfigDataString(configDataJSON.readText()).version != ConfigData().version){
                    println("CONFIG VERSION ANDERST")
                    writeNewConfigData(ConfigData())
                }
            }
        } catch (e: IOException) {

        }

        configData = convertConfigDataString(readConfigData())

    }

    fun convertConfigDataString(string : String) : ConfigData{
        return gson.fromJson(string, ConfigData::class.java)
    }
    fun readConfigData() : String {
        return File("$folderName/$fileName").readText()
    }

    fun writeNewConfigData (new : ConfigData){
        val jsonString = gson.toJson(new)
        val file = File("$folderName/$fileName")
        val writer = FileWriter(file)
        writer.write(jsonString)
        writer.close()
    }

    fun resetConfigData(){
        writeNewConfigData(ConfigData())
    }
}