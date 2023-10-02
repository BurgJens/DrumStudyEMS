package model

import com.fazecast.jSerialComm.SerialPort
import emsPort
import kotlinx.coroutines.*
import java.io.InputStream
import java.io.OutputStream
import java.time.Duration

class EmsHandler(timer: Timer) {

    val availablePorts = SerialPort.getCommPorts()
    private val comPort = availablePorts.firstOrNull { it.systemPortName == emsPort }

    lateinit var toEMSstream : OutputStream
    lateinit var fromEMSstream : InputStream

    init {
        if (comPort != null) {
            comPort.openPort()
            comPort.setComPortParameters(115200, 8,1,0)
            comPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED)
            comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0)

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

    fun sendCommandString(cmd : String){
        toEMSstream.write(cmd.toByteArray())
        toEMSstream.flush()
    }

    fun sendCommandValues(side : LeftRight, intensityMultiplier : Int, duration: Int){
        val channelToInt = when(side){
            LeftRight.RIGHT -> 0
            LeftRight.LEFT -> 1
        }
        val cropintensityMultiplier = if (intensityMultiplier >= 100) {
            100
        } else if (intensityMultiplier <= 0){
            0
        } else {
            intensityMultiplier
        }
        sendCommandString("C${channelToInt}I${cropintensityMultiplier}T${duration}G;\n")
    }


}