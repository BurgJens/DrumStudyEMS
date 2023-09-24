package model

import com.fazecast.jSerialComm.SerialPort
import emsPort
import kotlinx.coroutines.*
import java.io.InputStream
import java.io.OutputStream

class EmsHandler(timer: Timer) {

    val availablePorts = SerialPort.getCommPorts()
    private val comPort = availablePorts.firstOrNull { it.systemPortName == emsPort }

    lateinit var toEMSstream : OutputStream
    lateinit var fromEMSstream : InputStream

    init {
        if (comPort != null) {
            comPort.openPort()
            comPort.setBaudRate(115200)

            toEMSstream = comPort.outputStream
            fromEMSstream = comPort.inputStream

            CoroutineScope(Dispatchers.Default).launch {
                while (true) {
                    delay(100)
                    if (fromEMSstream.available() > 0) {
                        val buffer = ByteArray(fromEMSstream.available())
                        val bytesRead = fromEMSstream.read(buffer)
                        if (bytesRead > 0) {
                            val receivedMessage = String(buffer, 0, bytesRead)
                            println("$receivedMessage")
                        }
                    }
                }
            }
        }
    }

//    private val activeRythmFlow = timer.getTimeDataFlow().onEach { timeData ->
//    }

    fun sendCommand(cmd : String){
        toEMSstream.write(cmd.toByteArray())
    }


}