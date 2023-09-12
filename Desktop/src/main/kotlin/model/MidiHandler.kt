import javax.sound.midi.*

class MidiHandler {
    private val sequencer = MidiSystem.getSequencer()
    private val midiIn: MidiDevice?

    init {
        sequencer.open()
        midiIn = findMidiInputDevice()
        midiIn?.open()
    }

    private fun findMidiInputDevice(): MidiDevice? {
        val infos = MidiSystem.getMidiDeviceInfo()
        for (info in infos) {
            if (info.name.contains("Edrum") && info.description.contains("No details available")) {
                return MidiSystem.getMidiDevice(info)
            }
        }
        return null
    }

    fun startListening(callback: (Int) -> Unit) {
        val receiver = object : Receiver {
            override fun send(message: MidiMessage, timeStamp: Long) {
                if (message is ShortMessage && message.command == ShortMessage.NOTE_ON) {
                    val note = message.data1 // MIDI-Notenwert
                    callback(note)
                }
            }
            override fun close() {}
        }

        midiIn?.transmitter?.receiver = receiver

        val sequence = Sequence(Sequence.PPQ, 24)
        sequencer.sequence = sequence

        sequencer.start()
    }

    fun stopListening() {
        sequencer.stop()
    }
}